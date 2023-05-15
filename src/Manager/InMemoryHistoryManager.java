package Manager;

import Tasks.*;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {


    private CustomLinkedList<Task> historyTask = new CustomLinkedList<Task>();
    private Map<Integer, Node> nodeMap = new HashMap<>();



    @Override
    public void add(Task task) {
        if(historyTask.getSize() == 10){
            Node<Task> node = historyTask.getHead();
            nodeMap.remove(node.data.getId());
        }
        if(nodeMap.containsKey(task.getId())){
            historyTask.removeNode(nodeMap.get(task.getId()));
            historyTask.linkLast(task);
            nodeMap.put(task.getId(), new Node<>(task));
            return;
        }
        historyTask.linkLast(task);
        nodeMap.put(task.getId(), new Node<>(task));
    }

    @Override
    public List<Task> getHistory() {
        List<Task> taskList = new ArrayList<>();
        System.out.println("История ваших задач: ");
        for(Node<Task> taskNode: nodeMap.values()){
            taskList.add(taskNode.data);
        }
        return taskList;
    }

    @Override
    public void remove(int id) {
        historyTask.removeNode(nodeMap.get(id));
        nodeMap.remove(id);
    }
}
