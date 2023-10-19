import processing.core.PApplet;

public class Ball {
    private int x, y;
    private double speed;
    private int r,g,b;
    private int radius;
    private double xChange;
    private int yChange;
    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
        radius = 10;
        speed = 5;
        xChange = 0;
        yChange = 1;
    }
    public void setSpeed(double speed) {
        if (speed > 0) this.speed = speed;
    }
    public void setRadius(int radius) {
        if (radius > 0) this.radius = radius;
    }

    public int getRadius(){
        return radius;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    //check if ball hits paddle/wall or gets past paddle

    public void draw(PApplet window){
        y-=speed*yChange;
        x-=speed*xChange;
        window.fill(255, 0, 0);
        window.ellipse(x,y, radius*2, radius*2);
    }
    public void collision(Paddle p ){
        if (Math.abs((this.y + radius * 2) - (p.getY() + p.getWidth())) <= 5) {
            if (this.x >= p.getX() && this.x <= (p.getX() + p.getLength())) {
                System.out.println("hit bottom paddle");
                speed = -1 * speed;
                xChange = Math.random()/5;
            }
        }
        else if (Math.abs((this.y - radius * 2) - (p.getY())) <= 5) {
            if (this.x >= p.getX() && this.x <= (p.getX() + p.getLength())) {
                System.out.println("hit top paddle");
                speed = -1 * speed;
                xChange = Math.random()/5;
            }
        }
    }
}
