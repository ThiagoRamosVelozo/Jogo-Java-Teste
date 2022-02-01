package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class GUI extends JFrame {

    Container c;
    public static JPanel norte = new JPanel(), sul = new JPanel(), leste = new JPanel(), oeste = new JPanel(), centro = new JPanel();

    public static int x = 100,
            y = 100,
            p = 5,
            w = 45,
            h = 60,
            a = 0,
            v1 = 0,
            v2 = 0;

    String magText = "C:/Users/Usuario/Desktop/PIXELART/MAJ.png";

    public static boolean toLeft = false,
            arrowActive = false,
            mouseInside = false,
            mousePressing = false;

    public static Image chr = null;
    Timer repainter = new Timer(),
            motor = new Timer();

    public static long time = 0;
    public static Point m = new Point();
    public static Item mi;

    public static ArrayList<Rectangle> walls = new ArrayList<>();
    public static ArrayList<Character> movements = new ArrayList<>();
    public static ArrayList<Item> inventory = new ArrayList<>();
    public static ArrayList<Projectile> projectiles = new ArrayList<>();
    public static ArrayList<Point> points = new ArrayList<>();

    static void setItem(int u) {
        for (int n = 0; n < sul.getComponentCount(); n++) {
            ((JLabel) sul.getComponent(n)).setBorder(BorderFactory.createLoweredBevelBorder());
        }
        mi = inventory.get(u);
        ((JLabel) sul.getComponent(u)).setBorder(BorderFactory.createLineBorder(Color.yellow, 3));
    }

    public static Rectangle wallHitbox(int x1, int y1, int w1, int h1) {
        
        for (Rectangle wall: walls){
            if (wall.intersects(new Rectangle(x1, y1, w1, h1))){
                return wall;
            }
        }

        return null;

    }
    
    public static Projectile projHitbox(int x1, int y1, int w1, int h1) {
        
        for (Projectile pr: projectiles){
            if (pr.getHitbox().intersects(new Rectangle(x1, y1, w1, h1))){
                return pr;
            }
        }

        return null;

    }

    void swt(char ch) {

        try {

            switch (ch) {

                case 'w':
                    if (y > p && wallHitbox(x, y - p, w, h) == null) {
                        y -= p;
                    }
                    break;

                case 'a':
                    if (x > p + 20 && wallHitbox(x - p, y, w, h) == null) {
                        x -= p;
                        toLeft = true;
                    }
                    break;

                case 's':
                    if (y + p < centro.getHeight() && wallHitbox(x, y + p, w, h) == null) {
                        y += p;
                    }
                    break;

                case 'd':
                    if (x + p < centro.getWidth() - 60 && wallHitbox(x + p, y, w, h) == null) {
                        x += p;
                        toLeft = false;
                    }
                    break;

                // ============================================================ //
                // POINTS // ================================================== //

                // ============================================================ //
                // WHATEVER // ================================================ //
                case 't':
                    arrowActive = !arrowActive;
                    break;

                default:
                    break;
            }

        } catch (Exception e) {
        }

    }

    public static boolean addThis(Projectile pj) {

        if (projectiles.size() < 14) {
            projectiles.add(pj);
            return true;
        }

        return false;

    }
    
    public static boolean addThis2(Projectile pj) {

        if (projectiles.size() < 30) {
            projectiles.add(pj);
            return true;
        }

        return false;

    }

    void sch() {

        motor.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                m = centro.getMousePosition();
                
                if (mouseInside){
                    try {
                        a = (int) Math .toDegrees( Math .atan2( y - m.y, x - m.x) ) + 180;
                    } catch (Exception e) {}
                }
                
                if (mousePressing){ mi.doWork(); }
                
                if (walls.size() > 0){
                    for (int i = 0; i < walls.size(); i++) {
                        Rectangle r = walls.get(i);
                        if (r.getHeight()*r.getWidth() < 100){
                            walls.remove(i);
                        }
                    }
                }
                
                if (projectiles.size() > 0) {

                    for (int i = 0; i < projectiles.size(); i++) {
                        if (!projectiles.get(i).proceed()) {
                            projectiles.get(i).legacy();
                            projectiles.remove(i);
                        }
                    }
                    
                    try{
                    for (Projectile proj : projectiles) {

                        proj.proceed();
                        proj.behavior();

                    }
                    }catch(Exception e){}

                }
                try{
                    movements.forEach((mov) -> {
                        swt(mov);
                    });
                } catch (Exception e) {}
                
                time ++;

            }
        }, 0, 10);

        repainter.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                centro.repaint();

            }
        }, 0, 10);

    }

    // ==================================================================================================================== //
    public GUI() throws IOException {

        c = getContentPane();
        c.setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            chr = ImageIO.read(new File(magText)).getScaledInstance(w, h, Image.SCALE_DEFAULT);
        } catch (IOException ex) {
        }

        // ZONA DE TESTE //
        walls.add(new Rectangle(200, 200, 100, 100));
        walls.add(new Rectangle(300, 200, 100, 100));
        walls.add(new Rectangle(500, 0, 100, 100));
        walls.add(new Rectangle(700, 100, 100, 100));

        // ============= //
        inventory.add(ICatalog.newFireWand());
        inventory.add(ICatalog.newFork());
        inventory.add(ICatalog.newSuspiciousDice());
        inventory.add(ICatalog.newWeirdThrowableBox());
        inventory.add(ICatalog.newBomb());
        inventory.add(ICatalog.newPizzaBomb());
        inventory.add(ICatalog.newBoweirdo());
        inventory.add(ICatalog.newHammer());
        inventory.add(ICatalog.newTeleportationRod());
        inventory.add(ICatalog.newTrident());

        centro = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2D = (Graphics2D) g;
                setBackground(Color.darkGray);

                g2D.setColor(Color.white);
                g2D.drawLine(0, 0, 30, 60);
                g2D.drawLine(centro.getWidth(), 0, centro.getWidth() - 30, 60);
                g2D.drawLine(30, 60, centro.getWidth() - 30, 60);
                g2D.drawLine(30, 60, 30, centro.getHeight());
                g2D.drawLine(centro.getWidth() - 30, 60, centro.getWidth() - 30, centro.getHeight());
                g2D.setColor(Color.gray);
                g2D.fillRect(31, 61, centro.getWidth() - 61, centro.getHeight());
                g2D.setColor(Color.black);

                for (Rectangle rect : walls) {
                    g2D.setColor(Color.lightGray);
                    g2D.fill(rect);
                    g2D.setColor(Color.white);
                    g2D.draw(rect);
                    g2D.drawImage(InitRes.bis.get(12), (int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight(), null);
                }

                if (toLeft) {
                    g2D.drawImage(chr, x + w, y, -w, h, null);
                    g2D.drawImage(mi.getTexture(), (int) (x + w * .25), y + h / 3, -mi.getWidth(), mi.getHeight(), null);
                } else {
                    g2D.drawImage(chr, x, y, null);
                    g2D.drawImage(mi.getTexture(), (int) (x + w * .75), y + h / 3, null);
                }

                if (projectiles.size() > 0) {

                    try {

                        projectiles.forEach((proj) -> {
                            g2D.drawImage(proj.getRotated(), proj.getX(), proj.getY(), null);
                            //g2D.drawRect(proj .getX(), proj .getY(), proj .getWidth(), proj .getHeight());
                        });

                    } catch (Exception e) {
                    }

                }

                if (arrowActive) {

                    g2D.setStroke(new BasicStroke(2));
                    g2D.setColor(Color.yellow);

                    try {
                        g2D.drawLine(x + 40, y + 30, centro.getMousePosition().x, centro.getMousePosition().y);
                        g2D.setColor(Color.red);
                        g2D.drawLine(x + 40, y + 30, x + 40, centro.getMousePosition().y);
                        g2D.setColor(Color.MAGENTA);
                        g2D.drawLine(x + 40, y + 30, centro.getMousePosition().x, y + 30);
                    } catch (Exception e) {
                    }

                }

                //System.out.println(projectiles .size());
            }
        };

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {

                if ("wasd".contains("" + ke.getKeyChar())) {
                    if (!movements.contains(ke.getKeyChar())) {
                        movements.add(ke.getKeyChar());
                    }

                } else {
                    swt(ke.getKeyChar());
                }

            }

            @Override
            public void keyReleased(KeyEvent ke) {

                movements.remove((Character) ke.getKeyChar());

            }
        });

        c.add(centro, BorderLayout.CENTER);

        // ========================================================================================================================= //
        sul.setLayout(new FlowLayout());
        sul.setBackground(Color.lightGray);
        sul.setBorder(BorderFactory.createRaisedBevelBorder());

        inventory.forEach((Item itm) -> {
            JLabel jL = new JLabel();
            jL.setPreferredSize(new Dimension(40, 40));
            jL.setIcon(new ImageIcon(itm.getTexture()));
            jL.setHorizontalAlignment(SwingConstants.CENTER);
            jL.setBorder(BorderFactory.createLoweredBevelBorder());
            sul.add(jL);
        });

        c.add(sul, BorderLayout.SOUTH);
        
        // ========================================================================================================================= //

        // ========================================================================================================================= //

        this.addMouseWheelListener((MouseWheelEvent mwe) -> {
            v2 = ((mwe.getWheelRotation() > 0 ? 1 : -1) + inventory .size() + v2) % inventory .size();
            setItem(v2);
        });
        
        addMouseListener( new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
                mousePressing = true;
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                mousePressing = false;
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                mouseInside = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                mouseInside = false;
            }
        } );

        setItem(0);
        setFocusable(true);
        requestFocusInWindow();

        setSize(400, 400);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(true);

        sch();

    }

}
