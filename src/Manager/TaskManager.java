package Manager;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

public interface TaskManager {


    Collection<Task> getAllTasks();

    Collection<Epic> getAllEpic();

    Collection<Subtask> getAllSubtasks();

    void deleteAllTasks();

    void deleteAllEpic();

    void deleteAllSubtask();

    Task getTaskByID(int id);

    Epic getEpicByID(int id);

    Subtask getSubTaskById(int id);

    void saveTask(Task tasks);

    void saveEpic(Epic epics);

    void saveSubtask(Subtask subtasks, Epic epics);

    void updateTask(Task tasks);

    TreeSet<Task> getPrioritizedTasks();

    void updateEpic(Epic epics);

    void updateSubtask(Subtask subtasks);

    void deleteByIDTask(int id);

    void deleteByIDEpic(int id);

    void deleteByIDSubtask(int id);

    List<Subtask> getListOfAllSubtasks(int id);
}
