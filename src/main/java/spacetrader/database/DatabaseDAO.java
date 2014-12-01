package spacetrader.database;

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
        Connection con;
        Statement stmt;
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
        Connection con;
        Statement stmt;
        DbResponse dbr = null;
        try {
            Class.forName(JDBC);
            con = DriverManager.getConnection(DB_NAME);
            stmt = con.createStatement();
            //Player player = null;
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table.name()
                    + ";");
            if (rs.next()) {
                dbr = new DbResponse(con, stmt, rs);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return dbr;
    }

    public boolean hasTable(DbTables table) {
        boolean bool = false;
        Connection con;
        try {
            Class.forName(JDBC);
            con = DriverManager.getConnection(DB_NAME);
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, table.name(), null);
            bool = tables.next();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return bool;
    }

    public void dropTable(DbTables table) {
        Connection con;
        Statement stmt;
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
        Connection con;
        Statement stmt;
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
