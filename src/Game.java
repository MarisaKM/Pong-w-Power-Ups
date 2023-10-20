import processing.core.PApplet;

public class Game extends PApplet {
    // TODO: declare game variables
    Ball b;
    Paddle paddle1;
    Paddle paddle2;
    PowerUp powerup;
    Boolean powerUpExists;
    int pointsPlayer1;
    int pointsPlayer2;
    boolean hasScored;
    boolean ballPresent;
    public void settings() {
        size(800, 800);   // set the window size
    }

    public void setup() {
        // TODO: initialize game variables
        powerUpExists = false;
        paddle1 = new Paddle(325,20);
        paddle2 = new Paddle(325,750);
        b = new Ball((int)(Math.random()*800),(int)(Math.random()*800));
        pointsPlayer1 = 0;
        pointsPlayer2 = 0;
        hasScored = false;
        ballPresent = true;
    }

    /***
     * Draws each frame to the screen.  Runs automatically in a loop at frameRate frames a second.
     * tick each object (have it update itself), and draw each object
     */
    public void draw() {
        background(255);// paint screen white
        fill(0);
        line(0, 400, 800, 400);
        textSize(50);
        text(" " + pointsPlayer1, 350, 350);
        text(" " + pointsPlayer2, 350, 500);
        fill(255,0,0);          // load green paint color
        if ((int)(Math.random()*500) == 1) {
            powerup = new PowerUp(400, (int) (Math.random()*800), 20);
            powerup.draw(this);
            powerUpExists = true;
            powerup.collision(b);
        }
        else if (powerUpExists) {
            powerup.draw(this);
            powerup.collision(b);
        }
        if(b.scorePoint(paddle1)){
            ballPresent = false;
        }
        if(ballPresent == false){

        }
        b.draw(this);
        paddle1.draw(this);
        paddle2.draw(this);
        b.collision(paddle1);
        b.collision(paddle2);

        /**
        ellipse(mouseX, mouseY, 60, 60);  // draw circle at mouse loc
        ellipse(mouseX - 80, mouseY, 60, 60);  // draw circle at mouse loc
        ellipse(mouseX + 80, mouseY, 60, 60);  // draw circle at mouse loc
         **/
    }

    public void addScore1(){
        pointsPlayer1++;
    }
    public void addScore2(){
        pointsPlayer2++;
    }
    public void keyPressed() {
        if (this.keyCode == LEFT) {
            paddle1.moveLeft();
        }
        if (this.keyCode == RIGHT) {
            paddle1.moveRight();
        }
        if (this.key == 'a') {
            paddle2.moveLeft();
        }
        if (this.key == 'd') {
            paddle2.moveRight();
        }
    }
    public static void main(String[] args) {
        PApplet.main("Game");
    }
}
