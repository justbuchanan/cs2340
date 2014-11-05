package spacetrader.models;

/**
 * Holds skill info.
 *
 */
public enum Skill {
    PILOT, FIGHTER, TRADER, ENGINEER, INVESTOR;

    private int points = 0;

    /**
     * Gets points for a skill.
     *
     * @return Point value
     */
    public int getPoints() {
        return this.points;
    }

    /**
     *  Sets points for a skill.
     *
     * @param points - The point value
     */
    public void setPoints(int points) {
        this.points = points;
    }
}