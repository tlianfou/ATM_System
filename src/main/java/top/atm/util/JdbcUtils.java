package top.atm.util;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * Jdbc 工具类，获取 Jdbc Connection
 *
 * @author taifu
 */

public class JdbcUtils {
    private static DataSource dataSource;

    static {
        InputStream resource = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();

        try {
            properties.load(resource);
            DruidDataSource druid = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);

            // url
            String ipPassword = properties.getProperty("ip");
            String ipPublicKey = properties.getProperty("ipPublicKey");
            String ip = ConfigTools.decrypt(ipPublicKey, ipPassword);
            String urlHead = properties.getProperty("urlHead");
            String urlTail = properties.getProperty("urlTail");
            druid.setUrl(urlHead + ip + urlTail);

            // Password
            String password = properties.getProperty("password");
            String publicKey = properties.getProperty("publicKey");
            druid.setPassword(ConfigTools.decrypt(publicKey, password));
            druid.init();

            dataSource = druid;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }
}
