import java.awt.*;
import java.util.Random;

public class BouncingBall {

    private double xPos, yPos;
    private int radius;
    private double xVel, yVel;
    private Rectangle boundingBox;

    public BouncingBall() {
        Random rand = new Random();

        this.xPos = rand.nextInt(DisplayFrame.getScreenWidth()-100);
        this.yPos = rand.nextInt(DisplayFrame.getScreenHeight()-100);

        this.xVel = (rand.nextInt(40)+10)/10.0;
        this.yVel = (rand.nextInt(40)+10)/10.0;

        this.radius = 15;

        this.boundingBox = new Rectangle((int)xPos, (int)yPos, radius, radius);

    }

    public void draw(Graphics g) {

        g.setColor(Color.BLUE);

        g.fillOval((int)xPos, (int)yPos, radius, radius);
    }

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

    public void collisionCheck(BouncingBall anotherBall) {
        if (this.getBoundingBox().intersects(anotherBall.getBoundingBox())) {
            this.reverseX();
            this.reverseY();
            anotherBall.reverseX();
            anotherBall.reverseY();
        }
    }

    public void setBoxPosition(double x, double y) {
        this.boundingBox.setLocation((int)x,(int)y);
    }


    public void reverseX() {
        this.xVel = -this.xVel;
    }

    public void reverseY() {
        this.yVel = -this.yVel;
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getxVel() {
        return xVel;
    }

    public void setxVel(double xVel) {
        this.xVel = xVel;
    }

    public double getyVel() {
        return yVel;
    }

    public void setyVel(double yVel) {
        this.yVel = yVel;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }
}
