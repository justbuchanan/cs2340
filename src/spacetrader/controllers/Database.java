package spacetrader.controllers;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import spacetrader.models.Player;

/**
 * This class provides access to the Database
 * @author Bao
 */
public class Database {
    private final String JDBC = "org.sqlite.JDBC";
    private final String DB_NAME = "jdbc:sqlite:spacetrader.db";

    public Database() {
        if (!hasTable("player")) {
            execSQL("CREATE TABLE PLAYER (NAME TEXT NOT NULL, P0 INT NOT NULL, P1 INT NOT NULL, P2 INT NOT NULL, P3 INT NOT NULL, P4 INT NOT NULL)");
        }
    }
    
    public void addPlayer(Player p) {
        execSQL(String.format("INSERT INTO PLAYER (NAME, P0, P1, P2, P3, P4) VALUES ('%s', %d, %d, %d, %d, %d)",
                p.getName(), p.getPilotPoints(), p.getFighterPoints(), p.getTraderPoints(), p.getEngineerPoints(), p.getInvestorPoints()));
    }
    
    public Player getPlayer() {
        Connection c = null;
        Statement stmt = null;
        try {
          Class.forName(JDBC);
          c = DriverManager.getConnection(DB_NAME);
          stmt = c.createStatement();
          Player p = null;
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PLAYER;" );
            if (rs.next()) {
                String name = rs.getString("name");
                int[] skills = new int[5];
                skills[0] = rs.getInt("p0");
                skills[1] = rs.getInt("p1");
                skills[2] = rs.getInt("p2");
                skills[3] = rs.getInt("p3");
                skills[4] = rs.getInt("p4");
                p = new Player(name, skills);
            }
          rs.close();
          stmt.close();
          c.close();
          return p;
        } catch (ClassNotFoundException | SQLException e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
          return null;
        }
    }
    
    private void execSQL(String s) {
        Connection c = null;
        Statement stmt = null;
        try {
          Class.forName(JDBC);
          c = DriverManager.getConnection(DB_NAME);
          stmt = c.createStatement();
          String sql = s;
          stmt.executeUpdate(sql);
          stmt.close();
          c.close();
        } catch (ClassNotFoundException | SQLException e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }
    
    public boolean hasTable(String s) {
        boolean bool;
        Connection c = null;
        try {
            Class.forName(JDBC);
            c = DriverManager.getConnection(DB_NAME);
            DatabaseMetaData dbm = c.getMetaData();
            // check if "employee" table is there
            ResultSet tables = dbm.getTables(null, null, s, null);
            if (tables.next()) {
              bool = true;
            }
            else {
              bool = false;
            }
            c.close();
            return bool;
        } catch (ClassNotFoundException | SQLException e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
          return false;
        }
    }
    
    public void dropTable(String s) {
        Connection c = null;
        Statement stmt = null;
        try {
          Class.forName(JDBC);
          c = DriverManager.getConnection(DB_NAME);
          stmt = c.createStatement();
          String sql = "DROP TABLE " + s;
          stmt.executeUpdate(sql);
          stmt.close();
          c.close();
        } catch (ClassNotFoundException | SQLException e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }
    
    public void clearTable(String s) {
        Connection c = null;
        Statement stmt = null;
        try {
          Class.forName(JDBC);
          c = DriverManager.getConnection(DB_NAME);
          stmt = c.createStatement();
          String sql = "DELETE FROM " + s;
          stmt.executeUpdate(sql);
          stmt.close();
          c.close();
        } catch (ClassNotFoundException | SQLException e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }
}
