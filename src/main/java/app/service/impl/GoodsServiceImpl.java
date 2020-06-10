package app.service.impl;

import app.dao.GoodDao;
import app.entity.Good;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Lazy
public class GoodsServiceImpl {

    private GoodDao goodDao;

    @Autowired
    public void setGoodDao(GoodDao goodDao) {
        this.goodDao = goodDao;
    }

    public GoodsServiceImpl(){}

    /**
     * @return list contains all Goods
     */
    @Transactional
    public List<Good> getGoods(){
        return goodDao.getAllGoods();
    }

    /**
     * Returns the good by the transferred name
     * @param name good's name
     * @return the {@link Good}
     */
    @Transactional
    public Good getGood(String name) {
        Optional<Good> good = goodDao.getGood(name);
        return good.orElseThrow(() -> new ServiceException("Can't get Good"));
    }

    /**
     * Returns the good by the transferred id
     * @param id good's id
     * @return the {@link Good}
     */
    @Transactional
    public Good getGood(Long id) {
        Optional<Good> good = goodDao.getGood(id);
        return good.orElseThrow(() -> new ServiceException("Can't get Good"));
    }
}
