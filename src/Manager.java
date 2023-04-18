import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Manager {
    private int id = 1;

    private HashMap<Integer, Task> task = new HashMap<>();
    private HashMap<Integer, Epic> epic = new HashMap<>();
    private HashMap<Integer, Subtask> subtask = new HashMap<>();

    public Collection<Task> getAllTasks() {
        return task.values(); // без явного приведения IDEA подсказала сделать так, но Collection мы еще не проходили
    }

    public Collection<Epic> getAllEpic() {
        return epic.values();
    }

    public Collection<Subtask> getAllSubtasks() {
        return subtask.values();
    }

    public void deleteAllTasks() {

        task.clear();
    }

    public void deleteAllEpic() {
        epic.clear();
    }

    public void deleteAllSubtask() {
        subtask.clear();
        for (int key : epic.keySet()) {
            epic.get(key).clearEpicList();
        }
    }

    public Task getTaskByID(int id) {
        return task.getOrDefault(id, null);
    }

    public Epic getEpicByID(int id) {
        return epic.getOrDefault(id, null);
    }

    public Subtask getSubTaskById(int id) {
        return subtask.getOrDefault(id, null);
    }


    public void createTask(Task tasks) {
        if (tasks == null) {
            return;
        }
        tasks.setId(id);
        task.put(id++, tasks);
    }

    public void createEpic(Epic epics) {
        if (epics == null) {
            return;
        }
        epics.setId(id);
        epic.put(id++, epics);
    }

    public void createSubtask(Subtask subtasks) {
        if (subtasks == null) {
            return;
        }
        if (epic.containsKey(subtasks.getEpicId())) {
            subtasks.setId(id);
            subtask.put(id++, subtasks);
            epic.get(subtasks.getEpicId()).addItemList(subtasks);
        }
    }

    public void updateTask(Task tasks) {
        if (tasks == null) {
            return;
        }
        if (task.containsKey(tasks.getId())) {
            tasks.setStatus("DONE");
            task.put(tasks.getId(), tasks);
        }
    }

    public void updateEpic(Epic epics) {
        if (epics == null) {
            return;
        }
        if (epic.containsKey(epics.getId())) {
            epic.put(epics.getId(), epics);
        }
    }

    public void updateSubtask(Subtask subtasks) {
        if (subtasks == null) {
            return;
        }
        if (epic.containsKey(subtasks.getEpicId())) {
            epic.get(subtasks.getEpicId()).deleteItemList(subtasks);
            subtasks.setStatus("DONE");
            epic.get(subtasks.getEpicId()).addItemList(subtasks);
            epic.get(subtasks.getEpicId()).updateEpicStatus();
        }
    }

    public void deleteByIDTask(int id) {
        if (task.containsKey(id)) {
            task.remove(id);
        }
    }

    public void deleteByIDEpic(int id) {
        if (epic.containsKey(id)) {
            epic.remove(id);
        }
    }

    public void deleteByIDSubtask(int id) {
        if (subtask.containsKey(id)) {
            int epicId = subtask.get(id).getEpicId();
            if (epic.containsKey(epicId)) {
                ArrayList<Subtask> arrayList = epic.get(epicId).getEpicList();
                arrayList.remove(subtask.get(id));
                epic.get(epicId).setEpicList(arrayList);
            }
            subtask.remove(id);
        }
    }

    public List<Subtask> getListOfAllSubtasks(int id) {

        if (epic.containsKey(id)) {
            return epic.get(id).getEpicList();
        } else {
            return new ArrayList<>();
        }
    }
}
