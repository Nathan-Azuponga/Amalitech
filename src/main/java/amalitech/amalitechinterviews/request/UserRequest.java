package amalitech.amalitechinterviews.request;

import amalitech.amalitechinterviews.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
}
