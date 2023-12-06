import processing.core.PApplet;

import java.util.Timer;

public class PowerUp {
    private int x,y,radius;
    private String powerUpType;
    Paddle p1;
    Paddle p2;

    public String getPowerUpType() {
        return powerUpType;
    }

    public PowerUp(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        String[] types = {"ballSize", "paddleSize", "doublePoints"};
        powerUpType = types[(int)(Math.random()*types.length)];
        p1 = new Paddle(325, 20);
        p2 = new Paddle(325, 750);
    }

    public void draw(PApplet window){
        window.fill(255, 0, 0);
        window.ellipse(x,y, radius*2, radius*2);
    }

    public boolean colliding(Ball other) {
        int sumRadius = (this.getRadius() + other.getRadius());
        double d = distance(this.getX(), this.getY(), other.getX(), other.getY());
        return sumRadius >= d;
    }

    public boolean collision(Ball b) {
        if (colliding(b)) {
            if (powerUpType.equals("ballSize")) {
                ballSize(b);
            }
            if(powerUpType.equals("paddleSize")){
                if(b.lastPaddle() == 1){
                    paddleSize(p1);
                }
                else if(b.lastPaddle() == 2){
                    paddleSize(p2);
                }
            }
            return true;
        }
        return false;
    }

    private void ballSize(Ball b) {
        b.setRadius((int)(Math.random()*35)+5);
    }
    private void paddleSize(Paddle p){p.setLength((int)(Math.random()*400+100));}
    private void doublePoints(){}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    private double distance(float x1, float y1, float x2, float y2) {
        float dx = x1-x2;
        float dy = y1-y2;
        return Math.sqrt(dx*dx+dy*dy);
    }
}
