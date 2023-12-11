package HttpServer;

import ExceptionByTask.ManagerSaveException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {

    private final String url;
    private final String apiToken;
    private HttpClient httpClient;

    public KVTaskClient(int port){
        url = "http://localhost" + port + "/";
        apiToken = register(url);
        httpClient = HttpClient.newHttpClient();
    }

    private String register(String url){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "register"))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() != 200) {
                throw new ManagerSaveException("Can't do save request, status code " + response.statusCode());
            }
            return response.body();
        }catch (IOException | InterruptedException exception){
            throw new ManagerSaveException("Can't do save request");
        }
    }

    public String load (String key) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "load/" + key + "?API_TOKEN=" + apiToken))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new ManagerSaveException("Can't do save request, status code " + response.statusCode());
            }
            return response.body();
        } catch (IOException | InterruptedException exception) {
            throw new ManagerSaveException("Can't do save request");
        }
    }

    public void save (String key, String value){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "save/" + key + "?API_TOKEN=" + apiToken))
                    .POST(HttpRequest.BodyPublishers.ofString(value))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new ManagerSaveException("Can't do save request, status code " + response.statusCode());
            }
        }catch (IOException | InterruptedException exception) {
            throw new ManagerSaveException("Can't do save request");
        }
    }
}
