/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

/**
 * @author Kristjan
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Dungeon {
    private int length;
    private int height;
    private int vampires;

    private boolean vampiresMove;
    private Player player;
    private Scanner reader;
    private Random random;

    private List<Vampire> vampireList;

    public Dungeon(int length, int height, int vampires, int moves, boolean vampiresMove) {
        this.length = length;
        this.height = height;
        this.vampires = vampires;

        this.vampiresMove = vampiresMove;
        this.player = new Player(moves);
        reader = new Scanner(System.in);
        random = new Random();

        vampireList = new ArrayList<Vampire>();
    }

    /**
     * The method which starts the game
     */
    public void run() {
        this.generateVampires(vampires);
        while (true) {
            if (player.getMovesLeft() == 0) {
                System.out.println("YOU LOSE");
                break;
            }
            this.printStats();
            player.reduceMovesLeftByOne();
            System.out.print("\n");
            this.printMap();
            String inputMoves = this.askForDirections(reader);
            movePlayer(saveMovesAndGiveMoveList(inputMoves));
            if (vampireList.isEmpty()) {
                System.out.println("YOU WIN");
                break;
            }
            if (vampiresMove) {
                this.moveVampires(inputMoves.length());
            }
        }

    }

    /**
     * Prints map of the game
     *
     * @ for the player
     * v for the vampires
     * . for empty coordinates
     */
    public void printMap() {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < length; i++) {
                if (isPlayerHere(i, j)) {
                    System.out.print("@");
                } else if (isVampireHere(i, j)) {
                    System.out.print("v");
                } else {
                    System.out.print(".");
                }
            }
            System.out.print("\n");
        }
    }

    /**
     * Prints the coordinates of Movables in the game
     */
    public void printStats() {
        System.out.println(player.getMovesLeft());
        System.out.println("\n" + "@ " + player.getX() + " " + player.getY());
        for (Vampire vampire : vampireList) {
            System.out.println("v " + vampire.getX() + " " + vampire.getY());
        }
    }


    /**
     * Whether the player is at that location
     *
     * @param x x-coordinate that is checked
     * @param y y-coordinate that is checked
     * @return boolean
     */
    public boolean isPlayerHere(int x, int y) {
        return player.getX() == x && player.getY() == y;
    }

    /**
     * Whether a vampire is at that location
     *
     * @param x x-coordinate that is checked
     * @param y y-coordinate that is checked
     * @return boolean
     */
    public boolean isVampireHere(int x, int y) {
        for (Vampire vampire : vampireList) {
            if (vampire.getX() == x && vampire.getY() == y) return true;
        }
        return false;
    }

    /**
     * Whether the Movable is out of bonds
     *
     * @param movable Movable that is checked
     * @return boolean
     */
    public boolean isOutOfBounds(Movable movable) {
        int x = movable.getX();
        int y = movable.getY();
        return x < 0 || y < 0 || x >= length || y >= length;
    }

    /**
     * Moves the player according to the input list
     *
     * @param list list of inputs that is given by the player
     */
    public void movePlayer(List<Character> list) {
        for (char direction : list) {
            int beforeMovingX = player.getX();
            int beforeMovingY = player.getY();
            switch (direction) {
                case 'w':
                    player.goUp();
                    break;
                case 'a':
                    player.goLeft();
                    break;
                case 's':
                    player.goDown();
                    break;
                case 'd':
                    player.goRight();
            }
            if (isOutOfBounds(player)) {
                player.setX(beforeMovingX);
                player.setY(beforeMovingY);
            } else {
                if (hasPlayerFoundVampire()) {
                    killVampire();
                }
            }
        }
    }


    /**
     * Asks for the moves in which direction the player will move
     *
     * @param reader
     * @return String
     */
    public String askForDirections(Scanner reader) {
        return reader.nextLine();
    }


    /**
     * Translate string into an ArrayList
     *
     * @param moves String of characters that player inputs
     * @return ArrayList<String>
     */
    public List<Character> saveMovesAndGiveMoveList(String moves) {
        List<Character> list = new ArrayList<Character>();
        for (int i = 0; i < moves.length(); i++) {
            list.add(moves.charAt(i));
        }
        return list;
    }


    /**
     * Generate vampires in the game
     *
     * @param amount Amount of vampires will be spawned
     */
    public void generateVampires(int amount) {
        for (int i = 0; i < amount; i++) {

            int x = random.nextInt(length);
            int y = random.nextInt(height);

            if (!isOccupied(x, y)) {
                vampireList.add(new Vampire(x, y));
            } else {
                i--;
            }
        }
    }

    /**
     * Whether the location is occupied
     *
     * @param x x-coordinate that is checked
     * @param y y-coordinate that is checked
     * @return boolean
     */
    public boolean isOccupied(int x, int y) {
        if (x == player.getX() && y == player.getY()) {
            return true;
        }
        return isVampireHere(x, y);
    }

    /**
     * Whether the player is on the same location with vampire
     *
     * @return boolean
     */
    public boolean hasPlayerFoundVampire() {
        for (Vampire vampire : vampireList) {
            if (isPlayerOnVampire(vampire)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPlayerOnVampire(Vampire vampire) {
        return vampire.getX() == player.getX() && vampire.getY() == player.getY();
    }

    /**
     * Removes the vampire from the game if the player is on the same location as the vampire
     *
     * @return Integer
     */
    public int killVampire() {
        for (Vampire vampire : vampireList) {
            if (isPlayerOnVampire(vampire)) {
                vampireList.remove(vampire);
                return 1;
            }
        }
        return -1;
    }

    /**
     * Moves randomly the vampires
     *
     * @param howManyTimes How many times will the vampires move
     */
    public void moveVampires(int howManyTimes) {
        for (Vampire vampire : vampireList) {
            for (int i = 0; i < howManyTimes; i++) {
                int direction = random.nextInt(4);
                int posBeforeMovingX = vampire.getX();
                int posBeforeMovingY = vampire.getY();
                switch (direction) {
                    case 0:
                        vampire.goUp();
                        break;
                    case 1:
                        vampire.goLeft();
                        break;
                    case 2:
                        vampire.goRight();
                        break;
                    case 3:
                        vampire.goDown();
                }
                if (isOutOfBounds(vampire)) {
                    vampire.setX(posBeforeMovingX);
                    vampire.setY(posBeforeMovingY);
                    i--;
                }
            }
        }
    }
}
    
    

