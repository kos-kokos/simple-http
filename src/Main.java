import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/requests", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String request = new BufferedReader(new InputStreamReader(t.getRequestBody(),Charset.forName("UTF-8"))).readLine();
            String response = "Отвечаю на опрос "+request;
            byte[] bytes = response.getBytes(Charset.forName("UTF-8"));
            t.sendResponseHeaders(200, bytes.length);
            System.out.println(response);
            OutputStream os = t.getResponseBody();

            os.write(bytes);
            os.close();
        }
    }
}