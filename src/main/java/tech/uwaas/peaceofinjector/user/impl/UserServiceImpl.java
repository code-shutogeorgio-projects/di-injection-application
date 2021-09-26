package tech.uwaas.peaceofinjector.user.impl;

import tech.uwaas.peaceofinjector.core.annotation.Component;
import tech.uwaas.peaceofinjector.user.User;
import tech.uwaas.peaceofinjector.user.UserService;

/**
 * @author SHUTO, Uwai
 */
@Component
public class UserServiceImpl implements UserService {

    @Override
    public User createUser(String name, String email) {
        return new User(name, email);
    }


    @Override
    public User updateEmail(User user, String email) {
        return user.updateEmail(email);
    }
}
