package ExceptionByTask;



public class ManagerTaskFromString extends RuntimeException {

    public ManagerTaskFromString(){

    }
    public ManagerTaskFromString(String message) {
        super(message);
    }
}
