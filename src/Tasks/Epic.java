package Tasks;

import Manager.Status;

import Manager.TypeTask;
import Tasks.Subtask;
import Tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    LocalDateTime endTime;


    public Epic(String name, String description) {
        super(name, description);
        this.typeTask = TypeTask.EPIC;

    }

    private ArrayList<Subtask> subtaskList = new ArrayList<>();

    public void addItemList(Subtask subtask){

        subtaskList.add(subtask);
        if(subtask.getDuration() != null){
            if(duration == null){
                duration = subtask.getDuration();
            }else {
                duration = duration.plus(subtask.getDuration());
            }
        }
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

    public void createStartTimeEpic(){
        if(subtaskList.isEmpty()){
           return;
        }
        startTime = subtaskList.get(0).getStartTime();
        for (Subtask subtask: subtaskList){
            if(subtask.getStartTime().isBefore(startTime)){
                startTime = subtask.getStartTime();
            }
        }
    }

    public void createEndTime(){
        if(subtaskList.isEmpty()){
            return;
        }
        endTime = subtaskList.get(0).getEndTime();
        for (Subtask subtask: subtaskList){
            if(subtask.getEndTime().isAfter(endTime)){
                endTime = subtask.getEndTime();
            }
        }
    }

    @Override
    public LocalDateTime getEndTime() {
        return this.endTime;
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
                }else setStatus(Status.IN_PROGRESS);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtaskList, epic.subtaskList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskList);
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
