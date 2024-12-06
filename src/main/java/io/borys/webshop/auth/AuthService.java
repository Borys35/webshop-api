package io.borys.webshop.auth;

import io.borys.webshop.role.Role;
import io.borys.webshop.role.RoleRepository;
import io.borys.webshop.user.LoginUserDto;
import io.borys.webshop.user.RegisterUserDto;
import io.borys.webshop.user.User;
import io.borys.webshop.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }

    public User signUp(RegisterUserDto dto) {
        Optional<User> foundUser = userRepository.findByEmail(dto.getEmail());
        if (foundUser.isEmpty()) {
            User user = new User(dto.getFirstName(), dto.getLastName(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()));

            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("USER"));

            if (user.getEmail().split("@")[1].equals("boryskaczmarek.pl")) {
                roles.add(roleRepository.findByName("ADMIN"));
            }

            user.setRoles(roles);

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
