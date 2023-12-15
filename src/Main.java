import Manager.InMemoryTaskManager;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

public class Main {

    public static void main(String[] args) {

       InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task dog = new Task("Завести собаку", "Найти в питомнике черного мопсика-девочку и купить ее");
        Task attribute = new Task("Купить все атрибуты", "Пойти в зоомагазин и купить все, что нужно "
                + "для комфортного содержания собаки");

        inMemoryTaskManager.saveTask(dog);
        inMemoryTaskManager.saveTask(attribute);

        System.out.println(inMemoryTaskManager.getTaskByID(3));









    }
}
