import com.lissa.bean.DbPropertyBean;
import com.lissa.configs.DbGetPropertyValues;
import com.lissa.configs.MySQLConfig;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.Objects;

public class DbTest {

    private MySQLConfig config;
    private DbPropertyBean bean;

    @Before
    public void before() {
        config = new MySQLConfig();
        bean = new DbGetPropertyValues().getProperties();
    }

    @Test
    public void createDbTest() {
        assert Objects.equals("mysql", bean.getDbType());
        assert Objects.equals("lissa_db_test", bean.getDbName());
        assert Objects.equals("root", bean.getUserName());
        assert Objects.equals("1995", bean.getPassword());
        assert Objects.equals("create", bean.getCreatingStrategy());
        Connection connection = config.createConnection(bean);
        assert connection != null;
    }

}
