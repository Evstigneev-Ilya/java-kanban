package Manager;


import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private int id = 1;
    protected InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();




    public HistoryManager getInMemoryHistoryManager() {
        return inMemoryHistoryManager;
    }


    protected HashMap<Integer, Task> task = new HashMap<>();
    protected HashMap<Integer, Epic> epic = new HashMap<>();
    protected HashMap<Integer, Subtask> subtask = new HashMap<>();


    @Override
    public Collection<Task> getAllTasks() {
        return task.values();
    }

    @Override
    public Collection<Epic> getAllEpic() {
        return epic.values();
    }

    @Override
    public Collection<Subtask> getAllSubtasks() {
        return subtask.values();
    }

    @Override
    public void deleteAllTasks() {
        task.clear();
    }

    @Override
    public void deleteAllEpic() {
        epic.clear();
    }

    @Override
    public void deleteAllSubtask() {
        subtask.clear();
        for (int key : epic.keySet()) {
            epic.get(key).clearEpicList();
        }
    }

    @Override
    public Task getTaskByID(int id) {
        Task tasks = task.getOrDefault(id, null);
        if (tasks != null) {
            inMemoryHistoryManager.add(tasks);
        }
        return tasks;
    }

    @Override
    public Epic getEpicByID(int id) {
        Epic epics = epic.getOrDefault(id, null);
        if (epics != null) {
            inMemoryHistoryManager.add(epics);
        }
        return epics;
    }

    @Override
    public Subtask getSubTaskById(int id) {
        Subtask subtasks = subtask.getOrDefault(id, null);
        if (subtasks != null) {
            inMemoryHistoryManager.add(subtasks);
        }
        return subtasks;
    }

    @Override
    public void createTask(Task tasks) {
        if (tasks == null) {
            return;
        }
        tasks.setId(id);
        task.put(id++, tasks);
    }

    @Override
    public void createEpic(Epic epics) {
        if (epics == null) {
            return;
        }
        epics.setId(id);
        epic.put(id++, epics);
    }

    @Override
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

    @Override
    public void updateTask(Task tasks) {
        if (tasks == null) {
            return;
        }
        if (task.containsKey(tasks.getId())) {
            tasks.setStatus(Status.DONE);
            task.put(tasks.getId(), tasks);
        }
    }

    @Override
    public void updateEpic(Epic epics) {
        if (epics == null) {
            return;
        }
        if (epic.containsKey(epics.getId())) {
            epic.put(epics.getId(), epics);
        }
    }

    @Override
    public void updateSubtask(Subtask subtasks) {
        if (subtasks == null) {
            return;
        }
        if (epic.containsKey(subtasks.getEpicId())) {
            epic.get(subtasks.getEpicId()).deleteItemList(subtasks);
            subtasks.setStatus(Status.DONE);
            epic.get(subtasks.getEpicId()).addItemList(subtasks);
            epic.get(subtasks.getEpicId()).updateEpicStatus();
        }
    }

    @Override
    public void deleteByIDTask(int id) {
        if (task.containsKey(id)) {
            task.remove(id);
            inMemoryHistoryManager.remove(id);
        }

    }

    @Override
    public void deleteByIDEpic(int id) {
        if (epic.containsKey(id)) {
            epic.remove(id);
            inMemoryHistoryManager.remove(id);
        }
    }

    @Override
    public void deleteByIDSubtask(int id) {
        if (subtask.containsKey(id)) {
            int epicId = subtask.get(id).getEpicId();
            if (epic.containsKey(epicId)) {
                ArrayList<Subtask> arrayList = epic.get(epicId).getSubtaskList();
                arrayList.remove(subtask.get(id));
                epic.get(epicId).setSubtaskList(arrayList);
            }
            subtask.remove(id);
            inMemoryHistoryManager.remove(id);
        }
    }

    @Override
    public List<Subtask> getListOfAllSubtasks(int id) {

        if (epic.containsKey(id)) {
            return epic.get(id).getSubtaskList();
        } else {
            return new ArrayList<>();
        }
    }

}
