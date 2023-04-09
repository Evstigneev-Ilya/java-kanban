import java.util.ArrayList;

public class Epic extends Task {

    public Epic(String name, String description) {
        super(name, description);
    }

    ArrayList<Subtask> epicList = new ArrayList<>();

    public void updateEpicStatus() {
        int i = 0;
        for (Subtask sub : epicList) {
            if (sub.getStatus() == "IN_PROGRESS") {
                setStatus("IN_PROGRESS");
            } else if (sub.getStatus() == "DONE") {
                i = i + 1;
                if (epicList.size() == i) {
                    setStatus("DONE");
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
