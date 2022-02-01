package main;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Item {
    
    int width, height, age;
    long last_use, delay;
    String texturePath, name;

    public Item(int width, int height, long last_use, long delay, String texturePath, String name) {
        this.width = width;
        this.height = height;
        this.last_use = last_use;
        this.delay = delay;
        this.age = 0;
        this.texturePath = texturePath;
        this.name = name;
    }
    
    protected Image getTexture(){
        
        try { return ImageIO.read(new File( this.texturePath )).getScaledInstance(width, height, Image.SCALE_DEFAULT);
        } catch (IOException ex) {System.out.println("ERRO AO OBTER TEXTURA");}  
        
        return null;
        
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getLast_use() {
        return last_use;
    }

    public long getDelay() {
        return delay;
    }

    public int getAge() {
        return age;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public String getName() {
        return name;
    }
    
    protected void doWork(){
        
        if (this .last_use + this .delay <= GUI.time){
            work();
            last_use = GUI.time;
        }
        
    }

    public void work(){
        
    }
    
}
