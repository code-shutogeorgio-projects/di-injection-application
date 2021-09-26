package tech.uwaas.peaceofinjector.item;

import tech.uwaas.peaceofinjector.user.User;

/**
 * @author SHUTO, Uwai
 */
public interface ItemService {

    Item createItem(User user, String name);

    Item updateOwner(Item item, User user);
}
