/*
 * Collision Detection
 * A Program that uses a quad tree to detect collisions
 * @author Eric Ke
 * 10/30/2018
 *
 */

//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


//Util
import java.util.ArrayList;


public class DisplayFrame extends JFrame {

    //class variables
    private static JFrame window;
    private JPanel gamePanel;
    private static double scaleRatio = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/1920;
    private Quadrants<BouncingBall> quadrants = new Quadrants<BouncingBall>();


    public static int getScreenHeight() {
        return (int)(1000*scaleRatio);
    }

    public static int getScreenWidth() {
        return (int)(1000*scaleRatio);
    }


    //Main
    public static void main(String[] args) {
        window = new DisplayFrame();
    }


    DisplayFrame() {
        super("Balls");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize((int)(1000*scaleRatio),(int)(1000*scaleRatio));

        gamePanel = new DisplayPanel();
        this.add(new DisplayPanel());

        MyKeyListener keyListener = new MyKeyListener();
        this.addKeyListener(keyListener);

        this.requestFocusInWindow(); //make sure the frame has focus

        this.setVisible(true);



    } //End of Constructor


    //inner classes
    private class DisplayPanel extends JPanel {

        Clock clock = new Clock();


        public void paintComponent(Graphics g) {
            super.paintComponent(g); //required
            setDoubleBuffered(true);

            //move enemies
            clock.update();
            update(quadrants.getRoot(), g);

            //repaint
            repaint();
        }

        public void update(Node<BouncingBall> a, Graphics g) {

            quadrants.update(a, g);
            cleanseBalls(a);
            insertBalls(a);
            drawStuff(a, g);
            updateNodes(a);


        }

        public void updateNodes(Node<BouncingBall> a) {

            Node<BouncingBall> tempNode = a;

            if(tempNode.getChildren().size() != 4) {
                doCollisionCheck(a);
            } else {
                for(int i = 0; i < 4; i++) {
                    updateNodes(a.getChild(i));
                }
            }
        }

        /**
         * Removes balls from a specific quadrant
         * @param a a node of bouncing balls
         */
        public void cleanseBalls(Node<BouncingBall> a) {
            Node<BouncingBall> tempNode = a;
            ArrayList<BouncingBall> balls = a.getListOfStuff();

            for(int i = 0; i < balls.size(); i++) {
                if((balls.get(i).getxPos() > a.getxBound() || balls.get(i).getxPos() < a.getX()) &&(balls.get(i).getyPos() > a.getyBound() || balls.get(i).getyPos() < a.getY())) {
                    balls.remove(i);
                }
            }

            if(tempNode.getChildren().size() == 4) {
                for(int i = 0; i < 4; i++) {
                    cleanseBalls(a.getChild(i));
                }
            }

        }


        /**
         * Inserts balls into their proper node
         * @param a a node of bouncing balls
         */
        public void insertBalls(Node<BouncingBall> a) {
            Node<BouncingBall> tempNode = a;
            ArrayList<BouncingBall> balls = quadrants.getAllTheItems();

            for(int i = 0; i < balls.size(); i++) {
                if((balls.get(i).getxPos() <= tempNode.getxBound() && balls.get(i).getxPos() >= tempNode.getX()) &&(balls.get(i).getyPos() <= tempNode.getyBound() && balls.get(i).getyPos() >= tempNode.getY())) {
                    if(!tempNode.getListOfStuff().contains(balls.get(i))) {
                        tempNode.addItem(balls.get(i));
                    }
                }
            }

            if(a.getChildren().size() == 4) {
                for(int i = 0; i < 4; i++) {
                    insertBalls(tempNode.getChild(i));
                }
            }


        }


        public void doCollisionCheck(Node<BouncingBall> a) {
            Node<BouncingBall> tempNode = a;
            ArrayList<BouncingBall> balls = tempNode.getListOfStuff();

            for(int i = 0; i < balls.size()-1; i++) {
                for(int j = i+1; j < balls.size(); j++) {
                    balls.get(i).collisionCheck(balls.get(j));
                }
            }

        }


        public void drawStuff(Node<BouncingBall> a, Graphics g) {

            ArrayList<BouncingBall> balls = a.getListOfStuff();

            for(int i = 0; i < balls.size(); i++ ) {
                balls.get(i).update(clock.getElapsedTime());
                balls.get(i).draw(g);
            }
        }

    }

    private class MyKeyListener implements KeyListener {

        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));

            if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
                quadrants.addItem(new BouncingBall());
                System.out.println("Aaaa");//If 'D' is pressed

            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {  //If ESC is pressed
                System.out.println("Quitting!"); //close frame & quit
                window.dispose();

            } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("E")) {
                quadrants.remove(0);
                System.out.println("Aaaa");//If 'D' is pressed

            }
        }

        public void keyReleased(KeyEvent e) {
        }
    } //end of keyboard listener



}