package client.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by xinszhou on 5/25/16.
 */
public class GlobalConfig {

    private static Config config = ConfigFactory.load();

    public static String host = config.getString("client.host");
    public static int port = config.getInt("client.port");

}
