package amalitech.amalitechinterviews.controller;

import amalitech.amalitechinterviews.request.AuthenticationRequest;
import amalitech.amalitechinterviews.response.AuthenticationResponse;
import amalitech.amalitechinterviews.service.AuthenticationService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @MutationMapping()
    @PreAuthorize("isAnonymous()")
    public AuthenticationResponse login(@Argument("input") AuthenticationRequest authenticationRequest) {
        return authenticationService.login(authenticationRequest);
    }
//
//    @MutationMapping
//    public String register(@Argument("input"))

//    {
//        "input": {
//        "name": "John Doe",
//                "email": "john.doe@example.com",
//                "role": "USER",
//                "password": "securepassword123"
//    }
//    }
}
