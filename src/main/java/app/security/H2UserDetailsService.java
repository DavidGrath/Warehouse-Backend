package app.security;

import app.models.entities.Person;
import app.models.entities.Privilege;
import app.models.entities.Role;
import app.models.entities.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class H2UserDetailsService implements UserDetailsService {

    @Autowired
    PersonRepository personRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username).orElseThrow(()->
                new UsernameNotFoundException(username + " doesn't exist!"));
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : person.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            for (Privilege privilege : role.getPrivileges()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(privilege.getName()));
            }
        }
        return new H2UserDetails(person, grantedAuthorities);

    }
}
