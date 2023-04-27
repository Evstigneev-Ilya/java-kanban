import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public void createTask(Task tasks);

    public void createEpic(Epic epics);

    public void createSubtask(Subtask subtasks);

    public void updateTask(Task tasks);

    public void updateEpic(Epic epics);

    public void updateSubtask(Subtask subtasks);

    public void deleteByIDTask(int id);

    public void deleteByIDEpic(int id);

    public void deleteByIDSubtask(int id);

    public List<Subtask> getListOfAllSubtasks(int id);

}
