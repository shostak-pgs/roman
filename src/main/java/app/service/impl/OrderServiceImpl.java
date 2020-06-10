package app.service.impl;

import app.dao.OrderDao;
import app.entity.Order;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Lazy
public class OrderServiceImpl {
    private OrderDao orderDao;

    @Inject
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * Return the Order by User's id. If it not exist, create a new order and the return
     * @param stringId user's id
     * @return the Order
     */
    @Transactional
    public Order get(String stringId) {
        Long id = Long.parseLong(stringId);
        Optional<Order> order = orderDao.getOrderByUserId(id);
        return order.orElseGet(() -> create(id));
    }

    /**
     * Create a new Order by the transferred user's id
     * @param id the user's id
     * @return created user
     */
    @Transactional
    Order create(Long id){
        orderDao.addToOrder(id);
        return orderDao.getOrderByUserId(id).orElseThrow(() -> new org.hibernate.service.spi.ServiceException("Order can't be created"));
    }

    /**
     * Clears basket when the order is finished
     */
    public boolean updateByUserId(double orderPrice, Long orderId) {
        return orderDao.updateOrderById(orderPrice, orderId);
    }
}