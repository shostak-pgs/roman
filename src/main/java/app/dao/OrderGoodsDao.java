package app.dao;

import app.entity.OrderGoods;
import java.util.List;

public interface OrderGoodsDao {

    /**
     * Return list contains all {@link OrderGoods} of the transferred orderId
     * @return the list with all {@link OrderGoods} of this order
     */
    List<OrderGoods> getByOrderId(Long orderId);

    /**
     * Add to OrderGoods table units with transferred goodId and orderId
     * @param orderId the order id
     * @param goodId the good id
     */
    boolean addToOrderGood(Long orderId, Long goodId) ;
}
