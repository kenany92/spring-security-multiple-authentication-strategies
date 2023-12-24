package com.numericaideas.multipleauthstrategies.configuration;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Admin authentication provider
 * for /admin/** matcher
 */
@Component
public class AdminAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        authentication.getCredentials().toString();
        Object user = loadUser(username);
        checkPassword(user, authentication.getCredentials().toString());
        return new User(username, "fake_password", List.of());
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Authenticate admin user");
        return super.createSuccessAuthentication("admin", authentication, retrieveUser("username", (UsernamePasswordAuthenticationToken) authentication));
    }

    private Object loadUser(String username) throws AuthenticationException {
        // You should implement the way you want to retrieve the user with the username
        return null;
    }

    private void checkPassword(Object user, String requestPassword) throws AuthenticationException {
        // You should implement the way you want to retrieve the user with the request password
        // You can use BcryptPasswordEncoder for verification
    }
}
