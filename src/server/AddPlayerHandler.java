package server;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import model.Model;
import model.exceptions.PlayerAlreadyExistsException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AddPlayerHandler extends HttpHandlerPlus {
    public AddPlayerHandler(Model model){
        super(model);
    }

    @Override
    protected void resolvePost(JsonObject requestObject, HttpExchange he) throws IOException {
        String name = requestObject.get("name").getAsString();
        int team = requestObject.get("team").getAsInt();
        List<String> cookieValues = he.getRequestHeaders().get("Cookie");
        if(cookieValues != null && cookieValues.contains("PLAYERNAME")){
            //doNothing
        }
        try {
            model.addPlayer(name, team);
        } catch(PlayerAlreadyExistsException e){
            he.getResponseHeaders().set("Content-Type","text/plain");
            String response = "That player name has already been chosen";
            he.sendResponseHeaders(409, getResponseLength(response));
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        List<String> newCookieValues = new ArrayList<String>();
        newCookieValues.add("PLAYERNAME=" + name);

        he.getResponseHeaders().set("Content-Type","application/json");
        he.getResponseHeaders().put("Set-Cookie", newCookieValues);
        he.sendResponseHeaders(200, 0);
        OutputStream os = he.getResponseBody();
        os.close();
    }

}
