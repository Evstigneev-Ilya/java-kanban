package Tasks;

import Manager.Status;

import Manager.TypeTask;
import Tasks.Subtask;
import Tasks.Task;

import java.util.ArrayList;

public class Epic extends Task {


    public Epic(String name, String description) {
        super(name, description);
        this.typeTask = TypeTask.EPIC;

    }

    private ArrayList<Subtask> subtaskList = new ArrayList<>();

    public void addItemList(Subtask subtask){
        subtaskList.add(subtask);
    }

    public void setSubtaskList(ArrayList<Subtask> subtaskList) {
        this.subtaskList = subtaskList;
    }

    public void clearEpicList(){
        subtaskList.clear();
    }

    public ArrayList<Subtask> getSubtaskList(){
        return subtaskList;
    }

    public void deleteItemList(Subtask subtask){
        if (subtaskList.contains(subtask)){
            subtaskList.remove(subtask);
        }
    }

    public void updateEpicStatus() {
        int i = 0;
        for (Subtask sub : subtaskList) {
            if (sub.getStatus() == Status.IN_PROGRESS) {
                setStatus(Status.IN_PROGRESS);
            } else if (sub.getStatus() == Status.DONE) {
                i = i + 1;
                if (subtaskList.size() == i) {
                    setStatus(Status.DONE);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Epic{"  +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                '}';
    }

}
