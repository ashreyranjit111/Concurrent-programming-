public class Memento {

    private Cell sheep;
    private Cell shepherd;
    private Cell wolf;
    private Cell player;

    Memento(Cell s, Cell shep, Cell w, Cell p){
        this.sheep=s;
        this.shepherd=shep;
        this.wolf = w;
        this.player = p;
    }

    public Cell getSheep(){
        return sheep;
    }

    public Cell getShepherd(){
        return shepherd;
    }

    public Cell getWolf(){
        return wolf;
    }

    public Cell getPlayer(){
        return player;
    }
}