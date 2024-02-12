package amalitech.amalitechinterviews.service;

import amalitech.amalitechinterviews.entity.EntityMapper;
import amalitech.amalitechinterviews.entity.User;
import amalitech.amalitechinterviews.exception.EmailAlreadyExistException;
import amalitech.amalitechinterviews.exception.UserDoesNotExistException;
import amalitech.amalitechinterviews.repository.UserRepository;
import amalitech.amalitechinterviews.request.UserRequest;
import amalitech.amalitechinterviews.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserResponse createUser(UserRequest userRequest) {
        userRepository.findByEmail(userRequest.getEmail()).ifPresent(user -> {
            throw new EmailAlreadyExistException(userRequest.getEmail());
        });
        User user = EntityMapper.INSTANCE.convertToUser(userRequest);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("User successfully created");
        return EntityMapper.INSTANCE.convertToUserDto(user);
    }

    public UserResponse updateUser(long id, UserRequest UserRequest) {
        var ref = new Object() {
            User userResult = null;
        };
        userRepository.findById(id).ifPresentOrElse(user -> {
            EntityMapper.INSTANCE.updateUserDetails(user, UserRequest);
            userRepository.save(user);
            ref.userResult = user;
        }, () -> {
            throw new UserDoesNotExistException(id);
        });
        log.info("User with id {} successfully updated", id);
        return EntityMapper.INSTANCE.convertToUserDto(ref.userResult);
    }
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(EntityMapper.INSTANCE::convertToUserDto).toList();
    }

    public UserResponse getUserById(long id) {
        return EntityMapper.INSTANCE.convertToUserDto(userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException(id)));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserDoesNotExistException(email));
        return org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
