package dk.chokobanan.flixbuster.DTO;

import dk.chokobanan.flixbuster.model.postgresql.Account;
import dk.chokobanan.flixbuster.model.postgresql.Payment;
import dk.chokobanan.flixbuster.model.postgresql.User;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountDTO {

    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private String birthday;
    private String phonenumber;
    private Account.Abonnement abonnement;
    private List<UserDTO> users = new ArrayList<>();
    private String token;

    public AccountDTO() {
    }

    public AccountDTO(Account account, String token) {

        this.id = account.getUuid();
        this.firstname = account.getFirstname();
        this.lastname = account.getLastname();
        this.email = account.getEmail();
        this.birthday = account.getBirthday();
        this.phonenumber = account.getPhonenumber();
        this.abonnement = account.getAbonnement();
        for (User user: account.getUsers()) {

            this.users.add(new UserDTO(user));

        }
        this.token = token;


    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public Account.Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Account.Abonnement abonnement) {
        this.abonnement = abonnement;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
