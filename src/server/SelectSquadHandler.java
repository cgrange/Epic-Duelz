package server;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import model.Model;

import java.io.IOException;

public class SelectSquadHandler extends HttpHandlerPlus {

    public SelectSquadHandler(Model model){
        super(model);
    }

    @Override
    protected void resolvePost(JsonObject requestObject, HttpExchange he) throws IOException {
        String squadName = requestObject.get("squadName").getAsString();
        model.addSquad("null", squadName);
    }
}
