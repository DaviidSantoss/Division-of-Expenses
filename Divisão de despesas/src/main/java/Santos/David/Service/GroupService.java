package Santos.David.Service;

import Santos.David.Controller.GroupController;
import Santos.David.Model.GroupOfMembers;
import Santos.David.Model.Groups;
import Santos.David.Service.Repository.GroupOfMembersRepository;
import Santos.David.Service.Repository.GroupRepository;
import Santos.David.Service.Repository.UserRepository;
import Santos.David.dto.GroupOfMembersDTO;
import Santos.David.dto.GroupsDTO;
import Santos.David.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
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
    private GroupRepository repository;
    private final GroupOfMembersRepository groupOfMembersRepository;
    private  final UserRepository userRepository;

    public GroupService(GroupRepository repository, UserRepository userRepository,GroupOfMembersRepository groupOfMembersRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.groupOfMembersRepository = groupOfMembersRepository;
    }

    private void  verifyGroupExist(String name){
        boolean exists = repository.existsByName(name);
        //TODO: Iplementar a exceção correta
        if (exists) throw new RuntimeException("Group already exists: " + name);
    }

    public GroupsDTO findGroupById(Long id){
        var entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Group Not Found"));
        var dto = parseObject(entity, GroupsDTO.class);
        return dto;
    }

    public PagedModel<EntityModel<GroupsDTO>> findAllGroup(Pageable pageable) {

        logger.info("Retrieving all groups");

        var groupsDto = repository.findGroupEnabled(pageable)
                .map(group -> {
                    var dto = parseObject(group, GroupsDTO.class);
                    return dto;
                });

        var selfLink = linkTo(methodOn(GroupController.class)
                .findAll(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        String.valueOf(pageable.getSort())))
                .withSelfRel();

        return assembler.toModel(groupsDto, selfLink);
    }

    public GroupsDTO createGroup(GroupsDTO groupsDTO){
       verifyGroupExist(groupsDTO.getName());

        logger.info("Group created.");

        var group = parseObject(groupsDTO , Groups.class);
        Groups saved = repository.save(group);
        return parseObject(saved, GroupsDTO.class);
    }

    public GroupsDTO updateGroup (GroupsDTO groupsDTO){
        //TODO:implementar exceção correta
        if (groupsDTO == null) throw new RuntimeException("Group is Null");
        var entity = repository.findById(groupsDTO.getId()).orElseThrow(() -> new RuntimeException("Group Not Found"));

        logger.info("Group updated successfully.");

        entity.setName(groupsDTO.getName());
        var dto = parseObject(repository.save(entity),GroupsDTO.class);

        return dto;
    }

    public GroupOfMembersDTO addMember(Long groupId, Long userId){

        var group = repository.findById(groupId).orElseThrow(() -> new RuntimeException("Group Not Found"));
        var user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        boolean alreadyMember = groupOfMembersRepository.existsByGroupAndUser(group,user);

        if (alreadyMember) throw new RuntimeException("User already in group");

        var member = new GroupOfMembers();
        member.setGroups(group);
        member.setUser(user);
        member.setEntered(LocalDateTime.now());

        var saved = groupOfMembersRepository.save(member);
        return parseObject(saved, GroupOfMembersDTO.class);
    }

    public GroupsDTO disabledGroup(Long id){

        var entity = repository.findById(id).orElseThrow(() -> new RuntimeException("No records found for this ID"));
        repository.disableGroup(id);
        entity.setEnabled(false);
        var dto = parseObject(entity, GroupsDTO.class);

        return dto;
    }
}
