package utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SQLExecutor {
    String domain;
    String port;
    String user;
    String password;
    String database;

    String connectionUrl = "jdbc:sqlserver://" + domain + ":" + port + ";"
            + "database=" + database + ";"
            + "encrypt=false;"
            + "trustServerCertificate=false;"
            + "loginTimeout=30;";

    Connection connection = null;

    public SQLExecutor(String domain, String port, String user, String password, String database) {
        this.domain = domain;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;

        this.connection = getConnection(domain, port, database, user, password);
    }

    public SQLExecutor(String domain, String port, String user, String password) {
        this.domain = domain;
        this.port = port;
        this.user = user;
        this.password = password;

        this.connection = getConnection(domain, port, user, password);
    }

    public SQLExecutor(String domain, String user, String password) {
        this.domain = domain;
        this.user = user;
        this.password = password;

        this.connection = getConnection(domain, user, password);
    }

    private Connection getConnection(String domain, String user, String password) {
        connectionUrl = "jdbc:sqlserver://" + domain + ";"
                + "encrypt=false;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;";
        try {
            return DriverManager.getConnection(connectionUrl, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection(String domain, String port, String user, String password) {
        connectionUrl = "jdbc:sqlserver://" + domain + ":" + port + ";"
                + "encrypt=false;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;";
        try {
            return DriverManager.getConnection(connectionUrl, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection(String domain, String port, String database, String user, String password) {
        Connection connection = null;
        connectionUrl = "jdbc:sqlserver://" + domain + ":" + port + ";"
                + "database=" + database + ";"
                + "encrypt=false;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;";
        try {
            connection = DriverManager.getConnection(connectionUrl, user, password);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return connection;

    }

    public void execute(String sql) {
        try {
            System.out.println(sql);
            connection.createStatement().execute(sql);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public ResultSet query(String sql) {
        try {
            System.out.println(sql);
            return connection.createStatement().executeQuery(sql);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public DatabaseMetaData getMetadata() {
        DatabaseMetaData dbMetadata = null;
        try {
            dbMetadata = this.connection.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbMetadata;
    }

    public List<String> getDatabases() {
        List<String> databases = new LinkedList<>();
        try {
            ResultSet rs = getMetadata().getCatalogs();
            while (rs.next()) {
                databases.add(rs.getString("TABLE_CAT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return databases;
    }

    public List<String> getTables(String database) {
        List<String> tables = new LinkedList<>();
        try {
            ResultSet rs = getMetadata().getTables(database, null, null, null);
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    public Boolean databaseExists(String database) {
        return getDatabases().contains(database);
    }

    public Boolean tableExists(String database, String table) {
        return getTables(database).contains(table);
    }
}