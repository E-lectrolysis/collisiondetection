/*
 * Node.java
 * A node in the quad tree
 * @author Eric Ke
 * 2018/10/30
 */

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
        this.children.add(new Node<T>((xBound+x)/2,y,xBound,(yBound+y)/2)); //1
        this.children.add(new Node<T>(x,y,(xBound+x)/2,(yBound+y)/2)); //2
        this.children.add(new Node<T>(x,(yBound+y)/2, (xBound+x)/2, yBound));//3
        this.children.add(new Node<T>((xBound+x)/2, (yBound+y)/2, xBound, yBound)); //4
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
     * draws the node on the screen
     * @param g paintComponent graphics
     */
    public void draw(Graphics g) {
        g.drawRect(x,y,xBound-x,yBound-y);
    }


    /**
     * clears out the children
     */
    public void clearChildren() {
        this.children.clear();
        this.children = new ArrayList<Node<T>>();
    }

    /**
     * adds an item into the list
     * @param item the item to be added
     */
    public void addItem(T item) {
        this.listOfStuff.add(item);
    }

    /**
     * removes a specific item
     * @param item the item to be removed
     */
    public void removeItem(T item) {
        this.listOfStuff.remove(item);
    }

    /**
     * gets the items in the node
     * @return ArrayList of items in node
     */
    public ArrayList<T> getListOfStuff() {
        return listOfStuff;
    }

    /**
     * sets list of stuff
     * @param items sets list of stuff as this
     */
    public void setListOfStuff(ArrayList<T> items) {
        this.listOfStuff = items;
    }

    /**
     * gets the children nodes
     * @return an ArrayList of children nodes
     */
    public ArrayList<Node<T>> getChildren() {
        return children;
    }

    /**
     * gets a specific child node
     * @param index the index of the child node
     * @return the child node
     */
    public Node<T> getChild(int index) {
        return children.get(index);
    }

    /**
     * gets the x coordinate of the node
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * gets the y coordinate of the node
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * gets the x boundary of the node
     * @return the x boundary
     */
    public int getxBound() {
        return xBound;
    }

    /**
     * gets the y boundary of the node
     * @return the y boundary
     */
    public int getyBound() {
        return yBound;
    }

}
