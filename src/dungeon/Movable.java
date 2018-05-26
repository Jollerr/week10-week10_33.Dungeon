/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;
import java.util.*;

/**
 *
 * @author Kristjan
 */
public abstract class Movable {

    List<Integer> coordinates;
    
    public Movable() {
        this.coordinates = new ArrayList<Integer>();
        this.coordinates.add(0);
        this.coordinates.add(0);
    }
    
    public void goUp() {
        int old = this.coordinates.get(1);
        this.coordinates.set(1, old - 1);
    }
    
    public void goDown() {
        int old = this.coordinates.get(1);
        this.coordinates.set(1, old + 1);
    }
    
    public void goRight() {
        int old = this.coordinates.get(0);
        this.coordinates.set(0, old + 1);
    }
    
    public void goLeft() {
        int old = this.coordinates.get(0);
        this.coordinates.set(0, old - 1);
    }
    
    public void setXY(int x, int y) {
        this.coordinates.clear();
        this.coordinates.add(x);
        this.coordinates.add(y);
    }
     
    public List<Integer> getXY() {
        return coordinates;
    } 
}
