package Manager;

public class Node<E> {

    public E data;
    public Node<E> next;
    public Node<E> prev;

    public Node(E data) {
        this.data = data;

    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}
