package projects.todolistapp.model.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
}