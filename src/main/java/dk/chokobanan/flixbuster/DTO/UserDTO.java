package dk.chokobanan.flixbuster.DTO;

import dk.chokobanan.flixbuster.model.postgresql.User;

import java.util.UUID;

public class UserDTO {

    private UUID id;
    private String username;
    private boolean child;
    private String token;
    private UUID accountId;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getUuid();
        this.username = user.getUsername();
        this.child = user.isChild();
        this.accountId = user.getAccount().getUuid();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isChild() {
        return child;
    }

    public void setChild(boolean child) {
        this.child = child;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }
}
