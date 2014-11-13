package spacetrader.database;

import spacetrader.models.Player;

import java.sql.*;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * This class provides access to the Database.
 *
 * @author Bao
 */
public class DatabaseDAO {
    private final String JDBC = "org.sqlite.JDBC";
    private final String DB_NAME = "jdbc:sqlite:savedgame.db";

    public void execSQL(String sql) {
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC);
            con = DriverManager.getConnection(DB_NAME);
            stmt = con.createStatement();
            stmt.execute(sql);
            stmt.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public DbResponse select(DbTables table) {
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC);
            con = DriverManager.getConnection(DB_NAME);
            stmt = con.createStatement();
            //Player player = null;
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table.name()
                    + ";");
            if (rs.next()) {
                return new DbResponse(con, stmt, rs);
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
        Connection con = null;
        try {
            Class.forName(JDBC);
            con = DriverManager.getConnection(DB_NAME);
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, table.name(), null);
            if (tables.next()) {
                bool = true;
            } else {
                bool = false;
            }
            con.close();
            return bool;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return false;
        }
    }

    public void dropTable(DbTables table) {
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC);
            con = DriverManager.getConnection(DB_NAME);
            stmt = con.createStatement();
            stmt.executeUpdate("DROP TABLE " + table.name());
            stmt.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void clearTable(DbTables table) {
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC);
            con = DriverManager.getConnection(DB_NAME);
            stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM " + table.name());
            stmt.close();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
