package tech.uwaas.peaceofinjector.user;

/**
 * @author SHUTO, Uwai
 */
public interface UserService {
    User createUser(String name, String email);

    User updateEmail(User user, String email);
}
