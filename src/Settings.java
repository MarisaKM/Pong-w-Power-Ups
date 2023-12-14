import processing.core.PApplet;

public class Settings {
    public void draw(PApplet window){
        window.background(255);
        window.fill(0);
        window.text("SETTINGS", 300, 100);
        window.textSize(30);
        window.text("Background: ", 25, 200);
        window.text("Ball Color: ", 25, 320);
        window.text("Paddle Color: ", 25, 440);
        createBackgroundRectangles(window);
        window.fill(255);
        createBallRectangles(window);
        window.fill(255);
        createPaddleRectangles(window);
        window.fill(0);
        window.fill(255, 0, 0);
        window.textSize(20);
        window.text("< BACK", 50, 50);
        window.rect(300, 600, 300, 100);
        window.textSize(35);
        window.fill(34, 200, 34);
        window.text("Load Game", 350, 665);
    }
    public void createBackgroundRectangles(PApplet window){
        window.fill(0);
        window.rect(250, 165, 100, 50);
        window.fill(76, 136, 247);
        window.rect(375, 165, 100, 50);
        window.fill(79, 122, 115);
        window.rect(500, 165, 100, 50);
        window.fill(210, 123, 237);
        window.rect(625, 165, 100, 50);
    }
    public void createBallRectangles(PApplet window){
        window.rect(250, 280, 100, 50);
        window.fill(59, 175, 61);
        window.rect(375, 280, 100, 50);
        window.fill(20, 18, 117);
        window.rect(500, 280, 100, 50);
        window.fill(96, 224, 224);
        window.rect(625, 280, 100, 50);
    }
    public void createPaddleRectangles(PApplet window){
        window.rect(250, 400, 100, 50);
        window.fill(59, 175, 61);
        window.rect(375, 400, 100, 50);
        window.fill(20, 18, 117);
        window.rect(500, 400, 100, 50);
        window.fill(96, 224, 224);
        window.rect(625, 400, 100, 50);
    }
}
