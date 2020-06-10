package app.dao.impl;

import app.dao.GoodDao;
import app.entity.Good;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Repository
@Lazy
public class GoodDaoImpl implements GoodDao {
    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String SELECT_GOOD_SQL_STATEMENT = "from Good where name = :name";
    private static final String SELECT_GOOD_BY_ID_SQL_STATEMENT = "from Good where id = :id";
    private static final String SELECT_ALL_GOODS = "from Good";

    private SessionFactory factory;

    @Inject
    public GoodDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    /**
     * Returns the good by the transferred name
     * @param name good's name
     * @return the {@link Good}
     */
    @Override
    public Optional<Good> getGood(String name) {
        Optional<Good> good;
        try (Session session = factory.openSession()) {
            good = Optional.ofNullable((Good) session.createQuery(SELECT_GOOD_SQL_STATEMENT).setParameter(NAME_COLUMN, name).uniqueResult());
        }
        return good;
    }

    /**
     * Returns the good by the transferred id
     * @param goodId good's id
     * @return the {@link Good}
     */
    @Override
    public Optional<Good> getGood(Long goodId) {
        Optional<Good> good;
        try (Session session = factory.openSession()) {
            good = Optional.ofNullable((Good) session.createQuery(SELECT_GOOD_BY_ID_SQL_STATEMENT).setParameter(ID_COLUMN, goodId).uniqueResult());
        }
        return good;
    }

    /**
     * Return list contains all Goods in base
     * @return the list
     */
    @Override
    public List<Good> getAllGoods() {
        List<Good> goods;
        try (Session session = factory.openSession()) {
            goods = session.createQuery(SELECT_ALL_GOODS).getResultList();
        }
        return goods;
    }
}
