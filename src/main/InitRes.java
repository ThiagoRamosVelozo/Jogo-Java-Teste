package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class InitRes {
    
    public static String[] dirs = {
        "C:/Users/Usuario/Desktop/PIXELART/FIREBALL.png",
        "C:/Users/Usuario/Desktop/PIXELART/BOX.png",
        "C:/Users/Usuario/Desktop/PIXELART/HAMMER.png",
        "C:/Users/Usuario/Desktop/PIXELART/SLICE.png",
        "C:/Users/Usuario/Desktop/PIXELART/BBP.png",
        "C:/Users/Usuario/Desktop/PIXELART/EXP.png",
        "C:/Users/Usuario/Desktop/PIXELART/BB.png",
        "C:/Users/Usuario/Desktop/PIXELART/DICE.png",
        "C:/Users/Usuario/Desktop/PIXELART/DICE2.png",
        "C:/Users/Usuario/Desktop/PIXELART/BOLT.png",
        "C:/Users/Usuario/Desktop/PIXELART/ARR1.png",
        "C:/Users/Usuario/Desktop/PIXELART/SATANICROSS.png",
        "C:/Users/Usuario/Desktop/PIXELART/WALL.png"
    };
    
    public static ArrayList<BufferedImage> bis = imgs();
    
    private static ArrayList<BufferedImage> imgs() {
        
        ArrayList<BufferedImage> out = new ArrayList<>();
        
        for (String dir : dirs) {
            
            try {
                
                out .add( ImageIO.read(new File( dir )) );
                
            } catch (IOException ex) {System.out.println("ERRO AO OBTER TEXTURA");}  
            
        }
        
        return out;
        
    }
    
}
