package tech.uwaas.peaceofinjector.user;

import java.util.UUID;

/**
 * @author SHUTO, Uwai
 */
public class User {

    private final UUID id;

    private final String name;

    private String email;

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public User(String name, String email) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
    }

    public User updateEmail(String email) {
        this.email = email;
        return this;
    }
}
