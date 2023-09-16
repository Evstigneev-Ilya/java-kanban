package Manager;

import ExceptionByTask.ManagerSaveException;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

import java.io.*;

import java.util.Collection;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager{

    private File file;

    private CommaSeparated commaSeparated = new CommaSeparated();



    public FileBackedTasksManager(File file){
        this.file = file;
    }




    public FileBackedTasksManager(String historyFile){

        if(new File(historyFile).exists()){
            loadFromFile(new File(historyFile));
        }else {
            file = new File(historyFile);
        }

    }

    public void save(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){

        writer.write("id,type,name,status,description,epic");
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



        writer.write(commaSeparated.historyToString(inMemoryHistoryManager));



        } catch (IOException e) {

                throw new ManagerSaveException("Не получилось сохранить");

        }
    }


    public FileBackedTasksManager  loadFromFile(File file){
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        System.out.println(file);
        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));
            bf.readLine();
            while (bf.ready()){

                String line = bf.readLine();
                if(line.isBlank()){
                    break;
                }
                Task fromStringTask = commaSeparated.fromString(line);
                checkTypeOfTask(fromStringTask);
                if(fromStringTask.getTypeTask() == TypeTask.SUBTASK){
                    addSubtaskInEpic((Subtask) fromStringTask);
                }

            }

            String line = bf.readLine();
            List<Integer> historyString = commaSeparated.historyFromString(line);
            for(Integer integer: historyString) {
                for (Task tasked : task.values()) {
                    if (tasked.getId() == integer) {
                        inMemoryHistoryManager.add(tasked);
                    }
                }
                for (Epic epics : epic.values()) {
                    if (epics.getId() == integer) {
                        inMemoryHistoryManager.add(epics);
                    }
                }
                for (Subtask subtasked : subtask.values()) {
                    if (subtasked.getId() == integer) {
                        inMemoryHistoryManager.add(subtasked);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileBackedTasksManager;

    }

    private void checkTypeOfTask(Task tasks) {
        if(tasks.getTypeTask()==TypeTask.TASK){
            task.put(tasks.getId(), tasks);
        }else if(tasks.getTypeTask()==TypeTask.EPIC){
            epic.put(tasks.getId(), (Epic) tasks);
        }else subtask.put(tasks.getId(), (Subtask) tasks);
    }

    public void addSubtaskInEpic(Subtask subtasked){
        epic.get(subtasked.getEpicId()).addItemList(subtasked);
    }


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
    public void createTask(Task tasks) {
        super.createTask(tasks);
        save();
    }

    @Override
    public void createEpic(Epic epics) {
        super.createEpic(epics);
        save();
    }

    @Override
    public void createSubtask(Subtask subtasks) {
        super.createSubtask(subtasks);
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

        HistoryManager historyManager = Managers.getDefaultHistory();
        FileBackedTasksManager taskManager = new FileBackedTasksManager("history.txt");

//        taskManager.createTask(new Task("first task", "eat breAKfast"));
//        taskManager.createTask(new Task("second Task", "go to work"));
//        taskManager.createEpic(new Epic("first epic", "workWork"));
//        taskManager.createSubtask(new Subtask("FirstSubtask", "many work", 3));
//        taskManager.createSubtask(new Subtask("SecondSubtask", "ForepicID3", 3));
//
//        taskManager.getTaskByID(1);
//        taskManager.getEpicByID(3);


//        taskManager.stingHistory();
        System.out.println(taskManager.getInMemoryHistoryManager().getHistory());
//        System.out.println(taskManager.commaSeparated.historyToString(taskManager.getInMemoryHistoryManager()));










    }


}
