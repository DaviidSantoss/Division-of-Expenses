package Santos.David.Controller.Docs;

import Santos.David.dto.GroupsDTO;
import Santos.David.dto.UserDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface GroupsControllerDocs {

    UserDTO findGroupById(@PathVariable("id") Long id);

    ResponseEntity<PagedModel<EntityModel<GroupsDTO>>> findAll(
            @RequestParam(value = "page",defaultValue = "0") Integer page,
            @RequestParam(value = "size",defaultValue = "12") Integer size,
            @RequestParam(value = "direction",defaultValue = "asc") String direction
    );

    UserDTO createGroup(@RequestBody UserDTO user);
    UserDTO UpdateGroup(@RequestBody UserDTO user);
    UserDTO disabledGroup(@PathVariable("id") Long id);
    ResponseEntity<Void> deleteGroup(@PathVariable("id") Long id);
}
