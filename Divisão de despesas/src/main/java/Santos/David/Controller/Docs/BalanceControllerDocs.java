package Santos.David.Controller.Docs;

import Santos.David.dto.MemberBalanceDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(
        name = "Saldos do Grupo",
        description = "Operações relacionadas à consulta dos saldos financeiros dos membros de um grupo."
)
public interface BalanceControllerDocs {

    @Operation(
            summary = "Consultar saldo dos membros de um grupo",
            description = """
            Retorna o saldo financeiro de cada membro do grupo informado,
            considerando as despesas compartilhadas e os pagamentos registrados.

            O saldo permite identificar a situação financeira de cada participante,
            conforme as regras de cálculo implementadas pela aplicação.
            """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Saldos dos membros consultados com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = MemberBalanceDTO.class
                            )
                    )
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
                    description = "Grupo não encontrado.",
                    content = @Content
            )
    })
    ResponseEntity<List<MemberBalanceDTO>> getBalance(
            @Parameter(
                    description = "Identificador único do grupo cujos saldos serão consultados.",
                    example = "1",
                    required = true
            )
            Long groupId
    );
}