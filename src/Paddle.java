import processing.core.PApplet;

public class Paddle {
    private int x, y, length, width;
    private int speed;

    public Paddle(int x, int y){
        this.x = x;
        this.y = y;
        this.length = 100;
        this.width = 20;
        speed = 35;
    }

    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }
    public int getLength(){
        return length;
    }
    public int getWidth(){
        return width;
    }
    public void moveLeft() {
        x -= speed;
        if (wallCollision()) {
            x += speed;
        }
    }
    public void moveRight() {
        x += speed;
        if (wallCollision()) {
            x -= speed;
        }
    }

    public void setLength(int length) {
        if (length > 0) {
            this.length = length;
        }
    }
    public void setSpeed(int speed){
        if(speed > 0){
            this.speed = speed;
        }
    }

    public void draw(PApplet window){
        window.fill(255, 0, 0);
        window.rect(x, y, length, width);
    }

    public Boolean wallCollision(){
        if(this.x <= 0){
            return true;
        }
        if(this.x + length >= 800){
            return true;
        }
        return false;
    }
}
