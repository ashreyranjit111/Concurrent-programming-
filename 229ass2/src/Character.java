import java.awt.*;
import java.util.Map;
import java.util.Optional;
import java.util.List;

import bos.GamePiece;
import bos.RelativeMove;
import java.awt.*;
import java.util.Optional;

public abstract class Character implements GamePiece<Cell> {
    Optional<Color> display;
    Cell location;
    Behaviour behaviour;

    public Character(Cell location, Behaviour behaviour){
        this.location = location;
        this.display = Optional.empty();
        this.behaviour = behaviour;
    }

    public  void paint(Graphics g){
        if(display.isPresent()) {
            g.setColor(display.get());
            g.fillOval(location.x + location.width / 4, location.y + location.height / 4, location.width / 2, location.height / 2);
        }
    }

    public void setLocationOf(Cell loc){
        this.location = loc;
    }

    public Cell getLocationOf(){
        return this.location;
    }

    public void setBehaviour(Behaviour behaviour){
        this.behaviour = behaviour;
    }

    public RelativeMove aiMove(Stage stage){
        return behaviour.chooseMove(stage, this);
    }


}
