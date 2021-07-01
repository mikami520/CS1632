import java.util.*;

public class CoffeeMakerQuestImpl implements CoffeeMakerQuest {

	// TODO: Add more member variables and methods as needed.
	Player player;
	boolean gameOver;
	ArrayList<Room> rooms;
	Room currentRoom;

	
	CoffeeMakerQuestImpl() {
		this.player = null;
		this.gameOver = false;
		this.rooms = new ArrayList<Room>();
		this.currentRoom = null;

	}

	/**
	 * Whether the game is over. The game ends when the player drinks the coffee.
	 * 
	 * @return true if successful, false otherwise
	 */
	public boolean isGameOver() {
		return this.gameOver;
	}


	/**
	 * Set the player to p.
	 * 
	 * @param p the player
	 */
	public void setPlayer(Player p) {
		this.player = p;
	}
	
	/**
	 * Add the first room in the game. If room is null or if this not the first room
	 * (there are pre-exiting rooms), the room is not added and false is returned.
	 * 
	 * @param room the room to add
	 * @return true if successful, false otherwise
	 */
	public boolean addFirstRoom(Room room) {
		// TODO
		if (this.rooms.size() == 0) {
			rooms.add(room);
			return true;
		}
		return false;
	}

	/**
	 * Attach room to the northern-most room. If either room, northDoor, or
	 * southDoor are null, the room is not added. If there are no pre-exiting rooms,
	 * the room is not added. If room is not a unique room (a pre-exiting room has
	 * the same adjective or furnishing), the room is not added. If all these tests
	 * pass, the room is added. Also, the north door of the northern-most room is
	 * labeled northDoor and the south door of the added room is labeled southDoor.
	 * Of course, the north door of the new room is still null because there is
	 * no room to the north of the new room.
	 * 
	 * @param room      the room to add
	 * @param northDoor string to label the north door of the current northern-most room
	 * @param southDoor string to label the south door of the newly added room
	 * @return true if successful, false otherwise
	 */
	public boolean addRoomAtNorth(Room room, String northDoor, String southDoor) {
		if (room == null || northDoor == null || southDoor == null) {
			return false;
		}
		if (this.rooms.size() == 0) {
			return false;
		}
		for(int i = 0; i < this.rooms.size(); i++) {
			Room temp = this.rooms.get(i);
			if(temp.getAdjective() == room.getAdjective() || temp.getFurnishing() == room.getFurnishing()) {
				return false;
			}
		}
		this.rooms.get(this.rooms.size()-1).setNorthDoor(northDoor);
		room.setSouthDoor(southDoor);
		this.rooms.add(room);
		return true;
	}

	/**
	 * Returns the room the player is currently in. If location of player has not
	 * yet been initialized with setCurrentRoom, returns null.
	 * 
	 * @return room player is in, or null if not yet initialized
	 */ 
	public Room getCurrentRoom() {
		// TODO
		return this.currentRoom;
	}
	
	/**
	 * Set the current location of the player. If room does not exist in the game,
	 * then the location of the player does not change and false is returned.
	 * 
	 * @param room the room to set as the player location
	 * @return true if successful, false otherwise
	 */
	public boolean setCurrentRoom(Room room) {
		if (!this.rooms.contains(room)){
			return false;
		}
		this.currentRoom = this.rooms.get(rooms.indexOf(room));
		return true;
	}
	
	/**
	 * Get the instructions string command prompt. It returns the following prompt:
	 * " INSTRUCTIONS (N,S,L,I,D,H) > ".
	 * 
	 * @return comamnd prompt string
	 */
	public String getInstructionsString() {
		// TODO
		return " INSTRUCTIONS (N,S,L,I,D,H) > ";
	}
	
	/**
	 * Processes the user command given in String cmd and returns the response
	 * string. For the list of commands, please see the Coffee Maker Quest
	 * requirements documentation (note that commands can be both upper-case and
	 * lower-case). For the response strings, observe the response strings printed
	 * by coffeemaker.jar. The "N" and "S" commands potentially change the location
	 * of the player. The "L" command potentially adds an item to the player
	 * inventory. The "D" command drinks the coffee and ends the game. Make
     * sure you use Player.getInventoryString() whenever you need to display
     * the inventory.
	 * 
	 * @param cmd the user command
	 * @return response string for the command
	 */
	public String processCommand(String cmd) {
		if (cmd.toLowerCase().equals("n")) {
			if (this.rooms.indexOf(this.currentRoom) + 1 < this.rooms.size()) {
				this.currentRoom = this.rooms.get(rooms.indexOf(this.currentRoom) + 1);
			} else if (this.rooms.indexOf(this.currentRoom) + 1 == this.rooms.size()) {
				String part = "A door in that direction does not exist.\n";
				return part;
			}
		}
		else if (cmd.toLowerCase().equals("s")) {
			if (this.rooms.indexOf(this.currentRoom) - 1  >= 0) {
				this.currentRoom = this.rooms.get(rooms.indexOf(this.currentRoom) - 1);
			} else if (this.rooms.indexOf(this.currentRoom) == 0) {
				String part = "A door in that direction does not exist.\n";
				return part;
			}
		}
		else if (cmd.toLowerCase().equals("l")) {
			this.player.addItem(this.currentRoom.getItem());
			String part = "";
			if (this.currentRoom.getItem() == Item.NONE) {
				part = "You don't see anything out of the ordinary.\n";
			} else if (this.currentRoom.getItem() == Item.CREAM) {
				part = "There might be something here...\nYou found some creamy cream!\n";
			} else if (this.currentRoom.getItem() == Item.COFFEE) {
				part = "There might be something here...\nYou found some caffeinated coffee!\n";
			} else if (this.currentRoom.getItem() == Item.SUGAR) {
				part = "There might be something here...\nYou found some sweet sugar!\n";
			}
			return part;
		} 
		else if (cmd.toLowerCase().equals("d")) {
			this.gameOver = true;
			String ret = this.addFormat(this.player);
			return this.player.getInventoryString() + ret;
		}
		else if (cmd.toLowerCase().equals("i")) {
			return this.player.getInventoryString();
		}
		else if (cmd.toLowerCase().equals("h")) {
			String ret = "N - Go north\nS - Go south\nL - Look and collect any items in the room\nI - Show inventory of items collected\nD - Drink coffee made from items in inventory\n";
			return ret;
		}
		return "";
	}

	private String addFormat(Player player) {
		String ret = "";
		if(player.checkSugar() && !player.checkCream() && !player.checkCoffee()) {
			ret = "\nYou eat the sugar, but without caffeine, you cannot study.\nYou lose!\n";
		} else if (!player.checkSugar() && player.checkCream() && !player.checkCoffee()) {
			ret = "\nYou drink the cream, but without caffeine, you cannot study.\nYou lose!\n";
		} else if (!player.checkSugar() && !player.checkCream() && player.checkCoffee()) {
			ret = "\nWithout cream, you get an ulcer and cannot study.\nYou lose!\n";
		} else if (player.checkSugar() && player.checkCream() && !player.checkCoffee()) {
			ret = "\nYou drink the sweetened cream, but without caffeine you cannot study.\nYou lose!\n";
		} else if (player.checkSugar() && !player.checkCream() && player.checkCoffee()) {
			ret = "\nWithout cream, you get an ulcer and cannot study.\nYou lose!\n";
		} else if (!player.checkSugar() && player.checkCream() && player.checkCoffee()) {
			ret = "\nWithout sugar, the coffee is too bitter. You cannot study.\nYou lose!\n";
		} else if (!player.checkSugar() && !player.checkCream() && !player.checkCoffee()) {
			ret = "\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n";
		} else {
			ret = "\nYou drink the beverage and are ready to study!\nYou win!\n";
		}
		return ret;
	}
	
}
