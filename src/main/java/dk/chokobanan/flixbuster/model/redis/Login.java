package dk.chokobanan.flixbuster.model.redis;

public class Login {

    private String email;
    private String password;

    public Login() {

    }

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String hash() {
        this.password += "chokobanan";
        long sum = 0, mul = 1;
        for (int i = 0; i < this.password.length(); i++) {
            mul = (i % 4 == 0) ? 1 : mul * 256;
            sum += this.password.charAt(i) * mul;
        }
        return Long.toString(Math.abs(sum) % 3000);
    }
}
