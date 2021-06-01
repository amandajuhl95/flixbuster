package dk.chokobanan.flixbuster.model.postgresql;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "account")
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_id")
    private long id;

    @GeneratedValue(generator = "UUID")
    @Type(type="org.hibernate.type.PostgresUUIDType")
    //@Column(updatable = false, nullable = false, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
    //@GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Generated(GenerationTime.INSERT)
    private UUID uuid;

    private String firstname;
    private String lastname;
    private String email;
    private String passwd;
    private String birthday;
    private String phonenumber;

    public enum Abonnement {basis, standard, premium};
    @Enumerated(EnumType.STRING)
    @Column(name="current_abonnement")
    private Abonnement abonnement;

    @OneToMany(mappedBy = "account", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    List<User> users = new ArrayList<>();

    @OneToOne(mappedBy = "account", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @PrimaryKeyJoinColumn
    private Payment payment;

    public Account() {
    }

    public Account(String firstname, String lastname, String email, String passwd, String birthday, String phonenumber, Abonnement abonnement, Payment payment) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.passwd = passwd;
        this.birthday = birthday;
        this.phonenumber = phonenumber;
        this.abonnement = abonnement;
        this.payment = payment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
        user.setAccount(this);
    }

    public User getUser(UUID id) {
        for (User user: this.users)
        {
            if(user.getUuid().equals(id))
                return user;
        }

        return null;
    }



    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void hash() {
        this.passwd += "chokobanan";
        long sum = 0, mul = 1;
        for (int i = 0; i < this.passwd.length(); i++) {
            mul = (i % 4 == 0) ? 1 : mul * 256;
            sum += this.passwd.charAt(i) * mul;
        }
        this.setPasswd(Long.toString(Math.abs(sum) % 3000));
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
