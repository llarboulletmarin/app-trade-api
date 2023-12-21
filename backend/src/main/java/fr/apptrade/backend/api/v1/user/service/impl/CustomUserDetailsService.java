package fr.apptrade.backend.api.v1.user.service.impl;

import fr.apptrade.backend.api.v1.user.model.Role;
import fr.apptrade.backend.api.v1.user.model.User;
import fr.apptrade.backend.api.v1.user.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Permet à Spring de récupérer un utilisateur par son email lors de l'authentification
     *
     * @param email email de l'utilisateur
     * @return UserDetails
     * @throws UsernameNotFoundException si l'utilisateur n'est pas trouvé
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email : " + email)
        );

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getAuthorities(user.getRole())
        );
    }

    /**
     * Permet de récupérer le rôle de l'utilisateur
     *
     * @param role rôle de l'utilisateur (objet Role)
     * @return Collection<? extends GrantedAuthority>
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getRole()));
    }

}
