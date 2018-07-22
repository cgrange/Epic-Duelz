package server;

import com.sun.net.httpserver.HttpServer;
import model.Model;

import java.io.*;
import java.net.InetSocketAddress;

public class Server {

    public Server(Model model) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);
//        server.createContext("/chooseDude", new ChooseDudeHandler());
//        server.createContext("/characters", new DisplayCharactersHandler());
        server.createContext("/placeDude", new PlaceDudeHandler(model));
        server.createContext("/addPlayer", new AddPlayerHandler(model));
        server.createContext("/selectSquad", new SelectSquadHandler(model));
        server.setExecutor(null); // creates a default executor
        server.start();
    }

//    static void enableCORS(HttpExchange he) throws IOException {
//        Headers responseHeaders = he.getResponseHeaders();
//        responseHeaders.add("Access-Control-Allow-Origin", "*");
//        if (he.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
//            responseHeaders.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
//            responseHeaders.add("Access-Control-Allow-Headers", "Content-Type");
//            he.sendResponseHeaders(204, -1);
//            return;
//        }
//    }
//
//    static JsonObject requestToJsonObject(InputStream inputStream) throws IOException {
//        InputStreamReader isr = new InputStreamReader(inputStream, UTF_8);
//        BufferedReader br = new BufferedReader(isr);
//        String requestJson = br.readLine();
//        JsonObject requestObject = new JsonParser().parse(requestJson).getAsJsonObject();
//        return requestObject;
//    }
//
//    static int getResponseLength(String response){
//        return response.getBytes(UTF_8).length;
//    }
//
//    static class ChooseDudeHandler implements HttpHandler {
//        @Override
//        public void handle(HttpExchange he) throws IOException {
//            enableCORS(he);
//            if (he.getRequestMethod().equalsIgnoreCase("POST")){
//                JsonObject requestObject = requestToJsonObject(he.getRequestBody());
//                String squadName = requestObject.get("name").getAsString();
//                JsonObject responseObj = new JsonObject();
//                responseObj.addProperty("selection", squadName);
//                String response = responseObj.toString();
//                he.getResponseHeaders().set("Content-Type","application/json");
//                he.sendResponseHeaders(200, getResponseLength(response));
//                OutputStream os = he.getResponseBody();
//                os.write(response.getBytes());
//                os.close();
//            }
//        }
//    }
//
//    static class DisplayCharactersHandler implements HttpHandler {
//        @Override
//        public void handle(HttpExchange he) throws IOException {
//            // parse request
//            Map<String, String> parameters = new HashMap<String, String>();
//            InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
//            BufferedReader br = new BufferedReader(isr);
//            String query = br.readLine();
//
//            // send response
//            Headers h = he.getResponseHeaders();
//            h.set("Content-Type","text/html");
//            he.sendResponseHeaders(200, 0);
//            OutputStream os = he.getResponseBody();
//            FileInputStream fs = new FileInputStream(new File("web/dudes.html"));
//            final byte[] buffer = new byte[100000];
//            int count = 0;
//            while ((count = fs.read(buffer)) >= 0) {
//                os.write(buffer,0,count);
//            }
//            fs.close();
//            os.close();
//        }
//    }

}
