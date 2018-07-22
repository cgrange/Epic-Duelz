package server;

import board.Board;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Model;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public abstract class HttpHandlerPlus implements HttpHandler {
    public static final Charset UTF_8 = StandardCharsets.UTF_8;
    protected Model model;

    public HttpHandlerPlus(Model model){
        this.model = model;
    }

    protected void enableCORS(HttpExchange he) throws IOException {
        Headers responseHeaders = he.getResponseHeaders();
        responseHeaders.add("Access-Control-Allow-Origin", "*");
        responseHeaders.add("Access-Control-Allow-Credentials", "true");
        if (he.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            responseHeaders.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            responseHeaders.add("Access-Control-Allow-Headers", "Content-Type");
            he.sendResponseHeaders(204, -1);
            return;
        }
    }

    protected JsonObject requestToJsonObject(InputStream inputStream) throws IOException {
        InputStreamReader isr = new InputStreamReader(inputStream, UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String requestJson = br.readLine();
        JsonObject requestObject = new JsonParser().parse(requestJson).getAsJsonObject();
        return requestObject;
    }

    protected int getResponseLength(String response){
        return response.getBytes(UTF_8).length;
    }

    protected abstract void resolvePost(JsonObject requestObject, HttpExchange he) throws IOException;

    @Override
    public void handle(HttpExchange he) throws IOException{
        enableCORS(he);
        if (he.getRequestMethod().equalsIgnoreCase("POST")) {
            JsonObject requestObject = requestToJsonObject(he.getRequestBody());
            resolvePost(requestObject, he);
        }
    }
}
