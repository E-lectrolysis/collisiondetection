/**
 * This template can be used as reference or a starting point for the Shape Game
 * for your final summative project
 * @author Mangat
 **/

//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;

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

        //create enemies and player

        //spawn 5 eneimies

        // Set the frame to full screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize((int)(1000*scaleRatio),(int)(1000*scaleRatio));
        // this.setUndecorated(true);  //Set to true to remove title bar
        //frame.setResizable(false);



        //Set up the game panel (where we put our graphics)
        gamePanel = new GameAreaPanel();
        this.add(new GameAreaPanel());

        MyKeyListener keyListener = new MyKeyListener();
        this.addKeyListener(keyListener);

        this.requestFocusInWindow(); //make sure the frame has focus

        this.setVisible(true);



    } //End of Constructor



    /** --------- INNER CLASSES ------------- **/

    // Inner class for the the game area - This is where all the drawing of the screen occurs
    private class GameAreaPanel extends JPanel {

        Clock clock = new Clock();


        public void paintComponent(Graphics g) {
            super.paintComponent(g); //required
            setDoubleBuffered(true);

            //move enemies
            clock.update();
            update(quadrants.getRoot(), g);

            g.drawRect(0,0, getScreenWidth(), getScreenHeight());

            //repaint
            repaint();
        }

        public void update(Node<BouncingBall> a, Graphics g) {
            Node<BouncingBall> tempNode = a;

            quadrants.update(a, g);

            if(tempNode.getChildren().size() < 1) {
                doCollisionCheck(a);
                drawStuff(a, g);
            }

        }

        /**
         * Removes balls from a specific quadrant
         * @param a
         */
        public void cleanseBalls(Node<BouncingBall> a) {
            Node<BouncingBall> tempNode = a;
            ArrayList<BouncingBall> balls = a.getListOfStuff();

            for(int i = 0; i < balls.size(); i++) {
                if((balls.get(i).getxPos() > a.getxBound() || balls.get(i).getxPos() < a.getX()) &&(balls.get(i).getxPos() > a.getyBound() || balls.get(i).getyPos() < a.getY())) {
                    balls.remove(i);
                }
            }

            if(a.getChildren().size() == 4) {
                for(int i = 0; i < balls.size(); i++) {
                    cleanseBalls(a.getChildren().get(i));
                }
            }

        }

        public void insertBalls(Node<BouncingBall> a) {
            Node<BouncingBall> tempNode = a;
            ArrayList<BouncingBall> balls = quadrants.getAllTheItems();

            for(int i = 0; i < balls.size(); i++) {

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


    // -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
    private class MyKeyListener implements KeyListener {

        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));

            if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
                quadrants.addItem(new BouncingBall());//If 'D' is pressed

            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {  //If ESC is pressed
                System.out.println("Quitting!"); //close frame & quit
                window.dispose();

            }
        }

        public void keyReleased(KeyEvent e) {
        }
    } //end of keyboard listener



}