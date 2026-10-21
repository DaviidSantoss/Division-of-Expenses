package Santos.David.Controller.Docs;

import Santos.David.dto.GroupOfMembersDTO;
import Santos.David.dto.GroupsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(
        name = "Grupos",
        description = "Operações para criação, consulta e gerenciamento de grupos e seus participantes."
)
public interface GroupsControllerDocs {

    @Operation(
            summary = "Consultar grupo por ID",
            description = "Retorna os dados do grupo correspondente ao identificador informado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Grupo encontrado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GroupsDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado.", content = @Content)
    })
    GroupsDTO findGroupById(
            @Parameter(description = "Identificador único do grupo.", example = "1", required = true)
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Listar grupos com paginação",
            description = """
            Retorna os grupos cadastrados em uma coleção paginada.
            Permite controlar a página, a quantidade de registros por página
            e a direção da ordenação pelo nome do grupo.
            """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Grupos consultados com sucesso."
            ),
            @ApiResponse(responseCode = "400", description = "Parâmetros de paginação inválidos.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content)
    })
    ResponseEntity<PagedModel<EntityModel<GroupsDTO>>> findAll(
            @Parameter(description = "Número da página, começando em zero.", example = "0")
            @RequestParam(value = "page", defaultValue = "0") Integer page,

            @Parameter(description = "Quantidade máxima de grupos por página.", example = "12")
            @RequestParam(value = "size", defaultValue = "12") Integer size,

            @Parameter(description = "Direção da ordenação pelo nome: asc ou desc.", example = "asc")
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    );

    @Operation(
            summary = "Criar grupo",
            description = "Cadastra um novo grupo com os dados fornecidos no corpo da requisição."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Grupo criado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GroupsDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Já existe um grupo com os dados informados.", content = @Content)
    })
    GroupsDTO createGroup(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do grupo a ser criado.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GroupsDTO.class)
                    )
            )
            @RequestBody GroupsDTO groups
    );

    @Operation(
            summary = "Atualizar grupo",
            description = "Atualiza os dados de um grupo conforme as informações fornecidas."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Grupo atualizado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GroupsDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflito com os dados de outro grupo.", content = @Content)
    })
    GroupsDTO UpdateGroup(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados do grupo.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GroupsDTO.class)
                    )
            )
            @RequestBody GroupsDTO groups
    );

    @Operation(
            summary = "Desativar grupo",
            description = "Desativa o grupo identificado pelo ID, sem necessariamente removê-lo do banco de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Grupo desativado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GroupsDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado.", content = @Content)
    })
    GroupsDTO disabledGroup(
            @Parameter(description = "Identificador único do grupo a ser desativado.", example = "1", required = true)
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Adicionar membro ao grupo",
            description = "Vincula um usuário existente ao grupo informado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Membro adicionado ao grupo com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GroupOfMembersDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Não foi possível adicionar o membro devido a uma regra de negócio.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Grupo ou usuário não encontrado.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Usuário já pertence ao grupo.", content = @Content)
    })
    GroupOfMembersDTO addMember(
            @Parameter(description = "Identificador único do grupo.", example = "1", required = true)
            @PathVariable("groupId") Long groupId,

            @Parameter(description = "Identificador único do usuário que será adicionado.", example = "10", required = true)
            @PathVariable("userId") Long userId
    );

    @Operation(
            summary = "Excluir grupo",
            description = "Remove o grupo identificado pelo ID, conforme as regras de exclusão da aplicação."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Grupo excluído com sucesso.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Não foi possível excluir o grupo devido a uma restrição de negócio.", content = @Content)
    })
    ResponseEntity<Void> deleteGroup(
            @Parameter(description = "Identificador único do grupo a ser excluído.", example = "1", required = true)
            @PathVariable("id") Long id
    );
}
