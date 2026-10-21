package Santos.David.Controller.Docs;

import Santos.David.dto.PaymentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(
        name = "Pagamentos",
        description = "Operações relacionadas ao registro de pagamentos de despesas."
)
public interface PaymentControllerDocs {

    @Operation(
            summary = "Registrar pagamento de uma despesa",
            description = """
            Registra um pagamento associado à despesa identificada pelo ID.
            Os dados enviados devem identificar as informações necessárias
            para registrar o pagamento, incluindo os participantes envolvidos
            e o valor pago.

            A aplicação valida as regras de negócio antes de registrar
            a operação, incluindo as restrições relacionadas aos valores pagos.
            """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Pagamento registrado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou violação de uma regra de negócio.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Autenticação necessária para acessar o recurso.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "O usuário não possui permissão para acessar o recurso.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Despesa ou participante não encontrado.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "O pagamento não pode ser registrado devido a um conflito com as regras de negócio.",
                    content = @Content
            )
    })
    ResponseEntity<PaymentDTO> registerPayment(

            @Parameter(
                    description = "Identificador único da despesa à qual o pagamento será associado.",
                    example = "7",
                    required = true
            )
            @PathVariable("costId") Long costId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados necessários para registrar o pagamento.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDTO.class)
                    )
            )
            @RequestBody PaymentDTO dto
    );
}