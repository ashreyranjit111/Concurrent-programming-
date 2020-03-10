import java.awt.*;
import java.awt.event.MouseMotionAdapter;
import java.util.*;
import java.time.*;
import java.util.List;

import bos.GameBoard;
import bos.RelativeMove;

public class Stage extends KeyObservable {
    protected Grid grid;
    protected Character sheep;
    protected Character shepherd;
    protected Character wolf;
    protected Block block;
    private List<Character> allCharacters;
    protected Player player;

    protected List<Cell> blocksLoc;

    private Instant timeOfLastMove = Instant.now();

    public Memento saveMemento() {
        return new Memento(sheep.getLocationOf(), shepherd.getLocationOf(),wolf.getLocationOf(),player.getLocation());
    }

    public void setMemento(Memento m){
        sheep.setLocationOf(m.getSheep());
        shepherd.setLocationOf(m.getShepherd());
        wolf.setLocationOf(m.getWolf());
        player.setLocation(m.getPlayer());

    }

    public Stage() {
        SAWReader sr = new SAWReader("data/stage1.saw");
        grid = new Grid(10, 10);
        shepherd = new Shepherd(grid.cellAtRowCol(sr.getShepherdLoc().first, sr.getShepherdLoc().second), new StandStill());
        sheep = new Sheep(grid.cellAtRowCol(sr.getSheepLoc().first, sr.getSheepLoc().second), new MoveTowards(shepherd));
        wolf = new Wolf(grid.cellAtRowCol(sr.getWolfLoc().first, sr.getWolfLoc().second), new MoveTowards(sheep));
        initialBlocks(sr);
        grid.setBlocksLoc(blocksLoc);

        player = new Player(grid.getRandomCell(), blocksLoc);
        this.register(player);


        allCharacters = new ArrayList<Character>();
        allCharacters.add(sheep);
        allCharacters.add(shepherd);
        allCharacters.add(wolf);
    }
    public void initialBlocks(SAWReader sr){
        blocksLoc = new ArrayList<Cell>();

        List<bos.Pair<Integer,Integer>> tempBlocksLoc = sr.getBlocksLoc();

        for (bos.Pair<Integer, Integer> pair: tempBlocksLoc){
            blocksLoc.add(grid.cellAtRowCol(pair.first, pair.second));
        }
    }


    public void update(){

            if (!player.inMove()) {
                if (sheep.location == shepherd.location) {
                    System.out.println("The sheep is safe :)");
                    System.exit(0);
                } else if (sheep.location == wolf.location) {
                    System.out.println("The sheep is dead :(");
                    System.exit(1);
                } else {
                    if (sheep.location.x == sheep.location.y) {
                        sheep.setBehaviour(new StandStill());
                        shepherd.setBehaviour(new MoveTowards(sheep));
                    }
                    allCharacters.forEach((c) -> c.aiMove(this).perform());
                    player.startMove();
                    timeOfLastMove = Instant.now();
                }
            }

        }


        public void paint (Graphics g, Point mouseLocation){

            grid.paint(g, mouseLocation);

            sheep.paint(g);
            for (Cell bLoc : blocksLoc){
                Block block = new Block(bLoc);
                block.paint(g);
            }
            shepherd.paint(g);
            wolf.paint(g);

            player.paint(g);




        }
    }

