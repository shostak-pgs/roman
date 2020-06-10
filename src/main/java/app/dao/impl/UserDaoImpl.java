package app.dao.impl;

import app.dao.UserDao;
import app.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Repository
@Lazy
public class UserDaoImpl implements UserDao {
    private static final String NAME_COLUMN = "userName";
    private static final String SELECT_USER = "from User where login = :userName";
    private static final String SELECT_ALL_USERS = "from User";

    private SessionFactory factory;

    @Inject
    public UserDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    /**
     * Returns the user by the transferred name
     * @param userName user's name
     * @return the {@link User}
     * error or other errors.
     */
    @Override
    public Optional<User> getUserByName(String userName) {
        Optional<User> user;
        try(Session session = factory.openSession()) {
            user = Optional.ofNullable((User)session.createQuery(SELECT_USER).setParameter(NAME_COLUMN, userName).uniqueResult());
        }
        return user;
    }

    /**
     * Return list contains all users in base
     * @return the list with all users
     * error or other errors.
     */
    @Override
    public List<User> getAllUsers(){
        List users;
        try (Session session = factory.openSession()) {
            users = session.createQuery(SELECT_ALL_USERS).getResultList();
        }
        return (List<User>) users;
    }

    /**
     * Add user in base by the transferred name
     * @param userName user's name
     * @param password user's password
     * error or other errors.
     */
    @Override
    public void addUser(String userName, String password){
        try(Session session = factory.openSession()) {
            User user = new User();
            user.setLogin(userName);
            user.setPassword(password);
            session.save(user);
        }
    }

}
