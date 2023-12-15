package HttpServer;

import Manager.Managers;
import Manager.TaskManager;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {
    public static final int PORT = 8080;
    private final HttpServer server;
    private final Gson gson;
    private final TaskManager taskManager;

    public HttpTaskServer() throws IOException {
        this(Managers.getDefaultTaskManager());
    }

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        gson = Managers.getGson();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", this::handler);
    }

    private void handler(HttpExchange httpExchange) {
        try {
            System.out.println("Processing /task end-point " + httpExchange.getRequestURI());
            String path = httpExchange.getRequestURI().getPath().replaceFirst("/tasks/", "");
            String method = httpExchange.getRequestMethod();
            String response;

            switch (method){
                case "GET":{
                    if(Pattern.matches("^task/$", path)){
                        response = gson.toJson(taskManager.getAllTasks());
                        sendText(httpExchange, response);
                    }else if(Pattern.matches("^epic/$", path)){
                        response = gson.toJson(taskManager.getAllEpic());
                        sendText(httpExchange, response);
                    }else if (Pattern.matches("^substack/$", path)) {
                        response = gson.toJson(taskManager.getAllSubtasks());
                        sendText(httpExchange, response);
                    } else if (Pattern.matches("^task/?id=\\d$", path)) {
                        path = path.replaceFirst("^task/?id=", "");
                        int id = parseInteger(path);
                        if(id!= -1){
                            response = gson.toJson(taskManager.getTaskByID(id));
                            sendText(httpExchange, response);
                        }else {
                            System.out.println("Неверный id " + id);
                            httpExchange.sendResponseHeaders(405, 0);
                        }
                    }else if (Pattern.matches("^epic/?id=\\d$", path)) {
                        path = path.replaceFirst("epic/?id=", "");
                        int id = parseInteger(path);
                        if (id != -1) {
                            response = gson.toJson(taskManager.getEpicByID(id));
                            sendText(httpExchange, response);
                        } else {
                            System.out.println("Неверный id " + id);
                            httpExchange.sendResponseHeaders(405, 0);
                        }
                    }
                    else if (Pattern.matches("^subtask/?id=\\d$", path)) {
                        path = path.replaceFirst("subtask/?id=", "");
                        int id = parseInteger(path);
                        if (id != -1) {
                            response = gson.toJson(taskManager.getSubTaskById(id));
                            sendText(httpExchange, response);
                        } else {
                            System.out.println("Неверный id " + id);
                            httpExchange.sendResponseHeaders(405, 0);
                        }
                    } else if (Pattern.matches("^substask/epic/?id=\\d$", path)) {
                        path = path.replaceFirst("subtask/?id=", "");
                        int id = parseInteger(path);
                        if(id != -1){
                            response = gson.toJson(taskManager.getListOfAllSubtasks(id));
                            sendText(httpExchange, response);
                        }else {
                            System.out.println("Неверный id " + id);
                            httpExchange.sendResponseHeaders(405, 0);
                        }
                    } else if (Pattern.matches("^history/$", path)) {
                        response = gson.toJson(taskManager.getHistory());
                        sendText(httpExchange, response);
                    }else if(path.equals("")){
                        response = gson.toJson(taskManager.getPrioritizedTasks());
                        sendText(httpExchange, response);
                    }else {
                        System.out.println(" Введен неправльный GET запрос");
                        httpExchange.sendResponseHeaders(405, 0);
                    }
                }
                case "POST":{
                    String respone = readText(httpExchange);
                    Task task = gson.fromJson(respone, Task.class);
                    if(Pattern.matches("^task/$", path)){
                        if(!taskManager.existTask(task)){
                            taskManager.saveTask(task);
                            System.out.println("Задача сохранена");
                            httpExchange.sendResponseHeaders(200,0);
                        }else {
                            taskManager.updateTask(task);
                            System.out.println("Задача обновлена");
                            httpExchange.sendResponseHeaders(200,0);
                        }
                    } else if (Pattern.matches("^epic/$", path)) {
                        if(!taskManager.existTask(task)){
                            taskManager.saveEpic((Epic) task);
                            System.out.println("Epic сохранен");
                            httpExchange.sendResponseHeaders(200,0);
                        }else {
                            taskManager.updateEpic((Epic) task);
                            System.out.println("Epic обновлен");
                            httpExchange.sendResponseHeaders(200,0);
                        }
                    } else if (Pattern.matches("^subtask/$", path)) {
                        if(!taskManager.existTask(task)){
                            Subtask sub = (Subtask) task;
                            Epic epic = taskManager.getEpicByID(sub.getEpicId());
                            taskManager.saveSubtask(sub, epic);
                            System.out.println("Subtask сохранен");
                            httpExchange.sendResponseHeaders(200,0);
                        }else {
                            taskManager.updateSubtask((Subtask) task);
                            System.out.println("Subtask обновлен");
                            httpExchange.sendResponseHeaders(200,0);
                        }
                    }else{
                        System.out.println("Введен неправильный POST");
                        httpExchange.sendResponseHeaders(405, 0);
                    }
                }
                case "DELETE":{
                    if(Pattern.matches("^task/$", path)){
                        taskManager.deleteAllTasks();
                        httpExchange.sendResponseHeaders(200, 0);
                    }else if(Pattern.matches("^epic/$", path)){
                        taskManager.deleteAllEpic();
                        httpExchange.sendResponseHeaders(200, 0);
                    }else if (Pattern.matches("^substack/$", path)) {
                        taskManager.deleteAllSubtask();
                        httpExchange.sendResponseHeaders(200, 0);
                    } else if (Pattern.matches("^task/?id=\\d$", path)) {
                        path = path.replaceFirst("^task/?id=", "");
                        int id = parseInteger(path);
                        if(id!= -1){
                            taskManager.deleteByIDTask(id);
                            httpExchange.sendResponseHeaders(200, 0);
                        }else {
                            System.out.println("Неверный id " + id);
                            httpExchange.sendResponseHeaders(405, 0);
                        }
                    }else if (Pattern.matches("^epic/?id=\\d$", path)) {
                        path = path.replaceFirst("epic/?id=", "");
                        int id = parseInteger(path);
                        if (id != -1) {
                            taskManager.deleteByIDEpic(id);
                            httpExchange.sendResponseHeaders(200, 0);
                        } else {
                            System.out.println("Неверный id " + id);
                            httpExchange.sendResponseHeaders(405, 0);
                        }
                    }
                    else if (Pattern.matches("^subtask/?id=\\d$", path)) {
                        path = path.replaceFirst("subtask/?id=", "");
                        int id = parseInteger(path);
                        if (id != -1) {
                            taskManager.deleteByIDSubtask(id);
                            httpExchange.sendResponseHeaders(200, 0);
                        } else {
                            System.out.println("Неверный id " + id);
                            httpExchange.sendResponseHeaders(405, 0);
                        }
                    }else {
                        System.out.println("неверный DELETE запрос");
                        httpExchange.sendResponseHeaders(405, 0);
                    }

                }
                default:{
                    System.out.println("Неверный запрос " + httpExchange.getRequestMethod());
                    httpExchange.sendResponseHeaders(405, 0);
                }
            }

        }catch (Exception exception){
            exception.printStackTrace();
        }finally {
            httpExchange.close();
        }
    }

    public int parseInteger(String str){
        try {
            int id = Integer.parseInt(str);
            return id;
        }catch (NumberFormatException numberFormatException){
            return -1;
        }
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        server.start();
    }

    public  void stop(){
        System.out.println("Сервер остановлен");
        server.stop(0);
    }

    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }
}
