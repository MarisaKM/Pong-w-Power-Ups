import processing.core.PApplet;

public class Game extends PApplet {
    // TODO: declare game variables
    Ball b;
    Paddle paddle1;
    Paddle paddle2;
    PowerUp powerup;
    Boolean powerUpExists;
    public void settings() {
        size(800, 800);   // set the window size
    }

    public void setup() {
        // TODO: initialize game variables
        powerUpExists = false;
        paddle1 = new Paddle(350,20);
        paddle2 = new Paddle(350,750);
        b = new Ball(400,400);
        int pointsPlayer1;
        int pointsPlayer2;


    }

    /***
     * Draws each frame to the screen.  Runs automatically in a loop at frameRate frames a second.
     * tick each object (have it update itself), and draw each object
     */
    public void draw() {
        background(255);    // paint screen white
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
