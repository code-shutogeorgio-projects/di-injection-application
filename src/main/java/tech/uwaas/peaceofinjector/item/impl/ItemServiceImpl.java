package tech.uwaas.peaceofinjector.item.impl;

import tech.uwaas.peaceofinjector.core.annotation.Component;
import tech.uwaas.peaceofinjector.item.Item;
import tech.uwaas.peaceofinjector.item.ItemService;
import tech.uwaas.peaceofinjector.user.User;

/**
 * @author SHUTO, Uwai
 */
@Component
public class ItemServiceImpl implements ItemService {

    @Override
    public Item createItem(User user, String name) {
        return new Item(user, name);
    }

    @Override
    public Item updateOwner(Item item, User user) {
        return item.updateOwner(user);
    }
}
