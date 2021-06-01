package dk.chokobanan.flixbuster.repository.redis;

import java.util.Set;
import java.util.UUID;

public interface SessionManagement {

    boolean setSession(UUID userId, UUID accountId, String token);
    Set<String> getUsersLoggedIn(UUID accountId);
    boolean loginAllowed(UUID accountId);
    String addToken(UUID accountId);
    boolean hasSession(UUID userId, UUID accountId, String token);
}
