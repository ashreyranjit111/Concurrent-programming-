import java.awt.Color;
import java.awt.Graphics;
import java.util.Optional;

public class Block {
    Optional<Color> display;
    Cell location;

    public Cell getLocation()
    {
        return location;
    }

    public Block(Cell location)
    {
        this.location=location;
        this.display = Optional.of(new Color(190,69,10));
    }

    public void paint(Graphics g)
    {
        if(display.isPresent())
        {
            g.setColor(display.get());
            g.fillRect(location.x, location.y, 35, 35);
            g.setColor(Color.BLACK);
            g.drawRect(location.x, location.y, 35, 35);
        }
    }
}
