/*
 * BouncingBall.java
 * Balls that bounce around on the screen, can collide with each other
 * @author Eric Ke
 * 2018/10/30
 */

import java.awt.*;
import java.util.Random;

public class BouncingBall {

    private double xPos, yPos;
    private int radius;
    private double xVel, yVel;
    private Rectangle boundingBox;

    /**
     * Constructor for a BouncingBall
     * on creation, gives the ball random speed and direction
     */
    public BouncingBall() {
        Random rand = new Random();

        this.xPos = rand.nextInt(DisplayFrame.getScreenWidth()-100);
        this.yPos = rand.nextInt(DisplayFrame.getScreenHeight()-100);

        this.xVel = (rand.nextInt(40)+10)/10.0;
        this.yVel = (rand.nextInt(40)+10)/10.0;

        this.radius = 15;

        this.boundingBox = new Rectangle((int)xPos, (int)yPos, radius, radius);

    }

    /**
     * draws the ball
     * @param g paintComponent graphics
     */
    public void draw(Graphics g) {

        g.setColor(Color.BLUE);

        g.fillOval((int)xPos, (int)yPos, radius, radius);
    }

    /**
     * updates the position of the ball and bounces if it touches the border
     * @param elapsedTime time that has passed since last update
     */
    public void update(double elapsedTime) {
        this.setxPos(getxPos()+this.xVel*elapsedTime*100);
        this.setyPos(getyPos()+this.yVel*elapsedTime*100);//d = d0 + vt
        if(this.xPos >= DisplayFrame.getScreenWidth() - radius*2|| this.xPos <= 0) {
            reverseX();
        }
        if(this.yPos >= DisplayFrame.getScreenHeight() - radius*3.5|| this.yPos <= 0) {
            reverseY();
        }
        if(this.xPos >= DisplayFrame.getScreenWidth() - radius*2) {
            this.xPos = DisplayFrame.getScreenWidth()  - radius*2;
        }
        if(this.yPos >= DisplayFrame.getScreenHeight() - radius*3.5) {
            this.yPos = DisplayFrame.getScreenHeight() - radius*3.5;
        }

        this.setBoxPosition(getxPos(), getyPos());
    }

    /**
     * checks if a ball is in contact with another ball, bounces if it is the case
     * @param anotherBall the ball to be checked with this one
     */
    public void collisionCheck(BouncingBall anotherBall) {
        if (this.getBoundingBox().intersects(anotherBall.getBoundingBox())) {
            this.reverseX();
            this.reverseY();
            anotherBall.reverseX();
            anotherBall.reverseY();
        }
    }


    /**
     * moves the boundingBox
     * @param x the x position
     * @param y the y position
     */
    public void setBoxPosition(double x, double y) {
        this.boundingBox.setLocation((int)x,(int)y);
    }


    /**
     * reverses x speed
     */
    public void reverseX() {
        this.xVel = -this.xVel;
    }

    /**
     * reverses y velocity
     */
    public void reverseY() {
        this.yVel = -this.yVel;
    }

    /**
     * gets the ball's x position
     * @return the ball's x position
     */
    public double getxPos() {
        return xPos;
    }

    /**
     * sets ball x position
     * @param xPos the x position to give the ball
     */
    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    /**
     * gets the ball's y position
     * @return the y position
     */
    public double getyPos() {
        return yPos;
    }

    /**
     * sets y position
     * @param yPos the y position to set the ball to
     */
    public void setyPos(double yPos) {
        this.yPos = yPos;
    }


    /**
     * gets the ball's bounding box
     * @return the ball's bounding box
     */
    public Rectangle getBoundingBox() {
        return boundingBox;
    }

}
