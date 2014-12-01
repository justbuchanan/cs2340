package spacetrader.data;

/**
 * The type of natural resource found on a planet.
 *
 * @author Michael
 */
public enum Resource {

    NOSPECIALRESOURCES(0, "No special resources"),
    MINERALRICH(1, "Mineral rich"),
    MINERALPOOR(2, "Mineral poor"),
    DESERT(3, "Desert"),
    LOTSOFWATER(4, "Lots of water"),
    RICHSOIL(5, "Rich soil"),
    POORSOIL(6, "Poor soil"),
    RICHFAUNA(7, "Rich fauna"),
    LIFELESS(8, "Lifeless"),
    WEIRDMUSHROOMS(9, "Weird mushrooms"),
    LOTSOFHERBS(10, "Lots of herbs"),
    ARTISTIC(11, "Artistic"),
    WARLIKE(12, "Warlike");

    private int value;
    private String text;

    private Resource(int val, String txt) {
        this.value = val;
        this.text = txt;
    }

    /**
     * String representation of this Resource.
     *
     * @return info string
     */
    @Override
    public String toString() {
        return this.text;
    }

    /**
     *
     * @return
     */
    public int getValue() {
        return value;
    }
}
