import java.awt.*;
import java.util.Random;

public class Cell extends Rectangle {

    private static Random rand = new Random();
    Color c;

    public Cell(int x, int y) {
        super(x, y, 35, 35);
        c = new Color(rand.nextInt(30), rand.nextInt(155)+100, rand.nextInt(30));
    }

    public void paint(Graphics g, Boolean highlighted) {
        g.setColor(c);
        g.fillRect(x, y, 35, 35);
        g.setColor(Color.BLACK);
        g.drawRect(x,y, 35, 35);
        if (highlighted) {
            g.setColor(Color.LIGHT_GRAY);
            for(int i = 0; i < 10; i++){
                g.drawRoundRect(x+1, y+1, 33, 33, i, i);
            }
        }
    }

    @Override
    public boolean contains(Point target){
        if (target == null)
            return false;
        return super.contains(target);
    }

    public int getGrassHeight(){
        return c.getGreen()/50;
    }
}
