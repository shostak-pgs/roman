package app.dao.impl;

import app.dao.OrderGoodsDao;
import app.entity.OrderGoods;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import javax.inject.Inject;
import java.util.List;

@Repository
@Lazy
public class OrderGoodsDaoImpl implements OrderGoodsDao {
    private static final String SELECT_ORDER_GOOD_BY_ORDER_ID = "from OrderGoods where orderId = :orderId";

    private SessionFactory factory;

    @Inject
    public OrderGoodsDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    /**
     * Return list contains all {@link OrderGoods} of the transferred orderId
     * @return the list with all {@link OrderGoods} of this order
     */
    @Override
    public List<OrderGoods> getByOrderId(Long orderId) {
        List<OrderGoods> orderGoods;
        try(Session session = factory.openSession()) {
            orderGoods = session.createQuery(SELECT_ORDER_GOOD_BY_ORDER_ID).setParameter("orderId", orderId).getResultList();
        }
        return orderGoods;
    }

    /**
     * Add to OrderGoods table units with transferred goodId and orderId
     * @param orderId the order id
     * @param goodId the good id
     */
    @Override
    public boolean addToOrderGood(Long orderId, Long goodId) {
        boolean isUpdated;
        try(Session session = factory.openSession()) {
            OrderGoods orderGood = new OrderGoods();
            orderGood.setOrderId(orderId);
            orderGood.setGoodId(goodId);
            session.save(orderGood);
            isUpdated = true;
        }
        return isUpdated;
    }
}
