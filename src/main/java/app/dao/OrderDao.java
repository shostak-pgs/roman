package app.dao;

import app.entity.Order;

import java.util.Optional;

public interface OrderDao {

    /**
     * Returns the order by the transferred user's id
     * @param id user's id
     * @return the {@link Order}
     */
    Optional<Order> getOrderByUserId(Long id);

    /**
     * Add to Order table by the transferred user's id
     * @param userId id user's id
     */
    void addToOrder(Long userId);

    /**
     * Update user's Order with transferred price
     * @param totalPrice price to be assigned to the order
     * @param userId id of user to update
     */
    boolean updateOrderById(double totalPrice, long userId);
}
