package Santos.David.Controller.Docs;

import Santos.David.dto.PaymentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface PaymentControllerDocs {

    public ResponseEntity<PaymentDTO> registerPayment(@PathVariable("costId") Long costId, PaymentDTO dto);

}
