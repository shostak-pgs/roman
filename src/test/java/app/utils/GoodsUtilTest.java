package app.utils;

import app.entity.Good;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

public class GoodsUtilTest {
    List<Good> orderedGoods;

    @Before
    public void init(){
        orderedGoods = new ArrayList<>();
    }

    @After
    public void destroy(){ orderedGoods = null;
    }

    @Test
    public void testCountTotalPriceEmpty() {
        //Given
        Double expected = 0.0;
        //When
        Double actual = GoodsUtil.countTotalPrice(orderedGoods);
        //Then
        assertEquals(expected, actual);
    }

    @Test
    public void testCountTotalPrice() {
        //Given
        orderedGoods.add(new Good(1, "War and Peace", 1.5));
        orderedGoods.add(new Good(2, "The Great Gatsby", 1.5));
        orderedGoods.add(new Good(3, "Hamlet by William Shakespeare", 1.5));
        Double expected = 4.5;
        //When
        Double actual = GoodsUtil.countTotalPrice(orderedGoods);
        //Then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetName() {
        //Given
        String item = "The Great Gatsby (5.2 $)";
        String expected = "The Great Gatsby";
        //When
        String actual = GoodsUtil.getName(item);
        //Then
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void testGetNameEmpty() {
        //Given
        String item = null;
        //When
        String actual = GoodsUtil.getName(item);
    }

}