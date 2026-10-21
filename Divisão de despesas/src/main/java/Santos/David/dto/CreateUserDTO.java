package Santos.David.dto;

public record CreateUserDTO(
        String name,
        String email,
        String password
) {}