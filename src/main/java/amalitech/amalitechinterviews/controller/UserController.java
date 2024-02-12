package amalitech.amalitechinterviews.controller;

import amalitech.amalitechinterviews.request.UserRequest;
import amalitech.amalitechinterviews.response.UserResponse;
import amalitech.amalitechinterviews.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@Argument("id") long id) {
        userService.deleteUser(id);
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public UserResponse updateUser(@Argument("id") long id, @Argument("input") UserRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }

    @MutationMapping
    @PreAuthorize("isAnonymous()")
    public UserResponse createUser(@Argument("input") UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public UserResponse getUserById(@Argument("id") long id) {
        return userService.getUserById(id);
    }
}
