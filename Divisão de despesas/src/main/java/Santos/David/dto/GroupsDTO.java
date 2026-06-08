package Santos.David.dto;

import Santos.David.Model.User;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonPropertyOrder({"id","name","created_by","createdIn"})
public class GroupsDTO extends RepresentationModel<GroupsDTO> {

    private long id;
    private String name;
    private User createdBy;
    private boolean enabled = true;
    private LocalDateTime createdIn;
}
