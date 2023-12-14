import processing.core.PApplet;

public class Ball {
    private int x, y;
    private double speed;
    private int r,g,b;
    private int radius;
    private double xChange;
    private int yChange;
    private int ignore;
    private int score;
    private int direction;
    private Boolean p1Collision, p2Collision;
    private int lastPlayer;
    public static int DEFAULT_RADIUS = 10;
    public static int DEFAULT_SPEED = 5;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
        radius = DEFAULT_RADIUS;
        speed = DEFAULT_SPEED;
        xChange = Math.random()*-0.5;
        yChange = 1;
        ignore = 0;
        score = 0;
        direction = (int)(Math.random()*2);
        p1Collision = false;
        p2Collision = false;
        lastPlayer = 0;
        r = 255;
        g = 255;
        b = 255;
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
    public void setColor(PApplet window, int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void draw(PApplet window){
        if(direction == 0) {
            y -= speed * yChange;
            x -= speed * xChange;
        }
        if(direction == 1){
            y+=speed*yChange;
            x+=speed*xChange;
        }
        window.fill(r, g, b);
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
        x = 400;
        y = (int)(Math.random()*400+200);
        xChange = Math.random()*1-0.5;
        yChange = 1;
        while (Math.abs(xChange) < 0.2) {
            xChange = Math.random() *1 -0.5;
        }
    }
    public int lastPaddle(){
        if(p1Collision){
            return 1;
        }
        if(p2Collision){
            return 2;
        }
        return 0;
    }


    public void collision(Paddle p, String lastKeyPress){

        if ((Math.abs((this.y + radius) - (p.getY() + p.getWidth())) <= p.getWidth()) && this.y < 400) {
            if ((this.x + radius) >= p.getX() && (this.x - radius) <= (p.getX() + p.getLength())) {
                //System.out.println("hit top paddle");
                //System.out.println("dir of T: " + lastKeyPress);
                speed = -1 * speed;
                if (!lastKeyPress.equals(getBallDirection())){
                    xChange = -xChange;
                }
                p1Collision = true;
                lastPlayer = 2;
            }
        }
        else if (Math.abs((this.y - radius) - (p.getY())) <= p.getWidth()) {
            if ((this.x + radius) >= p.getX() && (this.x - radius) <= (p.getX() + p.getLength())) {
                if(this.y == p.getLength()){
                    xChange = xChange;
                }
                //System.out.println("hit bottom paddle");
                //System.out.println("dir of B: " + lastKeyPress);
                speed = -1 * speed;
                if (lastKeyPress.equals(getBallDirection())) {
                    xChange = -xChange;
                }
                p2Collision = true;
                lastPlayer = 1;
            }
        }
        else if ((this.x - radius) <= 0 || (this.x + radius) >= 800) {
            if(this.x == 0){
                xChange = -xChange;
            }
            if (ignore <= 0) {
                xChange = -1 * xChange;
                ignore = 5;
            }
        }
        ignore--;
    }
    public String getBallDirection(){
        String dir = "";
        if(xChange > 0){
            dir = "right";
        }
        else if(xChange < 0){
            dir = "left";
        }
        return dir;
    }
    public int getLastPlayer(){
        return lastPlayer;
    }
}
