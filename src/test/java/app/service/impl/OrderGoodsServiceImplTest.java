package app.service.impl;

import app.dao.OrderGoodsDao;
import app.entity.Good;
import app.entity.OrderGoods;
import app.utils.GoodsUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderGoodsServiceImplTest {

    @Mock
    private OrderGoodsDao orderGoodsDao;

    @Mock
    private GoodsServiceImpl goodService;

    @InjectMocks
    private OrderGoodsServiceImpl orderGoodsService;

    @Test
    public void testGetGoods() {
        //Given
        List<Good> expected = Arrays.asList(new Good(1L, "Toy", 36.0),
                new Good(2L, "Toy", 2.0));
        List<OrderGoods> orderGoods = Arrays.asList(new OrderGoods(1L, 1L, 1L),
                new OrderGoods(2L, 1L, 2L));

        when(orderGoodsDao.getByOrderId(1L)).thenReturn(orderGoods);
        when(goodService.getGood(1L)).thenReturn(new Good(1L, "Toy", 36.0));
        when(goodService.getGood(2L)).thenReturn(new Good(2L, "Toy", 2.0));
        //When
        List<Good> actual = orderGoodsService.getGoods(1L);
        //Then
        assertEquals(expected, actual);

    }

    @Test
    public void testGetOrderedGoods() {
        //Given
        List<OrderGoods> orderGoods = Arrays.asList(new OrderGoods(1L, 1L, 1L),
                new OrderGoods(2L, 1L, 1L),
                new OrderGoods(3L, 1L, 2L));

        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("Toy (2.0 $)", 2);
        expectedMap.put("House (255.0 $)", 1);

        when(orderGoodsDao.getByOrderId(1L)).thenReturn(orderGoods);
        when(goodService.getGood(1L)).thenReturn(new Good(1L, "Toy", 2.0));
        when(goodService.getGood(2L)).thenReturn(new Good(2L, "House", 255.0));
        when(goodService.getGood(3L)).thenReturn(new Good(2L, "Toy", 2.0));

        //When
        Map<String, Integer> actual = orderGoodsService.getOrderedGoods(1L);
        //Then
        assertEquals(expectedMap, actual);
    }

    @Test
    public void testAdd() {
        //Given
        Good good = new Good(2L, "Book", 5.0);
        when(goodService.getGood(GoodsUtil.getName("Book 5$"))).thenReturn(good);
        when(orderGoodsDao.addToOrderGood(2L, 2L)).thenReturn(true);
        //When
        boolean actual = orderGoodsService.add("Book", 2L);
        //Then
        assertTrue(actual);
    }

    @Test
    public void testCreatePresentationParams() {
        //Given
        Map<String, Integer> data = new HashMap<>();
        data.put("Toy (2.0 $)", 2);
        data.put("House (255.0 $)", 1);
        data.put("Car (50.0 $)", 3);
        List<String> expected = new ArrayList<>();
        expected.add("1) House (255.0 $) x 1");
        expected.add("2) Car (50.0 $) x 3");
        expected.add("3) Toy (2.0 $) x 2");
        //When
        List<String> actual = orderGoodsService.createPresentationParams(data);
        //Then
        assertEquals(expected, actual);
    }

    @Test
    public void testCountPrice() {
        //Given
        List<OrderGoods> orderGoods = Arrays.asList(new OrderGoods(1L, 1L, 1L),
                new OrderGoods(2L, 1L, 2L));

        when(orderGoodsDao.getByOrderId(1L)).thenReturn(orderGoods);
        when(goodService.getGood(1L)).thenReturn(new Good(1L, "Toy", 36.0));
        when(goodService.getGood(2L)).thenReturn(new Good(2L, "Toy", 2.0));

        //When
        String actual = orderGoodsService.countPrice(1L);
        //Then
        assertEquals(actual, "38,0");
    }
}