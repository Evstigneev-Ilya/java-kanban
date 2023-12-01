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
                append(task.getDescription() + DELIMETR);
        if(task.getStartTime() != null){
            taskToString.append(task.getStartTime().format(task.formatter) + DELIMETR);
        }else taskToString.append(" " + DELIMETR);
        if(task.getDuration() != null){
            taskToString.append(task.getDuration().toMinutes() + DELIMETR);
        }else taskToString.append(" " + DELIMETR);
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
                System.out.println(taskFromString[5]);
                if(!taskFromString[5].isBlank()){
                    task.setStartTime(taskFromString[5]);
                }
                if(!taskFromString[6].isBlank()){
                    task.setDuration(Integer.parseInt(taskFromString[6]));
                }
                break;
            case "EPIC":
                task = new Epic(taskFromString[2], taskFromString[4]);
                task.setId(Integer.parseInt(taskFromString[0]));
                task.setStatus(Status.valueOf(taskFromString[3]));
                break;
            case "SUBTASK":
                task = new Subtask(taskFromString[2], taskFromString[4], Integer.parseInt(taskFromString[7]));
                task.setId(Integer.parseInt(taskFromString[0]));
                task.setStatus(Status.valueOf(taskFromString[3]));
                if(!taskFromString[5].isBlank()){
                task.setStartTime(taskFromString[5]);}
                if(!taskFromString[6].isBlank()){
                task.setDuration(Integer.parseInt(taskFromString[6]));}
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
        return "id,type,name,status,description,startTime,duration,epic";
    }


}
