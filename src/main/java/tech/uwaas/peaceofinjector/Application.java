package tech.uwaas.peaceofinjector;

import tech.uwaas.peaceofinjector.client.UserItemClientComponent;
import tech.uwaas.peaceofinjector.core.injector.Injector;
import tech.uwaas.peaceofinjector.user.User;

/**
 * @author SHUTO, Uwai
 */
public class Application {

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());

        final String userName = "Di Tage";
        final String userEmail = "di.tage@code.berlin";
        final String itemName = "MBP Pro";

        Injector.startApplication(Application.class);

        Injector.getService(UserItemClientComponent.class).createUser(userName, userEmail);
        Injector.getService(UserItemClientComponent.class).createItem(new User(userName, userEmail), itemName);

        System.out.println(System.currentTimeMillis());
    }
}
