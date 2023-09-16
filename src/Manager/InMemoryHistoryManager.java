package Manager;

import Tasks.*;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    protected Node<Task> head;
    protected Node<Task> tail;
    protected Map<Integer, Node> nodeMap = new HashMap<>();

    @Override
    public void add(Task task) {
        if (nodeMap.containsKey(task.getId())) {
            removeNode(nodeMap.get(task.getId()));
            linkLast(task);
            nodeMap.put(task.getId(), new Node<>(task));
            return;
        }
        linkLast(task);
        nodeMap.put(task.getId(), new Node<>(task));
    }

    @Override
    public List<Task> getHistory() {
        List<Task> taskList = new ArrayList<>();
        for (Node<Task> taskNode : nodeMap.values()) {
            taskList.add(taskNode.data);
        }
        return taskList;
    }

    @Override
    public void remove(int id) {
        removeNode(nodeMap.get(id));
        nodeMap.remove(id);
    }


    public void linkLast(Task element) {
        Node<Task> addNode = new Node<>(element);
        if (head == null) {
            head = addNode;
            return;
        }
        if (tail == null) {
            tail = addNode;
            tail.prev = head;
            return;
        }
        tail.next = addNode;
        tail = addNode;

    }

    public void removeNode(Node<Task> element) {
        if (element == head) {
            head.next.prev = null;
            head = head.next;
            return;
        }
        if (element == tail) {
            tail.prev.next = null;
            tail = tail.prev;
            return;
        }
        Node<Task> myHead = head;
        while (myHead != element) {
            myHead = myHead.next;
        }
        myHead.prev.next = myHead.next;
        myHead.next.prev = myHead.prev;
    }

}
