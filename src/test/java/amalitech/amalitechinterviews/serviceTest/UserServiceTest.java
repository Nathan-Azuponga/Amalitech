package amalitech.amalitechinterviews.serviceTest;

import amalitech.amalitechinterviews.entity.Role;
import amalitech.amalitechinterviews.entity.User;
import amalitech.amalitechinterviews.exception.EmailAlreadyExistException;
import amalitech.amalitechinterviews.exception.UserDoesNotExistException;
import amalitech.amalitechinterviews.repository.UserRepository;
import amalitech.amalitechinterviews.request.UserRequest;
import amalitech.amalitechinterviews.response.UserResponse;
import amalitech.amalitechinterviews.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_Success() {
        UserRequest userRequest = new UserRequest("John", "john@example.com", "password", Role.USER);
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode("password")).thenReturn("encodedPassword");

        UserResponse response = userService.createUser(userRequest);

        assertNotNull(response);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_EmailAlreadyExists_ThrowsException() {
        // Given
        UserRequest userRequest = new UserRequest("John","john@example.com", "password", Role.USER);
        User existingUser = new User(); // Set necessary fields
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(existingUser));
        assertThrows(EmailAlreadyExistException.class, () -> userService.createUser(userRequest));
    }

    @Test
    void updateUser_ExistingUser_Success() {
        long userId = 1L;
        UserRequest userRequest = new UserRequest("Jane", "jane@example.com", "encodedNewPassword", Role.USER);
        User existingUser = new User(); // Assuming setters or constructor to initialize
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        UserResponse updatedUserResponse = userService.updateUser(userId, userRequest);

        assertNotNull(updatedUserResponse);
        verify(userRepository).save(existingUser);
        assertEquals("encodedNewPassword", existingUser.getPassword());
    }

    @Test
    void updateUser_NonExistingUser_ThrowsException() {
        long userId = 2L;
        UserRequest userRequest = new UserRequest("Non","non.existent@example.com", "password", Role.USER);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserDoesNotExistException.class, () -> userService.updateUser(userId, userRequest));
    }
}

