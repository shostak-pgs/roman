package app.service.impl;

import app.dao.UserDao;
import app.dao.impl.UserDaoImpl;
import app.entity.User;
import app.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Lazy
public class UserServiceImpl {
    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    /**
     * Return the User by name. If he not exist, create new user and the return
     * @param name user's name
     * @return the User
     */
    @Transactional
    public User getUser(String name) throws ServiceException {
        Optional<User> user = userDao.getUserByName(name);
        return user.orElseGet(() -> create(name));
    }

    /**
     * Create a new user by the transferred name
     * @param name the user's name
     * @return created user
     */
    @Transactional
    public User create(String name) throws ServiceException {
        userDao.addUser(name, name);
        return userDao.getUserByName(name).orElseThrow(() -> new org.hibernate.service.spi.ServiceException("Order can't be created"));
    }
}
