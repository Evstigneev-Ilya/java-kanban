package Manager;

import Tasks.*;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    protected Node<Task> head = null;
    protected Node<Task> tail = null;
    protected Map<Integer, Node<Task>> nodeMap = new HashMap<>();

    @Override
    public void add(Task task) {
        Node<Task> taskNode = new Node<>(task);
        if (nodeMap.containsKey(task.getId())) {
            removeNode(task);
            linkLast(task);
            return;
        }
        linkLast(task);
        nodeMap.put(task.getId(), taskNode);
    }

    @Override
    public List<Task> getHistory() {
        List<Task> taskList = new ArrayList<>();
        Node<Task> taskNode = head;
        if (head != null){
            while (taskNode != null){
                taskList.add(taskNode.getData());
                taskNode = taskNode.next;
            }
        }
        return taskList;
    }

    @Override
    public void remove(int id) {
        if(nodeMap.containsKey(id)) {
            removeNode(nodeMap.get(id).data);
            nodeMap.remove(id);
        }
    }

    public void linkLast(Task element) {
        Node<Task> addNode = new Node<>(element);
        if (head == null) {
            head = addNode;
            return;
        }
        if(tail == null){
            tail = addNode;
            tail.prev = head;
            head.next = tail;
            return;
        }
        tail.next = addNode;
        addNode.prev = tail;
        tail = addNode;
        tail.next = null;

    }

    public void removeNode(Task task) {
        if (task.equals(head.getData())) {
            head = head.next;
            return;
        }
        if (task.equals(tail.getData())) {
            tail = tail.prev;
            return;
        }
        Node<Task> myHead = head;
        while (myHead.next != null){
            if(myHead.getData().equals(task)){
                myHead.prev.next = myHead.next;
                myHead.next.prev = myHead.prev;
                return;
            }
            myHead = myHead.next;
        }
    }
}
