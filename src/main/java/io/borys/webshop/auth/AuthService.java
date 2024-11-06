package io.borys.webshop.auth;

import io.borys.webshop.user.LoginUserDto;
import io.borys.webshop.user.RegisterUserDto;
import io.borys.webshop.user.User;
import io.borys.webshop.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signUp(RegisterUserDto dto) {
        Optional<User> foundUser = userRepository.findByEmail(dto.getEmail());
        if (foundUser.isEmpty()) {
            User user = new User(dto.getFirstName(), dto.getLastName(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()));

            return userRepository.save(user);
        } else {
            return foundUser.get();
        }
    }

    public User authenticate(LoginUserDto dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        return userRepository.findByEmail(dto.getEmail()).orElseThrow();
    }
}
