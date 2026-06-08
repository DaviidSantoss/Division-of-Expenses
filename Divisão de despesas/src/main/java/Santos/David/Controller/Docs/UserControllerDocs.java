package Santos.David.Controller.Docs;

import Santos.David.dto.UserDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserControllerDocs {

    UserDTO findUserById(@PathVariable("id") Long id);

    ResponseEntity<PagedModel<EntityModel<UserDTO>>> findAll(
            @RequestParam(value = "page",defaultValue = "0") Integer page,
            @RequestParam(value = "size",defaultValue = "12") Integer size,
            @RequestParam(value = "direction",defaultValue = "asc") String direction
    );

    UserDTO createUser(@RequestBody UserDTO user);
    UserDTO UpdateUser(@RequestBody UserDTO user);
    UserDTO disabledUser(@PathVariable("id") Long id);
    ResponseEntity<Void> deleteUser(@PathVariable("id") Long id);

}
