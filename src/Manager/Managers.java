package Manager;

import HttpServer.HttpTaskManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class Managers {


    public static InMemoryTaskManager getDefaultTaskManager() {
        return new HttpTaskManager(8080);
    }

    public static FileBackedTasksManager getFileTaskManager(String nameFile) {
        Path path = Paths.get(nameFile);
        if (Files.exists(path)) {
            return FileBackedTasksManager.loadFromFile(nameFile);
        } else {
            return new FileBackedTasksManager(nameFile);
        }
    }


    public static Gson getGson(){
         GsonBuilder gsonBuilder = new GsonBuilder();
         gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
         return gsonBuilder.create();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
