package app.config;

import app.entity.Good;
import app.entity.Order;
import app.entity.OrderGoods;
import app.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:hibernate.properties")
@EnableTransactionManagement
public class HibernateUtils {
    private final static String URL = "jdbc.url";
    private final static String DB_DRIVER = "jdbc.driverClassName";
    private static final String CREATE_STRATEGY = "hibernate.hbm2ddl.auto";
    private final static String DIALECT = "hibernate.dialect";
    private final static String SHOW_SQL = "hibernate.show_sql";
    private final static String USER_NAME = "jdbc.username";
    private final static String PASSWORD = "jdbc.password";
    private final static String PACKAGE = "app";

    private final Environment environment;

    public HibernateUtils(Environment environment) {
        this.environment = environment;
    }

    /**
     * Create the bean for building the Session factory
     * @return the {@link LocalSessionFactoryBean} that creates a Hibernate SessionFactory
     */
    @Bean
    @Lazy
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setAnnotatedClasses(Good.class, Order.class, User.class, OrderGoods.class);
        sessionFactory.setPackagesToScan(PACKAGE);
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    /**
     * Returns the factory for connections to the physical data source that this DataSource object represents.
     * @return the{@link DataSource}
     */
    @Bean
    @Lazy
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty(DB_DRIVER));
        dataSource.setUrl(environment.getRequiredProperty(URL));
        dataSource.setUsername(environment.getRequiredProperty(USER_NAME));
        dataSource.setPassword(environment.getRequiredProperty(PASSWORD));
        return dataSource;
    }

    /**
     * @return the{@link HibernateTransactionManager} for transactional actions with data base
     */
    @Bean
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    /**
     * Returns the property object for hibernate configure
     * @return the {@link Properties} object
     */
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(DIALECT, environment.getRequiredProperty(DIALECT));
        properties.put(SHOW_SQL, environment.getRequiredProperty(SHOW_SQL));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty(CREATE_STRATEGY));
        return properties;
    }
}
