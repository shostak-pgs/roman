package app.utils;

import app.entity.Good;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GoodsUtil {

    /**
     * Return the name of the transferred item.
     * @param item String representation of item position, contains name and price like"The Lord of the Rings (3.5 $)"
     * @return the name of item
     */
    public static String getName(String item) {
        if(item == null) {
            throw new NullPointerException("Argument is null! Check out parameters!");
        }
        Pattern namePattern = Pattern.compile("(.+)\\s\\(([0-9]+\\.[0-9]+)\\s?.?\\)");
        Matcher matcher = namePattern.matcher(item);
        String result = "";
        while (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }

    /**
     * Calculates the amount of the completed order
     * @param orderedGoods {@link List} contains chosen goods
     * @return calculated price
     */
    public static double countTotalPrice(List<Good> orderedGoods) {
        double orderPrice = 0.0;

        for(Good good : orderedGoods) {
            orderPrice = orderPrice + good.getPrice();
        }
        return orderPrice;
    }
}
