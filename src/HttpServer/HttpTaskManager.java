package HttpServer;

import Manager.FileBackedTasksManager;
import Manager.Managers;
import Tasks.Task;
import com.google.gson.Gson;

import java.util.List;
import java.util.stream.Collectors;

public class HttpTaskManager extends FileBackedTasksManager {
    private final Gson gson;
    private final KVTaskClient client;

    public HttpTaskManager(int port){
        super(null);
        this.client = new KVTaskClient(port);
        this.gson = Managers.getGson();
    }

    @Override
    public void save() {
        String jsonTask = gson.toJson(getAllTasks());
        client.save("tasks",jsonTask);
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
}
