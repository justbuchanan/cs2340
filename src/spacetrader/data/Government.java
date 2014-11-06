package spacetrader.data;

public enum Government {

    ANARCHY(0, "Anarchy"),
    CAPITALIST(1, "Capitalist State"),
    COMMUNIST(2, "Communist State"),
    CONFEDERACY(3, "Confederacy State"),
    CORPORATE(4, "Corporate State"),
    CYBERNETIC(5, "Cybernetic State"),
    DEMOCRACY(6, "Democracy State"),
    DICTATORSHIP(7, "Dictatorship"),
    FASCIST(8, "Fascist State"),
    FEUDAL(9, "Feudal State"),
    MILITARY(10, "Military State"),
    MONARCHY(11, "Monarchy"),
    PACIFIST(12, "Pacifist State"),
    SOCIALIST(13, "Socialist State"),
    SATORI(14, "State of Satori"),
    TECHNOCRACY(15, "Technocracy"),
    THEOCRACY(16, "Theocracy");

    private int value;
    private String text;
    
    private Government(int val, String txt) {
        this.value = val;
        this.text = txt;
    }

    /**
     * Returns the text representation of this Government.
     */
    @Override
    public String toString() {
        return this.text;
    }

    /**
     * Gets the value of the Government.
     *
     * @return numerical value for this type of Government
     */
    public int getValue() {
        return this.value;
    }

}
