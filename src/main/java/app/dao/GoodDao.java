package app.dao;

import app.entity.Good;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GoodDao{

    /**
     * Returns the good by the transferred name
     * @param name good's name
     * @return the {@link Good}
     */
    Optional<Good> getGood(String name);

    /**
     * Returns the good by the transferred id
     * @param goodId good's id
     * @return the {@link Good}

     */
    Optional<Good> getGood(Long goodId);

    /**
     * Return list contains all Goods in base
     * @return the list
     */
    List<Good> getAllGoods();
}
