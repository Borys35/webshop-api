package io.borys.webshop.auth;

import io.borys.webshop.user.User;
import io.borys.webshop.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // user.getAuthorities().add
            return user;
        } else {
            throw new UsernameNotFoundException("User not found with e-mail: " + username);
        }
    }
}
