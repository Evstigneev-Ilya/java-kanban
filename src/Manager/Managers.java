package Manager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Managers {

    public static InMemoryTaskManager getDefaultTaskManager() {
        return new InMemoryTaskManager();
    }

    public static FileBackedTasksManager getFileTaskManager(String nameFile) {
        Path path = Paths.get(nameFile);
        if (Files.exists(path)) {
            return FileBackedTasksManager.loadFromFile(nameFile);
        } else {
            return new FileBackedTasksManager(nameFile);
        }
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
