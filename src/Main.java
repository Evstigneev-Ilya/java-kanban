public class Main {

    public static void main(String[] args) {

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task dog = new Task("Завести собаку", "Найти в питомнике черного мопсика-девочку и купить ее");
        Task attribute = new Task("Купить все атрибуты", "Пойти в зоомагазин и купить все, что нужно "
                + "для комфортного содержания собаки");

        inMemoryTaskManager.createTask(dog);
        inMemoryTaskManager.createTask(attribute);

        Epic job = new Epic("Устроиться на работу", "Отправить резюме в 2 компании");
        Subtask interview1 = new Subtask("Пройти собеседование в первой компании", "Поехать по "
                + "указанному адресу и подробнее ознакомится с вакансией", 3);
        Subtask interview2 = new Subtask("Пройти собеседование вo второй компании", "Поехать по "
                + "указанному адресу и подробнее ознакомится с вакансией", 3);

        inMemoryTaskManager.createEpic(job);
        inMemoryTaskManager.createSubtask(interview1);
        inMemoryTaskManager.createSubtask(interview2);

        Epic vacation = new Epic("Поехать в отпуск", "Подготовить все самое необходимое к поездке");
        Subtask suitcase = new Subtask("Собрать чемодан", "Не забыть взять нового питомца с собой:)",
                6);

        inMemoryTaskManager.createEpic(vacation);
        inMemoryTaskManager.createSubtask(suitcase);
        inMemoryTaskManager.updateTask(dog);
        inMemoryTaskManager.updateSubtask(suitcase);
        System.out.println(inMemoryTaskManager.getAllTasks());
        System.out.println(inMemoryTaskManager.getAllEpic());
        System.out.println(inMemoryTaskManager.getAllSubtasks());
        inMemoryTaskManager.getTaskByID(1);
        inMemoryTaskManager.getTaskByID(2);

        System.out.println(inMemoryTaskManager.getInMemoryHistoryManager().getHistory());

    }
}
