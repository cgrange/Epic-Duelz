package board;

public class SpaceFactory {
    public ISpace createSpace(char c, int rowIdx, int spaceIdx){
        ISpace space = null;
        if(c == 'w'){
            space = new WalkableSpace(rowIdx, spaceIdx);
        } else if(c == 'o'){
            space = new ObstacleSpace(rowIdx, spaceIdx);
        } else if(c == 'd'){
            space = new DropoffSpace(rowIdx, spaceIdx);
        }
        return space;
    }
}
