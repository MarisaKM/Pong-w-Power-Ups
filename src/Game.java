import processing.core.PApplet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Game extends PApplet {
    // TODO: declare game variables
    Ball b;
    Paddle paddle1;
    Paddle paddle2;
    PowerUp powerup;
    Boolean powerUpExists;
    int pointsPlayer1;
    int pointsPlayer2;
    boolean win;
    int powerUpTimer;
    boolean saved;
    boolean gameOver;

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
        powerUpTimer = 90;
        saved = false;
        gameOver = false;
    }

    public void draw() {
        background(255);// paint screen white
        fill(0);
        line(0, 400, 800, 400);
        textSize(50);
        text(" " + pointsPlayer1, 350, 350);
        text(" " + pointsPlayer2, 350, 500);
        fill(255,0,0);          // load red paint color
        if ((int)(Math.random()*500) == 1) {
            powerup = new PowerUp((int)(Math.random()*800), (int) (Math.random()*800), 20);
            powerup.draw(this);
            powerUpExists = true;
            powerup.collision(b);
        }

        else if (powerUpExists) {
            powerup.draw(this);
            powerup.collision(b);
        }
        if(b.scorePoint1()){
            pointsPlayer1++;
            b.reset();
        }
        if(b.scorePoint2()){
            pointsPlayer2++;
            b.reset();
        }
        b.draw(this);
        paddle1.draw(this);
        paddle2.draw(this);
        b.collision(paddle1);
        b.collision(paddle2);
        if(pointsPlayer1 == 15 || pointsPlayer2 == 15){
            gameOver = true;
            background(0);
            text("Game Over!", 280, 400);
            if (pointsPlayer1 == 15) {
                text("player 1 wins", 260, 450);
            }
            else {
                text("player 2 wins", 260, 450);
            }
            b.setX(400);
            b.setY(400);
            if (!saved) {
                try {
                    saveScores(pointsPlayer1, pointsPlayer2);
                    saved = true;
                } catch (IOException e) {
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

    public void keyReleased(){

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
