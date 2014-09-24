package spacetrader.data;

/**
 * @author Michael
 * The stage of a solar system's technological development
 */
public enum TechLevel {
	PREAGRICULTURAL(0, "Pre-Agricultural"),
	AGRICULTURAL(1, "Agricultural"),
	MEDIEVAL(2, "Medieval"),
	RENAISSANCE(3, "Renaissance"),
	EARLYINDUSTRIAL(4, "Early Industrial"),
	INDUSTRIAL(5, "Industrial"),
	POSTINDUSTRIAL(6, "Post Industrial"),
	HIGHTECH(7, "High-Tech");
	
	private int value;
	private String text;
	private TechLevel(int _value, String _text) {
		this.value = _value;
		this.text = _text;
	}
	
	@Override
	public String toString() {
		return this.text;
	}
        
        public int getValue() {
            return this.value;
        }
}
