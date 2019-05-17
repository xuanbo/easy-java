package tk.fishfish.easyjava.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Hikari测试
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class HikariTest {

    private static final Logger LOG = LoggerFactory.getLogger(HikariTest.class);

    private DataSource dataSource;

    @Before
    public void setup() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/easyjava?useSSL=false&useUnicode=true&characterEncoding=utf-8");
        config.setUsername("root");
        config.setPassword("123456");
        config.setMinimumIdle(8);
        config.setMaximumPoolSize(32);
        // MySQL数据库编码设置为utf8mb4
        config.addDataSourceProperty("connectionInitSql", "set names utf8mb4;");
        // MySQL推荐配置
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("useServerPrepStmts", true);
        config.addDataSourceProperty("useLocalSessionState", true);
        config.addDataSourceProperty("rewriteBatchedStatements", true);
        config.addDataSourceProperty("cacheResultSetMetadata", true);
        config.addDataSourceProperty("cacheServerConfiguration", true);
        config.addDataSourceProperty("elideSetAutoCommits", true);
        config.addDataSourceProperty("maintainTimeStats", false);

        dataSource = new HikariDataSource(config);
    }

    @Test
    public void run() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement("show tables");
            rs = ps.executeQuery();
            while (rs.next()) {
                String table = rs.getString(1);
                LOG.info("table: {}", table);
            }
        } catch (SQLException e) {
            LOG.error("数据库异常", e);
        } finally {
            // 释放
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOG.error("数据库ResultSet关闭异常", e);
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LOG.error("数据库PreparedStatement关闭异常", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOG.error("数据库连接关闭异常", e);
                }
            }
        }
    }

}
