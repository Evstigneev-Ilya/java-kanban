import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    int id = 1;

    HashMap<Integer, Task> task = new HashMap<>();
    HashMap<Integer, Epic> epic = new HashMap<>();

    public void getAllTasks() {
        for (int key : task.keySet()) {
            System.out.println(task.get(key));
        }
    }

    public void getAllEpic() {
        for (int key : epic.keySet()) {
            System.out.println(epic.get(key));
        }
    }

    public void getAllSubtasks() {
        for (int key : epic.keySet()) {
            ArrayList<Subtask> subtaskList = epic.get(key).epicList;
            for (Subtask subtask : subtaskList)
                System.out.println(subtask);
        }
    }

    public void deleteAllTasks() {
        task.clear();
    }

    public void deleteAllEpic() {
        epic.clear();
    }

    public Task getTaskByID(int id) {
        if (task.containsKey(id)) {
            return task.get(id);
        }
        return null;
    }

    public Epic getEpicByID(int id) {
        if (epic.containsKey(id)) {
            return epic.get(id);
        }
        return null;
    }

    public Subtask getSubtaskByID(int id) {
        for (int key : epic.keySet()) {
            ArrayList<Subtask> subtaskList = epic.get(key).epicList;
            for (Subtask subtask : subtaskList)
                if (subtask.getId() == id) {
                    return subtask;
                }
        }
        return null;
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
        if (epic.containsKey(subtasks.epicId)) {
            subtasks.setId(id++);
            epic.get(subtasks.epicId).epicList.add(subtasks);
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
        if (epic.containsKey(subtasks.epicId)) {
            epic.get(subtasks.epicId).epicList.remove(subtasks);
            subtasks.setStatus("DONE");
            epic.get(subtasks.epicId).epicList.add(subtasks);
            epic.get(subtasks.epicId).updateEpicStatus();
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
        for (int epics : epic.keySet()) {
            ArrayList<Subtask> sub = epic.get(epics).epicList;
            for (Subtask s : sub) {
                if (s.getId() == id) {
                    sub.remove(s);
                    epic.get(epics).epicList = sub;
                }
            }
        }
    }

    public void getListOfAllSubtasks(int id) {
        if (epic.containsKey(id)) {
            System.out.println(epic.get(id).epicList);
        }
    }


}
