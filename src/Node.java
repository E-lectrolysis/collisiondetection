import java.awt.*;
import java.util.ArrayList;


public class Node<T> {

    private ArrayList<T> listOfStuff;
    private ArrayList<Node<T>> children;

    private int x,y;
    private int xBound, yBound;

    /**
     * Constructor for node
     * @param x x location
     * @param y y location
     * @param xBound x boundary
     * @param yBound y boundary
     */
    public Node(int x, int y, int xBound, int yBound) {
        this.x = x;
        this.y = y;
        this.xBound = xBound;
        this.yBound = yBound;
        this.listOfStuff = new ArrayList<T>();
        this.children = new ArrayList<Node<T>>();
    }


    /*
    2 1
    3 4
     */

    /**
     * creates 4 children nodes
     */
    public void addChildren() {
        this.children.add(new Node((xBound+x)/2,y,xBound,(yBound+y)/2)); //1
        this.children.add(new Node(x,y,(xBound+x)/2,(yBound+y)/2)); //2
        this.children.add(new Node(x,(yBound+y)/2, (xBound+x)/2, yBound));//3
        this.children.add(new Node((xBound+x)/2, (yBound+y)/2, xBound, yBound)); //4
    }


    /**
     * updates
     */
    public void update() {
        if(this.listOfStuff.size() > 10 && this.children.size() < 4) {
            addChildren();
        } else if (this.listOfStuff.size() < 10 && this.children != null) {
            clearChildren();
        }
    }

    /**
     * draws the quadrant
     * @param g paintComponent graphics
     */
    public void draw(Graphics g) {
        g.drawRect(x,y,xBound-x,yBound-y);
    }


    public void clearChildren() {
        this.children.clear();
        this.children = new ArrayList<Node<T>>();
    }

    public void addItem(T item) {
        this.listOfStuff.add(item);
    }

    public void removeItem(T item) {
        this.listOfStuff.remove(item);
    }

    public ArrayList<T> getListOfStuff() {
        return listOfStuff;
    }

    public void setListOfStuff(ArrayList<T> items) {
        this.listOfStuff = items;
    }

    public ArrayList<Node<T>> getChildren() {
        return children;
    }

    public Node<T> getChild(int index) {
        return children.get(index);
    }

    public void setChildren(ArrayList<Node<T>> children) {
        this.children = children;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getxBound() {
        return xBound;
    }

    public int getyBound() {
        return yBound;
    }

}
