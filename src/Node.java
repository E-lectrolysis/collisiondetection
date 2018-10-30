import java.awt.*;
import java.util.ArrayList;


public class Node<T> {

    private ArrayList<T> listOfStuff = new ArrayList<T>();
    private ArrayList<Node<T>> children = new ArrayList<Node<T>>();

    private int x,y;
    private int xBound, yBound;

    public Node(int x, int y, int xBound, int yBound) {
        this.x = x;
        this.y = y;
        this.xBound = xBound;
        this.yBound = yBound;
    }


    /*
    2 1
    3 4
     */


    public void addChildren() {
        this.children = new ArrayList<Node<T>>();

        this.children.add(new Node(xBound/2,y,xBound,yBound/2)); //1
        this.children.add(new Node(x,y,xBound/2,yBound/2)); //2
        this.children.add(new Node(x,yBound/2, xBound/2, yBound));//3
        this.children.add(new Node(xBound/2, yBound/2, xBound, yBound)); //4
    }



    public void update() {
        if(this.listOfStuff.size() > 10 && children.size() != 4) {
            addChildren();
        } else if (this.listOfStuff.size() < 10 && children != null) {
            clearChildren();
        }
    }

    public void draw(Graphics g) {
        g.drawRect(x,y,xBound,yBound);
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

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getxBound() {
        return xBound;
    }

    public void setxBound(int xBound) {
        this.xBound = xBound;
    }

    public int getyBound() {
        return yBound;
    }

    public void setyBound(int yBound) {
        this.yBound = yBound;
    }
}
