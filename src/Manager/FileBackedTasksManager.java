package Manager;

import ExceptionByTask.ManagerSaveException;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private Path path;
    private static CommaSeparated commaSeparated = new CommaSeparated();

    private FileBackedTasksManager(Path path) {
        this.path = path;
    }


    public FileBackedTasksManager(String historyFile) {
        this.path = Paths.get(historyFile);
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Path getPath() {
        return path;
    }

    public void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("id,type,name,status,description,startTime,duration,epic");
            writer.newLine();
            for (Task taskOfManager : task.values()) {
                String line = commaSeparated.taskToString(taskOfManager);
                writer.write(line);
                writer.newLine();
            }
            //Сохраняем список Эпиков
            for (Epic epicOfManager : epic.values()) {
                writer.write(commaSeparated.taskToString(epicOfManager));
                writer.newLine();
            }
            // Сохраняем список Подзадач
            for (Subtask subtaskOfManager : subtask.values()) {
                writer.write(commaSeparated.taskToString(subtaskOfManager));
                writer.newLine();
            }
            writer.newLine();
            if (!inMemoryHistoryManager.nodeMap.isEmpty()) {
                writer.write(commaSeparated.historyToString(inMemoryHistoryManager));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Не получилось сохранить");
        }
    }

    public static FileBackedTasksManager loadFromFile(String loadFile) {
        Path path1 = Paths.get(loadFile);
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(path1);
        try (BufferedReader bf = Files.newBufferedReader(path1)) {
            bf.readLine();
            while (bf.ready()) {
                String line = bf.readLine();
                if (line.isBlank()) {
                    break;
                }
                Task fromStringTask = commaSeparated.fromString(line);
                if (fromStringTask.getTypeTask() == TypeTask.TASK) {
                    fileBackedTasksManager.task.put(fromStringTask.getId(), fromStringTask);
                } else if (fromStringTask.getTypeTask() == TypeTask.EPIC) {
                    fileBackedTasksManager.epic.put(fromStringTask.getId(), (Epic) fromStringTask);
                    ((Epic) fromStringTask).createStartTimeEpic();
                    ((Epic) fromStringTask).createEndTime();
                } else {
                    fileBackedTasksManager.subtask.put(fromStringTask.getId(), (Subtask) fromStringTask);
                }
            }
            for (Subtask subtask : fileBackedTasksManager.subtask.values()) {
                fileBackedTasksManager.epic.get(subtask.getEpicId()).addItemList(subtask);
            }
            String line = bf.readLine();
            if (line != null) {
                List<Integer> historyString = commaSeparated.historyFromString(line);
                for (Integer integer : historyString) {
                    if (fileBackedTasksManager.task.containsKey(integer)) {
                        fileBackedTasksManager.inMemoryHistoryManager.add(fileBackedTasksManager.task.get(integer));
                    }
                    if (fileBackedTasksManager.epic.containsKey(integer)) {
                        fileBackedTasksManager.inMemoryHistoryManager.add(fileBackedTasksManager.epic.get(integer));
                    }
                    if (fileBackedTasksManager.subtask.containsKey(integer)) {
                        fileBackedTasksManager.inMemoryHistoryManager.add(fileBackedTasksManager.subtask.get(integer
                        ));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileBackedTasksManager;
    }

    @Override
    public HistoryManager getInMemoryHistoryManager() {
        return super.getInMemoryHistoryManager();
    }

    @Override
    public Collection<Task> getAllTasks() {
        return super.getAllTasks();
    }

    @Override
    public Collection<Epic> getAllEpic() {
        return super.getAllEpic();
    }

    @Override
    public Collection<Subtask> getAllSubtasks() {
        return super.getAllSubtasks();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();
        save();
    }

    @Override
    public void deleteAllSubtask() {
        super.deleteAllSubtask();
        save();
    }

    @Override
    public Task getTaskByID(int id) {
        Task tasks = super.getTaskByID(id);
        save();
        return tasks;
    }

    @Override
    public Epic getEpicByID(int id) {
        Epic epics = super.getEpicByID(id);
        save();
        return epics;
    }

    @Override
    public Subtask getSubTaskById(int id) {
        Subtask subtask1 = super.getSubTaskById(id);
        save();
        return subtask1;
    }

    @Override
    public void saveTask(Task tasks) {
        super.saveTask(tasks);
        save();
    }

    @Override
    public void saveEpic(Epic epics) {
        super.saveEpic(epics);
        save();
    }

    @Override
    public void saveSubtask(Subtask subtasks, Epic epicThis) {
        super.saveSubtask(subtasks, epicThis);
        save();
    }

    @Override
    public void updateTask(Task tasks) {
        super.updateTask(tasks);
        save();
    }

    @Override
    public void updateEpic(Epic epics) {
        super.updateEpic(epics);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtasks) {
        super.updateSubtask(subtasks);
        save();
    }

    @Override
    public void deleteByIDTask(int id) {
        super.deleteByIDTask(id);
        save();
    }

    @Override
    public void deleteByIDEpic(int id) {
        super.deleteByIDEpic(id);
        save();
    }

    @Override
    public void deleteByIDSubtask(int id) {
        super.deleteByIDSubtask(id);
        save();
    }

    @Override
    public List<Subtask> getListOfAllSubtasks(int id) {
        return super.getListOfAllSubtasks(id);
    }
}
