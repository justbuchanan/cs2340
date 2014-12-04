package spacetrader.data;

public enum Government {

    ANARCHY(0, "Anarchy"), // a random good is unavailable in the market
    CAPITALIST(1, "Capitalist State"),// ore buy price *2
    COMMUNIST(2, "Communist State"),// available number of goods for sale /2
    CONFEDERACY(3, "Confederacy State"),//games bought at price *2
    CORPORATE(4, "Corporate State"),//robots bought at price *2
    CYBERNETIC(5, "Cybernetic State"),//robots and machines bought at price *1.5
    DEMOCRACY(6, "Democracy State"),//drugs and games bought at price *2
    DICTATORSHIP(7, "Dictatorship"),//all items sold at price *0.9
    FASCIST(8, "Fascist State"),//machines bought at price *2
    FEUDAL(9, "Feudal State"),//firearms bought at price *2
    MILITARY(10, "Military State"),//robots bought at price *2
    MONARCHY(11, "Monarchy"),//medicine bought at price *2
    PACIFIST(12, "Pacifist State"),//refuse to sell firearms
    SOCIALIST(13, "Socialist State"),//medicine sold at price *.75
    SATORI(14, "State of Satori"),//refuse to buy firearms
    TECHNOCRACY(15, "Technocracy"),//drugs bought at price *2
    THEOCRACY(16, "Theocracy");//water bought at price *2

    private int value;
    private String text;

    private Government(int val, String txt) {
        this.value = val;
        this.text = txt;
    }

    /**
     * Returns the text representation of this Government.
     * @return text string
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
