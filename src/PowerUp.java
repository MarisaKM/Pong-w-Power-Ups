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
        int sumRadius = (this.getRadius() + other.getRadius());
        double d = distance(this.getX(), this.getY(), other.getX(), other.getY());
        System.out.println(sumRadius);
        return sumRadius >= d;
    }

    public void collision(Ball b) {
        if (colliding(b)) {
            this.x = 9999;
            if (powerUpType == "ballSize") {
                ballSize(b);
            }
        }
    }

    private void ballSize(Ball b) {
        b.setRadius((int)(Math.random()*35)+5);
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
        return Math.sqrt(dx*dx+dy*dy);
    }
}
