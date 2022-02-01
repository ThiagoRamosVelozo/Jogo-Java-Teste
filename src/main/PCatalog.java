package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

/**
 *
 * @author Usuario
 */
public class PCatalog {
    
    static Random rng = new Random();
    
    static Color[] rbw = new Color[]{
        Color.yellow, Color.blue, Color.red, Color.cyan, Color.green, Color.magenta
    };
    
    static Projectile newFireball(int a, int x, int y){
        
        return new Projectile(9, 10, a, 60, 30, x, y, 99, 0, 0){
            
            @Override public void behavior(){
                this.ang += rng .nextInt(9) -4;
                
            }
            
            @Override public void collisionWith(Projectile p){
                
                if (p.getTxt() == 4){
                    p.life = 0;
                }
                
            }

        };
    
    };
    
    static Projectile newTreeRay(int a, int x, int y){
        
        Graphics2D g2 = (Graphics2D) GUI.centro.getGraphics();
        
        return new Projectile(9, 10, a, 60, 30, x, y, 99, 0, -1){
            
            int ox = x, oy = y; boolean b = false;
            
            @Override public void behavior(){
                
                if (life % 3 == 0 && !b){
                    GUI .addThis2( newTreeRay(a+(30), x, y) );
                    GUI .addThis2( newTreeRay(a-(30), x, y) );
                    b = true;
                    speed = 0;
                }
                
                g2 .setColor(new Color(life*2, life*2, life*2));
                g2 .setStroke(new BasicStroke(33-life/3));
                g2 .drawLine(ox, oy, x, y);
                
            }
            
            @Override public void collisionWith(Projectile p){
                
                ghost = true;
                
            }

        };
    
    }
    
    static Projectile newSatanicross(int a, int x, int y){
        
        return new Projectile(9, 10, a, 60, 30, x, y, 99, 0, 11){
            @Override public void behavior(){
                if (rng.nextInt(10) > 8){
                    GUI.projectiles.add( newFireball( rng .nextInt(360) , x, y) );
                    GUI.projectiles.add( newMeteor(x) );
                }
            }
            
            @Override public void collisionWith( Rectangle r ){
                
                GUI.projectiles.add( newMeteor(x) );
                GUI.projectiles.add( newMeteor(x) );
                GUI.projectiles.add( newMeteor(x) );
                ang += 180;
                
            }
        };
    
    };
    
    static Projectile newMeteor(int x){
        
        return new Projectile(20, 80, 90+(rng .nextInt(15) -7)*2, 240, 120, x-120, -120, 99, 0, 0){
            
            @Override public void behavior(){
            }
            
            @Override public void collisionWith( Rectangle r ){ GUI.walls.remove(r); }
        };
    
    };
    
    static Projectile newBox(int a, int x, int y){
        
        return new Projectile(16, 5, a, 60, 60, x, y, 99, 5, 1) 
        { 
            @Override public void behavior(){
                this.speed --;
                this.ang += rng .nextInt(9) -4;
                this.rot += rng .nextInt(9);
            }
            
        };
        
    }
    
    static Projectile newHammerBlast(int a, int x, int y){
        
        return new Projectile(16, 500, a, 30, 30, x, y, 99, 5, 2) 
        { 
            
            @Override public void behavior(){
                this.rot += 15;
                orbit(GUI.m.x, GUI.m.y, -1);
                
            }            
            
            @Override public void collisionWith(Rectangle r){
              
              r .setSize((int)r.getWidth() -1, (int)r.getHeight() -1);
              
          }
        };
        
    }
    
    static Projectile newPizzaSlice(int a, int x, int y){
        
        return new Projectile(7, 15, a, 21*2, 16*2, x, y, 99, 5, 3) 
        { @Override public void behavior(){
            this.ang += rng .nextInt(5) -2;
            this.rot += rng .nextInt(5);
        }
        
        @Override public void collisionWith(Rectangle r){
            this.ang += 180;
        }
        
        };
        
    }
    
    static Projectile newPizzaBomb(int x, int y){
        
        return new Projectile(0, 0, 0, 60, 80, x, y, 99*9, 5, 4)
        {   
            @Override public void behavior(){
                this .rot = -(int) (120 * Math .sin(2*Math.PI*this.age/((this.age+this.life))))-60;
            }
            @Override public void legacy(){
                for (int n = 0; n < 24; n++){
                    GUI.projectiles.add(PCatalog .newPizzaSlice(15*n, x, y) );
                }
            }
        };
        
    }
    
    static Projectile newExplosion(int x, int y){
        
        return new Projectile(0, 0, 0, 80, 80, x, y, 9, 5, 5)
        {   
            
            @Override public void behavior(){
                
                x -= 3;
                y -= 3;
                width += 6;
                height += 6;
                rot += 5;
                
            }
            
            @Override public void collisionWith(Rectangle r){
              
              ghost = true;
              
              r.x +=3;
              r.y +=3;
                
              r.height -= 6;
              r.width -= 6;
              
          }
            
        };
        
    }
    
    static Projectile newBomb(int x, int y){
        
        return new Projectile(2, 0, 0, 60, 80, x, y, 99*9, 5, 6)
        {   
            @Override public void behavior(){
                this .rot = -(int) (120 * Math .sin(2*Math.PI*this.age/((this.age+this.life))))-60;
            }
            @Override public void legacy(){
                GUI.projectiles.add(PCatalog .newExplosion(x, y) );
                for (int n = 0; n < 12; n++){
                    GUI.projectiles.add(PCatalog .newFireball(30*n, x, y) );
                }
            }
            @Override public void collisionWith( Rectangle r ){
                
                life --;
                
            }
        };
        
    }
    
    static Projectile newGrenade(int a, int x, int y){
        
        return new Projectile(10, 0, a, 15, 20, x, y, 99*9, 5, 6)
        {   
            @Override public void behavior(){
                this .rot += 2;
            }
            @Override public void legacy(){
                GUI.projectiles.add(PCatalog .newExplosion(x, y) );
                for (int n = 0; n < 12; n++){
                    GUI.projectiles.add(PCatalog .newFireball(30*n, x, y) );
                }
            }
        };
        
    }
    
    static Projectile newDiceOfUltimateRandomness(int a, int x, int y){
        
        return new Projectile(24, 0, a, 60, 60, x, y, 99*5, 5, 7) 
        { 
            @Override public void behavior(){
                if (this .speed > 0){
                    this.speed --;
                }
                this.ang += rng .nextInt(9) -4;
                this.rot += rng .nextInt(9);
            }
            
            @Override public void legacy(){
                switch (rng .nextInt(5)){
                    case 0: GUI.projectiles.add(PCatalog.newCrazyArrow(a, x, y) ); break;
                    case 1: GUI.projectiles.add(PCatalog.newMeteor(x) ); break;
                    case 2: GUI.projectiles.add(PCatalog.newFireball(a, x, y) ); break;
                    case 3: GUI.projectiles.add(PCatalog.newBolt(a, x, y) ); break;
                    case 4: GUI.projectiles.add(PCatalog.newBox(x, x, y) ); break;
                }
            }
            
            @Override public void collisionWith(Rectangle r){
                this.speed = 0;
            }
        
        };
        
    }
    
    static Projectile newDiceOfInfiniteRandomness(int a, int x, int y){
        
        return new Projectile(0, 0, a, 60, 60, x, y, 99, 5, 8) 
        { 
            @Override public void behavior(){
                if (this .speed > 0){
                    this.speed --;
                }
                this.ang += rng .nextInt(9) -4;
                this.rot += rng .nextInt(9);
            }
            
            @Override public void legacy(){
                int newA = rng .nextInt(359);
                switch (rng .nextInt(5)){
                    case 0: GUI.projectiles.add(PCatalog.newCrazyArrow(newA, x, y) ); break;
                    case 1: GUI.projectiles.add(PCatalog.newMeteor(x) ); break;
                    case 2: GUI.projectiles.add(PCatalog.newFireball(newA, x, y) ); break;
                    case 3: GUI.projectiles.add(PCatalog.newBolt(newA, x, y) ); break;
                    case 4: GUI.projectiles.add(PCatalog.newBox(x, x, y) ); break;
                }
                GUI.projectiles.add(PCatalog.newDiceOfInfiniteRandomness(a, x, y) );
            }
        
        };
        
    }
    
    static Projectile newBolt(int a, int x, int y){
        
        return new Projectile(1, 50, a, 100, 60, x, y, 99, 0, 9) 
        { @Override public void behavior(){
            if (this.speed <= 20){
                this.speed ++;
                this.width += 5;
            }
        }};
        
    }
    
    static Projectile newGiantArrow(int a, int x, int y){
        
        return new Projectile(15, 35, a + rng .nextInt(5)-2, 100, 40, x, y, 99, 0, 10) 
        { @Override public void behavior(){
        }};
        
    }
    
    static Projectile newArrow(int a, int x, int y){
        
        return new Projectile(15, 10, a + rng .nextInt(5)-2, 50, 20, x, y, 99, 0, 10) 
        { @Override public void behavior(){
            this.ang += 2;
        }};
        
    }
    
    static Projectile newRainArrow(int x){
        
        return new Projectile(10, 7, 90+rng .nextInt(20)-10, 30, 12, x, 0, 99, 0, 10) 
        { @Override public void behavior(){
        }};
        
    }
    
    static Projectile newCrazyArrow(int a, int x, int y){
        
        return new Projectile(15, 11, a + rng .nextInt(5)-2, 40, 16, x, y, 99, 0, 10) 
        { @Override public void behavior(){
            this.ang += rng .nextInt(61) -30;
        }
          @Override public void collisionWith(Rectangle r){
              this.ang += 180;
          }
        };
        
    }
    
    static Projectile newCrazierArrow(int a, int x, int y){
        
        return new Projectile(15, 19, a + rng .nextInt(5)-2, 50, 20, x, y, 99, 0, 10) 
        { @Override public void behavior(){
            this.ang += rng .nextInt(61) -30;
            this.rot += 10;
        }};
        
    }
    
}
