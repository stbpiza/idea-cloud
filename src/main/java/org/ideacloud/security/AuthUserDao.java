package org.ideacloud.security;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthUserDao {
    private final JdbcTemplate jdbcTemplate;

    public AuthUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<AuthUser> findByEmail(String email) {
        String query = "SELECT user_id, password, role FROM users WHERE email=?";

        return jdbcTemplate.query(query, resultSet -> {
            if (!resultSet.next()) {
                return Optional.empty();
            }

            AuthUser authUser = AuthUser.of(
                    resultSet.getString("user_id"),
                    email,
                    resultSet.getString("password"),
                    resultSet.getString("role")
            );

            return Optional.of(authUser);
        }, email);
    }

    public void addAccessToken(String userId, String accessToken) {
        jdbcTemplate.update("""
                        INSERT INTO access_tokens (token, user_id, created, updated)
                        VALUES (?, ?, NOW(), NOW())
                        """,
                accessToken, userId
        );
    }

    public Optional<AuthUser> findByAccessToken(String accessToken) {
        String query = """
                SELECT users.user_id, users.role
                FROM users
                JOIN access_tokens ON access_tokens.user_id=users.user_id
                WHERE access_tokens.token=?
                """;

        return jdbcTemplate.query(query, resultSet -> {
            if (!resultSet.next()) {
                return Optional.empty();
            }

            AuthUser authUser = AuthUser.authenticated(
                    resultSet.getString("user_id"),
                    resultSet.getString("role"),
                    accessToken
            );

            return Optional.of(authUser);
        }, accessToken);
    }

    public void removeAccessToken(String accessToken) {
        jdbcTemplate.update("""
                        DELETE FROM access_tokens WHERE token=?
                        """,
                accessToken
        );
    }
}
