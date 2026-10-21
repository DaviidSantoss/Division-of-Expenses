package Santos.David.Service;

import Santos.David.Controller.GroupController;
import Santos.David.Exception.*;
import Santos.David.Model.GroupOfMembers;
import Santos.David.Model.Groups;
import Santos.David.Service.Repository.GroupOfMembersRepository;
import Santos.David.Service.Repository.GroupRepository;
import Santos.David.Service.Repository.UserRepository;
import Santos.David.dto.GroupOfMembersDTO;
import Santos.David.dto.GroupsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import static Santos.David.Mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class GroupService {


    @Autowired
    PagedResourcesAssembler<GroupsDTO> assembler;


    private final Logger logger = Logger.getLogger(GroupService.class.getName());
    private final GroupRepository repository;
    private final GroupOfMembersRepository groupOfMembersRepository;
    private  final UserRepository userRepository;


    public GroupService(GroupRepository repository, UserRepository userRepository,GroupOfMembersRepository groupOfMembersRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.groupOfMembersRepository = groupOfMembersRepository;
    }


    private void  verifyGroupExist(String name){
        boolean exists = repository.existsByName(name);

        if(exists){
            throw new GroupAlreadyExistsException();
        }
    }


    public GroupsDTO findGroupById(Long id){
        var entity = repository.findById(id).orElseThrow(() -> new GroupNotFoundException());
        return parseObject(entity, GroupsDTO.class);
    }


    public PagedModel<EntityModel<GroupsDTO>> findAllGroup(Pageable pageable) {

        logger.info("Retrieving all groups");

        var groupsDto = repository.findGroupEnabled(pageable)
                .map(group -> {
                    return parseObject(group, GroupsDTO.class);
                });

        var selfLink = linkTo(methodOn(GroupController.class)
                .findAll(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        String.valueOf(pageable.getSort())))
                .withSelfRel();

        return assembler.toModel(groupsDto, selfLink);
    }


    public GroupsDTO createGroup(GroupsDTO groupsDTO) {
        verifyGroupExist(groupsDTO.getName());

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException());

        var group = new Groups();
        group.setName(groupsDTO.getName());
        group.setCreatedBy(user);
        group.setEnabled(true);
        group.setCreatedIn(LocalDateTime.now());

        Groups saved = repository.save(group);

        logger.info("Group created.");

        return parseObject(saved, GroupsDTO.class);
    }


    public GroupsDTO updateGroup (GroupsDTO groupsDTO){
        if (groupsDTO == null){
            throw new GroupRequiredException();
        }
        var entity = repository.findById(groupsDTO.getId()).orElseThrow(() -> new GroupNotFoundException());

        logger.info("Group updated successfully.");

        entity.setName(groupsDTO.getName());
        return parseObject(repository.save(entity),GroupsDTO.class);
    }


    public GroupOfMembersDTO addMember(Long groupId, Long userId){

        var group = repository.findById(groupId).orElseThrow(() -> new GroupNotFoundException());
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        boolean alreadyMember = groupOfMembersRepository.existsByGroupsAndUser(group,user);

        if (alreadyMember) throw new UserAlreadyInGroupException();

        var member = new GroupOfMembers();
        member.setGroups(group);
        member.setUser(user);
        member.setEntered(LocalDateTime.now());

        var saved = groupOfMembersRepository.save(member);
        return parseObject(saved, GroupOfMembersDTO.class);
    }


    public GroupsDTO disabledGroup(Long id) {

        var entity = repository.findById(id)
                .orElseThrow(() -> new RecordsNotFoundException());

        entity.setEnabled(false);

        var saved = repository.save(entity);

        return parseObject(saved, GroupsDTO.class);
    }


    public ResponseEntity<Void> deleteGroup(Long id){
        logger.info("Group has been Delete.");

        var entity = repository.findById(id).orElseThrow(() -> new GroupNotFoundException());
        repository.delete(entity);

        return ResponseEntity.noContent().build();
    }
}
