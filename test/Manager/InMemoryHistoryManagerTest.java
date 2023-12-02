package Manager;

import Tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    HistoryManager historyManager;

    @BeforeEach
    void makeHistoryManager() {
        historyManager = Managers.getDefaultHistory();
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        task1.setId(1);
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        task2.setId(2);
        Task task3 = new Task("Задача 3", "Описание задачи 3");
        task3.setId(3);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
    }

    @Test
    void add() {
        Task task4 = new Task("Задача 4", "Описание задачи 4");
        task4.setId(4);
        int expectedSize = 4;
        historyManager.add(task4);
        historyManager.add(task4);
        assertEquals(expectedSize, historyManager.getHistory().size(), "Дублирование!");
    }

    @Test
    void getEmptyHistory() {
        historyManager = Managers.getDefaultHistory();
        assertTrue(historyManager.getHistory().isEmpty(), "История не пуста!");
    }

    @Test
    void removeFirstNode() {
        int sizeList = historyManager.getHistory().size();
        historyManager.remove(1);
        assertNotEquals(1, historyManager.getHistory().get(0), "Не удалилось из начала");
        assertNotNull(historyManager.getHistory().get(0), "Не удалилось из начала");
        assertNotEquals(sizeList, historyManager.getHistory().size(), "Не удалилось из начала");
    }

    @Test
    void removeLastNode() {
        int sizeList = historyManager.getHistory().size();
        historyManager.remove(3);
        assertNotEquals(1, historyManager.getHistory().get(sizeList - 1), "Не удалилось из конца");
        assertNotNull(historyManager.getHistory().get(0), "Не удалилось из конца");
        assertNotEquals(sizeList - 1, historyManager.getHistory().size(), "Не удалилось из конца");
    }

    @Test
    void removeMiddleNode() {
        int sizeList = historyManager.getHistory().size();
        Task taskRemove = historyManager.getHistory().get(1);
        historyManager.remove(2);
        assertNotEquals(sizeList, historyManager.getHistory().size(), "Не удалилось из середины");
        for (Task taskNode : historyManager.getHistory()) {
            assertNotEquals(taskRemove, taskNode, "Не удалилось из середины");
        }
    }
}