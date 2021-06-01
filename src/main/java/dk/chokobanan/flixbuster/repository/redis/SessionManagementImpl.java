package dk.chokobanan.flixbuster.repository.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public class SessionManagementImpl implements SessionManagement {

    private Jedis jedis;
    private long sessionTime;

    public SessionManagementImpl() {

        this.jedis = new Jedis("localhost", 6379);
        this.sessionTime = 60*30;

    }


    @Override
    public boolean setSession(UUID userId, UUID accountId, String token) {
        boolean exists = this.jedis.sismember("tokens:" + accountId, token);
        if(exists) {
            try (Transaction transaction = jedis.multi()) {

                transaction.set("session:" + accountId + ":" + userId, token);
                Response<Long> success = transaction.expire("session:" + accountId + ":" + userId, this.sessionTime);
                transaction.exec();
                return success.get() == 1;
            }
        }

        return false;
    }

    @Override
    public Set<String> getUsersLoggedIn(UUID accountId) {

        return this.jedis.keys("session:" + accountId + "*");

    }

    @Override
    public boolean loginAllowed(UUID accountId) {

        Set<String> sessions = getUsersLoggedIn(accountId);

        return sessions.size() < 2;
    }

    @Override
    public String addToken(UUID accountId) {

        StringBuilder builder = new StringBuilder();
        long currentTimeInMilisecond = Instant.now().toEpochMilli();
        String token = builder.append(currentTimeInMilisecond).append("-").append(UUID.randomUUID().toString()).toString();

        try (Transaction transaction = jedis.multi()) {

            transaction.sadd("tokens:" + accountId, token);
            transaction.expire("tokens:" + accountId, this.sessionTime);
            transaction.exec();
        }

        return token;

    }

    @Override
    public boolean hasSession(UUID userId, UUID accountId, String token) {

        String savedToken = this.jedis.get("session:" + accountId + ":" + userId);

        if(savedToken != null && token != null) {
            return token.equals(savedToken);
        }

        return false;
    }
}
