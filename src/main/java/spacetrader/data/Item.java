package spacetrader.data;

/**
 * Types of goods that are tradable.
 *
 * @author Bao
 */
public enum Item {

    WATER(0, "Water", 0, 0, 2, 30, 3, 4, RadicalEvent.DROUGHT,
            Resource.LOTSOFWATER, Resource.DESERT, 30, 50),
    FURS(1, "Furs", 0, 0, 0, 250, 10, 10, RadicalEvent.COLD,
            Resource.RICHFAUNA, Resource.LIFELESS, 230, 280),
    FOOD(2, "Food", 1, 0, 1, 100, 5, 5, RadicalEvent.CROPFAIL,
            Resource.RICHSOIL, Resource.POORSOIL, 90, 160),
    ORE(3, "Ore", 2, 2, 3, 350, 20, 10, RadicalEvent.WAR,
            Resource.MINERALRICH, Resource.MINERALPOOR, 350, 420),
    GAMES(4, "Games", 3, 1, 6, 250, -10, 5, RadicalEvent.BOREDOM,
            Resource.ARTISTIC, null, 160, 270),
    FIREARMS(5, "Firearms", 3, 1, 5, 1250, -75, 100, RadicalEvent.WAR,
            Resource.WARLIKE, null, 600, 1100),
    MEDICINE(6, "Medicine", 4, 1, 6, 650, -20, 10, RadicalEvent.PLAGUE,
            Resource.LOTSOFHERBS, null, 400, 700),
    MACHINES(7, "Machines", 4, 3, 5, 900, -30, 5, RadicalEvent.LACKOFWORKERS,
            null, null, 600, 800),
    NARCOTICS(8, "Narcotics", 5, 0, 5, 3500, -125, 150, RadicalEvent.BOREDOM,
            Resource.WEIRDMUSHROOMS, null, 2000, 3000),
    ROBOTS(9, "Robots", 6, 4, 7, 5000, -150, 100, RadicalEvent.LACKOFWORKERS,
            null, null, 3500, 5000);

    private final int key;
    private final String name;
    //Minimum Tech Level to Produce this resource
    //(You can't buy on planets below this level)
    private final int mtlp;
    //Minimum Tech Level to Use this resource 
    //(You can't sell on planets below this level)
    private final int mtlu;
    //Tech Level which produces the most of this item
    private final int ttp; 
    private final int basePrice;
    //Price increase per tech level
    private final int ipl;
    //variance is the maximum percentage that the price can vary above or
    //below the base
    private final int var;
    //Radical price increase event, when this even happens on a planet,
    //the price may increase astronomically
    private final RadicalEvent ie;
    //When this condition is present the price of this resource is unusually low
    private final Resource cr;
    //When this condition is present, the resource is expensive
    private final Resource er;
    //Min price offered in space trade with random trader (not on a planet)
    private final int mtl;
    //Max price offered in space trade with random trader (not on a planet)
    private final int mth;

    private Item(int _key, String _name, int mtlp, int mtlu, int ttp, int base,
            int ipl, int var, RadicalEvent radEvent, Resource rscCheap,
            Resource rscCostly, int mtl, int mth) {
        this.key = _key;
        this.name = _name;
        this.mtlp = mtlp;
        this.mtlu = mtlu;
        this.ttp = ttp;
        this.basePrice = base;
        this.ipl = ipl;
        this.var = var;
        this.ie = radEvent;
        this.cr = rscCheap;
        this.er = rscCostly;
        this.mtl = mtl;
        this.mth = mth;
    }

    /**
     * Gets value of the item.
     *
     * @return value
     */
    public int getValue() {
        return key;
    }

    /**
     * Gets name of the item.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Same as getValue.
     *
     * @return
     */
    public int getKey() {
        return key;
    }

    /**
     * Gets Minimum Tech Level to produce this resource.
     * (You can't buy on planets below this level)
     *
     * @return MTLP
     */
    public int getMTLP() {
        return mtlp;
    }

    /**
     * Gets Minimum Tech Level to use this resource.
     * (You can't sell on planets below this level)
     *
     * @return MTLU
     */
    public int getMTLU() {
        return mtlu;
    }

    /**
     * Gets Tech Level which produces the most of this item.
     *
     * @return TTP
     */
    public int getTTP() {
        return ttp;
    }

    /**
     * Gets base price of the item.
     *
     * @return basePrice
     */
    public int getBasePrice() {
        return basePrice;
    }

    /**
     * Gets price increase per tech level.
     *
     * @return IPL
     */
    public int getIPL() {
        return ipl;
    }

    /**
     * Gets variance, which is the maximum percentage that the price can vary
     * above or below the base.
     *
     * @return variance
     */
    public int getVar() {
        return var;
    }

    /**
     * Gets Radical price increase event
     * When this event happens on a planet,
     * the price may increase astronomically.
     *
     * @return increase event
     */
    public RadicalEvent getIE() {
        return ie;
    }

    /**
     * Gets the condition when the price is unusually low.
     *
     * @return cheap resource
     */
    public Resource getCR() {
        return cr;
    }

    /**
     * Gets the condition when the resource is expensive.
     *
     * @return expensive resource
     */
    public Resource getER() {
        return er;
    }

    /**
     * Gets min price offered in space trade with random traders (not on a
     * planet).
     *
     * @return minPrice
     */
    public int getMTL() {
        return mtl;
    }

    /**
     * Gets max price offered in space trade with random traders (not on a
     * planet).
     *
     * @return maxPrice
     */
    public int getMTH() {
        return mth;
    }

}
