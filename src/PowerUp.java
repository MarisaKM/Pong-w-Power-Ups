import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.util.Timer;

public class PowerUp {
    private int x,y,radius;
    private String powerUpType;
    private Paddle p1;
    private Paddle p2;
    private Ball b;
    private int affectedPaddle;
    private PImage powerUpIcon;

    public String getPowerUpType() {
        return powerUpType;
    }

    public PowerUp(PApplet window, int x, int y, int radius) {
        this.x = 400;//x;
        this.y = y;
        this.radius = radius;
        this.length = radius*4;
        this.width = radius*4;
        String[] types = {"ballSize", "paddleSize", "doublePoints", "paddleSpeed", "ballSpeed"};
        powerUpType = types[(int)(Math.random()*types.length)];
        powerUpIcon = window.loadImage("powerUp.png");
    }

    public void draw(PApplet window){
        //window.ellipse(x,y + radius, radius*2, radius*2);
        window.imageMode(PConstants.CENTER);
        powerUpIcon.resize(radius*2, radius*2);
        window.image(this.powerUpIcon,x ,y + radius);
    }

    public boolean colliding(Ball other) {
        int sumRadius = (this.getRadius() + other.getRadius());
        double d = distance(this.getX(), this.getY(), other.getX(), other.getY());
        return sumRadius >= d;
    }

    public boolean collision(Ball b, Paddle p1, Paddle p2) {
        this.b = b;
        this.p1 = p1;
        this.p2 = p2;
        affectedPaddle = b.lastPaddle();
        if (colliding(b)) {
            if (powerUpType.equals("ballSize")) {
                ballSize(b);
            }
            if (powerUpType.equals("ballSpeed")) {
                ballSpeed(b);
            }
            if(powerUpType.equals("paddleSize")){
                if(b.lastPaddle() == 1){
                    paddleSize(p1);
                }
                else if(b.lastPaddle() == 2){
                    paddleSize(p2);
                }
            }
            if (powerUpType.equals("paddleSpeed")) {
                if(b.lastPaddle() == 1){
                    paddleSpeed(p1);
                }
                else if(b.lastPaddle() == 2){
                    paddleSpeed(p2);
                }
            }
            return true;
        }
        return false;
    }

    private void ballSize(Ball b) {
        b.setRadius((int)(Math.random()*35)+5);
    }
    private void paddleSize(Paddle p){p.setLength((int)(Math.random()*400)+100);p.wallCollision();}
    private void ballSpeed(Ball b){b.setSpeed((int)(Math.random()*5)+10);}
    private void paddleSpeed(Paddle p){p.setSpeed((int)(Math.random()*50)+20);}

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

    public void toDefaultValue() {
        if (powerUpType.equals("ballSize")) {
            b.setRadius(Ball.DEFAULT_RADIUS);
        }
        if (powerUpType.equals("ballSpeed")) {
            b.setSpeed(Ball.DEFAULT_SPEED);
        }
        if (powerUpType.equals("paddleSize")) {
            if (affectedPaddle == 1) {
                p1.setLength(Paddle.DEFAULT_LENGTH);
            }
            else if (affectedPaddle == 2) {
                p2.setLength(Paddle.DEFAULT_LENGTH);
            }
        }
        if (powerUpType.equals("paddleSpeed")) {
            if (affectedPaddle == 1) {
                p1.setSpeed(Paddle.DEFAULT_SPEED);
            }
            else if (affectedPaddle == 2) {
                p2.setSpeed(Paddle.DEFAULT_SPEED);
            }
        }
    }
}
