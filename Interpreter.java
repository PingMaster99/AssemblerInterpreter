public interface Interpreter<E> {

    // Methods used to operate ArrayLists
    void push (int index, E item);
    E remove(int index);
    E peek(int index);
    boolean empty();
    int size();
    int getIndex(E element);
}
