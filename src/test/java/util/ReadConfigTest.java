package util;

import client.util.GlobalConfig;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Created by xinszhou on 5/25/16.
 */


public class ReadConfigTest {

    @Test
    public void readHostPort() {
        assertTrue("icam-dev-mysql1".equals(GlobalConfig.host));
        assertTrue(9200 == GlobalConfig.port);
    }
}
