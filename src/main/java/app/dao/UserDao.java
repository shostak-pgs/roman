package app.dao;

import app.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    /**
     * Returns the user by the transferred name
     * @param userName user's name
     * @return the {@link User}
     */
    Optional<User> getUserByName(String userName);

    /**
     * Return list contains all users in base
     * @return the list with all users
     */
    List<User> getAllUsers();

    /**
     * Add user in base by the transferred name
     * @param userName user's name
     * @param password user's password
     */
    void addUser(String userName, String password);
}
