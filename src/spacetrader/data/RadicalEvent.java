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
	
	private int value;
	private String text;
	private RadicalEvent(int _value, String _text) {
		this.value = _value;
		this.text = _text;
	}
	
	@Override
	public String toString() {
		return this.text;
	}
}
