import board.Board;
import model.Model;
import server.Server;

import java.io.IOException;

public class Ignition {
    public static void main(String[] args) throws IOException {
        Model model = new Model();
        Server server = new Server(model);
    }
}
