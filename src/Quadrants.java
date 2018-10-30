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

    /**
     * Removes the specified item from the entire tree
     * @param item the item to be removed
     * @param n the current node
     */
    public void remove(T item, Node n) {
        n.getListOfStuff().remove(item);
        if(n.getChildren().size() == 4) {
            for(int i = 0; i < n.getChildren().size(); i++) {
                remove(item, n.getChild(i));
            }
        }
    }


    /**
     * updates every single node
     * @param n the node
     * @param g
     */
    public void update(Node<T> n, Graphics g) {

        n.update();
        updateNodes(n,g);


    }

    public void updateNodes(Node<T> n,Graphics g) {

        Node tempNode = n;
        n.draw(g);

        if(n.getChildren().size() == 4) {
            for(int i = 0; i < tempNode.getChildren().size(); i++) {
                updateNodes(tempNode.getChild(i),g);
            }
        }
    }

    public ArrayList<T> getAllTheItems() {
        return allTheItems;
    }

    private void insertItemsIntoNodes() {
        root.setListOfStuff(allTheItems);
    }

    public Node<T> getRoot() {
        return root;
    }

}
