package app.service.impl;

import app.dao.impl.GoodDaoImpl;
import app.entity.Good;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GoodsServiceImplTest {

    @Mock
    private GoodDaoImpl daoMock;

    @InjectMocks
    private GoodsServiceImpl goodsService;

    @Test
    public void getGoods() {
        //Given
        List<Good> expected = Arrays.asList(new Good(1L, "Toy", 2.0),
                new Good(2L, "Toy", 2.0),
                new Good(3L, "House", 255.0),
                new Good(4L, "Car", 80.0));
        when(daoMock.getAllGoods()).thenReturn(expected);
        //When
        List<Good> actual = goodsService.getGoods();
        //Then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetGood() {
        //Given
        Good expected = new Good(4L, "Car", 80.0);
        when(daoMock.getGood("Car")).thenReturn(Optional.of(expected));
        //When
        Good actual = goodsService.getGood("Car");
        //Then
        assertEquals(expected, actual);
    }

    @Test(expected = org.hibernate.service.spi.ServiceException.class)
    public void testGetGoodUnsuccessful() {
        //Given
        when(daoMock.getGood("Car")).thenReturn(Optional.empty());
        //When
        Good actual = goodsService.getGood("Car");
    }

    @Test
    public void testGetGood_ById() {
        //Given
        Good expected = new Good(3L, "House", 255.0);
        when(daoMock.getGood(3L)).thenReturn(Optional.of(expected));
        //When
        Good actual = goodsService.getGood(3L);
        //Then
        assertEquals(expected, actual);
    }

    @Test(expected = org.hibernate.service.spi.ServiceException.class)
    public void testGetGood_ByIdUnsuccessful() {
        //Given
        when(daoMock.getGood(2L)).thenReturn(Optional.empty());
        //When
        Good actual = goodsService.getGood(2L);
    }
}
