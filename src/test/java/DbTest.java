import com.lissa.configs.MySQLConfig;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

public class DbTest {

    private MySQLConfig config;

    @Before
    public void before() {
        config = new MySQLConfig();
    }

    @Test
    public void createDbTest() {
        Connection connection = config.createConnection();
        assert connection != null;
    }

}
