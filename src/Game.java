import javafx.scene.layout.Background;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Game extends PApplet {
    // TODO: declare game variables
    Ball b;
    Paddle paddle1, paddle2;
    PowerUp powerup;
    boolean powerUpExists;
    int pointsPlayer1, pointsPlayer2;
    boolean win;
    int powerUpTimer;
    boolean powerUpActive;
    boolean saved;
    double pointMultiplier;
    int multiplierFor;
    String lastUser1KeyPressed, lastUser2KeyPressed;
    private static int DEFAULT_TIMER = 5*60;
    public void settings() {
        size(800, 800);   // set the window size
    }

    public void setup() {
        powerUpExists = false;
        paddle1 = new Paddle(325,20);
        paddle2 = new Paddle(325,750);
        b = new Ball(400,400);
        pointsPlayer1 = 0;
        pointsPlayer2 = 0;
        win = false;
        powerUpTimer = DEFAULT_TIMER;
        saved = false;
        gameOver = false;
        pointMultiplier = 1;
        multiplierFor = 0;
        powerUpActive = false;
        lastUser1KeyPressed = "";
        lastUser2KeyPressed = "";
        homeScreen = true;
        numKeys = "";
        currX = 410;
        winAmount = 0;
        currStr = "Enter Win Amount: ";
        update1 = 0;
        update2 = 0;
        settings = loadImage("settings-icon.png");
        settingsOpen = false;
        background1 = true;
        background2 = false;
        background3 = false;
        background4 = false;
        ballCol1 = true;
        ballCol2 = false;
        ballCol3 = false;
        ballCol4 = false;
        paddleCol1 = true;
        paddleCol2 = false;
        paddleCol3 = false;
        paddleCol4 = false;
    }

    public void draw() {
        update1--;
        update2--;
        if (update1 <= 0) {
            lastUser1KeyPressed = "";
        }
        if (update2 <= 0) {
            lastUser2KeyPressed = "";
        }
        if (homeScreen) {
            background(255);
            fill(74, 151, 219);
            textSize(50);
            text("Pong with Power Ups", 150, 200);
            fill(0);
            textSize(35);
            text(currStr, 250, 400);
            settings.resize(75, 75);
            image(settings, 712, 12);
            rect(300, 450, 200, 100);
            fill(34, 200, 34);
            text("START", 345, 515);
            return;
        }
        if (settingsOpen) {
            background(255);
            fill(0);
            text("SETTINGS", 300, 100);
            textSize(30);
            text("Background: ", 25, 200);
            text("Ball Color: ", 25, 320);
            text("Paddle Color: ", 25, 440);
            createBackgroundRectangles();
            fill(255);
            createBallRectangles();
            fill(255);
            createPaddleRectangles();
            fill(0);
            fill(255, 0, 0);
            textSize(20);
            text("< BACK", 50, 50);
            rect(300, 600, 300, 100);
            textSize(35);
            fill(34, 200, 34);
            text("Load Game", 350, 665);
        }
        if (powerUpActive) {
            powerUpTimer--;
        }
        if (powerUpTimer <= 0) {
            powerUpActive = false;
            System.out.println(powerup.getPowerUpType() + " has expired");
            powerup.toDefaultValue();
            if (powerup.getPowerUpType().equals("doublePoints")) {
                pointMultiplier /= 2;
            }
            powerUpTimer = DEFAULT_TIMER;
        }
        textSize(50);

        if (!gameOver && !homeScreen && !settingsOpen) {
            if (background1) {
                background(0);
            } else if (background2) {
                background(76, 136, 247);
            } else if (background3) {
                background(79, 122, 115);
            } else if (background4) {
                background(210, 123, 237);
            }
            stroke(255);
            line(0, 400, 800, 400);
            stroke(0);
            fill(255);
            text(" " + pointsPlayer1, 350, 350);
            text(" " + pointsPlayer2, 350, 500);
            fill(255, 0, 0);  // load red paint color
            if ((int) (Math.random() * 500) == 1 && powerUpActive == false) {
                powerup = new PowerUp(this, (int) (Math.random() * 800), (int) (Math.random() * 800), 20);
                powerup.draw(this);
                System.out.println("Powerup type: " + powerup.getPowerUpType());
                powerUpExists = true;
                if (powerup.collision(b, paddle1, paddle2)) {
                    powerUpActive = true;
                    powerUpExists = false;
                    if (powerup.getPowerUpType().equals("doublePoints")) {
                        multiplierFor = b.lastPaddle();
                        pointMultiplier *= 2;
                    }
                }
            } else if (powerUpExists) {
                powerup.draw(this);
                if (powerup.collision(b, paddle1, paddle2)) {
                    powerUpActive = true;
                    powerUpExists = false;
                    if (powerup.getPowerUpType().equals("doublePoints")) {
                        multiplierFor = b.lastPaddle();
                        pointMultiplier *= 2;
                    }
                }
            }
            if (b.scorePoint1()) {
                if (multiplierFor == 1) {
                    pointsPlayer1 += pointMultiplier;
                } else {
                    pointsPlayer1++;
                }
                b.reset();
            }
            if (b.scorePoint2()) {
                if (multiplierFor == 2) {
                    pointsPlayer2 += pointMultiplier;
                } else {
                    pointsPlayer2++;
                }
                b.reset();
            }
            fill(0, 0, 255);
            b.draw(this);
            paddle1.draw(this);
            paddle2.draw(this);
            b.collision(paddle1, lastUser1KeyPressed);
            b.collision(paddle2, lastUser1KeyPressed);
        }
        if (winAmount > 0) {
            if (pointsPlayer1 >= winAmount || pointsPlayer2 >= winAmount) {
                gameOver = true;
                background(0);
                text("Game Over!", 280, 400);
                if (pointsPlayer1 >= 15) {
                    text("player 1 wins", 260, 450);
                } else {
                    text("player 2 wins", 260, 450);
                }
                b.setX(400);
                b.setY(400);
                fill(255);
                rect(300, 520, 200, 100);
                fill(34, 200, 34);
                textSize(35);
                text("play again", 315, 580);
                fill(255, 0, 0);
                if (!saved) {
                    try {
                        saveScores(pointsPlayer1, pointsPlayer2);
                        saved = true;
                    } catch (IOException e) {
                    }
                }
            }
        }
    }


    private void saveScores(int pointsPlayer1, int pointsPlayer2) throws IOException{
        String saved = readFile("saveFile.txt");
        saved += pointsPlayer1 + "," + pointsPlayer2 + "\n";
        writeDataToFile("saveFile.txt", saved);
    }

    public static void writeDataToFile(String filePath, String data) throws IOException {
        try (FileWriter f = new FileWriter(filePath);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter writer = new PrintWriter(b)) {


            writer.println(data);


        } catch (IOException error) {
            System.err.println("There was a problem writing to the file: " + filePath);
            error.printStackTrace();
        }
    }

    public static String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public void mousePressed() {
        if (this.mouseX > 300 && this.mouseX < 500 && this.mouseY > 470 && this.mouseY < 570) {
            setup();
        }
        if(homeScreen){
            if(this.mouseX >= 712 && this.mouseX <= 787 && this.mouseY >= 12 && this.mouseY <= 87){
                homeScreen = false;
                settingsOpen = true;
            }
            else if(this.mouseX >= 300 && this.mouseX <= 500 && this.mouseY >= 450 && this.mouseY <= 550){
                winAmount = Integer.parseInt(this.numKeys.trim()+1);
                if(numKeys.equals("")){
                    System.out.println("Invalid Amount --> set default 1");
                }
                homeScreen = false;
            }
        }
        if(settingsOpen){
            if(this.mouseX >= 50 && this.mouseX <= 130 && this.mouseY >= 32 && this.mouseY <= 52){
                settingsOpen = false;
                homeScreen = true;
            }
            if(this.mouseX >= 250 && this.mouseX <= 350){
                 if(this.mouseY >= 165 && this.mouseY <= 215) {
                     setBackgroundsFalse();
                     background1 = true;
                     System.out.println("Background is black");
                 }
                 if(this.mouseY >= 280 && this.mouseY <= 330){
                     setBallColorsFalse();
                     ballCol1 = true;
                     b.setColor(this, 255, 255, 255);
                     System.out.println("Ball color is white");
                 }
                 if(this.mouseY >= 400 && this.mouseY <= 450){
                     setPaddleColorsFalse();
                     paddleCol1 = true;
                     paddle1.setColor(this, 255, 255, 255);
                     paddle2.setColor(this, 255, 255, 255);
                     System.out.println("Paddle color is white");
                 }
            }
            if(this.mouseX >= 375 && this.mouseX <= 475){
                if(this.mouseY >= 165 && this.mouseY <= 215) {
                    setBackgroundsFalse();
                    background2 = true;
                    System.out.println("Background is blue");
                }
                if(this.mouseY >= 280 && this.mouseY <= 330){
                    setBallColorsFalse();
                    ballCol2 = true;
                    b.setColor(this, 59, 175, 61);
                    System.out.println("Ball color is green");
                }
                if(this.mouseY >= 400 && this.mouseY <= 450){
                    setPaddleColorsFalse();
                    paddleCol2 = true;
                    paddle1.setColor(this, 59, 175, 61);
                    paddle2.setColor(this, 59, 175, 61);
                    System.out.println("Paddle color is green");
                }
            }
            if(this.mouseX >= 500 && this.mouseX <= 600){
                if(this.mouseY >= 165 && this.mouseY <= 215) {
                    setBackgroundsFalse();
                    background3 = true;
                    System.out.println("Background is turquoise");
                }
                if(this.mouseY >= 280 && this.mouseY <= 330){
                    setBallColorsFalse();
                    ballCol3 = true;
                    b.setColor(this, 79, 122, 115);
                    System.out.println("Ball color is navy");
                }
                if(this.mouseY >= 400 && this.mouseY <= 450){
                    setPaddleColorsFalse();
                    paddleCol3 = true;
                    paddle1.setColor(this, 79, 122, 115);
                    paddle2.setColor(this, 79, 122, 115);
                    System.out.println("Paddle color is navy");
                }
            }
            if(this.mouseX >= 625 && this.mouseX <= 725){
                if(this.mouseY >= 165 && this.mouseY <= 215) {
                    setBackgroundsFalse();
                    background4 = true;
                    System.out.println("Background is purple");
                }
                if(this.mouseY >= 280 && this.mouseY <= 330){
                    setBallColorsFalse();
                    ballCol4 = true;
                    b.setColor(this,210, 123, 237);
                    System.out.println("Ball color is teal");
                }
                if(this.mouseY >= 400 && this.mouseY <= 450){
                    setPaddleColorsFalse();
                    paddleCol4 = true;
                    paddle1.setColor(this, 210, 123, 237);
                    paddle2.setColor(this, 210, 123, 237);
                    System.out.println("Paddle color is teal");
                }
            }

        }
    }
    public void keyReleased(){

    }
    public void setBackgroundsFalse(){
        background1 = false;
        background2 = false;
        background3 = false;
        background4 = false;
    }
    public void setBallColorsFalse(){
        ballCol1 = false;
        ballCol2 = false;
        ballCol3 = false;
        ballCol4 = false;
    }
    public void setPaddleColorsFalse(){
        paddleCol1 = false;
        paddleCol2 = false;
        paddleCol3 = false;
        paddleCol4 = false;
    }
    public void createBackgroundRectangles(){
        fill(0);
        rect(250, 165, 100, 50);
        fill(76, 136, 247);
        rect(375, 165, 100, 50);
        fill(79, 122, 115);
        rect(500, 165, 100, 50);
        fill(210, 123, 237);
        rect(625, 165, 100, 50);
    }
    public void createBallRectangles(){
        rect(250, 280, 100, 50);
        fill(59, 175, 61);
        rect(375, 280, 100, 50);
        fill(20, 18, 117);
        rect(500, 280, 100, 50);
        fill(96, 224, 224);
        rect(625, 280, 100, 50);
    }
    public void createPaddleRectangles(){
        rect(250, 400, 100, 50);
        fill(59, 175, 61);
        rect(375, 400, 100, 50);
        fill(20, 18, 117);
        rect(500, 400, 100, 50);
        fill(96, 224, 224);
        rect(625, 400, 100, 50);
    }

    public void keyPressed() {
        if (this.keyCode == LEFT) {
            paddle1.moveLeft();
            lastUser1KeyPressed = "left";
        }
        if (this.keyCode == RIGHT) {
            paddle1.moveRight();
            lastUser1KeyPressed = "right";
        }
        if (this.key == 'a') {
            paddle2.moveLeft();
            lastUser2KeyPressed = "left";
        }
        if (this.key == 'd') {
            paddle2.moveRight();
            lastUser2KeyPressed = "right";
        }
    }
    public static void main(String[] args) {
        PApplet.main("Game");
    }
}
