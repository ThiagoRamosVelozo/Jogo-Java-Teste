package main;

import java.awt.Rectangle;

public class ICatalog {
    
    static Item newFireWand(){
        
        return new Item(20, 20, 0, 50, "C:/Users/Usuario/Desktop/PIXELART/WAND.png", "FIREWAND"){
            
            @Override public void work(){
                
                //GUI.addThis( PCatalog.newFireball(GUI.a, GUI.x+GUI.w/2*(GUI.toLeft ? -1: 1), GUI.y+GUI.h/2) );
                GUI.addThis( PCatalog.newTreeRay(GUI.a, GUI.x+GUI.w/2*(GUI.toLeft ? -1: 1), GUI.y+GUI.h/2) );
                
            }
            
        };
        
    }
    
    static Item newFork(){
        
        return new Item(20, 20, 0, 50, "C:/Users/Usuario/Desktop/PIXELART/FORK.png", "FORK"){
            
            @Override public void work(){
                
                GUI.addThis( PCatalog.newPizzaSlice(GUI.a, GUI.x+GUI.w/2*(GUI.toLeft ? -1: 1), GUI.y+GUI.h/2) );
                
            }
            
        };
        
    }
    
    static Item newHammer(){
        
        return new Item(30, 30, 0, 50, "C:/Users/Usuario/Desktop/PIXELART/HAMMER.png", "WEIRDBOX"){
            
            @Override public void work(){
                
                //GUI.addThis( PCatalog.newHammerBlast(GUI.a, GUI.x+GUI.w/2*(GUI.toLeft ? -1: 1), GUI.y+GUI.h/2) );
                GUI.walls.add( new Rectangle( GUI.m.x - 50, GUI.m.y - 50, 100, 100 ) );
                
            }
            
        };
        
    }
    
    static Item newTeleportationRod(){
        
        return new Item(30, 30, 0, 1, "C:/Users/Usuario/Desktop/PIXELART/TP.png", "TPR"){
            
            @Override public void work(){
                
                GUI.x = GUI.m.x - GUI.w/2;
                GUI.y = GUI.m.y - GUI.h/2;
                
            }
            
        };
        
    }
    
    static Item newWeirdThrowableBox(){
        
        return new Item(30, 30, 0, 5, "C:/Users/Usuario/Desktop/PIXELART/BOX.png", "WEIRDBOX"){
            
            @Override public void work(){
                
                GUI.addThis( PCatalog.newBox(GUI.a, GUI.x+GUI.w/2*(GUI.toLeft ? -1: 1), GUI.y+GUI.h/2) );
                
            }
            
        };
        
    }
    
    static Item newTrident(){
        
        return new Item(30, 30, 0, 50, "C:/Users/Usuario/Desktop/PIXELART/TRIDN.png", "TRIDN"){
            
            @Override public void work(){
                
                GUI.addThis( PCatalog.newSatanicross(GUI.a, GUI.x+GUI.w/2*(GUI.toLeft ? -1: 1), GUI.y+GUI.h/2) );
                //GUI.projectiles.add( PCatalog.newGrenade(GUI.a, GUI.x+GUI.w/2*(GUI.toLeft ? -1: 1), GUI.y+GUI.h/2) );
                
            }
            
        };
        
    }
    
    static Item newBoweirdo(){
        
        return new Item(15, 30, 0, 50, "C:/Users/Usuario/Desktop/PIXELART/BOWEIRDO.png", "BOWEIRDO"){
            
            @Override public void work(){
                
                GUI.addThis( PCatalog.newCrazyArrow(GUI.a, GUI.x+GUI.w/2*(GUI.toLeft ? -1: 1), GUI.y+GUI.h/2) );
                
            }
            
        };
        
    }
    
    static Item newBomb(){
        
        return new Item(25, 30, 0, 10, "C:/Users/Usuario/Desktop/PIXELART/BB.png", "BOMB"){
            
            @Override public void work(){
                
                GUI.addThis( PCatalog.newBomb(GUI.x+GUI.w/2*(GUI.toLeft ? -1: 1), GUI.y+GUI.h/2) );
                
            }
            
        };
        
    }
    
    static Item newPizzaBomb(){
        
        return new Item(25, 30, 0, 1000, "C:/Users/Usuario/Desktop/PIXELART/BBP.png", "PBOMB"){
            
            @Override public void work(){
                
                GUI.addThis( PCatalog.newPizzaBomb(GUI.x+GUI.w/2*(GUI.toLeft ? -1: 1), GUI.y+GUI.h/2) );
                
            }
            
        };
        
    }
    
    static Item newSuspiciousDice(){
        
        return new Item(20, 20, 0, 50, "C:/Users/Usuario/Desktop/PIXELART/DICE.png", "ULTIMATEDICE"){
            
            @Override public void work(){
                
                GUI.addThis( PCatalog.newDiceOfUltimateRandomness(GUI.a, GUI.x+GUI.w/2*(GUI.toLeft ? -1: 1), GUI.y+GUI.h/2) );
                
            }
            
        };
        
    }
    
}
