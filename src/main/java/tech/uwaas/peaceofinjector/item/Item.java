package tech.uwaas.peaceofinjector.item;

import tech.uwaas.peaceofinjector.user.User;

import java.util.UUID;

/**
 * @author SHUTO, Uwai
 */
public class Item {

    private UUID id;

    private UUID ownerId;

    private String name;

    public UUID getId() {
        return this.id;
    }

    public UUID getOwnerId() {
        return this.ownerId;
    }

    public String getName() {
        return this.name;
    }

    public Item(User user, String name) {
        this.id = UUID.randomUUID();
        this.ownerId = user.getId();
        this.name = name;
    }

    public Item updateOwner(User user) {
        this.ownerId = user.getId();
        return this;
    }

}
