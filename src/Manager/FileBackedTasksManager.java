package Manager;

import ExceptionByTask.ManagerSaveException;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

public class FileBackedTasksManager extends InMemoryTaskManager{

    private Path path;


    private static CommaSeparated commaSeparated = new CommaSeparated();


    private FileBackedTasksManager(Path path){
        this.path = path;
    }




    public FileBackedTasksManager(String historyFile){
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

    //    static public FileBackedTasksManager createFileBackedTasksManager(String historyFile){
//        if(Files.exists(Paths.get(historyFile))){
//            return loadFromFile(historyFile);
//        }else{
//            try {
//                this.path = Files.createFile(Paths.get(historyFile));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    public void save(){
        try (BufferedWriter writer = Files.newBufferedWriter(path)){

        writer.write("id,type,name,status,description,startTime,duration,epic");
        writer.newLine();
        for(Task taskOfManager: task.values()){
            String line = commaSeparated.taskToString(taskOfManager);
            writer.write(line);
            writer.newLine();

        }
        //Сохраняем список Эпиков
        for (Epic epicOfManager: epic.values()){
            writer.write(commaSeparated.taskToString(epicOfManager));
            writer.newLine();
        }
            // Сохраняем список Подзадач
        for(Subtask subtaskOfManager: subtask.values()){
            writer.write(commaSeparated.taskToString(subtaskOfManager));
            writer.newLine();
        }


        writer.newLine();


        if(!inMemoryHistoryManager.nodeMap.isEmpty()){
            writer.write(commaSeparated.historyToString(inMemoryHistoryManager));
        }



        } catch (IOException e) {

                throw new ManagerSaveException("Не получилось сохранить");

        }
    }


    public static FileBackedTasksManager  loadFromFile(String loadFile){
        Path path1 = Paths.get(loadFile);
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(path1);

        try (BufferedReader bf = Files.newBufferedReader(path1)){
            bf.readLine();
            while (bf.ready()){

                String line = bf.readLine();
                if(line.isBlank()){
                    break;
                }
                Task fromStringTask = commaSeparated.fromString(line);
                if(fromStringTask.getTypeTask()==TypeTask.TASK){
                    fileBackedTasksManager.task.put(fromStringTask.getId(),fromStringTask);
                } else if (fromStringTask.getTypeTask()==TypeTask.EPIC) {
                    fileBackedTasksManager.epic.put(fromStringTask.getId(),(Epic) fromStringTask);
                    ((Epic) fromStringTask).createStartTimeEpic();
                    ((Epic) fromStringTask).createEndTime();

                }else {fileBackedTasksManager.subtask.put(fromStringTask.getId(),(Subtask) fromStringTask);

                }

            }

            for(Subtask subtask: fileBackedTasksManager.subtask.values()){
                fileBackedTasksManager.epic.get(subtask.getEpicId()).addItemList(subtask);
            }
            String line = bf.readLine();
            if(line != null) {
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
//                for (Task tasked : task.values()) {
//                    if (tasked.getId() == integer) {
//                        fileBackedTasksManager.inMemoryHistoryManager.add(tasked);
//                    }
//                }
//                for (Epic epics : epic.values()) {
//                    if (epics.getId() == integer) {
//                        fileBackedTasksManager.inMemoryHistoryManager.add(epics);
//                    }
//                }
//                for (Subtask subtasked : subtask.values()) {
//                    if (subtasked.getId() == integer) {
//                        fileBackedTasksManager.inMemoryHistoryManager.add(subtasked);
//                    }
//                }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileBackedTasksManager;

    }

//    private void checkTypeOfTask(Task tasks) {
//        if(tasks.getTypeTask()==TypeTask.TASK){
//            this.task.put(tasks.getId(), tasks);
//        }else if(tasks.getTypeTask()==TypeTask.EPIC){
//            this.epic.put(tasks.getId(), (Epic) tasks);
//        }else subtask.put(tasks.getId(), (Subtask) tasks);
//    }





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

    public static void main(String[] args){
//            File file1 xt= new File("history.t");
//            file1.delete();

        FileBackedTasksManager taskManager = FileBackedTasksManager.loadFromFile("history.txt");
        System.out.println(taskManager.task.values());
        for (Task task1 : taskManager.task.values()){
            System.out.println(task1.getStartTime());
        }
//        FileBackedTasksManager taskManager = new FileBackedTasksManager("history.txt");
//        Task task1 = new Task("Task1", "Disc task1");
//        Task task2 = new Task("Task2", "Disc task2");
//        Epic epic1 = new Epic("Epic1", "Disc epic2");
//        Subtask subtask1 = new Subtask("Subtask1", "Disc subtask1");
//        Subtask subtask2 = new Subtask("Subtask2", "Disc subtask2");
//        Subtask subtask3 = new Subtask("Subtask3", "Disc subtask3");
//        task1.setStartTime("01.11.1992 12:14");
//        task1.setDuration(80);
//        task2.setStartTime("01.12.2013 22:22");
//        task2.setDuration(30);
//        taskManager.saveTask(task1);
//        taskManager.saveTask(task2);
//        taskManager.saveEpic(epic1);
//        taskManager.saveSubtask(subtask1,epic1);
//        taskManager.saveSubtask(subtask2,epic1);
//        taskManager.saveSubtask(subtask3,epic1);
//
//        taskManager.getTaskByID(1);
//        taskManager.getTaskByID(2);
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
//        TreeSet<Task> sadsa = taskManager.getPrioritizedTasks();
//        for(Task task: sadsa){
//            if(task.getStartTime()!= null){
//                System.out.println(task.getStartTime().format(dateTimeFormatter));
//            }else System.out.println("1");
//
//        }


//
//
////        taskManager.stingHistory();
//        System.out.println(taskManager.getInMemoryHistoryManager().getHistory());

//        System.out.println(taskManager.commaSeparated.historyToString(taskManager.getInMemoryHistoryManager()));
//









    }


}
