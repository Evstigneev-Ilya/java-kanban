package Manager;

import Tasks.*;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List <Task> historyTask = new ArrayList<>(10);//Мы проходили только Hashmap и ArrayList

    @Override
    public void add(Task task) {
        if (historyTask.size() == 10) {
            historyTask.remove(0);
        }
        historyTask.add(task);
    }

    @Override
    public List<Task> getHistory() {
        System.out.println("История ваших задач: ");
        return historyTask;
    }
}
