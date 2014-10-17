package spacetrader.database;

import spacetrader.models.Player;

import java.sql.*;

/**
 * This class provides access to the Database
 *
 * @author Bao
 */
public class DatabaseDAO {
    private final String JDBC = "org.sqlite.JDBC";
    private final String DB_NAME = "jdbc:sqlite:savedgame.db";

    public void execSQL(String sql) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC);
            c = DriverManager.getConnection(DB_NAME);
            stmt = c.createStatement();
            stmt.execute(sql);
            stmt.close();
            c.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public DbResponse select(DbTables table) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC);
            c = DriverManager.getConnection(DB_NAME);
            stmt = c.createStatement();
            Player p = null;
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table.name() + ";");
            if (rs.next()) {
                return new DbResponse(c, stmt, rs);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return null;
        }

        return null;
    }

    public boolean hasTable(DbTables table) {
        boolean bool;
        Connection c = null;
        try {
            Class.forName(JDBC);
            c = DriverManager.getConnection(DB_NAME);
            DatabaseMetaData dbm = c.getMetaData();
            ResultSet tables = dbm.getTables(null, null, table.name(), null);
            if (tables.next()) {
                bool = true;
            } else {
                bool = false;
            }
            c.close();
            return bool;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }

    public void dropTable(DbTables table) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC);
            c = DriverManager.getConnection(DB_NAME);
            stmt = c.createStatement();
            stmt.executeUpdate("DROP TABLE " + table.name());
            stmt.close();
            c.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void clearTable(DbTables table) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC);
            c = DriverManager.getConnection(DB_NAME);
            stmt = c.createStatement();
            stmt.executeUpdate("DELETE FROM " + table.name());
            stmt.close();
            c.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
