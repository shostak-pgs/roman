package app.service.impl;

import app.dao.impl.OrderDaoImpl;
import app.entity.Order;
import app.service.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {
    @Mock
    private OrderDaoImpl daoMock;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void testGet() throws ServiceException {
        //Given
        Order expected = new Order(4L, 2L, 80.0);
        when(daoMock.getOrderByUserId(2L)).thenReturn(Optional.of(expected));
        //When
        Order actual = orderService.get("2");
        //Then
        assertEquals(expected, actual);
    }

    @Test(expected = org.hibernate.service.spi.ServiceException.class)
    public void testGetUnsuccessful() {
        //Given
        when(daoMock.getOrderByUserId(5L)).thenReturn(Optional.empty());
        //When
        Order actual = orderService.get("5");
    }

    @Test
    public void testCreate() {
        //Given
        Order expected = new Order(3L, 8L, 80.7);
        when(daoMock.getOrderByUserId(8L)).thenReturn(Optional.of(expected));
        //When
        Order actual = orderService.create(8L);
        //Then
        assertEquals(expected, actual);
    }

    @Test(expected = org.hibernate.service.spi.ServiceException.class)
    public void testUnsuccessfulCreate() {
        //Given
        when(daoMock.getOrderByUserId(5L)).thenReturn(Optional.empty());
        //When
        Order actual = orderService.create(5L);
    }

    @Test
    public void testUpdateByUserId() {
        //Given
        when(daoMock.updateOrderById(11.7, 1L)).thenReturn(true);
        //When
        boolean actual = orderService.updateByUserId(11.7, 1L);
        //Then
        assertTrue(actual);
    }
}
