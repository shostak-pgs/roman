package app.service.impl;

import app.dao.OrderGoodsDao;
import app.dao.impl.OrderGoodsDaoImpl;
import app.entity.Good;
import app.entity.OrderGoods;
import app.utils.GoodsUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Lazy
public class OrderGoodsServiceImpl {
    public static final String PRINT_GOODS = "goodsList";
    public static final String PRICE = "price";

    private OrderGoodsDao orderGoodsDao;
    private GoodsServiceImpl goodService;

    /**
     * @param orderGoodsDao the {@link OrderGoodsDaoImpl}
     * @param goodService the {@link OrderGoodsDaoImpl}
     */
    @Inject
    public void setDao(OrderGoodsDaoImpl orderGoodsDao, GoodsServiceImpl goodService) {
        this.orderGoodsDao = orderGoodsDao;
        this.goodService = goodService;
    }

    /**
     * Returns the list contains goods in order. Sampling by transferred id
     * @param orderId id of order for list building
     * @return the list
     */
    @Transactional
    public List<Good> getGoods(Long orderId){
        List<OrderGoods> inCurrentOrder = orderGoodsDao.getByOrderId(orderId);
        return getGoodsInCurrentOrder(inCurrentOrder);
    }

    /**
     * Returns the map contains name of item, its price and number of it in order
     * @param orderId id of order for map building
     * @return the map
     */
    @Transactional
    public Map<String, Integer> getOrderedGoods(Long orderId) {
        Map<String, Integer> map = new HashMap<>();
        for(Good good : getGoods(orderId)) {
            String item = good.getName() + " (" + good.getPrice() + " $)";
            int value = 1;
            if (map.containsKey(item)) {
                value = map.get(item) + 1;
                map.remove(item);
            }
            map.put(item, value);
        }
        return map;
    }

    /**
     * Added product by name to the OrderGoods db table
     * @param name name of product to be added
     * @param orderId id of order in which the product included
     */
    @Transactional
    public boolean add(String name, Long orderId){
        Good good = (goodService.getGood(GoodsUtil.getName(name)));
        orderGoodsDao.addToOrderGood(orderId, good.getId());
        return true;
    }

    /**
     * Returns the list contains goods in order. Sampling by transferred OrderGoods list
     * @param goodsInCurrentOrder the OrderGoods list for good list creation
     * @return the list
     */
    protected List<Good> getGoodsInCurrentOrder(List<OrderGoods> goodsInCurrentOrder){
        List<Good> orderedGoods = new ArrayList<>();
        for (OrderGoods current : goodsInCurrentOrder) {
            orderedGoods.add(goodService.getGood(current.getGoodId()));
        }
        return orderedGoods;
    }

    /**
     * Create map with parameters for print check
     * @param basket the map contains goods
     * @return the map with parameters
     */
    @ModelAttribute("goods")
    public List<String> createPresentationParams(@NotNull Map<String, Integer> basket){
        List<String>params = new ArrayList<>();
        int i = 1;
        for (Map.Entry<String, Integer> pair : basket.entrySet()) {
            params.add(i + ") "+ pair.getKey() + " x " + pair.getValue());
            i += 1;
        }
        return params;
    }

    /**
     * Counts total price of order by id
     * @param orderId the order id
     * @return the price
     */
    public String countPrice(long orderId) {
        double orderPrice = GoodsUtil.countTotalPrice(getGoods(orderId));
        return String.format("%.1f", orderPrice);
    }
}
