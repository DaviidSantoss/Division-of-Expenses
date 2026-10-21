package Santos.David.Controller.Docs;

import Santos.David.dto.MemberBalanceDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface BalanceControllerDocs {

    ResponseEntity<List<MemberBalanceDTO>> getBalance(Long groupId);

}
