package Manager;


import ExceptionByTask.TaskValidationException;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int id = 1;
    protected InMemoryHistoryManager inMemoryHistoryManager;
    protected HashMap<Integer, Task> task = new HashMap<>();
    protected HashMap<Integer, Epic> epic = new HashMap<>();
    protected HashMap<Integer, Subtask> subtask = new HashMap<>();
    private Comparator<Task> nullLastComparator = Comparator.comparing(Task::getStartTime);
    protected TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime,
            Comparator.nullsLast(Comparator.naturalOrder())));

    public InMemoryTaskManager(){
        inMemoryHistoryManager = new InMemoryHistoryManager();
    }

    public HistoryManager getInMemoryHistoryManager() {
        return inMemoryHistoryManager;
    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return this.prioritizedTasks;
    }

    public void validation(Task task){
        LocalDateTime startDate = task.getStartTime();
        LocalDateTime endDate = task.getEndTime();

        Integer result = prioritizedTasks.stream().map(it -> {
            if(startDate.isBefore(it.getEndTime())&&startDate.isAfter(it.getStartTime())){
                return 1;
            }
            if(endDate.isBefore(it.getEndTime())&&endDate.isAfter(it.getStartTime())){
                return 1;
            }
            return 0;
        }).reduce(Integer::sum).orElse(0);

        if(result > 0){
            throw new TaskValidationException("Задача пересекается");
        }
    }


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
    public void saveTask(Task tasks) {
        if (tasks == null) {
            return;
        }
        tasks.setId(id);
        task.put(id++, tasks);
        validation(tasks);
        prioritizedTasks.add(tasks);
    }

    @Override
    public void saveEpic(Epic epics) {
        if (epics == null) {
            return;
        }
        epics.setId(id);
        epic.put(id++, epics);
    }

    @Override
    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    @Override
    public void saveSubtask(Subtask subtasks, Epic epicThis) {
        if (subtasks == null) {
            return;
        }
        if (epic.containsKey(epicThis.getId())) {
            subtasks.setId(id);
            subtasks.setEpicId(epicThis.getId());
            subtask.put(id++, subtasks);
            epicThis.addItemList(subtasks);
            if (subtasks.getStartTime() != null) {
                epicThis.createEndTime();
                epicThis.createStartTimeEpic();
            }
            epic.put(epicThis.getId(), epicThis);
            validation(subtasks);
            prioritizedTasks.add(subtasks);
        } else
            System.out.println("Эпика нет");
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
    public boolean existTask(Task tasked) {
        int id = tasked.getId();
        for(Task t: task.values()){
            if(t.getId() == id){
                return true;
            }
        }
        for(Epic e: epic.values()){
            if(e.getId() == id){
                return true;
            }
        }
        for(Subtask s: subtask.values()){
            if(s.getId() == id){
                return true;
            }
        }
        return false;
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
