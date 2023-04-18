public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        Task dog = new Task("Завести собаку", "Найти в питомнике черного мопсика-девочку и купить ее");
        Task attribute = new Task("Купить все атрибуты", "Пойти в зоомагазин и купить все, что нужно "
                + "для комфортного содержания собаки");

        manager.createTask(dog);
        manager.createTask(attribute);

        Epic job = new Epic("Устроиться на работу", "Отправить резюме в 2 компании");
        Subtask interview1 = new Subtask("Пройти собеседование в первой компании", "Поехать по "
                + "указанному адресу и подробнее ознакомится с вакансией", 3);
        Subtask interview2 = new Subtask("Пройти собеседование вo второй компании", "Поехать по "
                + "указанному адресу и подробнее ознакомится с вакансией", 3);

        manager.createEpic(job);
        manager.createSubtask(interview1);
        manager.createSubtask(interview2);

        Epic vacation = new Epic("Поехать в отпуск", "Подготовить все самое необходимое к поездке");
        Subtask suitcase = new Subtask("Собрать чемодан", "Не забыть взять нового питомца с собой:)",
                6);

        manager.createEpic(vacation);
        manager.createSubtask(suitcase);
        manager.updateTask(dog);
        manager.updateSubtask(suitcase);
        manager.deleteByIDSubtask(4);
        manager.deleteByIDEpic(6);
        manager.getAllTasks();
        manager.getAllEpic();
        manager.getAllSubtasks();

    }
}
