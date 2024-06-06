package org.ideacloud.security;

import org.ideacloud.models.Role;

public record AuthUser(
        Long id,
        String email,
        String password,
        String role,
        String accessToken
) {
    public static AuthUser of(
            Long id, String email, String password, String role) {
        return new AuthUser(id, email, password, role, "");
    }

    public static AuthUser authenticated(
            Long id, String role, String accessToken) {
        return new AuthUser(id, "", "", role, accessToken);
    }

    public boolean isAdmin() {
        return Role.valueOf(role).equals(Role.ROLE_ADMIN);
    }
}
