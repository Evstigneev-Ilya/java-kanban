package Manager;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

public interface TaskManager {



    public Collection<Task> getAllTasks();

    public Collection<Epic> getAllEpic();

    public Collection<Subtask> getAllSubtasks();





    public void deleteAllTasks();

    public void deleteAllEpic();

    public void deleteAllSubtask();

    public Task getTaskByID(int id);

    public Epic getEpicByID(int id);

    public Subtask getSubTaskById(int id);

    public void saveTask(Task tasks);

    public void saveEpic(Epic epics);

    public void saveSubtask(Subtask subtasks, Epic epics);

    public void updateTask(Task tasks);
    TreeSet<Task> getPrioritizedTasks();

    public void updateEpic(Epic epics);

    public void updateSubtask(Subtask subtasks);

    public void deleteByIDTask(int id);

    public void deleteByIDEpic(int id);

    public void deleteByIDSubtask(int id);

    public List<Subtask> getListOfAllSubtasks(int id);

}
