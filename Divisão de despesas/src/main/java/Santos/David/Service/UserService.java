package Santos.David.Service;

import Santos.David.Exception.RecordsNotFoundException;
import Santos.David.Exception.UserDisableExcpetion;
import Santos.David.Exception.UserNotFoundException;
import Santos.David.Mapper.ObjectMapper;
import Santos.David.dto.CreateUserDTO;
import org.springframework.data.domain.Pageable;
import java.util.logging.Logger;
import Santos.David.Controller.UserController;
import Santos.David.Model.User;
import Santos.David.Service.Repository.UserRepository;
import Santos.David.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static Santos.David.Mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class UserService {


    @Autowired
    PagedResourcesAssembler<UserDTO> assembler;

    private final Logger logger = Logger.getLogger(UserService.class.getName());
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    private User verifyEnableAndId(Long id){
        var entity = repository.findById(id).orElseThrow(() -> new UserNotFoundException());

        if (!entity.isEnabled()){
            throw  new UserDisableExcpetion();
        }
        return entity;
    }


    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserDTO findUserById(long id){
        logger.info("Found a User");

        var entity = verifyEnableAndId(id);
        var dto = parseObject(entity, UserDTO.class);
        return dto;
    }


    public PagedModel<EntityModel<UserDTO>> findAllUser(Pageable pageable) {

        logger.info("Retrieving all users");

        var usersDto = repository.findUserEnabled(pageable)
                .map(user -> {
                    var dto = parseObject(user, UserDTO.class);
                    addHateoasLinks(dto);
                    return dto;
                });

        var selfLink = linkTo(methodOn(UserController.class)
                .findAll(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        String.valueOf(pageable.getSort())))
                .withSelfRel();

        return assembler.toModel(usersDto, selfLink);
    }


    public UserDTO createUser(CreateUserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("Os dados do usuário são obrigatórios.");
        }

        User user = new User();
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setPasswordHash(passwordEncoder.encode(userDTO.password()));
        user.setEnabled(true);
        user.setCreatedIn(java.time.LocalDateTime.now());

        User saved = repository.save(user);

        UserDTO dto = parseObject(saved, UserDTO.class);
        addHateoasLinks(dto);

        return dto;
    }


    public UserDTO updateUser (UserDTO userDTO){
        if (userDTO == null) throw new UserNotFoundException();
        var entity = verifyEnableAndId(userDTO.getId());

        logger.info("User updated successfully.");

        entity.setName(userDTO.getName());
        entity.setEmail(userDTO.getEmail());
        var dto = parseObject(repository.save(entity),UserDTO.class);

        addHateoasLinks(dto);

        return dto;
    }


    public UserDTO disabledUser(Long id){

        var entity = repository.findById(id).orElseThrow(() -> new RecordsNotFoundException());
        repository.disableUser(id);
        entity.setEnabled(false);
        var dto = parseObject(entity, UserDTO.class);

        addHateoasLinks(dto);

        return dto;
    }


    public void deleteUser(Long id){
        logger.info("User has been Delete.");

        var entity = verifyEnableAndId(id);
        repository.delete(entity);
    }


    private static void addHateoasLinks(UserDTO dto) {
        dto.add(linkTo(methodOn(UserController.class).findUserById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(UserController.class).findAll(1,12,"asc")).withRel("findAll").withType("GET"));
//        dto.add(linkTo(methodOn(UserController.class).createUser()).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(UserController.class).UpdateUser(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(UserController.class).disabledUser(dto.getId())).withRel("disable").withType("PATCH"));
        dto.add(linkTo(methodOn(UserController.class).deleteUser(dto.getId())).withRel("delete").withType("DELETE"));
    }
}
