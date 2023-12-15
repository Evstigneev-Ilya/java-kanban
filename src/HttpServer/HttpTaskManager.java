package HttpServer;

import Manager.FileBackedTasksManager;
import Manager.Managers;
import Manager.TypeTask;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HttpTaskManager extends FileBackedTasksManager {
    private final Gson gson;
    private final KVTaskClient client;

    public HttpTaskManager(int port) {
        super(null);
        this.client = new KVTaskClient(port);
        this.gson = Managers.getGson();
    }

    protected void addTasks(List<? extends Task> tasks) {
        for (Task task : tasks) {
            int id = task.getId();
            TypeTask typeTask = task.getTypeTask();
            if (typeTask == TypeTask.TASK) {
                this.task.put(id, task);
                prioritizedTasks.add(task);
            } else if (typeTask == TypeTask.EPIC) {
                epic.put(id, (Epic) task);
            } else if (typeTask == TypeTask.SUBTASK) {
                subtask.put(id, (Subtask) task);
                prioritizedTasks.add(task);
            }
        }
    }

    @Override
    public void save() {
        String jsonTask = gson.toJson(getAllTasks());
        client.save("tasks", jsonTask);
        String jsonEpic = gson.toJson(getAllEpic());
        client.save("epic", jsonEpic);
        String jsonSubTask = gson.toJson(getAllSubtasks());
        client.save("subTask", jsonSubTask);

        List<Integer> historyID = getHistory().stream()
                .map(Task::getId)
                .collect(Collectors.toList());
        String jsonHistory = gson.toJson(historyID);
        client.save("history", jsonHistory);
    }

    public void load() {
        String jsonTask = client.load("tasks");
        Type typeTask = new TypeToken<List<Task>>() {
        }.getType();
        ArrayList<Task> tasks = gson.fromJson(jsonTask, typeTask);
        addTasks(tasks);

        String jsonEpic = client.load("epic");
        Type typeEpic = new TypeToken<List<Epic>>() {
        }.getType();
        ArrayList<Epic> epics = gson.fromJson(jsonEpic, typeEpic);
        addTasks(epics);

        String jsonSubtask = client.load("subTask");
        Type typeSubTask = new TypeToken<List<Subtask>>() {
        }.getType();
        ArrayList<Subtask> subtasks = gson.fromJson(jsonSubtask, typeSubTask);
        addTasks(subtasks);

        String jsonHistory = client.load("history");
        Type typeHistory = new TypeToken<List<Integer>>() {
        }.getType();
        List<Integer> historyList = gson.fromJson(jsonHistory, typeHistory);
        for (Integer integer : historyList) {
            if (task.containsKey(integer)) {
                inMemoryHistoryManager.add(task.get(integer));
            }
            if (epic.containsKey(integer)) {
                inMemoryHistoryManager.add(epic.get(integer));
            }
            if (subtask.containsKey(integer)) {
                inMemoryHistoryManager.add(subtask.get(integer));
            }

        }
    }
}
