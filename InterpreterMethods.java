import java.util.ArrayList;

public class InterpreterMethods<E> implements Interpreter<E> {

    private ArrayList<E> stackArray = new ArrayList<E>(50);

    // Constructor
    public InterpreterMethods() {
        this.stackArray = stackArray;
    }

    // Adds an item in the index position
    public void push(int index, E item) {
        stackArray.add(index, item);
    }

    // Removes the item in the index position
    public E remove(int index) {
        return stackArray.remove(index);
    }

    // Shows what item is in the index position
    public E peek(int index) {
        return stackArray.get(index);
    }

    // Used to see if an ArrayList is empty
    public boolean empty() {

        if(stackArray.isEmpty()) {
            return true;
        }
        return false;
    }

    // Returns the size of the ArrayList
    public int size() {
        return stackArray.size();
    }

    // Gets the position of the desired element in the ArrayList
    public int getIndex(E element) {
        return stackArray.indexOf(element);
    }
}
