package Manager;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest<T extends  TaskManager> {

    T taskManager;





    @Test
    void getAllTasksEmpty() {
        taskManager.deleteAllTasks();
        assertTrue(taskManager.getAllTasks().isEmpty(), "Список не пустой!");
    }

    @Test
    void getAllTasks(){
        assertEquals(2, taskManager.getAllTasks().size(), "не совпадает размер тасков");
    }



    @Test
    void getAllEpicEmpty() {
        taskManager.deleteAllEpic();
        assertTrue(taskManager.getAllEpic().isEmpty());
    }

    @Test
    void getAllEpic(){
        assertEquals(1, taskManager.getAllEpic().size(), "Не совпадает размер Эпиков");
    }

    @Test
    void getAllSubtasksEmpty() {
        taskManager.deleteAllSubtask();
        assertTrue(taskManager.getAllSubtasks().isEmpty(), "Список подзадач не пуст!");
    }

    @Test
    void getAllSubtask(){
        assertEquals(3, taskManager.getAllSubtasks().size(), "Не совпадает размер подзадач");
    }

    @Test
    void deleteAllTasks() {
        taskManager.deleteAllEpic();
        assertTrue(taskManager.getAllEpic().isEmpty(), "Удалились не все Задачи!");

    }

    @Test
    void deleteAllEpic() {
        taskManager.deleteAllSubtask();
        assertTrue(taskManager.getAllSubtasks().isEmpty(), "Удалились не все Эпики!");
    }

    @Test
    void deleteAllSubtask() {
        taskManager.deleteAllSubtask();
        assertTrue(taskManager.getAllSubtasks().isEmpty(), "Удалились не все подзадачи!");
    }

    @Test
    void getTaskByID() {
        Task task = taskManager.getTaskByID(1);
        assertNotNull(task);
        assertEquals(1, task.getId(), "Айди не совпали у тасков");
    }

    @Test
    void getTaskByIDisNull(){
        assertNull(taskManager.getTaskByID(5));
    }

    @Test
    void getEpicByID() {
        Epic epic = taskManager.getEpicByID(3);
        assertNotNull(epic);
        assertEquals(TypeTask.EPIC, epic.getTypeTask());
        assertEquals(3, epic.getId(), "Не совпали айди");
    }

    @Test
    void getEpicByIdNull(){
        assertNull(taskManager.getEpicByID(6));
    }

    @Test
    void getSubTaskById() {
        Subtask subtask = taskManager.getSubTaskById(4);
        assertNotNull(subtask);
        assertEquals(TypeTask.SUBTASK, subtask.getTypeTask());
        assertEquals(4, subtask.getId(),"айди не совпали");
    }

    @Test
    void  getSubtaskByIdNull(){
        assertNull(taskManager.getTaskByID(10));
    }

    @Test
    void saveTask() {
        taskManager.saveTask(new Task("Task", "task"));
        assertEquals("Task",taskManager.getTaskByID(7).getName()," Не создался Таск");
    }

    @Test
    void createEpic() {
        taskManager.saveEpic(new Epic("Epic", "epic"));
        assertEquals("Epic", taskManager.getEpicByID(7).getName(), "Не совпали Эпики");
    }

    @Test
    void createSubtask() {
        taskManager.saveSubtask(new Subtask("Subtask", "subtask"), taskManager.getEpicByID(3));
        assertEquals("Subtask", taskManager.getSubTaskById(7).getName(),"Не совпали подзадачи");

    }

    @Test
    void updateTask() {
        Task task = taskManager.getTaskByID(1);
        taskManager.updateTask(task);
        assertEquals(Status.DONE, taskManager.getTaskByID(1).getStatus(), "Статусы тасков не совпали");
    }

    @Test
    void updateEpic() {

        for(Subtask subtask: taskManager.getEpicByID(3).getSubtaskList()){
            subtask.setStatus(Status.DONE);
        }
        taskManager.getEpicByID(3).updateEpicStatus();
        assertEquals(Status.DONE, taskManager.getEpicByID(3).getStatus(), "Статусы эпиков не совпали");

    }

    @Test
    void updateSubtask() {
        Subtask subtask = taskManager.getSubTaskById(4);
        taskManager.updateSubtask(subtask);
        assertEquals(Status.DONE, taskManager.getSubTaskById(4).getStatus(), "Статусы подзадач не совпали");
    }

    @Test
    void deleteByIDTask() {
        taskManager.deleteByIDTask(1);
        assertNull(taskManager.getTaskByID(1));
    }

    @Test
    void deleteByIDEpic() {
        taskManager.deleteByIDEpic(3);
        assertNull(taskManager.getEpicByID(3));
    }

    @Test
    void deleteByIDSubtask() {
        taskManager.deleteByIDSubtask(4);
        assertNull(taskManager.getSubTaskById(4));
    }

    @Test
    void getListOfAllSubtasks() {
        List<Subtask> subtaskList = taskManager.getListOfAllSubtasks(3);
        Subtask subtask = subtaskList.get(0);
        assertEquals(4, subtask.getId());
    }

    @Test
    void getPrioritizedTasks(){
        assertEquals(3, taskManager.getPrioritizedTasks().size());
    }
}