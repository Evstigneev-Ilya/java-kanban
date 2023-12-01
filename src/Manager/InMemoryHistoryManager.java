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
 //           removeNode(task);
            removeNode(task);
            linkLast(task);
//            replaceNode(task);
            return;

        }
        linkLast(task);
        nodeMap.put(task.getId(), taskNode);
    }

//    public void replace(Task task){
//        Node<Task> taskNode = new Node<>(task);
//        if(taskNode.getData() == head.getData()){
//            head = head.next;
//            head.prev = null;
//            tail.next = taskNode;
//
//        }
//    }

    @Override
    public List<Task> getHistory() {
        List<Task> taskList = new ArrayList<>();
//        for (Node<Task> taskNode : nodeMap.values()) {
//            taskList.add(taskNode.data);
//        }
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
            //head.prev = null;
//            head = head.next;
            return;
        }
        if (task.equals(tail.getData())) {
            tail = tail.prev;
//            tail.next = null;
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

//    public void replaceNode(Task task){
//        Node<Task> taskNode = new Node<>(task);
//        if(taskNode.getData().equals(tail.getData())){
//            return;
//        }
//        if(taskNode.getData().equals(head.getData())){
//            head = head.next;
//            tail.next = taskNode;
//            taskNode.prev = tail;
//            tail = taskNode;
//            tail.next = null;
//            return;
//        }
//        taskNode = head;
//        while (taskNode.next != null){
//            if(taskNode.getData().equals(task)) {
//                taskNode.prev.next = taskNode.next;
//                taskNode.next.prev = taskNode.prev;
//                tail.next = taskNode;
//                taskNode.prev = tail;
//                tail = taskNode;
//                tail.next = null;
//            }
//        }
//    }

    public static void main(String[] args) {
        Task task1 = new Task("task1","desc task1");
        task1.setId(1);
        Task task2 = new Task("task2","desc task2");
        task2.setId(2);
        Task task3 = new Task("task3","desc task3");
        task3.setId(3);
        Task task4 = new Task("task4","desc task4");
        task4.setId(4);

        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.add(task3);
        inMemoryHistoryManager.add(task4);
        //inMemoryHistoryManager.removeNode(new Node<>(task1));
        inMemoryHistoryManager.add(task2);

//        System.out.println(inMemoryHistoryManager.head.getData());
        System.out.println(inMemoryHistoryManager.tail.getData());
        System.out.println(inMemoryHistoryManager.getHistory());
//        Node<Task> taskNode = inMemoryHistoryManager.head();
//        System.out.println(inMemoryHistoryManager.nodeMap.values());

    }


}
