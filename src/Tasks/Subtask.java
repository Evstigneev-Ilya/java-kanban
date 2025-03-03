package Tasks;

import Manager.TypeTask;
import Tasks.Task;

import java.util.Objects;

public class Subtask extends Task {

    private int epicId;

    public Subtask(String name, String description) {
        super(name, description);

        this.typeTask = TypeTask.SUBTASK;
    }
    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
        this.typeTask = TypeTask.SUBTASK;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                '}';
    }



}
