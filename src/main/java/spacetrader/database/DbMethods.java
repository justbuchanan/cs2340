package spacetrader.database;

import spacetrader.data.ShipType;
import spacetrader.models.Player;
import spacetrader.models.Ship;
import spacetrader.models.Skill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip on 10/16/2014.
 */
public class DbMethods {

    static DatabaseDAO db = null;
    private final String CREATE_PLAYER_TABLE = "CREATE TABLE " + DbTables.PLAYER + " (name TEXT, balance INT, " +
            "pilot_points INT, fighter_points INT, trader_points INT, engineer_points INT, investor_points INT)";
    private final String INSERT_PLAYER_TABLE = "INSERT INTO " + DbTables.PLAYER + " (name, balance, pilot_points, " +
            "fighter_points, trader_points, engineer_points, investor_points) VALUES ('%s', %d, %d, %d, %d, %d, %d)";

    private final String CREATE_SHIP_TABLE = "CREATE TABLE " + DbTables.SHIP + " (ship_type INT, fuel INT, weapon INT, gadget INT, shield INT);";
    private final String INSERT_SHIP_TABLE = "INSERT INTO " + DbTables.SHIP + " (ship_type, fuel, weapon, gadget, shield) VALUES (%d, %d, %d, %d, %d);";

    private final String CREATE_CARGO_TABLE = "CREATE TABLE " + DbTables.CARGO + " (key INT, value INT);";
    private final String INSERT_CARGO_TABLE = "INSERT INTO " + DbTables.CARGO + " (key, value) VALUES (%d, %d);";

    public DbMethods() {
        if (db == null) {
            db = new DatabaseDAO();
            onCreate();
        }
    }

    /**
     * Handles appropriate table creation
     */
    private void onCreate() {


        if (!db.hasTable(DbTables.PLAYER)) {
            db.execSQL(CREATE_PLAYER_TABLE);
        }

        if (!db.hasTable(DbTables.SHIP)) {
            db.execSQL(CREATE_SHIP_TABLE);
        }

        if (!db.hasTable(DbTables.CARGO)) {
            db.execSQL(CREATE_CARGO_TABLE);
        }
    }

    /**
     * Loads the saved state
     *
     * @return The loaded player
     */
    public Player load() {
        Player p = loadPlayer();

        if (p != null) {
            p.setShip(loadShip());
            p.getShip().setCargo(loadCargo());
        }

        return p;
    }

    /**
     * Saves the player
     *
     * @param p The player to save
     */
    public void save(Player p) {
    	
        db.clearTable(DbTables.PLAYER);
        db.execSQL(String.format(INSERT_PLAYER_TABLE,
                p.getName(), p.getBalance(), p.getSkill(Skill.PILOT).getPoints(), p.getSkill(Skill.FIGHTER).getPoints(),
                p.getSkill(Skill.TRADER).getPoints(), p.getSkill(Skill.ENGINEER).getPoints(),
                p.getSkill(Skill.INVESTOR).getPoints()));

        db.clearTable(DbTables.SHIP);
        db.execSQL(String.format(INSERT_SHIP_TABLE,
                p.getShip().getType().ordinal(), p.getShip().getFuelReading(), p.getShip().getWeaponSlots(), p.getShip().getGadgetSlots(), p.getShip().getShieldSlots()));

        db.clearTable(DbTables.CARGO);
        for (int i = 0; i < p.getShip().getCargo().size(); i++) {
            System.out.println("Saving cargo: " + i + ", " + p.getShip().getCargo().get(i));
            db.execSQL(String.format(INSERT_CARGO_TABLE,
                    i, p.getShip().getCargo().get(i)));
        }
    }


    /**
     * Loads the player
     *
     * @return The loaded player
     */
    public Player loadPlayer() {
        DbResponse d = db.select(DbTables.PLAYER);
        Player player = null;

        try {
            if (d.r != null) {
                ResultSet rs = d.r;
                player = new Player(rs.getString("name"), rs.getInt("balance"), null, rs.getInt("pilot_points"),
                        rs.getInt("fighter_points"), rs.getInt("trader_points"), rs.getInt("engineer_points"),
                        rs.getInt("investor_points"));
                d.r.close();
                d.s.close();
                d.c.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return null;
        }

        return player;
    }

    /**
     * Loads the players ship
     *
     * @return the Loaded ship
     */
    public Ship loadShip() {
        DbResponse d = db.select(DbTables.SHIP);
        Ship ship = null;

        try {
            if (d.r != null) {
                ResultSet rs = d.r;
                ship = new Ship(ShipType.values()[rs.getInt("ship_type")], rs.getInt("fuel"));
                ship.setWeaponSlots(rs.getInt("weapon"));
                ship.setGadgetSlots(rs.getInt("gadget"));
                ship.setShieldSlots(rs.getInt("shield"));
                d.r.close();
                d.s.close();
                d.c.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return null;
        }

        return ship;
    }

    /**
     * Loads the cargo
     *
     * @return The loaded cargo
     */
    public List<Integer> loadCargo() {
        DbResponse d = db.select(DbTables.CARGO);
        List<Integer> cargo = new ArrayList<>();

        try {
            if (d.r != null) {
                do {
                    ResultSet rs = d.r;
                    cargo.add(rs.getInt("key"), rs.getInt("value"));
                    System.out.println("Loading cargo: " + rs.getInt("key") + ", " + rs.getInt("value"));
                }
                while (d.r.next());
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            return null;
        }

        return cargo;
    }
}
