package amalitech.amalitechinterviews.response;

import amalitech.amalitechinterviews.entity.Role;
import lombok.Data;

@Data
public class UserResponse {
    private long id;
    private String name;
    private String email;
    private Role role;
}
