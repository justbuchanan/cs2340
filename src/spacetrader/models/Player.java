package spacetrader.models;

import spacetrader.data.ShipType;

/**
 * holds player's information
 *
 * @author Bao
 */
public class Player {

    private String name;
    private int balance;
    private Ship ship;

    private Skill pilot = Skill.PILOT;
    private Skill fighter = Skill.FIGHTER;
    private Skill trader = Skill.TRADER;
    private Skill engineer = Skill.ENGINEER;
    private Skill investor = Skill.INVESTOR;

    /**
     * Player constructor Constructs a player with name and allocated skill
     * points array length must be 5
     *
     * @param name      name of the player
     * @param pointsArr array of skill points
     */
    public Player(String name, int[] pointsArr) {
        this(name, 100000, null,  pointsArr[0], pointsArr[1], pointsArr[2], 
                pointsArr[3], pointsArr[4]);
        System.out.println("Created new player");
        System.out.println(this);
    }


    /**
     *
     * @param name name of the player
     * @param balance players balance
     * @param ship
     * @param pilotPoints players pilot points
     * @param fighterPoints players fighter points
     * @param traderPoints players trader points
     * @param engineerPoints players engineer points
     * @param investorPoints players investor points
     */
    public Player(String name, int balance, Ship ship, int pilotPoints,
            int fighterPoints, int traderPoints, int engineerPoints,
            int investorPoints) {
        this.name = name;
        this.balance = balance; //New player starts with 1000 credits
        this.pilot.setPoints(pilotPoints);
        this.fighter.setPoints(fighterPoints);
        this.trader.setPoints(traderPoints);
        this.engineer.setPoints(engineerPoints);
        this.investor.setPoints(investorPoints);
        this.ship = ship == null ?  new Ship(ShipType.GNAT) : ship;
    }

//<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">

    /**
     * Returns player's name
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets player's name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the Player's balance
     *
     * @return amount of cash Player currently has
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Sets the Player's cash to a new amount
     *
     * @param balance
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Gets the Player's ship
     *
     * @return
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Sets the Player's Ship
     *
     * @param ship new vessel
     */
    public void setShip(Ship ship) {
        this.ship = ship;
    }
//</editor-fold>

    /**
     * Returns player's info in a readable format
     *
     * @return player's info
     */
    @Override
    public String toString() {
        return String.format("Name:\t%s\nPilot:\t%s\nFighter:\t%s\nTrader:\t%s\nEngineer:\t%s\nInvestor:\t%s", name,
                pilot.getPoints(), fighter.getPoints(), trader.getPoints(),
                engineer.getPoints(), investor.getPoints());
    }

    public Skill getSkill(Skill skill) {
        switch (skill) {
            case PILOT:
                return pilot;
            case FIGHTER:
                return fighter;
            case TRADER:
                return trader;
            case ENGINEER:
                return engineer;
            case INVESTOR:
                return investor;
            default:
                return null;
        }
    }

    public void setSkill(Skill skill, int points) {
        Skill s = getSkill(skill);
        if (s != null) {
            s.setPoints(points);
        }
    }
}
