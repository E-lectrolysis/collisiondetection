import java.awt.*;
import java.util.ArrayList;

public class Quadrants<T> {

    private Node<T> root;
    private ArrayList<T> allTheItems;

    private int screenHeight = DisplayFrame.getScreenHeight();
    private int screenWidth = DisplayFrame.getScreenWidth();


    /**
     * Constructor for Quadrants
     */
    Quadrants() {
        root = new Node<T>(0,0,screenHeight,screenWidth);
        allTheItems = new ArrayList<T>();
    }


    /**
     * adds an item into the list
     * @param item the item to add
     */
    public void addItem(T item) {
        this.allTheItems.add(item);
        insertItemsIntoNodes();
    }



    /**
     * @param index the index of the item to remove
     */
    public void remove(int index) {
        this.allTheItems.remove(index);
    }



    /**
     * updates the nodes, going through all of them recursively
     * @param n the node to update
     * @param g paintComponent graphics
     */
    public void update(Node<T> n, Graphics g) {

        n.update();
        n.draw(g);

        if(((Node) n).getChildren().size() == 4) {
            for(int i = 0; i < 4; i++) {
                update(n.getChild(i),g); //call method within itself to go through them and update them individually
            }
        }
    }


    /**
     * gets the entire list of items
     * @return ArrayList of all the items
     */
    public ArrayList<T> getAllTheItems() {
        return allTheItems;
    }

    /**
     * sets the list of all the items
     */
    private void insertItemsIntoNodes() {
        root.setListOfStuff(allTheItems);
    }


    /**
     * gets the root node
     * @return the root node
     */
    public Node<T> getRoot() {
        return root;
    }

}
