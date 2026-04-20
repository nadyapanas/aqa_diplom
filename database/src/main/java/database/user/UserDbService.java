package database.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import database.base.DbConnection;
import database.enums.Role;

/**
 * Service for interacting with users table.
 */
public class UserDbService {

    private final DbConnection dbConnection = DbConnection.getInstance();

    public Optional<UserEntity> getUserById(Long id) {
        String sql = """
                SELECT id, username, password, first_name, last_name, role
                FROM users
                WHERE id = ?
                """;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get user by id: " + id, e);
        }
        return Optional.empty();
    }

    public Optional<UserEntity> getUserByUsername(String username) {
        String sql = """
                SELECT id, username, password, first_name, last_name, role
                FROM users
                WHERE username = ?
                """;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get user by username: " + username, e);
        }
        return Optional.empty();
    }

    public List<UserEntity> getAllUsers() {
        String sql = """
                SELECT id, username, password, first_name, last_name, role
                FROM users
                """;
        List<UserEntity> users = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                users.add(mapUser(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get users list", e);
        }
        return users;
    }

    public Long insertUser(UserEntity user) {
        String sql = """
                INSERT INTO users (username, password, first_name, last_name, role)
                VALUES (?, ?, ?, ?, ?)
                RETURNING id
                """;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getRole().name());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert user: " + user.getUsername(), e);
        }
        throw new RuntimeException("Failed to insert user: " + user.getUsername());
    }

    public UserEntity updateUser(UserEntity user) {
        String sql = """
                UPDATE users
                SET username = ?,
                    password = ?,
                    first_name = ?,
                    last_name = ?,
                    role = ?
                WHERE id = ?
                RETURNING id, username, password, first_name, last_name, role
                """;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getRole().name());
            statement.setLong(6, user.getId());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update user with id: " + user.getId(), e);
        }

        throw new RuntimeException("Failed to update, not found user with id: " + user.getId());
    }

    public Long grantUserRole(String username, String role) {
        String sql = """
                UPDATE users
                SET role = ?
                WHERE username = ?
                RETURNING id
                """;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, role);
            statement.setString(2, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to grant role %s for user with username %s: ".formatted(role, username), e);
        }

        throw new RuntimeException("Failed to grant role %s, not found user with username %s: ".formatted(role, username));
    }

    public boolean deleteUserById(Long id) {
        String sql = """
                DELETE FROM users
                WHERE id = ?
                """;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete user with id: " + id, e);
        }
    }

    public boolean deleteUserByUsername(String username) {
        String sql = """
                DELETE FROM users
                WHERE username = ?
                """;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete user with username: " + username, e);
        }
    }

    private UserEntity mapUser(ResultSet rs) throws SQLException {
        return UserEntity.builder()
                .id(rs.getLong("id"))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .role(Role.valueOf(rs.getString("role")))
                .build();
    }
}