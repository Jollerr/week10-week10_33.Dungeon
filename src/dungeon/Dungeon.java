/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

/**
 *
 * @author Kristjan
 */
import java.util.*;

public class Dungeon {
    private int length;
    private int height;
    private int vampires;

    private boolean vampiresMove;
    private Player player;
    private Scanner reader;
    
    private List<Vampire> vampireList;
    
    public Dungeon(int length, int height, int vampires, int moves, boolean vampiresMove) {
        this.length = length;
        this.height = height;
        this.vampires = vampires;
        this.vampiresMove = vampiresMove;
        this.player = new Player(moves);
        reader = new Scanner(System.in);
        vampireList = new ArrayList<Vampire>();
    }
    
    public void run() {
        this.generateVampires(vampires);
        while (true) {
            if (player.getMovesLeft() == 0) {
                System.out.println("YOU LOSE");
                break;
            }
            this.printStats();
            player.reduceMoves();
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
    
    public void printMap() {
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < length; i++) {
                if (isPlayerHere(i, j)) {
                    System.out.print("@");
                } else if (isVampireHere(i, j)) {
                    System.out.print("v");
                } else { System.out.print(".");
                }
            }
            System.out.print("\n");
        }
    }
    
    
    public void printStats() {
        System.out.println(player.getMovesLeft());
        System.out.println("\n" + "@ " + player.getXY().get(0)+ " " + player.getXY().get(1));
        for (Vampire vampire : vampireList) {
            System.out.println("v " + vampire.getXY().get(0)+ " " + vampire.getXY().get(1));
        }
    }
    
    public boolean isPlayerHere(int x, int y) {
        if (this.player.getXY().get(0) == x && this.player.getXY().get(1) == y) return true;
        return false;
    }
    
    public boolean isVampireHere(int x, int y) {
        for (Vampire vampire : vampireList) {
            if(vampire.getXY().get(0) == x && vampire.getXY().get(1) == y) return true;
        }
        return false;
    }
    
    public boolean isOutOfBounds(Movable moveable, int x, int y) {
        Movable movableCheck = new Vampire(moveable.getXY().get(0) + x, moveable.getXY().get(1) + y);
        if (movableCheck.getXY().get(0) >= 0 && movableCheck.getXY().get(1) >= 0) {
            if (movableCheck.getXY().get(0) < length && movableCheck.getXY().get(1) < length) {
                return false;
            }
        }
        return true;
    }
    
    
    public void movePlayer(List<String> list) {
        for (String direction : list) {
            
            if (direction.equals("w")) {
                if (!isOutOfBounds(player, 0, -1)) {
                    player.goUp();

                    if (hasPlayerFoundVampire()) {
                        killVampire();
                    }
                }
            } else if (direction.equals("a")) {
                if (!isOutOfBounds(player, -1, 0)) {
                    player.goLeft();
                    if (hasPlayerFoundVampire()) {
                        killVampire();
                        }
                }
            } else if (direction.equals("s")) {
                if (!isOutOfBounds(player, 0, 1)) {
                    player.goDown();
                    if (hasPlayerFoundVampire()) {
                        killVampire();
                    }
                }
            } else if (direction.equals("d")) {
                    if (!isOutOfBounds(player, 1, 0)) {
                    player.goRight();
                    if (hasPlayerFoundVampire()) {
                        killVampire();
                    }
                }
            }
        }
    }
    
    public String askForDirections(Scanner reader) {
        return reader.nextLine();
    }
    
    public List<String> saveMovesAndGiveMoveList (String moves) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < moves.length(); i++) {
            list.add(String.valueOf(moves.charAt(i))); 
        }
        return list;
    }
    
    

    
    public void generateVampires(int amount) {
        for (int i = 0; i < amount; i++) {
            
            int x = new Random().nextInt(length);
            int y = new Random().nextInt(height);
            if (!isOccupied(x ,y)) {
                vampireList.add(new Vampire(x, y));
            } else {
                i--;
            }
        }
    }
    
    public boolean isOccupied(int x, int y) {
        if (x == player.getXY().get(0) && y == player.getXY().get(1)) {
            return true;
        }
        if (!vampireList.isEmpty()) {
            for(Vampire vampire : vampireList) {
                if (x == vampire.getXY().get(0) && y == vampire.getXY().get(1)) {
                    return true;
                }
            }
        }
        return false;     
    }
    
    public boolean hasPlayerFoundVampire() {
        for (Vampire vampire : vampireList) {    
            if (vampire.getXY().get(0) == player.getXY().get(0) && vampire.getXY().get(1) == player.getXY().get(1)) {
                return true;
            }
        }
        return false;
    }
    
    public int killVampire() {
        for (Vampire vampire : vampireList) {    
            if (vampire.getXY().get(0) == player.getXY().get(0) && vampire.getXY().get(1) == player.getXY().get(1)) {
                vampireList.remove(vampire);
                return 1;
            }
        }
        return -1;
    }
    
    public void moveVampires(int howManyTimes) {

        for (Vampire vampire : vampireList) {
            for (int i = 0; i < howManyTimes; i++) {
                List<String> moveList = new ArrayList<String>();
                moveList.add("w");moveList.add("a");moveList.add("s");moveList.add("d");
                String direction = moveList.get(new Random().nextInt(4));
                if (direction.equals("w")) {
                    if (!isOutOfBounds(vampire, 0, -1) && !isOccupied(vampire.getXY().get(0) + 0, vampire.getXY().get(0) - 1)) {
                        vampire.goUp();
                    } else {
                        howManyTimes--;
                    }
                } else if (direction.equals("a")) {
                    if (!isOutOfBounds(vampire, -1, 0) && !isOccupied(vampire.getXY().get(0) - 1, vampire.getXY().get(0) + 0)) {
                        vampire.goLeft();
                    } else {
                        howManyTimes--;
                    }
                } else if (direction.equals("s")) {
                    if (!isOutOfBounds(vampire, 0, 1) && !isOccupied(vampire.getXY().get(0) + 0, vampire.getXY().get(0) + 1)) {
                        vampire.goDown();
                    } else {
                        howManyTimes--;
                    }
                } else if (direction.equals("d")) {
                    if (!isOutOfBounds(vampire, 1, 0) && !isOccupied(vampire.getXY().get(0) + 1, vampire.getXY().get(0) + 0)) {
                    vampire.goRight();
                    } else {
                        howManyTimes--;
                    }
                }
            }
        }
    }
}
    
    

