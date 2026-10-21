package Santos.David.Controller;

import Santos.David.Controller.Docs.PaymentControllerDocs;
import Santos.David.Service.PaymentService;
import Santos.David.dto.PaymentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/costs/{costId}/payments")
public class PaymentController implements PaymentControllerDocs {


    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }


    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentDTO> registerPayment(@PathVariable("costId") Long costId, @RequestBody PaymentDTO dto) {

        dto.setCostId(costId);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.registerPayment(dto));
    }
}
