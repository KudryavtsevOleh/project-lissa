import com.lissa.configs.Configurer;
import com.lissa.utils.exceptions.EmptyConnectionException;
import com.lissa.utils.exceptions.InvalidDbTypeException;
import org.junit.Test;

public class TableTest {

    private final String GET_ALL_TABLES = "select count(*) from lissa_db_test.tables where table_schema='lissa_db_test'";
    private final String GET_ALL_ROWS_IN_TABLE = "SELECT TABLE_ROWS FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'Cat'";

    @Test
    public void test() {
        try {
            Configurer.configure();
        } catch (InvalidDbTypeException | EmptyConnectionException e) {
            e.printStackTrace();
        }
    }

}
