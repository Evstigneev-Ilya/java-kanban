package Manager;



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CustomLinkedList<T> {
    private Node<T> head;

    private Node<T> tail;

    private int size;

    public Node<T> getHead() {
        return head;
    }

    public int getSize() {
        return size;
    }

    public void linkLast(T element) {
        if(size == 10){
            deleteFirst();
            size--;
        }
        Node<T> addNode = new Node<>(element);
       if(head == null){
           head = addNode;
           size++;
           return;
       }
       if(tail == null){
           tail = addNode;
           tail.prev = head;
       }
       tail.next = addNode;
       tail = addNode;

    }

    public void removeNode(Node<T> element){
        Node<T> myHead=head;
        while (myHead!=element){
            myHead=myHead.next;
        }
        myHead.prev.next = myHead.next;
        myHead.next.prev=myHead.prev;
    }

    public void deleteFirst(){
        head=head.next;
        head.prev=null;
    }

}
