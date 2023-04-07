package org.lessons.LaMiaPizzeriaCrud.security;

import org.lessons.LaMiaPizzeriaCrud.model.User;
import org.lessons.LaMiaPizzeriaCrud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()){
            return new DatabaseUserDetails(user.get());
        } else {
            throw new UsernameNotFoundException("Utente con email " + username + " non trovato");
        }
    }
}
