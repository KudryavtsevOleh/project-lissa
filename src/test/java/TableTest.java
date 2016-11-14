import com.lissa.configs.Configurer;
import com.lissa.utils.exceptions.EmptyConnectionException;
import com.lissa.utils.exceptions.InvalidDbTypeException;
import org.junit.Test;

public class TableTest {

    @Test
    public void test() {
        try {
            Configurer.configure();
        } catch (InvalidDbTypeException e) {
            e.printStackTrace();
        } catch (EmptyConnectionException e) {
            e.printStackTrace();
        }
    }

}
