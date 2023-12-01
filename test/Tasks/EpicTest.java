package Tasks;

import Manager.Managers;
import Manager.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    Status status;
    Epic epic;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @BeforeEach
    void createEpic(){
        epic = new Epic("Epic1", "DiscEpic");

        Subtask subtask1 = new Subtask("subtask1", "DiscSubtask1");
        Subtask subtask2 = new Subtask("subtask2", "DiscSubtask2");
        Subtask subtask3 = new Subtask("subtask3", "DiscSubtask3");
        subtask1.setId(2);
        subtask2.setId(3);
        subtask3.setId(4);
        subtask1.setStartTime("01.11.1992 12:14");
        subtask1.setDuration(20);
        subtask2.setStartTime("02.11.1992 13:15");
        subtask2.setDuration(10);
        subtask3.setStartTime("03.11.1992 14:00");
        subtask3.setDuration(30);
        epic.addItemList(subtask1);
        epic.addItemList(subtask2);
        epic.addItemList(subtask3);

    }

    @Test
    void EmptyListSubtask(){
        epic = new Epic("Epic2", "DiscEpic2");
        assertTrue(epic.getSubtaskList().isEmpty(), "Список не пуст");
    }

    @Test
    void StatusEpicNew(){
        assertFalse(epic.getSubtaskList().isEmpty(), "Список подзадач пуст");
        assertEquals(Status.NEW, epic.getStatus(), "Статус не New");
    }

    @Test
    void statusEpicDone(){
        for(Subtask subtask: epic.getSubtaskList()){
            subtask.setStatus(Status.DONE);
        }
        epic.updateEpicStatus();
        assertEquals(Status.DONE, epic.getStatus(), "Статус не поменялся");
    }

    @Test
    void statusNewDone(){
        Subtask subtask4 = new Subtask("subtask4", "DiscTask4", 1);
        subtask4.setStatus(Status.IN_PROGRESS);
        epic.addItemList(subtask4);
        epic.updateEpicStatus();
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус не прогресс");
    }

    @Test
    void statusInProgress(){
        for(Subtask subtask: epic.getSubtaskList()){
            subtask.setStatus(Status.IN_PROGRESS);
        }
        epic.updateEpicStatus();
        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус не прогресс");
    }

    @Test
    void createStartTimeEpic(){
        epic.createStartTimeEpic();
        assertEquals("01.11.1992 12:14", epic.getStartTime().format(dateTimeFormatter),"Не совпала дата старта");
    }

    @Test
    void createDurationEpic(){
        assertEquals(60, epic.getDuration().toMinutes(), "Не совпал duration");

    }

    @Test
    void createEndTimeEpic(){
        epic.createEndTime();
        assertEquals("03.11.1992 14:30", epic.getEndTime().format(dateTimeFormatter), "не совпал конец епика");
    }

}