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
    private JPanel displayPanel;
    private static double scaleRatio = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/1920;
    private Quadrants<BouncingBall> quadrants;


    /**
     * gets the height of the window
     * @return the window height
     */
    public static int getScreenHeight() {
        return (int)(1000*scaleRatio);
    }

    /**
     * gets the width of the window
     * @return the window width
     */
    public static int getScreenWidth() {
        return (int)(1000*scaleRatio);
    }


    /**
     * Creates a new window to display the graphics
     */
    DisplayFrame() {
        super("Balls");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize((int)(1000*scaleRatio),(int)(1000*scaleRatio));

        displayPanel = new DisplayPanel();
        this.add(new DisplayPanel());

        MyKeyListener keyListener = new MyKeyListener();
        this.addKeyListener(keyListener);

        this.requestFocusInWindow(); //make sure the frame has focus

        this.setVisible(true);


        quadrants = new Quadrants<BouncingBall>();
    } //End of Constructor


    //inner classes
    private class DisplayPanel extends JPanel {

        Clock clock = new Clock(); //clock for determining time


        /**
         * Draws the graphics on the screen
         * @param g the graphics to draw with
         */
        public void paintComponent(Graphics g) {
            super.paintComponent(g); //required
            setDoubleBuffered(true);

            //update things
            clock.update();
            update(quadrants.getRoot(), g);

            //repaint
            repaint();
        }

        /**
         * updates everything
         * @param a the node
         * @param g paintComponent graphics
         */
        private void update(Node<BouncingBall> a, Graphics g) {

            quadrants.update(a, g);
            cleanseBalls(a);
            insertBalls(a);
            drawStuff(a, g);
            updateNodes(a);


        }

        /**
         * does a collision check for balls if the node has no children
         * @param a
         */
        private void updateNodes(Node<BouncingBall> a) {

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
         * Removes out of bound balls from nodes
         * @param a a node of bouncing balls
         */
        private void cleanseBalls(Node<BouncingBall> a) {
            ArrayList<BouncingBall> balls = a.getListOfStuff();

            for(int i = 0; i < balls.size(); i++) {
                //checks if out of bounds, if so, removes ball from specified node
                if((balls.get(i).getxPos() > a.getxBound() || balls.get(i).getxPos() < a.getX()) &&(balls.get(i).getyPos() > a.getyBound() || balls.get(i).getyPos() < a.getY())) {
                    balls.remove(i);
                }
            }

            if(a.getChildren().size() == 4) {
                for(int i = 0; i < 4; i++) {
                    //call it again for children
                    cleanseBalls(a.getChild(i));
                }
            }

        }


        /**
         * Inserts balls into their proper node
         * @param a a node of bouncing balls
         */
        private void insertBalls(Node<BouncingBall> a) {
            ArrayList<BouncingBall> balls = quadrants.getAllTheItems();

            for(int i = 0; i < balls.size(); i++) {
                if((balls.get(i).getxPos() <= a.getxBound() && balls.get(i).getxPos() >= a.getX()) &&(balls.get(i).getyPos() <= a.getyBound() && balls.get(i).getyPos() >= a.getY())) {
                    if(!a.getListOfStuff().contains(balls.get(i))) {
                        a.addItem(balls.get(i)); //inserts when within boundaries, makes sure it isn't already there so it doesn't flood
                    }
                }
            }

            if(a.getChildren().size() == 4) {
                for(int i = 0; i < 4; i++) {
                    insertBalls(a.getChild(i)); //if children exist, checks them for insertion too
                }
            }


        }


        /**
         * checks collisions within a node
         * @param a the node to be checked
         */
        private void doCollisionCheck(Node<BouncingBall> a) {
            ArrayList<BouncingBall> balls = a.getListOfStuff();

            for(int i = 0; i < balls.size()-1; i++) {
                for(int j = i+1; j < balls.size(); j++) {
                    balls.get(i).collisionCheck(balls.get(j));
                }
            }

        }


        /**
         * draws balls
         * @param a a node
         * @param g paintComponent graphics
         */
        private void drawStuff(Node<BouncingBall> a, Graphics g) {

            ArrayList<BouncingBall> balls = a.getListOfStuff();

            for (BouncingBall ball : balls) {
                ball.update(clock.getElapsedTime());
                ball.draw(g);
            }
        }

    }

    //key listeners
    private class MyKeyListener implements KeyListener {

        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {

            if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
                quadrants.addItem(new BouncingBall());

            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {  //If ESC is pressed
                System.out.println("Quitting!"); //close frame & quit
                window.dispose();

            } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("E")) {
                try {
                    quadrants.remove(0);
                } catch(Exception ex) {
                    System.out.println("ArrayList is empty");
                }
            }
        }

        public void keyReleased(KeyEvent e) {
        }
    } //end of keyboard listener



}