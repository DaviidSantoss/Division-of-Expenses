package Santos.David.Controller.Docs;

import Santos.David.dto.GroupOfMembersDTO;
import Santos.David.dto.GroupsDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface GroupsControllerDocs {

    GroupsDTO findGroupById(@PathVariable("id") Long id);

    ResponseEntity<PagedModel<EntityModel<GroupsDTO>>> findAll(
            @RequestParam(value = "page",defaultValue = "0") Integer page,
            @RequestParam(value = "size",defaultValue = "12") Integer size,
            @RequestParam(value = "direction",defaultValue = "asc") String direction
    );

    GroupsDTO createGroup(@RequestBody GroupsDTO groups);
    GroupsDTO UpdateGroup(@RequestBody GroupsDTO groups);
    GroupsDTO disabledGroup(@PathVariable("id") Long id);
    GroupOfMembersDTO addMember(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId);
    ResponseEntity<Void> deleteGroup(@PathVariable("id") Long id);
}
