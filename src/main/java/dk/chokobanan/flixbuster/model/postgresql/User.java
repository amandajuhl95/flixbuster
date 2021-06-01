package dk.chokobanan.flixbuster.model.postgresql;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.UUID;

import dk.chokobanan.flixbuster.model.postgresql.Account;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "flixbuster_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private long id;

    @GeneratedValue(generator = "UUID")
    @Type(type="org.hibernate.type.PostgresUUIDType")
    @Column(updatable = false, nullable = false, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Generated(GenerationTime.INSERT)
    private UUID uuid;

    private String username;
    private boolean child;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonIgnore
    private Account account;

    public User() {
    }

    public User(String username, boolean child) {
        this.username = username;
        this.child = child;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
