package Manager;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import org.junit.jupiter.api.BeforeEach;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefaultTaskManager();
        Task task1 = new Task("Task1", "DiscTask1");
        Task task2 = new Task("Task2", "DiscTask2");
        Epic epic = new Epic("Epic1", "Epic disc");
        Subtask subtask1 = new Subtask("SubTask1", "SubTask1Disc");
        Subtask subtask2 = new Subtask("SubTask2", "SubTask2Disc");
        Subtask subtask3 = new Subtask("SubTask3", "SubTask3Disc");
        task1.setStartTime("01.11.1992 12:14");
        task1.setDuration(80);
        task2.setStartTime("01.12.2013 22:22");
        task2.setDuration(30);
        taskManager.saveTask(task1);
        taskManager.saveTask(task2);
        taskManager.saveEpic(epic);
        taskManager.saveSubtask(subtask1, epic);
        taskManager.saveSubtask(subtask2, epic);
        taskManager.saveSubtask(subtask3, epic);
    }
}