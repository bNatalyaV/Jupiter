package id.bnv.jupiter.security;

import id.bnv.jupiter.dao.UserDao;
import id.bnv.jupiter.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AuthProvider implements AuthenticationProvider {

    private final UserDao dao;

    @Autowired
    public AuthProvider(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = dao.getUser(name);

        if (user == null) {
            return null;
        } else if (user.password.equals(password)) {
            return new UsernamePasswordAuthenticationToken(
                    user,
                    password,
                    Collections.emptyList()
            );
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
