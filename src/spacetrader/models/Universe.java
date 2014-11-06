package spacetrader.models;

import spacetrader.data.Resource;
import spacetrader.data.TechLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Michael
 * Represents the game universe. Holds solar systems and spatial dimensions.
 */
public class Universe {
    private ArrayList<SolarSystem> solarSystems;
    private static final double FUEL_TO_DISTANCE_RATIO = 0.5;
    private int width;
    private int height;

    public Universe() {
        solarSystems = new ArrayList<SolarSystem>();
    }

    /**
     * Determines if this Ship can travel between these SolarSystems.
     *
     * @param ship   Ship which will travel
     * @param curSS  starting SolarSystem
     * @param destSS destination SolarSystem
     * @return true if this trip is possible
     */
    public static boolean shipCanTravel(Ship ship, SolarSystem curSS, 
            SolarSystem destSS) {
        if (ship.getFuelReading() >= calcFuelRequired(curSS, destSS)) 
            return true;
        else return false;
    }

    /**
     * Calculates the amount of fuel required to travel between SolarSystems.
     *
     * @param startSS starting SolarSystem
     * @param destSS  destination SolarSystem
     * @return fuel required for this trip
     */
    public static int calcFuelRequired(SolarSystem startSS, SolarSystem destSS) {
        return (int) (calcDistance(startSS, destSS)*FUEL_TO_DISTANCE_RATIO);
    }

    /**
     * Calculates the distance between 2 SolarSystems.
     *
     * @param ss1 first planet
     * @param ss2 second planet
     * @return Euclidean distance
     */
    public static double calcDistance(SolarSystem ss1, SolarSystem ss2) {
        int xDist = Math.abs(ss1.getX() - ss2.getX());
        int yDist = Math.abs(ss1.getY() - ss2.getY());
        return Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
    }

    /**
     * Creates a new universe based on the given parameters.
     *
     * @param _width          width of the universe
     * @param _height         height of the universe
     * @param numSolarSystems number of solar systems in the universe
     * @return the newly created Universe
     */
    public static Universe generateUniverse(int _width, int _height,
            int numSolarSystems) {
        shuffleArray(SolarSystemName);
        Random rand = new Random();
        Universe universe = new Universe();
        universe.width = _width;
        universe.height = _height;
        for (int i = 0; i < numSolarSystems && i < SolarSystemName.length;
                i++) {
            SolarSystem ss = new SolarSystem();
            ss.setName(SolarSystemName[i]);
            int xCoord, yCoord;
            do {
                xCoord = rand.nextInt(universe.width);
                yCoord = rand.nextInt(universe.height);
            } while (universe.hasSolarSystemAt(xCoord, yCoord));
            ss.setX(xCoord);
            ss.setY(yCoord);
            Resource rsc =
                    Resource.values()[rand.nextInt(Resource.values().length)];
            ss.setResource(rsc);
            TechLevel tech =
                    TechLevel.values()[rand.nextInt(TechLevel.values().length)];
            ss.setTechLevel(tech);
            ss.setMP();
            ss.setSy();
            universe.solarSystems.add(ss);
        }
        return universe;
    }

    /**
     * Checks whether a solar system exists at a given point in space.
     *
     * @param _x x coordinate
     * @param _y y coordinate
     * @return true if this point is the location of a solar system
     */
    public boolean hasSolarSystemAt(int _x, int _y) {
        if (_x < 0 || _x > width - 1 || _y < 0 || _y > height - 1) return false;
        for (SolarSystem ss : solarSystems) {
            if (ss.getX() == _x && ss.getY() == _y) return true;
        }
        return false;
    }

    /**
     * Finds a solar system by name.
     *
     * @param _name name to search for
     * @return true if a solar system with this name exists in the Universe
     */
    public boolean hasSolarSystemName(String _name) {
        for (SolarSystem ss : solarSystems) {
            if (ss.getName().equals(_name)) return true;
        }
        return false;
    }

    /**
     * Returns string representation of the universe.
     *
     * @return String universe information
     */
    @Override
    public String toString() {
        String str = "";
        str += "dimensions: " + width + "x" + height + "\n";
        str += "solar systems: \n";
        for (SolarSystem ss : solarSystems) {
            str += "\t" + ss.toString() + "\n";
        }
        return str;
    }

    /**
     * Sets the width of the Universe.
     *
     * @param _width
     */
    private void setWidth(int _width) {
        this.width = _width;
    }

    /**
     * Returns the width of the universe.
     *
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the height of the Universe.
     *
     * @param _height
     */
    private void setHeight(int _height) {
        this.height = _height;
    }

    /**
     * Gets height of the Universe.
     *
     * @return height of the Universe
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns list of all solar systems in the Universe.
     *
     * @return list of SolarSystems
     */
    public List<SolarSystem> getSolarSystems() {
        return solarSystems;
    }

    /**
     * array shuffling helper.
     *
     * @param ar input array
     */
    public static void shuffleArray(String[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    /**
     * Contains all potential names for solar systems.
     */
    public static String[] SolarSystemName =
            {
                    "Acamar",
                    "Adahn",        
                    "Aldea",
                    "Andevian",
                    "Antedi",
                    "Balosnee",
                    "Baratas",
                    "Brax",        // One of the heroes in Master of Magic
                    "Bretel",        
                    "Calondia",
                    "Campor",
                    "Capelle",        
                    "Carzon",
                    "Castor",        // A Greek demi-god
                    "Cestus",
                    "Cheron",
                    "Courteney",    // After Courteney Cox
                    "Daled",
                    "Damast",
                    "Davlos",
                    "Deneb",
                    "Deneva",
                    "Devidia",
                    "Draylon",
                    "Drema",
                    "Endor",
                    "Esmee",        
                    "Exo",
                    "Ferris",        // Iron
                    "Festen",        // A great Scandinavian movie
                    "Fourmi",        // An ant, in French
                    "Frolix",        
                    "Gemulon",
                    "Guinifer",        
                    "Hades",        // The underworld
                    "Hamlet",        // From Shakespeare
                    "Helena",        // Of Troy
                    "Hulst",        // A Dutch plant
                    "Iodine",        // An element
                    "Iralius",
                    "Janus",        // A seldom encountered Dutch boy's name
                    "Japori",
                    "Jarada",
                    "Jason",        // A Greek hero
                    "Kaylon",
                    "Khefka",
                    "Kira",        // My dog's name
                    "Klaatu",        // From a classic SF movie
                    "Klaestron",
                    "Korma",        // An Indian sauce
                    "Kravat",        
                    "Krios",
                    "Laertes",        // A king in a Greek tragedy
                    "Largo",
                    "Lave",        // The starting system in Elite
                    "Ligon",
                    "Lowry",            
                    "Lylat",
                    "Magrat",        
                    "Malcoria",
                    "Melina",
                    "Mentar",        
                    "Merik",
                    "Mintaka",
                    "Montor",        
                    "Mordan",
                    "Myrthe",        // The name of my daughter
                    "Nelvana",
                    "Nix",        
                    "Nyle",        // An interesting spelling of the great river
                    "Odet",
                    "Og",        
                    "Omega",        // The end of it all
                    "Omphalos",        // Greek for navel
                    "Orias",
                    "Othello",        // From Shakespeare
                    "Parade",        
                    "Penthara",
                    "Picard",        // The enigmatic captain from ST:TNG
                    "Pollux",        // Brother of Castor
                    "Quator",
                    "Rakhar",
                    "Ran",        // A film by Akira Kurosawa
                    "Regulas",
                    "Relva",
                    "Rhymus",
                    "Rochani",
                    "Rubicum",        
                    "Rutia",
                    "Sarpeidon",
                    "Sefalla",
                    "Seltrice",
                    "Sigma",
                    "Sol",        // That's our own solar system
                    "Somari",
                    "Stakoron",
                    "Styris",
                    "Talani",
                    "Tamus",
                    "Tantalos",        // A king from a Greek tragedy
                    "Tanuga",
                    "Tarchannen",
                    "Terosa",
                    "Thera",        // A seldom encountered Dutch girl's name
                    "Titan",        // The largest moon of Jupiter
                    "Torin",        // A hero from Master of Magic
                    "Triacus",
                    "Turkana",
                    "Tyrus",
                    "Umberlee",        
                    "Utopia",        // The ultimate goal
                    "Vadera",
                    "Vagra",
                    "Vandor",
                    "Ventax",
                    "Xenon",
                    "Xerxes",        // A Greek hero
                    "Yew",        
                    "Yojimbo",        // A film by Akira Kurosawa
                    "Zalkon",
                    "Zuul"        // From the first Ghostbusters movie
            };

}