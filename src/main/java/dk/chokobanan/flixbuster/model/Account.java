package dk.chokobanan.flixbuster.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account")
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_id")
    private long id;

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
        this.passwd = this.hash(passwd);
        this.birthday = birthday;
        this.phonenumber = phonenumber;
        this.abonnement = abonnement;
        this.payment = payment;
    }

    // To persist a new account
    public Account(String firstname, String lastname, String email, String passwd, String birthday, String phonenumber, Abonnement abonnement) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.passwd = this.hash(passwd);
        this.birthday = birthday;
        this.phonenumber = phonenumber;
        this.abonnement = abonnement;
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

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    private String hash(String password) {
        long sum = 0, mul = 1;
        for (int i = 0; i < password.length(); i++) {
            mul = (i % 4 == 0) ? 1 : mul * 256;
            sum += password.charAt(i) * mul;
        }
        return Long.toString(Math.abs(sum) % 300);
    }
}
