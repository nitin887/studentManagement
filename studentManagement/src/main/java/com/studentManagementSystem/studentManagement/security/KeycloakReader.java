package com.studentManagementSystem.studentManagement.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Custom converter to extract Keycloak realm roles from a JWT
 * and map them to Spring Security's GrantedAuthority objects.
 */
@Component
public class KeycloakReader implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        // Use getClaimAsMap for type-safe access to the 'realm_access' claim
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");

        if (realmAccess == null || realmAccess.isEmpty()) {
            return Collections.emptyList(); // No roles found
        }

        // Safely access the 'roles' claim, checking its type before casting
        Object rolesClaim = realmAccess.get("roles");
        if (!(rolesClaim instanceof Collection<?>)) {
            return Collections.emptyList();
        }

        // Now that we've checked the type, we can safely cast
        @SuppressWarnings("unchecked")
        Collection<String> roles = (Collection<String>) rolesClaim;

        return roles.stream()
                .map(roleName -> "ROLE_" + roleName.toUpperCase()) // Prefix with ROLE_ for Spring Security
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
