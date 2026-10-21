package Santos.David.Controller.Docs;

import Santos.David.dto.CostDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

public interface CostControllerDocs {

    ResponseEntity<CostDTO> createCost(@PathVariable("groupId") Long groupId, @RequestBody CostDTO cost);
    List<CostDTO> findCostsByGroup(@PathVariable("groupId") Long groupId);
}
