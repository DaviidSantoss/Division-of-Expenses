package Santos.David.Controller.Docs;

import Santos.David.dto.CreateUserDTO;
import Santos.David.dto.UserDTO;
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
        name = "Usuários",
        description = "Operações para cadastro, consulta, atualização, desativação e exclusão de usuários."
)
public interface UserControllerDocs {

    @Operation(
            summary = "Consultar usuário por ID",
            description = "Retorna os dados do usuário correspondente ao identificador informado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário consultado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content)
    })
    UserDTO findUserById(
            @Parameter(
                    description = "Identificador único do usuário.",
                    example = "10",
                    required = true
            )
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Listar usuários com paginação",
            description = """
            Retorna os usuários em uma coleção paginada.
            Permite definir a página, a quantidade de registros por página
            e a direção da ordenação pelo nome do usuário.
            """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários consultados com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parâmetros de paginação inválidos.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content)
    })
    ResponseEntity<PagedModel<EntityModel<UserDTO>>> findAll(
            @Parameter(description = "Número da página, começando em zero.", example = "0")
            @RequestParam(value = "page", defaultValue = "0") Integer page,

            @Parameter(description = "Quantidade máxima de usuários por página.", example = "12")
            @RequestParam(value = "size", defaultValue = "12") Integer size,

            @Parameter(description = "Direção da ordenação pelo nome: asc ou desc.", example = "asc")
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    );

    @Operation(
            summary = "Cadastrar usuário",
            description = """
            Cria um novo usuário a partir dos dados fornecidos.
            A senha é processada pela aplicação antes de ser armazenada,
            de acordo com a configuração de segurança do sistema.
            """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário cadastrado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados de cadastro inválidos.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Já existe um usuário com o e-mail informado.", content = @Content)
    })
    UserDTO createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados necessários para o cadastro do usuário.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateUserDTO.class)
                    )
            )
            @RequestBody CreateUserDTO user
    );

    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário conforme as informações fornecidas."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário atualizado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados de atualização inválidos.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflito com os dados de outro usuário.", content = @Content)
    })
    UserDTO UpdateUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados do usuário.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            )
            @RequestBody UserDTO user
    );

    @Operation(
            summary = "Desativar usuário",
            description = """
            Desativa o usuário identificado pelo ID.
            A desativação lógica mantém o registro armazenado,
            mas impede que o usuário seja tratado como ativo.
            """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário desativado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content)
    })
    UserDTO disabledUser(
            @Parameter(
                    description = "Identificador único do usuário a ser desativado.",
                    example = "10",
                    required = true
            )
            @PathVariable("id") Long id
    );

    @Operation(
            summary = "Excluir usuário",
            description = "Remove o usuário identificado pelo ID, respeitando as regras de exclusão da aplicação."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso.", content = @Content),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content),
            @ApiResponse(responseCode = "409", description = "Não foi possível excluir o usuário devido a uma restrição de negócio.", content = @Content)
    })
    ResponseEntity<Void> deleteUser(
            @Parameter(
                    description = "Identificador único do usuário a ser excluído.",
                    example = "10",
                    required = true
            )
            @PathVariable("id") Long id
    );
}
