import processing.core.PApplet;
import processing.core.PImage;

public class homeScreen {
    PImage settings;
    public homeScreen(PApplet window){
        settings = window.loadImage("settings-icon.png");
    }
    public void draw(PApplet window){
        window.background(255);
        window.fill(74, 151, 219);
        window.textSize(50);
        window.text("Pong with Power Ups", 150, 200);
        window.fill(0);
        window.textSize(35);
        settings.resize(75, 75);
        window.image(settings, 712, 12);
        window.rect(300, 450, 200, 100);
        window.fill(34, 200, 34);
        window.text("START", 345, 515);
    }
}
