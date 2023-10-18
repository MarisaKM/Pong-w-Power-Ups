import processing.core.PApplet;

public class PowerUp {
    private int x,y,radius;
    private String powerUpType;
    public PowerUp(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        String[] types = {"ballSize"};
        powerUpType = types[(int)(Math.random()*types.length)];
    }

    public void draw(PApplet window){
        window.fill(255, 0, 0);
        window.ellipse(x,y, radius*2, radius*2);
    }

    public boolean colliding(Ball other) {
        return this.getRadius() + other.getRadius() <= distance(this.getX(), this.getY(), other.getX(), other.getY());
    }

    public void collision(Ball b) {
        if (colliding(b)) {
            if (powerUpType == "ballSize") {
                ballSize();
            }
        }
    }

    private void ballSize() {

    }

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
        return dx/dy;
    }
}
