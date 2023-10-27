import processing.core.PApplet;

public class Ball {
    private int x, y;
    private double speed;
    private int r,g,b;
    private int radius;
    private double xChange;
    private int yChange;
    private int ignore;
    int score;
    int direction;
    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
        radius = 10;
        speed = 5;
        xChange = Math.random()*-0.5;
        yChange = 1;
        ignore = 0;
        score = 0;
        direction = (int)(Math.random()*2);
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
    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}
    //check if ball hits paddle/wall or gets past paddle

    public void draw(PApplet window){
        if(direction == 0) {
            y -= speed * yChange;
            x -= speed * xChange;
        }
        if(direction == 1){
            y+=speed*yChange;
            x+=speed*xChange;
        }
        window.fill(255, 0, 0);
        window.ellipse(x,y, radius*2, radius*2);
    }

    public boolean scorePoint1(){
        if(this.y <= 0){
            direction = (int)(Math.random()*2);
            return true;
        }
        return false;
    }
    public boolean scorePoint2(){
        if(this.y >= 800){
            direction = (int)(Math.random()*2);
            return true;
        }
        return false;
    }

    public void reset(){
        x = (int)(Math.random()*400+200);
        y = (int)(Math.random()*400+200);
    }
    public void collision(Paddle p ){
        if (Math.abs((this.y + radius) - (p.getY() + p.getWidth())) <= p.getWidth()) {
            if ((this.x + radius) >= p.getX() && (this.x - radius) <= (p.getX() + p.getLength())) {
                System.out.println("hit bottom paddle");
                speed = -1 * speed;
                xChange = xChange * -1;
            }
        }
        else if (Math.abs((this.y - radius) - (p.getY())) <= p.getWidth()) {
            if ((this.x + radius) >= p.getX() && (this.x - radius) <= (p.getX() + p.getLength())) {
                System.out.println("hit top paddle");
                speed = -1 * speed;
                xChange = xChange * -1;
            }
        }
        else if ((this.x - radius) <= 0 || (this.x + radius) >= 800) {
            if (ignore <= 0) {
                xChange = -1 * xChange;
                ignore = 5;
            }
        }
        ignore--;
    }
}
