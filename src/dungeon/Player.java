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
public class Player extends Movable {
    
    private int movesLeft;
    
    public Player(int moves) {
        super(0,0);
        this.movesLeft = moves;
    }
    
    public int getMovesLeft() {
        return movesLeft;
    }
    
    public void reduceMovesLeftByOne() {
        movesLeft -= 1;
    }
    
}
