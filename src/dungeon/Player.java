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
    
    private int moves;
    
    public Player(int moves) {
        super();
        this.moves = moves;
    }
    
    public int getMovesLeft() {
        return moves;
    }
    
    public void reduceMoves() {
        moves--;
    }
    
}
