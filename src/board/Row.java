package board;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Row {
    private ISpace[] spaces;
    public static final int NUM_SPACES = 10;

    public Row (JsonArray jsonArray, int rowIdx) {
        spaces = new ISpace[NUM_SPACES];
        for(int s = 0; s < jsonArray.size(); s++){
            SpaceFactory factory = new SpaceFactory();
            JsonObject spaceObj = jsonArray.get(s).getAsJsonObject();
            char c = spaceObj.get("type").getAsCharacter();
            //TODO implement if dude is already
            spaces[s] = factory.createSpace(c, rowIdx, s);
        }
    }

    public ISpace getSpace(int i) {
        return spaces[i];
    }
}
