package Santos.David.Controller;

import Santos.David.Controller.Docs.CostControllerDocs;
import Santos.David.Service.CostService;
import Santos.David.dto.CostDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/groups/{groupId}/costs")
public class CostController implements CostControllerDocs {


    private final CostService service;

    public CostController(CostService costService) {
        this.service = costService;
    }


    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CostDTO> createCost(@PathVariable("groupId") Long groupId, @RequestBody CostDTO cost) {

        cost.setGroupId(groupId);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCost(cost));
    }


    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CostDTO> findCostsByGroup(@PathVariable Long groupId) {

        return service.findCostsByGroup(groupId);
    }


}
