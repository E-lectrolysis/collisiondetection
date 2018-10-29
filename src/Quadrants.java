import java.awt.*;
import java.util.ArrayList;

public class Quadrants<T> {

    private Node<T> root;
    private ArrayList<T> allTheItems;

    private int screenHeight = DisplayFrame.getScreenHeight();
    private int screenWidth = DisplayFrame.getScreenWidth();

    Quadrants(ArrayList<T> o) {
        root = new Node<T>(0,0,screenHeight,screenWidth);
        root.setListOfStuff(o);
    }

    Quadrants() {
        root = new Node<T>(0,0,screenHeight,screenWidth);
        allTheItems = new ArrayList<T>();
    }

    public void setAllTheItems(ArrayList<T> items) {
        this.allTheItems = items;
        insertItemsIntoNodes();
    }

    public void addItem(T item) {
        this.allTheItems.add(item);
        insertItemsIntoNodes();
    }

    public void update(Node<T> n, Graphics g) {
        Node tempNode = n;

        n.draw(g);

        n.update();

        if(n.getChildren() != null) {
            ArrayList<Node<T>> children = n.getChildren();
            for(int i = 0; i < tempNode.getChildren().size(); i++) {
                update((Node<T>) tempNode.getChildren().get(i),g);

            }
        } else {
            return;
        }
    }

    private void insertItemsIntoNodes() {
        root.setListOfStuff(allTheItems);
    }

    public Node<T> getRoot() {
        return root;
    }

}
