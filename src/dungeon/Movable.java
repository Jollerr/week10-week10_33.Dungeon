/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeon;

/**
 * @author Kristjan
 */
public abstract class Movable {

    int x;
    int y;

    public Movable(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void goUp() {
        y -= 1;
    }

    public void goDown() {
        y += 1;
    }

    public void goRight() {
        x += 1;
    }

    public void goLeft() {
        x -= 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
