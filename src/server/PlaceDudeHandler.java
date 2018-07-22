package server;

import board.Board;
import board.exceptions.CannotOccupySpaceException;
import board.exceptions.SpaceOccupiedException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import model.Model;
import player.Dude;

import java.io.IOException;
import java.io.OutputStream;

public class PlaceDudeHandler extends HttpHandlerPlus {
    public PlaceDudeHandler(Model model){
        super(model);
    }

    @Override
    protected void resolvePost(JsonObject requestObject, HttpExchange he) throws IOException {
        String dudeName = requestObject.get("dude").getAsString();
        int rowIdx = requestObject.get("rowIdx").getAsInt();
        int spaceIdx = requestObject.get("spaceIdx").getAsInt();
        try {
            model.placeDude(dudeName, rowIdx, spaceIdx);
        } catch (CannotOccupySpaceException e) {
            String response = "the space you selected cannot be occupied";
            he.getResponseHeaders().set("Content-Type","text/plain");
            he.sendResponseHeaders(403, getResponseLength(response));
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (SpaceOccupiedException e) {
            String response = "the space you selected is already occupied";
            he.getResponseHeaders().set("Content-Type","text/plain");
            he.sendResponseHeaders(409, getResponseLength(response));
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        JsonArray responseObj = model.getBoard().toJson();
        String response = responseObj.toString();
        he.getResponseHeaders().set("Content-Type","application/json");
        he.sendResponseHeaders(200, getResponseLength(response));
        OutputStream os = he.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
