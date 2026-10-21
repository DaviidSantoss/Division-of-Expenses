package Santos.David.Controller;

import Santos.David.Controller.Docs.UserControllerDocs;
import Santos.David.Exception.UserNotFoundException;
import Santos.David.Mapper.ObjectMapper;
import Santos.David.Service.Repository.UserRepository;
import Santos.David.Service.UserService;
import Santos.David.dto.CreateUserDTO;
import Santos.David.dto.UserDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController implements UserControllerDocs {


    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Override
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO findUserById(@PathVariable("id") Long id) {
       var user = userService.findUserById(id);
       return user;
    }


    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedModel<EntityModel<UserDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        System.out.println("ENTROU NO findAll");

        var sortDirection = "desc".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortDirection, "name")
        );

        return ResponseEntity.ok(userService.findAllUser(pageable));
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO createUser(@RequestBody CreateUserDTO user) {
        return userService.createUser(user);
    }


    @Override
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO UpdateUser(@RequestBody UserDTO user) {
        return  userService.updateUser(user);
    }


    @Override
    @PatchMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO disabledUser(@PathVariable("id") Long id) {
        return userService.disabledUser(id);
    }


    @Override
    @DeleteMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
