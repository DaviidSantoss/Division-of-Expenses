package Santos.David.Controller;

import Santos.David.Controller.Docs.BalanceControllerDocs;
import Santos.David.Service.BalanceService;
import Santos.David.dto.MemberBalanceDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/groups/{groupId}/balance")
public class BalanceController implements BalanceControllerDocs {


    private final BalanceService service;

    public BalanceController(BalanceService service) {
        this.service = service;
    }


    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MemberBalanceDTO>> getBalance(@PathVariable("groupId") Long groupId) {
        return ResponseEntity.ok(service.getBalance(groupId));
    }
}
