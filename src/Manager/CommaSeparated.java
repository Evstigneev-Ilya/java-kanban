package Manager;


import ExceptionByTask.*;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class CommaSeparated {


    private final static String DELIMETR = ",";

    public String taskToString(Task task){
        StringBuilder taskToString = new StringBuilder();
        taskToString.append(task.getId() + DELIMETR).append(task.getTypeTask() + DELIMETR).
                append(task.getName() + DELIMETR).append(task.getStatus() + DELIMETR).
                append(task.getName() + DELIMETR).append(task.getDescription() + DELIMETR);
        if(task.getTypeTask() == TypeTask.SUBTASK){
            taskToString.append(((Subtask)task).getEpicId() + DELIMETR);
        }

        return taskToString.toString();
    }

    public Task fromString(String value){
        Task task;
        String[] taskFromString = value.split(DELIMETR);

        switch (taskFromString[1]){
            case "TASK":
                task = new Task(taskFromString[2], taskFromString[4]);
                task.setId(Integer.parseInt(taskFromString[0]));
                task.setStatus(Status.valueOf(taskFromString[3]));

                break;
            case "EPIC":
                task = new Epic(taskFromString[2], taskFromString[4]);
                task.setId(Integer.parseInt(taskFromString[0]));
                task.setStatus(Status.valueOf(taskFromString[3]));
                break;
            case "SUBTASK":
                task = new Subtask(taskFromString[2], taskFromString[4], Integer.parseInt(taskFromString[6]));
                task.setId(Integer.parseInt(taskFromString[0]));
                task.setStatus(Status.valueOf(taskFromString[3]));
                break;
            default:
                throw new ManagerTaskFromString("Не получилось перевести задачу!");

        }
        return task;
    }


    static public String historyToString(HistoryManager historyManager){
        List<String> result = new ArrayList<>();
        for(Task task: historyManager.getHistory()){
            result.add(String.valueOf(task.getId()));
        }
        String str = String.join(DELIMETR,result);
        return str;
    }

    public List<Integer> historyFromString(String value){
        List<Integer> history = new ArrayList<>();
        String[] result = value.split(DELIMETR);
        for(String s: result){
            history.add(Integer.parseInt(s));
        }
        return history;
    }

    public String hendler(){
        return "id,type,name,status,description,epic";
    }


}
