import bos.GameBoard;

import java.awt.*;
import java.util.List;

public class Player implements KeyObserver {

    public Cell location;
    private Boolean inMove;

    private List<Cell> blocksLoc;

    public Player(Cell location, List<Cell> blocksLoc){
        this.location = location;
        inMove = false;
        this.blocksLoc = blocksLoc;
    }

    public void paint(Graphics g){
        g.setColor(Color.ORANGE);
        g.fillOval(location.x + location.width / 4, location.y + location.height / 4, location.width / 2, location.height / 2);
    }

    public void startMove(){
        inMove = true;
    }

    public Boolean inMove(){
        return inMove;
    }

    public Cell getLocation() {
        return this.location;
    }

    public void setLocation(Cell location) {
        this.location = location;
    }

    public void notify(char c, GameBoard<Cell> gb) {
        if (inMove) {
            if (c == 'a' || c == 'd' || c == 'w' || c == 's') {
                Cell OriginalLoc = location;
                if (c == 'a') {
                    location = gb.leftOf(location).orElse(location);
                    //inMove = false;
                } else if (c == 'd') {
                    location = gb.rightOf(location).orElse(location);
                    //inMove = false;
                } else if (c == 'w') {
                    location = gb.above(location).orElse(location);
                    // inMove = false;
                } else if (c == 's') {
                    location = gb.below(location).orElse(location);
                    // inMove = false;
                }
                checkBlocks(OriginalLoc);
                inMove = false;
            }
        }
    }
    private void checkBlocks(Cell OriginalLoc)
    {
        for(Cell block : blocksLoc){
            if(location.getLocation().equals(block.getLocation())){
                location=OriginalLoc;
                break;
            }
        }
    }
}
