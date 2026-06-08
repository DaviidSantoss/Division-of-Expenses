package Santos.David.Controller;

import Santos.David.Controller.Docs.UserControllerDocs;
import Santos.David.dto.UserDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

public class UserController implements UserControllerDocs {


    @Override
    public UserDTO findUserById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<PagedModel<EntityModel<UserDTO>>> findAll(Integer page, Integer size, String direction) {
        return null;
    }

    @Override
    public UserDTO createUser(UserDTO user) {
        return null;
    }

    @Override
    public UserDTO UpdateUser(UserDTO user) {
        return null;
    }

    @Override
    public UserDTO disabledUser(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        return null;
    }
}
