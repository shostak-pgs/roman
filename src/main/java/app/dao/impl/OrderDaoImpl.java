package app.dao.impl;

import app.dao.OrderDao;
import app.entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import javax.inject.Inject;
import java.util.Optional;

@Repository
@Lazy
public class OrderDaoImpl implements OrderDao {
    private static final String SELECT_ORDER_BY_USER = "from Order where userId = :userId";
    private static final String UPDATE_ORDER = "update Order set userId = :userId";

    private SessionFactory factory;

    @Inject
    public OrderDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    /**
     * Returns the order by the transferred user's id
     * @param userId user's id
     * @return the {@link Order}
     */
    @Override
    public Optional<Order> getOrderByUserId(Long userId){
        Optional<Order> order;
        try (Session session = factory.openSession()) {
            order = Optional.ofNullable((Order)session.createQuery(SELECT_ORDER_BY_USER).setParameter("userId", userId).uniqueResult());
        }
        return order;
    }

    /**
     * Add to Order table by the transferred user's id
     * @param userId id user's id
     */
    @Override
    public void addToOrder(Long userId) {
        try(Session session = factory.openSession()) {
            Order order = new Order();
            order.setUserId(userId);
            order.setTotalPrice(0.0);
            session.save(order);
        }
    }

    /**
     * Update user's Order with transferred price
     * @param totalPrice price to be assigned to the order
     * @param userId id of user to update
     */
    @Override
    public boolean updateOrderById(double totalPrice, long userId) {
        try (Session session = factory.openSession()) {
            session.createQuery(UPDATE_ORDER).setParameter("userId", userId).executeUpdate();
            return true;
        }
    }
}
