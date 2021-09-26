package tech.uwaas.peaceofinjector.client;

import tech.uwaas.peaceofinjector.core.annotation.Autowired;
import tech.uwaas.peaceofinjector.core.annotation.Component;
import tech.uwaas.peaceofinjector.core.annotation.Qualifier;
import tech.uwaas.peaceofinjector.item.Item;
import tech.uwaas.peaceofinjector.item.ItemService;
import tech.uwaas.peaceofinjector.user.User;
import tech.uwaas.peaceofinjector.user.UserService;

/**
 * @author SHUTO, Uwai
 */
@Component
public class UserItemClientComponent {

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier(value = "itemServiceImpl")
    private ItemService itemService;

    public void createUser(String name, String email) {
        User user = this.userService.createUser(name, email);
        System.out.println(String.format("\n User id: %s, name: %s, email: %s was created ! \n", //
                user.getId().toString(), user.getName(), user.getEmail()));
    }

    public void createItem(User user, String name) {
        Item item = this.itemService.createItem(user, name);
        System.out.println(String.format("\n Item id: %s, ownerId: %s, name: %s was created ! \n", //
                item.getId().toString(), item.getOwnerId().toString(), item.getName()));
    }
}
