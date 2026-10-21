package Santos.David.Controller;

import Santos.David.Controller.Docs.GroupsControllerDocs;
import Santos.David.Service.GroupService;
import Santos.David.dto.GroupOfMembersDTO;
import Santos.David.dto.GroupsDTO;
import Santos.David.dto.UserDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
public class GroupController implements GroupsControllerDocs {


    private final GroupService service;

    public GroupController(GroupService service) {
        this.service = service;
    }


    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupsDTO findGroupById(@PathVariable("id") Long id) {

        var groupDTO = service.findGroupById(id);
        return groupDTO;
    }


    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagedModel<EntityModel<GroupsDTO>>> findAll(
            @RequestParam(value = "page",defaultValue = "0") Integer page,
            @RequestParam(value = "size",defaultValue = "12") Integer size,
            @RequestParam(value = "direction",defaultValue = "asc") String direction
    )
    {
        var sortDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortDirection, "id")
        );

        return ResponseEntity.ok(service.findAllGroup(pageable));
    }


    @Override
    @PostMapping("/{groupId}/members/{userId}")
    public GroupOfMembersDTO addMember(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId) {
        return service.addMember(groupId, userId);
    }


    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupsDTO createGroup(@RequestBody GroupsDTO groups) {
        return service.createGroup(groups);
    }


    @Override
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupsDTO UpdateGroup(@RequestBody GroupsDTO groups) {
        return service.updateGroup(groups);
    }


    @Override
    @PatchMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupsDTO disabledGroup(@PathVariable("id") Long id) {
        return service.disabledGroup(id);
    }


    @Override
    @DeleteMapping(value ="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteGroup(@PathVariable("id") Long id) {
        service.deleteGroup(id);

        return ResponseEntity.noContent().build();
    }
}
