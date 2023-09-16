package Manager;

public class Managers {

    public static InMemoryTaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager();
    }

    public static InMemoryTaskManager getFileTaskManager(String nameFile){
        return new FileBackedTasksManager(nameFile);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
