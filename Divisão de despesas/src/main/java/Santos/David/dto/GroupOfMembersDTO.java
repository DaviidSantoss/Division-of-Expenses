package Santos.David.dto;

import Santos.David.Model.Groups;
import Santos.David.Model.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class GroupOfMembersDTO {

    private long id;
    private Groups groups;
    private User user;
    private LocalDateTime entered;
}
