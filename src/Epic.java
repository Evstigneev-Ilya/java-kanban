import java.util.ArrayList;

public class Epic extends Task {

    public Epic(String name, String description) {
        super(name, description);
    }

    private ArrayList<Subtask> epicList = new ArrayList<>();

    public void addItemList(Subtask subtask){
        epicList.add(subtask);
    }

    public void setEpicList(ArrayList<Subtask> epicList) {
        this.epicList = epicList;
    }

    public void clearEpicList(){
        epicList.clear();
    }

    public ArrayList<Subtask> getEpicList(){
        return epicList;
    }

    public void deleteItemList(Subtask subtask){
        if (epicList.contains(subtask)){
            epicList.remove(subtask);
        }
    }

    public void updateEpicStatus() {
        int i = 0;
        for (Subtask sub : epicList) {
            if (sub.getStatus() == Status.IN_PROGRESS) {
                setStatus(Status.IN_PROGRESS);
            } else if (sub.getStatus() == Status.DONE) {
                i = i + 1;
                if (epicList.size() == i) {
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
