package main;

import java.awt.Rectangle;

interface Hitbox {
    
    void collisionWith(Rectangle r);
    void collisionWith(Projectile p);
    
}
