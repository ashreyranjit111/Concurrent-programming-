import bos.NoMove;
import bos.RelativeMove;

public class StandStill implements Behaviour {
    @Override
    public RelativeMove chooseMove(Stage stage, Character mover) {
        return new NoMove(stage.grid, mover);
    }
}
