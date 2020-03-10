import bos.GameBoard;

public class careTaker implements KeyObserver {

   Stage s;
   Memento m;

   public careTaker(Stage stage){
       this.s=stage;
   }

    @Override
    public void notify(char c, GameBoard<Cell> gb) {
        if (c == ' ') {

            this.m = s.saveMemento();
            System.out.println("Memento Saved");
        }
        if(m!=null){
            if (c == 'r') {
                s.setMemento(m);
                System.out.println("Memento restored");
            }
        }

    }
}
