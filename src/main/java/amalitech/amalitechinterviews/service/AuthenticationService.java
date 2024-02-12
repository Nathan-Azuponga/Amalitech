package amalitech.amalitechinterviews.service;

import amalitech.amalitechinterviews.entity.User;
import amalitech.amalitechinterviews.repository.UserRepository;
import amalitech.amalitechinterviews.request.AuthenticationRequest;
import amalitech.amalitechinterviews.response.AuthenticationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@Slf4j
public class AuthenticationService {
    private final UserDetailsService userDetailsService;
    private final JwtGeneratorService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    public AuthenticationService(UserDetailsService userDetailsService, JwtGeneratorService jwtService, AuthenticationManager authenticationManager, UserRepository userRepository, HttpSession httpSession) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.httpSession = httpSession;
    }
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        String token;
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
            token = jwtService.generateToken(userDetails);
            User user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
            httpSession.setAttribute("user", user);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email/password supplied");
        }

        return new AuthenticationResponse(token);
    }
}
