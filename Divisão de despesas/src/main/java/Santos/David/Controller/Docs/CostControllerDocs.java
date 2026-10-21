package Santos.David.Controller.Docs;

import Santos.David.dto.CostDTO;
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
        name = "Despesas",
        description = "Operações para criação e consulta de despesas vinculadas a grupos."
)
public interface CostControllerDocs {

    @Operation(
            summary = "Cadastrar uma despesa no grupo",
            description = """
            Registra uma nova despesa vinculada ao grupo informado.
            Os dados da despesa devem ser enviados no corpo da requisição.

            A aplicação realiza as validações de negócio necessárias
            antes de persistir a despesa.
            """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Despesa criada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CostDTO.class)
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
                    description = "Grupo não encontrado.",
                    content = @Content
            )
    })
    ResponseEntity<CostDTO> createCost(
            @Parameter(
                    description = "Identificador único do grupo ao qual a despesa será vinculada.",
                    example = "1",
                    required = true
            )
            Long groupId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados necessários para cadastrar a despesa.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CostDTO.class)
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody CostDTO cost
    );

    @Operation(
            summary = "Listar despesas de um grupo",
            description = """
            Retorna a lista de despesas vinculadas ao grupo informado.
            Cada item representa uma despesa cadastrada para o grupo.
            """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Despesas consultadas com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CostDTO.class)
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
    List<CostDTO> findCostsByGroup(
            @Parameter(
                    description = "Identificador único do grupo cujas despesas serão consultadas.",
                    example = "1",
                    required = true
            )
            Long groupId
    );
}
