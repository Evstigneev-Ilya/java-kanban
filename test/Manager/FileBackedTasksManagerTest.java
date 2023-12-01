package Manager;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager>{


    @BeforeEach
    void setUp(){
        taskManager = new FileBackedTasksManager("test.txt");
        Task task1 = new Task("Task1", "DiscTask1");
        Task task2 = new Task("Task2", "DiscTask2");
        Epic epic = new Epic("Epic1", "Epic disc");
        Subtask subtask1 = new Subtask("SubTask1", "SubTask1Disc");
        Subtask subtask2 = new Subtask("SubTask2", "SubTask2Disc");
        Subtask subtask3 = new Subtask("SubTask3", "SubTask3Disc");
        taskManager.saveTask(task1);
        taskManager.saveTask(task2);
        taskManager.saveEpic(epic);
        taskManager.saveSubtask(subtask1,epic);
        taskManager.saveSubtask(subtask2,epic);
        taskManager.saveSubtask(subtask3,epic);
    }

    @AfterEach
    void deleteFile(){
        File file = new File("test.txt");
        file.delete();
    }


    @Test
    void saveEmptyFile(){
        taskManager.deleteAllTasks();
        taskManager.deleteAllEpic();
        taskManager.deleteAllSubtask();
        taskManager.save();
        long count = 0;
        try {
            count = Files.lines(taskManager.getPath()).count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2, count, "Не совпало количество строк в пустом файле");
    }

    @Test
    void saveFile(){
        taskManager.save();
        long count = 0;
        try {
            count = Files.lines(taskManager.getPath()).count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(8, count, "Количество строк не совпало в файле");
    }

    @Test
    void loadEmptyFile(){
        taskManager.deleteAllTasks();
        taskManager.deleteAllEpic();
        taskManager.deleteAllSubtask();
        taskManager.save();
        FileBackedTasksManager fileBackedTasksManagerLoadFile = FileBackedTasksManager.loadFromFile("test.txt");
        assertNull(fileBackedTasksManagerLoadFile.getTaskByID(1));
    }

    @Test
    void loadFile(){
        taskManager.save();
        FileBackedTasksManager fileBackedTasksManagerLoadFile = FileBackedTasksManager.loadFromFile("test.txt");
        assertEquals(taskManager.getTaskByID(1),fileBackedTasksManagerLoadFile.getTaskByID(1),
                "Не правильно загрузился файл");

    }

    @Test
    void loadFileEmptySubsTask(){
        taskManager.deleteAllSubtask();
        taskManager.save();
        FileBackedTasksManager fileBackedTasksManagerLoadFile = FileBackedTasksManager.loadFromFile("test.txt");
        assertNull(fileBackedTasksManagerLoadFile.getSubTaskById(4));
        assertEquals(taskManager.getListOfAllSubtasks(2), fileBackedTasksManagerLoadFile.getListOfAllSubtasks(2));
    }






}