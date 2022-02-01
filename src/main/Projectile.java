package main;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import sun.awt.image.ToolkitImage;

class Projectile implements Hitbox{
    
    int speed, life, width, height, x, y, rot, age, damage, txt;
    double ang;
    public boolean ghost;

    public Projectile(int speed, int damage, double ang, int width, int height, int x, int y, int life, int rot, int txt) {
        this.speed = speed;
        this.damage = damage;
        this.ang = ang;
        this.life = life;
        this.age = life;
        this.txt = txt;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.rot = rot;
    }
    
    protected void setSpeed(int newSpeed){
        
        this.speed = newSpeed;
        
    }
    
    protected void follow(int x2, int y2){
        
        this .ang = (int) Math .toDegrees( Math .atan2( this.y - y2, this.x - x2) ) + 180;
        
    }
    
    protected void flee(int x2, int y2){
        
        follow(x2, y2);
        
        this.ang += 180;
        
    }
    
    protected void orbit(int x2, int y2, double s){
        
        follow(x2, y2);
        
        this .ang += 90*s;
        
    }
    
    protected boolean proceed(){
        
        Projectile hitProj = GUI.projHitbox(
                (int) (this.x + speed*Math.cos(Math.toRadians(ang))), 
                (int) (this.y + speed*Math.sin(Math.toRadians(ang))), this.width, this.height);
        
        if (hitProj!=null && !hitProj.equals(this)){
            
            collisionWith(hitProj);
            
        } 
        
        Rectangle hitWall = GUI.wallHitbox(
                (int) (this.x + speed*Math.cos(Math.toRadians(ang))), 
                (int) (this.y + speed*Math.sin(Math.toRadians(ang))), this.width, this.height);
        
        if (hitWall!=null){
            
            collisionWith(hitWall);
            
        } else
        
        if (this .life > 0 ){
            this.x += speed*Math.cos(Math.toRadians(ang));
            this.y += speed*Math.sin(Math.toRadians(ang));
            this.life --;
            this.age ++;
        }
        
        if (GUI.wallHitbox(this.x, this.y, this.width, this.height)!=null && !ghost){
            this.life = 0;
        }
        
        return !(this.life < 1);
        
    }
    
    protected Image getTexture(){
        
        return InitRes.bis.get(txt).getScaledInstance(width, height, Image.SCALE_DEFAULT);
        
    }
    
    protected Image getRotated(){
        
        Image txtr = getTexture();
        AffineTransform tx = AffineTransform.getRotateInstance(Math .toRadians(this.ang+this.rot), txtr .getWidth(null) /2, txtr .getHeight(null)/2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        
        return op .filter(((ToolkitImage) txtr) .getBufferedImage(), null);
        
    }
    
    public Rectangle getHitbox(){
        
        return new Rectangle( x, y, width, height );
        
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTxt() {
        return txt;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    @Override
    public void collisionWith(Rectangle r){
        this.life = 0;
    }
    
    @Override
    public void collisionWith(Projectile p){
    }

    public void behavior() {
        
    }
    
    public void legacy(){
        
    }
    
}
