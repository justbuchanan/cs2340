package spacetrader.data;

/**
 * Radical price increase event, when this even happens on a planet, the price may increase astronomically
 * 
 * @author Bao
 */
public enum RadicalEvent {
	DROUGHT(0, "Drought"),
	COLD(1, "Cold"),
	CROPFAIL(2, "Crop fail"),
	WAR(3, "War"),
	BOREDOM(4, "Boredom"),
	PLAGUE(5, "Plague"),
	LACKOFWORKERS(6, "Lack of workers");
	
	private int priceIncrease;
	private String name;
	private RadicalEvent(int priceIncrease, String name) {
            this.priceIncrease = priceIncrease;
            this.name = name;
	}
	
	@Override
	public String toString() {
            return this.name;
	}
        
        public int getPriceIncrease() {
            return this.priceIncrease;
        }
}
