package projects.todolistapp.model.DTO;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDTO {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String username;
    private String firstName;
    private String lastName;
}