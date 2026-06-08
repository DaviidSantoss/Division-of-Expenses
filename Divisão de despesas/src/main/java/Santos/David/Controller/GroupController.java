package Santos.David.Controller;

import Santos.David.Controller.Docs.GroupsControllerDocs;
import Santos.David.dto.GroupsDTO;
import Santos.David.dto.UserDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

public class GroupController implements GroupsControllerDocs {
    @Override
    public UserDTO findGroupById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<PagedModel<EntityModel<GroupsDTO>>> findAll(Integer page, Integer size, String direction) {
        return null;
    }

    @Override
    public UserDTO createGroup(UserDTO user) {
        return null;
    }

    @Override
    public UserDTO UpdateGroup(UserDTO user) {
        return null;
    }

    @Override
    public UserDTO disabledGroup(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteGroup(Long id) {
        return null;
    }
}
