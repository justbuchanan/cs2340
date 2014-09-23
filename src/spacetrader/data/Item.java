package spacetrader.data;

/**
 * Types of goods that are tradable.
 * 
 * @author Bao
 */
public enum Item {
	WATER(0, "Water"),
	FURS(1, "Furs"),
	FOOD(2, "Food"),
	ORE(3, "Ore"),
	GAMES(4, "Games"),
	FIREARMS(5, "Firearms"),
	MEDICINE(6, "Medicine"),
	MACHINES(7, "Machines"),
	NARCOTICS(8, "Narcotics"),
	ROBOTS(9, "Robots");
        
    private final int value;
    private final String text;
        
    private Item(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }
    
    public String getName() {
        return text;
    }
    
}
