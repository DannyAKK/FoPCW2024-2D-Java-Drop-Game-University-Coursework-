package uk.ac.bradford.dropgame;


import java.util.Random;
import uk.ac.bradford.dropgame.Tile.TileType;

/**
 * The GameEngine class is responsible for managing information about the game,
 * creating rooms, the player and dropper, as well as updating information when a
 * key is pressed (processed by the InputHandler) while the game is running.
 *
 * @author prtrundl
 */
public class GameEngine {

    /**
     * The width of the level, measured in tiles. Changing this may cause the
     * display to draw incorrectly, and as a minimum the size of the GUI would
     * need to be adjusted.
     */
    public static final int LEVEL_WIDTH = 35;

    /**
     * The height of the level, measured in tiles. Changing this may cause the
     * display to draw incorrectly, and as a minimum the size of the GUI would
     * need to be adjusted.
     */
    public static final int LEVEL_HEIGHT = 18;

    /**
     * A random number generator that can be used to include randomised choices
     * in the creation of levels, in choosing places to place the player and
     * objects, and to randomise movement etc. Passing an integer (e.g. 123) to
     * the constructor called here will give fixed results - the same numbers
     * will be generated every time WHICH CAN BE VERY USEFUL FOR TESTING AND
     * BUGFIXING!
     */
    private Random rng = new Random();

    /**
     * The current level number for the game. As the player completes levels the
     * level number should be increased and can be used to increase the
     * difficulty e.g. by raising the door height and items required to exit
     */
    private int levelNumber = 1;

    /**
     * The current turn number. Increased by one every turn. Used to control
     * effects that only occur on certain turn numbers.
     */
    private int turnNumber = 1;
    
    
    
    public int getItemsCollected() {
    return this.itemsCollected;
}

    /**
     * The number of items collected in this level. Used to check if enough
     * items have been collected to allow the player to exit via a DOOR
     * tile and complete the current level.
     */
    private int itemsCollected = 0;
    
    /**
     * The GUI associated with this GameEngine object. This link allows the
     * engine to pass level and entity information to the GUI to be drawn.
     */
    private GameGUI gui;

    /**
     * The 2 dimensional array of tiles that represent the current level. The
     * size of this array should use the LEVEL_HEIGHT and LEVEL_WIDTH attributes
     * when it is created. This is the array that is used to draw images to the
     * screen by the GUI class, and by you to check what a specific Tile contains
     * by checking the content of specific elements of the array and using the
     * getType() method to determine the type of the Tile.
     */
    private Tile[][] room;

    /**
     * A Player object that is the current player. This object stores the state
     * information for the player, including energy and the current position
     * (which is a pair of co-ordinates that corresponds to a tile in the
     * current level - see the Entity class for more information on the
     * co-ordinate system used as well as the coursework specification
     * document).
     */
    private Player player;

    /**
     * A Dropper object used to create the dropper for this level. The object 
     * has position information stored via its attributes, and methods to check and
     * update the position of the dropper.
     */
    private Dropper dropper;
    
    /**
     * Constructor that creates a GameEngine object and connects it with a
     * GameGUI object.
     *
     * @param gui The GameGUI object that this engine will pass information to
     * in order to draw levels and entities to the screen.
     */
    public GameEngine(GameGUI gui) {
        this.gui = gui;
    }

    /**
     * Generates a new level. This method should instantiate the room array,
     * and then fill it with Tile objects that are created inside this method.
     * It is recommended that for your first version of this method you fill the
     * 2D array using for loops, and create new Tile objects inside the inner
     * loop, set their type using the constructor and the TileType enumeration,
     * then assigning the Tile object to an element in the array. For the first version
     * you should use just Tile objects with the type BACKGROUND.
     *
     * Later tasks will require additions to this method to add new content, see
     * the specification document for more details.
     */
    private void generateRoom() {
        room = new Tile[LEVEL_WIDTH][LEVEL_HEIGHT];
        for (int x = 0; x < LEVEL_WIDTH; x++) {
            for (int y = 0; y < LEVEL_HEIGHT; y++) {
                if (y == 0) {
                    room[x][y] = new Tile(Tile.TileType.CEILING);
                } else if (y == LEVEL_HEIGHT - 1) {
                    room[x][y] = new Tile(Tile.TileType.FLOOR);
                } else if (x == 0 || x == LEVEL_WIDTH - 1) {
                    room[x][y] = new Tile(Tile.TileType.WALL);
                } else {
                    room[x][y] = new Tile(Tile.TileType.BACKGROUND);
                }
            }
        }
    //This is for the the new levels//
    int numObstacles = levelNumber * 3; // More obstacles as levels increase
int numPlatforms = levelNumber; // One floating platform per level
Random rng = new Random();

// Generate random obstacles (BOX, ITEMBOX, TRAP)
for (int i = 0; i < numObstacles; i++) {
    int x = rng.nextInt(LEVEL_WIDTH);
    int y = rng.nextInt(LEVEL_HEIGHT - 2); // Avoid placing on the floor or ceiling

    if (room[x][y].getType() == TileType.BACKGROUND) {
        // Randomly select an obstacle type
        TileType obstacleType;
        double random = Math.random();
        if (random < 0.6) {
            obstacleType = TileType.BOX; // 60% chance of BOX
        } else if (random < 0.9) {
            obstacleType = TileType.ITEMBOX; // 30% chance of ITEMBOX
        
        }
    }
}

// Generate floating platforms
for (int i = 0; i < numPlatforms; i++) {
    int platformY = rng.nextInt(LEVEL_HEIGHT / 2) + 2; // Avoid floor/ceiling
    int startX = rng.nextInt(LEVEL_WIDTH - 5); // Ensure platform fits within bounds

    for (int x = startX; x < startX + 5; x++) { // Platform length = 5 tiles
        if (room[x][platformY].getType() == TileType.BACKGROUND) {
            room[x][platformY] = new Tile(TileType.FLOOR);
        }
    }
}


    // Place the DOOR tile (higher each level for added difficulty)
    int doorY = LEVEL_HEIGHT - 2 - levelNumber; // Adjust height based on level
    doorY = Math.max(1, doorY); // Ensure door stays within bounds
    room[LEVEL_WIDTH - 1][doorY] = new Tile(TileType.DOOR);
 
   

    this.room = room;
    
}

    

    /**
     * Adds a dropper to the current level. The dropper should be placed at the
     * top of the room by setting appropriate X and Y co-ordinates when creating
     * this object, and the object created should be assigned to the "dropper"
     * attribute of this class.
     */
    private void addDropper() {
        
    this.dropper = new Dropper(17, 0);
    }

    /**
     * Creates a Player object in the game. This method should instantiate the
     * Player class by creating an object using the appropriate constructor.
     * The created object should then be assigned to the "player" attribute of
     * this class.
     * 
     * The player should be placed at the bottom of the level by using appropriate
     * X and Y co-ordinate values when calling the constructor. The energy value
     * can be set to any integer for your first version of this method, but may
     * need updating in a later task.
     */
    private void createPlayer() {
        player = new Player(100, LEVEL_WIDTH / 2, LEVEL_HEIGHT - 2);
    }
    /**
     * Activates the dropper object in the level by creating a new Tile object,
     * with the type TileType.BOX. This new object should be set to the position
     * in the room array that is one tile below the dropper's current position
     * (use the relevant methods from the Entity/Dropper class to get the current
     * position of the dropper and calculate the co-ordinates of the space below it).
     */
    private void activateDropper() {
    int dropperX = this.dropper.getX();
    int dropperY = this.dropper.getY();

    // Randomly decide whether to create a BOX or an ITEMBOX
    TileType tileType = Math.random() < 0.8 ? TileType.BOX : TileType.ITEMBOX; // 80% chance for BOX, 20% for ITEMBOX
    Tile newTile = new Tile(tileType);

    // Place the tile one row below the dropper
    if (dropperY + 1 < LEVEL_HEIGHT) {
        this.room[dropperX][dropperY + 1] = newTile; // Place the tile directly below the dropper
    }
    }
    


    /**
     * Handles the movement of the player when attempting to move in the game.
     * This method is automatically called by the GameInputHandler class when
     * the user has pressed the left arrow key on the keyboard.
     * Your code should set the X and Y position of the player to place them
     * in the correct tile based on the direction of movement, i.e. changing
     * their X position by one. You will need to use the relevant method(s) from
     * the Player/Entity class to find the current position of the player, and
     * set new values based on the current X and Y values.
     *
     * In later tasks you will need to update this method to handle more complex
     * movement cases (e.g. only moving into BACKGROUND tiles, collecting items
     * and climbing boxes).
     *
     */
  public void movePlayerLeft() {
    int currentX = player.getX();
    int currentY = player.getY();

    if (currentX > 0) {
        Tile targetTile = room[currentX - 1][currentY];

        // Allow movement into BACKGROUND tiles
        if (targetTile.getType() == Tile.TileType.BACKGROUND) {
            player.setPosition(currentX - 1, currentY);
        } 
        // Handle BOX or ITEMBOX movement
        else if (targetTile.getType() == Tile.TileType.BOX || targetTile.getType() == Tile.TileType.ITEMBOX) {
            if (currentY > 0 && room[currentX - 1][currentY - 1].getType() == Tile.TileType.BACKGROUND) {
                player.setPosition(currentX - 1, currentY - 1);
            }
        } 
        // Collect ITEM
        else if (targetTile.getType() == Tile.TileType.ITEM) {
            room[currentX - 1][currentY] = new Tile(Tile.TileType.BACKGROUND);  // Replace with BACKGROUND
            player.incrementItemsCollected();  // Increment items collected
            player.setPosition(currentX - 1, currentY);  // Move to new position
        }
    }
}







    /**
     * Handles the movement of the player when attempting to move in the game.
     * This method is automatically called by the GameInputHandler class when
     * the user has pressed the right arrow key on the keyboard.
     * Your code should set the X and Y position of the player to place them
     * in the correct tile based on the direction of movement, i.e. changing
     * their X position by one. You will need to use the relevant method(s) from
     * the Player/Entity class to find the current position of the player, and
     * set new values based on the current X and Y values.
     *
     * In later tasks you will need to update this method to handle more complex
     * movement cases (e.g. only moving into BACKGROUND tiles, collecting items
     * and climbing boxes).
     *
     */
  public void movePlayerRight() {
    int currentX = player.getX();
    int currentY = player.getY();

    if (currentX < LEVEL_WIDTH - 1) {
        Tile targetTile = room[currentX + 1][currentY];

        // Allow movement into BACKGROUND tiles
        if (targetTile.getType() == Tile.TileType.BACKGROUND) {
            player.setPosition(currentX + 1, currentY);
        } 
        // Handle BOX or ITEMBOX movement
        else if (targetTile.getType() == Tile.TileType.BOX || targetTile.getType() == Tile.TileType.ITEMBOX) {
            if (currentY > 0 && room[currentX + 1][currentY - 1].getType() == Tile.TileType.BACKGROUND) {
                player.setPosition(currentX + 1, currentY - 1);
            }
        } 
        // Collect ITEM
        else if (targetTile.getType() == Tile.TileType.ITEM) {
            room[currentX + 1][currentY] = new Tile(Tile.TileType.BACKGROUND);  // Replace with BACKGROUND
            player.incrementItemsCollected();  // Increment items collected
            player.setPosition(currentX + 1, currentY);  // Move to new position
        }
    }
}






    /**
     * Breaks all boxes surrounding the player. This method is automatically 
     * called when the player presses the Q key on the keyboard. This method
     * should check all the tiles surrounding the player (ideally 8 tiles that
     * are adjacent to the player object) and "break" them by replacing them
     * with new Tile objects with the type BACKGROUND.
     * 
     * A later task will require you to handle the breaking of ITEMBOX tiles,
     * and to handle player energy to limit the use of this ability.
     */
 public void breakBoxes() {
    if (player.getEnergy() == player.getMaxEnergy()) {
        int playerX = player.getX();
        int playerY = player.getY();
        boolean boxBroken = false;

        for (int x = Math.max(0, playerX - 1); x <= Math.min(LEVEL_WIDTH - 1, playerX + 1); x++) {
            for (int y = Math.max(0, playerY - 1); y <= Math.min(LEVEL_HEIGHT - 1, playerY + 1); y++) {
                Tile targetTile = room[x][y];

                // Break BOX tiles
                if (targetTile.getType() == Tile.TileType.BOX) {
                    room[x][y] = new Tile(Tile.TileType.BACKGROUND);
                    boxBroken = true;
                }
                // Replace ITEMBOX tiles with ITEM tiles
                else if (targetTile.getType() == Tile.TileType.ITEMBOX) {
                    room[x][y] = new Tile(Tile.TileType.ITEM);
                    boxBroken = true;
                }
            }
        }

        if (boxBroken) {
            player.changeEnergy(-player.getMaxEnergy());
        }
    }
}








    /**
     * Moves the dropper object within the room. The method updates the X and Y
     * attributes of the "dropper" object (attribute of this class) to move it
     * left or right.
     * 
     * Your first version of this method should move the dropper either left or
     * right depending on a random choice - it may be easiest to generate a
     * random number with the value 0 or 1 and move left or right depending on
     * this randomly generated value.
     * 
     * A later version of this method will require you to move the dropper
     * by checking the player object's position and "following" the player.
     */
    private void moveDropper() {
    int currentX = dropper.getX();
    int playerX = player.getX();
    
    if (currentX < playerX) {
        dropper.setPosition(currentX + 1, dropper.getY());
    } else if (currentX > playerX) {
        dropper.setPosition(currentX - 1, dropper.getY());
    }
}


    /**
     * A method that applies gravity to the player and boxes in the game, using
     * two specific methods.
     */
    private void applyGravity() {
        applyPlayerGravity();
        applyBoxGravity();
    }

    /**
     * Applies gravity to the player object. This method should check if the Tile
     * below the player is of the type BACKGROUND; if it is then the player's
     * position should be changed to "drop" the player one Tile into the empty
     * Tile below their current position. You will need to retrieve their
     * current position using the appropriate methods in the Player/Entity class.
     */
    private void applyPlayerGravity() {
    int currentX = this.player.getX();
    int currentY = this.player.getY();

    
    while (currentY + 1 < LEVEL_HEIGHT && this.room[currentX][currentY + 1].getType() == TileType.BACKGROUND) {
       
        this.player.setPosition(currentX, currentY + 1);

        
        currentY = this.player.getY();
    }
    }
    /**
     * Applies gravity to BOX tiles. This method should (starting from the
     * bottom of the level!) traverse over every Tile object in the room array,
     * and for every tile with the type BOX it should check if the tile below
     * the BOX is of the type BACKGROUND. If the tile below the BOX is a BACKGROUND
     * tile then the BOX should be "dropped" one tile by creating new objects and
     * placing them into the room array at the correct positions, or swapping
     * the BOX and BACKGROUND tile objects in the array. After dropping, the BOX
     * should now be one tile lower and the position it used to be in should be a
     * BACKGROUND tile.
     */
   
  
    
    
 private void applyBoxGravity() {
    
    for (int y = LEVEL_HEIGHT - 1; y >= 0; y--) { 
        for (int x = 0; x < LEVEL_WIDTH; x++) { 
            Tile currentTile = room[x][y];
            
            // Check if the tile is BOX or ITEMBOX
            if (currentTile.getType() == TileType.BOX || currentTile.getType() == TileType.ITEMBOX) { 
                int newY = y + 1;

                // Ensure within bounds and the tile below is BACKGROUND
                if (newY < LEVEL_HEIGHT && room[x][newY].getType() == TileType.BACKGROUND) {
                    // Move the current tile down
                    room[x][newY] = currentTile; 
                    room[x][y] = new Tile(TileType.BACKGROUND);
                }

                // Check if the falling tile "crushes" the player
                if (player.getX() == x && player.getY() == newY) {
                    System.out.println("Player crushed by a box " + currentTile.getType() + " at: (" + x + ", " + newY + ")");
                    System.exit(0); 
                }
            }
        }
    }
}





 





    /**
     * A method to check if the current level has been "completed". A level is
     * complete when the player has reached the DOOR tile. This method should
     * return either true or false depending on whether the level is complete.
     * 
     * A later version of this method will also need to check if a sufficient
     * number of items have been collected before the door can be used.
     * 
     * @return true if the level is completed, or false otherwise.
     */
   private boolean levelComplete() {
    int itemsRequired = levelNumber * 2;  // Items required increases with level
    return this.room[this.player.getX()][this.player.getY()].getType() == TileType.DOOR
           && this.itemsCollected >= itemsRequired;
}



    
    /**
     * This method is called when the player completes the current level by reaching
     * a DOOR tile, optionally also requiring some ITEM tiles to be collected first (in a later task).
     *
     * This method should increase the current level number, reset the counter
     * for the number of items collected in the level, generate a new room by
     * using the appropriate method, and place a dropper and the player in the
     * new level using the addDropper() method and the placePlayer() method.
     *
     */
    private void nextLevel() {
    levelNumber++; // Increment level
    itemsCollected = 0; // Reset items collected

    generateRoom(); // Generate new room
    addDropper();   // Add the dropper
    placePlayer();  // Place the player

    gui.updateDisplay(room, player, dropper); // Update GUI
}


    /**
     * Places the existing player object into a new level by setting appropriate
     * X and Y values for the player objects position.
     */
    private void placePlayer() {
    int startX = 1; // Starting X-coordinate (near the left wall)
    int startY = LEVEL_HEIGHT - 2; // Just above the floor
    this.player.setPosition(startX, startY);
}


    /**
     * Performs a single turn of the game when the user presses a key on the
     * keyboard. The method increments the turnNumber, applies gravity, moves
     * the dropper every two turns, activates the dropper every seven turns and
     * checks if the level has been completed, creating a new level if it has.
     * Finally it requests the GUI to redraw the game level by passing it the
     * room, player and dropper objects for the current level.
     *
     */
    public void doTurn() {
        turnNumber++;
        applyGravity();
        
         if (this.player.getEnergy() < this.player.getMaxEnergy()) {
        this.player.changeEnergy(1);  // Recharge by 1 energy per turn
    }
        
        if (turnNumber % 2 == 0) {      //moves every two turns
            moveDropper();
        }
        if (turnNumber % 7 == 0) {      //activates every seven turns
            activateDropper();
        }
        if (levelComplete()) {
            nextLevel();
        }
        gui.updateDisplay(room, player, dropper);
    }

    /**
     * Starts a game. This method generates a room, adds the dropper and player, 
     * then requests the GUI to update the level on screen using the information
     * passed to it.
     */
    public void startGame() {
        generateRoom();
        addDropper();
        createPlayer();
        gui.updateDisplay(room, player, dropper);
    }
}
