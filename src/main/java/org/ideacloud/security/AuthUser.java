package org.ideacloud.security;

import org.ideacloud.models.Role;

public record AuthUser(
        Long id,
        String email,
        String name,
        String password,
        String role,
        String accessToken
) {
    public static AuthUser of(
            Long id, String email, String name, String password, String role) {
        return new AuthUser(id, email, name, password, role, "");
    }

    public boolean isAdmin() {
        return Role.valueOf(role).equals(Role.ROLE_ADMIN);
    }
}
