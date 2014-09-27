package spacetrader.models;

import spacetrader.data.Resource;
import spacetrader.data.TechLevel;
import spacetrader.models.SolarSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Michael
 * Represents the game universe. Holds solar systems and spatial dimensions.
 */
public class Universe {
	private ArrayList<SolarSystem> solarSystems;
	private int width;
	private int height;
	
	public Universe() {
		solarSystems = new ArrayList<SolarSystem>();
	}
	/**
	 * Creates a new universe based on the given parameters
	 * @param _width width of the universe
	 * @param _height height of the universe
	 * @param numSolarSystems number of solar systems in the universe
	 * @return the newly created Universe
	 */
	public static Universe generateUniverse(int _width, int _height, int numSolarSystems) {
                shuffleArray(SolarSystemName);
		Random rand = new Random();
		Universe universe = new Universe();
		universe.width = _width;
		universe.height = _height;
		for (int i=0; i<numSolarSystems && i<SolarSystemName.length; i++) {
			SolarSystem ss = new SolarSystem();
			ss.setName(SolarSystemName[i]);
			int xCoord, yCoord;
			do {
				xCoord = rand.nextInt(universe.width);
				yCoord = rand.nextInt(universe.height);
			} while (universe.hasSolarSystemAt(xCoord, yCoord));
			ss.setX(xCoord);
			ss.setY(yCoord);
			Resource rsc = Resource.values()[rand.nextInt(Resource.values().length)];
			ss.setResource(rsc);
			TechLevel tech = TechLevel.values()[rand.nextInt(TechLevel.values().length)];
			ss.setTechLevel(tech);
                        ss.setMP();
			universe.solarSystems.add(ss);
		}
		return universe;
	}
	/**
	 * Checks whether a solar system exists at a given point in space
	 * @param _x x coordinate
	 * @param _y y coordinate
	 * @return true if this point is the location of a solar system
	 */
	public boolean hasSolarSystemAt(int _x, int _y) {
		if (_x < 0 || _x > width-1 || _y < 0 || _y > height-1) return false;
		for (SolarSystem ss : solarSystems) {
			if (ss.getX() == _x && ss.getY() == _y) return true;
		}
		return false;
	}
	/**
	 * Finds a solar system by name
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
	 * @return String universe information
	 */
	@Override
	public String toString() {
		String str = "";
		str += "dimensions: "+width+"x"+height+"\n";
		str += "solar systems: \n";
		for (SolarSystem ss : solarSystems) {
			str += "\t" + ss.toString() + "\n";
		}
		return str;
	}
	/**
	 * Sets the width of the Universe
	 * @param _width
	 */
	private void setWidth(int _width) { this.width = _width; }
	/**
	 * Returns the width of the universe
	 * @return 
	 */
	public int getWidth() { return width; }
	/**
	 * Sets the height of the Universe.
	 * @param _height
	 */
	private void setHeight(int _height) { this.height = _height; }
	/**
	 * Gets height of the Universe
	 * @return height of the Universe
	 */
	public int getHeight() { return height; }
	/**
	 * Returns list of all solar systems in the Universe.
	 * @return list of SolarSystems
	 */
    public List<SolarSystem> getSolarSystems() {
        return solarSystems;
    }
        
	/**
	 * array shuffling helper
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
	 * Contains all potential names for solar systems
	 */
	public static String[] SolarSystemName =
	{
	    "Acamar",
	    "Adahn",		// The alternate personality for The Nameless One in "Planescape: Torment"
	    "Aldea",
	    "Andevian",
	    "Antedi",
	    "Balosnee",
	    "Baratas",
	    "Brax",		// One of the heroes in Master of Magic
	    "Bretel",		// This is a Dutch device for keeping your pants up.
	    "Calondia",
	    "Campor",
	    "Capelle",		// The city I lived in while programming this game
	    "Carzon",
	    "Castor",		// A Greek demi-god
	    "Cestus",
	    "Cheron",		
	    "Courteney",	// After Courteney Cox
	    "Daled",
	    "Damast",
	    "Davlos",
	    "Deneb",
	    "Deneva",
	    "Devidia",
	    "Draylon",
	    "Drema",
	    "Endor",
	    "Esmee",		// One of the witches in Pratchett's Discworld
	    "Exo",
	    "Ferris",		// Iron
	    "Festen",		// A great Scandinavian movie
	    "Fourmi",		// An ant, in French
	    "Frolix",		// A solar system in one of Philip K. Dick's novels
	    "Gemulon",
	    "Guinifer",		// One way of writing the name of king Arthur's wife
	    "Hades",		// The underworld
	    "Hamlet",		// From Shakespeare
	    "Helena",		// Of Troy
	    "Hulst",		// A Dutch plant
	    "Iodine",		// An element
	    "Iralius",
	    "Janus",		// A seldom encountered Dutch boy's name
	    "Japori",
	    "Jarada",
	    "Jason",		// A Greek hero
	    "Kaylon",
	    "Khefka",
	    "Kira",		// My dog's name
	    "Klaatu",		// From a classic SF movie
	    "Klaestron",
	    "Korma",		// An Indian sauce
	    "Kravat",		// Interesting spelling of the French word for "tie"
	    "Krios",
	    "Laertes",		// A king in a Greek tragedy
	    "Largo",
	    "Lave",		// The starting system in Elite
	    "Ligon",
	    "Lowry",            // The name of the "hero" in Terry Gilliam's "Brazil"
            "Lylat",
	    "Magrat",		// The second of the witches in Pratchett's Discworld
	    "Malcoria",
	    "Melina",
	    "Mentar",		// The Psilon home system in Master of Orion
	    "Merik",
	    "Mintaka",
	    "Montor",		// A city in Ultima III and Ultima VII part 2
	    "Mordan",
	    "Myrthe",		// The name of my daughter
	    "Nelvana",
	    "Nix",		// An interesting spelling of a word meaning "nothing" in Dutch
	    "Nyle",		// An interesting spelling of the great river
	    "Odet",
	    "Og",		// The last of the witches in Pratchett's Discworld
	    "Omega",		// The end of it all
	    "Omphalos",		// Greek for navel
	    "Orias",
	    "Othello",		// From Shakespeare
	    "Parade",		// This word means the same in Dutch and in English
	    "Penthara",
	    "Picard",		// The enigmatic captain from ST:TNG
	    "Pollux",		// Brother of Castor
	    "Quator",
	    "Rakhar",
	    "Ran",		// A film by Akira Kurosawa
	    "Regulas",
	    "Relva",
	    "Rhymus",
	    "Rochani",
	    "Rubicum",		// The river Ceasar crossed to get into Rome
	    "Rutia",
	    "Sarpeidon",
	    "Sefalla",
	    "Seltrice",
	    "Sigma",
	    "Sol",		// That's our own solar system
	    "Somari",
	    "Stakoron",
	    "Styris",
	    "Talani",
	    "Tamus",
	    "Tantalos",		// A king from a Greek tragedy
	    "Tanuga",
	    "Tarchannen",
	    "Terosa",
	    "Thera",		// A seldom encountered Dutch girl's name
	    "Titan",		// The largest moon of Jupiter
	    "Torin",		// A hero from Master of Magic
	    "Triacus",
	    "Turkana",
	    "Tyrus",
	    "Umberlee",		// A god from AD&D, which has a prominent role in Baldur's Gate
	    "Utopia",		// The ultimate goal
	    "Vadera",
	    "Vagra",
	    "Vandor",
	    "Ventax",
	    "Xenon",
	    "Xerxes",		// A Greek hero
	    "Yew",		// A city which is in almost all of the Ultima games
	    "Yojimbo",		// A film by Akira Kurosawa
	    "Zalkon",
	    "Zuul"		// From the first Ghostbusters movie
	};

}