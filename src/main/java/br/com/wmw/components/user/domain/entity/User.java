package br.com.wmw.components.user.domain.entity;

public class User {

    private final String id;
    private final String email;
    private final String password;
    private final String customerId;
    private final String coreUrl;

    public User(String id, String email, String password, String customerId, String coreUrl) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.customerId = customerId;
        this.coreUrl = coreUrl;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCoreUrl() {
        return coreUrl;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", password=" + password + ", customerId=" + customerId
                + ", coreUrl=" + coreUrl + "]";
    }
}
