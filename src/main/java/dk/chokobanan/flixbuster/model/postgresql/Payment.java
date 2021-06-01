package dk.chokobanan.flixbuster.model.postgresql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dk.chokobanan.flixbuster.model.postgresql.Account;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "payment")
public class Payment implements Serializable {

    @Id
    private long id;

    @MapsId
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "payment_id")
    private Account account;

    @Column(name="cardnumber")
    private String cardNumber;
    @Column(name="securitycode")
    private String securityCode;
    @Column(name="expirationdate")
    private String expirationDate;

    public Payment() {
    }

    public Payment(String cardNumber, String securityCode, String expirationDate) {
        this.cardNumber = cardNumber;
        this.securityCode = securityCode;
        this.expirationDate = expirationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
