# Task 0

Clone this repository (well done!)

# Task 1

Take a look at the two repositories:

  * (A) https://bitbucket.org/altmattr/personalised-correlation/src/master/
  * (B) https://github.com/Whiley/WhileyCompiler

And answer the following questions about them:

  * These repositories are at two different websites - github and bitbucket - what are these sites?  What service do they provide? Which is better?
  * Who made the last commit to repository A?
  * Who made the first commit to repository A?
  * Who made the first and last commits to repository B?
  * Are either/both of these projects active at the moment?  🤔 If not, what do you think happened?
  * 🤔 Which file in each project has had the most activity?

# Task 2

Setup a new IntelliJ project with a main method that will print the following message to the console when run:

~~~~~
Sheep and Wolves
~~~~~

# Task 3

Draw a 20 by 20 grid on a 1280x720 window. Have the grid take up the 720x720 square on the left of the window.  Each cell in the grid should be 35 pixels high and wide and the grid should be drawn 10 pixels off the top and left borders of the screen.  To do this, you should use the `Graphics` class from the Java libraries.  Be sure to consult the tips page for this task (it is a link in iLearn).  Without it, you will be very confused.

# ☆ Task 4

Create a 2D array to represent the grid and connect the drawn grid to the array in some way.

# Task 5

Modify your program so that mousing over a cell will "highlight" it.  Highlighted cells should be drawn in grey.

# Task 6

Ensure your program, if it does not already, has a `Cell` class and that your grid array is an array of `Cell` objects.  It should still display as before.  What are reasonable methods and fields for the `Cell` class?  Now create a `Grid` class to subsume your 2D array of `Cell`s.  What fields and methods should this class have?

# Task 7

Our `Cell` class is really a specialised rectangle and the Java API already has a `Rectangle` class.  Have `Cell` inherit from `java.awt.Rectangle` (https://docs.oracle.com/javase/8/docs/api/java/awt/Rectangle.html).  It will be good to call `super` in the `Cell` constructor and to use the `contains` method that comes in `Rectangle` instead of your own.  NB:  The `contains` we wrote was graceful when given a `null` pointer for the point, the one from `Rectangle` is not, you will need to "protect" it in some way.

# Task 8

Define a `Stage` class that can contain one `Grid` object and many `Character` objects.  There must be three separate characters, each a subclass of a `Character` _interface_ and each must have its own `paint` method.  The `paint` method must take a `Graphics` parameter and draw the character on that graphic.  Have the `paint` method specified in the `Character` interface and have each subclass define it.

Since `Character`s are drawing themselves, they need to know where they are on the screen so each will have a `Cell` field (that is set in the constructor) indicating where on the grid they are.

Have the program start with 1 grid and 3 characters:

  * Sheep (drawn white)
  * Wolf (drawn red)
  * Shepherd (drawn green)

# Task 9

Have a close look at your `Shepherd`, `Sheep` and `Wolf` classes.  If they are anything like mine they are _all the same except for the colour they use_.  This repetition is "a bad thing" because if the same thing is done in three different places, we need to remember that updating one requires us to update all three.

Is there a place that you could put all the common parts?

🤔 Will this work given what you currently have?  If not, what would we need to change?

# Task 10

Draw a picture of the inheritance hierarchy you have created.  You should (loosely) use [UML notation](http://www.csci.csusb.edu/dick/cs201/uml.html) for your diagram.  You are using UML In this case, and all through this course, only for "a rough sketch of an idea".

# Task 11

Start this task from the solution to Task 10.  The abstract `Character` class we were left with had to pick a default colour.  This was an entirely arbitrary choice.  Whenever you see arbitrary default values, you are seeing bad code.  But don't worry, Java 8 has us covered.  Java 8 introduced `Optional` values so that instead of arbitrary choices (or worse - `null`!) you can have an empty value.  [Read up on `Optional` values (just read until "Default Values and Actions")](http://www.oracle.com/technetwork/articles/java/java8-optional-2175753.html) and then change the `display` colour in the `Character` class to be an `Optional<Color>` instead of a `Color`.  You will need to make changes in the subclasses as well to support this.

🤔 Does the `Character` class even _need_ to be abstract?  Why or why not?

Your company builds a lot of games like this one and you now have to incorporate your work with the company's game processing code.  We have included a jar of this code in the `\lib` directory and javadoc explaining that code in the `\doc` directory.  In there you will see a `GameBoard` interface that represents things that a game might be played on, like a Chess board or the grid you have created.  You will also see an `GamePiece` interface that represents things that can move around such a game board, like a Chess piece or one of your characters.

## Task 12 - Step One

Have `Grid` implement `GameBoard` and have `Character` implement `GamePiece`.

## Task 12 - Step Two

With that done, you are now able to make use of the `RelativeMove`s provided by the library.  You should add functionality to play a set list of moves automatically.  I.e. when the game starts, it will play some hard-coded moves.  To do this you will need a list of moves to play, something like

~~~~~
private java.util.List<bos.RelativeMove> moves;
~~~~~

in your `Stage` class.

You will also need to have the `paint` method (that runs over and over again) make a move whenever a certain amount of time is up (say 2 seconds).

Note, we are now doing some processing between painting so we should move from the

~~~~~
loop forever {
  paint
}
~~~~~

paint loop to an update-and-paint loop like

~~~~~
loop forever {
  update game state
  paint
}
~~~~~

Something like the following `update` method in `Stage` (assuming you have a `timeOfLastMove` field in `Stage`) will work.

~~~~~
    public void update() {
        if (moves.size() > 0 && timeOfLastMove.plus(Duration.ofSeconds(2)).isBefore(Instant.now())){
            timeOfLastMove = Instant.now();
            moves.remove(0).perform();
        } else if (moves.size() == 0  && timeOfLastMove.plus(Duration.ofSeconds(20)).isBefore(Instant.now())) {
            System.exit(0);
        }
~~~~~

Fill your `moves` object with example moves and see if you can get your program to automatically play the moves you entered.

# Task 13

Currently, the game loop (in `Main.run`) is running as fast as it can.  We fixed the rate of movement at 2-second intervals, but the frame is still painted as quickly as possible.  This just burns CPU cycles and heats up your computer needlessly.  Your task is to "fix" the frame-rate so we are not pointlessly burning CPU power. You can do this by asking the current thread to sleep for a period of time using `Thread.sleep`. We want the frame-rate to be about 50 frames per second, that means we need the loop to take 20ms to complete.

Sleeping a thread throws an `InterruptedException` so you will need to catch that. In fact, we don't care about the thread being interrupted so the catch block should just report the fact it was interrupted, print out a representation (via `toString`) of the exception that was thrown, and continue on as normal.

# Task 13a

Our task now is to add the ability to read in configuration data from a file.  Someone else at the company (person A) has tried and come up with the following.

A file is kept in a "data" folder called "name.saw". That file has one line for each configuration item.  We begin with just the character locations (given as row and column).

~~~~~
sheep: (2,0)
wolf: (3,5)
shepherd: (3,6)
~~~~~

Person A tried to write code to read this file

~~~~~
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SAWReader {
    List<String> contents;

    public SAWReader(String filename) {
      contents = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(filename));
    }

    public bos.Pair<Integer, Integer> getSheepLoc(){
        for (String s: contents){
            Pattern p = Pattern.compile("sheep:\\s*\\((\\d*),\\s*(\\d)\\)");
            Matcher m = p.matcher(s);
            if(m.matches()){
                return new bos.Pair( Integer.parseInt(m.group(1).trim())
                                   , Integer.parseInt(m.group(2).trim()));
            }
        }
        return new bos.Pair(0,0);
    }
}

~~~~~
but is getting the following error

~~~~~
Error:(11, 56) java: unreported exception java.io.IOException; must be caught or declared to be thrown
~~~~~

Can you help them out?  What are they doing wrong?  What is the right solution?  Once you have done that, write the `getWolfLoc` and `getShepherdLoc` methods and see if you can incorporate this code into your game to load the character starting positions from a file.

🤔 There is some interesting code in here, what is a `Pattern` and how is it helping with reading the file?

# Task 14

Add the following method to the `Grid` class

~~~~~
    /**
     * Takes a cell consumer (i.e. a function that has a single `Cell` argument and
     * returns `void`) and applies that consumer to each cell in the grid.
     * @param func The `Cell` to `void` function to apply at each spot.
     */
    public void doToEachCell(Consumer<Cell> func){
      // Your job to add the body
    }
~~~~~

 Notice that the method accepts a `Consumer` functional interface.

 Now use this method to turn the `paint` method of the `Grid` class into a single line of code.  I.e. remove the double-nested loop and replace it with a call to `doToEachCell`.

# Task 15

Add two more methods to the `Grid` class

~~~~~
    /** Takes a cell predicate (i.e. a function that has a single `Cell` argument and
     * returns a `boolean` result) and applies that predicate to each cell, returning
     * the first cell it finds for which the predicate is true.
     * @param predicate The `Cell` to `boolean` function to test with
     * @return The first cell (searching row by row, left to right) that is true for the predicate.  Returns `null` if no such cell found.
     */
    public Pair<Integer, Integer> findAmongstCells(Predicate<Cell> predicate){
        // Your job to fill in the body
    }

    /** Takes a cell predicate (i.e. a function that has a single `Cell` argument and
     * returns a `result` and applies that predicate to each cell, returning
     * the first cell it finds for which the predicate is true.  Returns an optional that is full
     * if such a cell is found and an empty optional if there is no such cell.
     * @param predicate The `Cell` to `boolean` function to test with
     * @return The first cell (searching row by row, left to right) that is true for the predicate.  Returns an empty optional if no cell found.
     */
    public Optional<Pair<Integer, Integer>> safeFindAmongstCells(Predicate<Cell> predicate){
        // Your job to fill in the body

    }
~~~~~

Notice that these two accept an argument that is a `Predicate` functional interface, a function that takes something and returns `true` or `false`.  It should be fairly clear how to use `findAmongstCells` in your methods implementing `GameBoard`.  Make that change.

🤔 What would you think if I told you that you can use `safeFindAmongstCell` to turn each of those methods into a single statement?

Here is the solution for `below`:

~~~~~
    @Override
    public Optional<Cell> below(Cell relativeTo) {
        return safeFindAmongstCells((c) -> c == relativeTo).filter((p) -> p.first < 19              )
                                                           .map   ((p) -> cells[p.first+1][p.second]);
    }
~~~~~

Look up the `filter` and `map` methods of the `Optional` class and explain how this implementation of `below` works.

Modify the three similar methods (`above`, `leftOf`, and `rightOf`) to all use the same approach.

Do you like these versions better?  Why or why not?

# Task 16

It is time to start allowing characters in the game to make their own moves.  We call these "AI moves" because we are adding (simple) artificial intelligence to the non-shepherd characters.  There are quite a few changes required to make this work.  Firstly, we will need to use the latest version of the `bos` library.  It supports more types of moves (adding a random move and a non-move) and it requires game boards to be able to compute a path from one place to another.  The `bos.jar` file in this commit has all the additions, copy it into your `lib` folder.  Immediately your `Grid` will have unimplemented methods because the new `bos.GameBoard` has the extra abstract `movesBetween` method.  Thus your first job is to implement this method in your `Grid` class.

The simplest algorithm for doing this is to first compute all the horizontal moves required to get from one place to another.  They will either all be left moves or all right moves (depending on whether the "from" location is to the right or left of the "to" location).  You can use the indices in the grid to calculate exactly how many you need as well.  Once you have done this, repeat the process for vertical moves.  Add all these moves into a list for returning from the `movesBetween` method.

**WARNING:** Be very careful to make sure you know which index (first or second) is representing rows and which is columns in your Grid.  In the sample solution we have made the first co-ordinate the row and the second the column (this will seem backwards to many of you).

Now you need to give all characters the ability to choose a move themselves.  Add `public abstract RelativeMove aiMove(Stage stage);` to the `Character` abstract class.  This will force you to implement that method in each `Character` subclass.  You can choose exactly what move you want them to return to get the behaviour your want out of them.  You can start with having each return a `RandomMove` just so we can test the next step.

Now we have everything we need to move from replaying pre-set moves to having the characters choose their moves.  These changes will all take place in the `update` method of the `Stage` class.  We can take the moves list away entirely and on each "step", which you will recall was happening every two seconds, we ask each character what move it wants to make (with `aiMove`) and then `perform` that move.

Test that you have this working before moving on.

Now we will finesse the stage updating.  The game should end whenever the sheep makes it to the shepherd (the sheep lives!) or the wolf catches the sheep (the sheep dies).  Modify the `update` method to implement this.  You will need an `if` statement or two to check for those conditions and then `System.exit` when they are satisfied.  On the updates where they are not satisfied, the characters make their `aiMove`.

Now we add sensible behaviour to each character.  The behaviours will be:

  * The sheep moves one step along the path towards the shepherd.
  * The wolf moves one step along the path towards the sheep
  * The shepherd remains still.

Make the changes to the various `aiMove` methods to achieve the above.  This is where the `movesBetween` method available on the grid will be useful.  Note, to make this work in my solution, I needed to relax the access modifiers on most of the fields of the `Stage` class from `private` to `protected`.  Is this change reasonable?  What are the consequences?  We ask these questions because any relaxation of access modifiers must be justified in program design terms, not just as a matter of convenience.

# Task 17

So, time to throw a spanner in the works.  It has been decided that any time a sheep steps on a cell that is on the top-left to bottom-right diagonal, it must stop moving.  As if those cells have glue on them or something.  So we want to be able to _change the behaviour of the sheep at run-time_.  Sound familiar?  This will require a strategy pattern.  Change your program so the `aiMove` is not done directly in the method but done via a strategy pattern.  I suggest a `Behaviour` interface for the "top" of the strategy that contains a single method `public RelativeMove chooseMove(Stage stage, Character mover)`.  I also recommend adding a `Behaviour` field to the `Character` class that is set in its constructor.  Now change your program so that when the sheep is on a cell where `x == y` (i.e. that top-left to bottom-right diagonal), its behaviour changes to standing still (not moving).  Your sheep will die more often now.  How about making the shepherd start moving towards the sheep if it gets stuck?

You can have quite a bit of fun with interesting behaviour changes now and the `movesBetween` method is powerful enough to support a number of different movement strategies.

## 🤔 Task 17a

This task sits to the side of our other tasks.  It is an experiment.  Even after we get an answer, we won't build upon that answer in later tasks, i.e. we will use the Task 17 answer as the basis for Task 18.  However, I think this is the most interesting task so far, it is certainly worth your time.

Can we make the strategy pattern we just created disappear with lambda expressions?  More concretely, can I get rid of the `Behaviour` interface and its subclasses and still have dynamic behaviour at run-time?  If so, implement it and discuss the pros and cons of this approach compared to a "real" strategy pattern.

# Task 18

## Task 18a (getting ready for observer)

We are now going to introduce a human controlled player.  This player does not influence the characters (yet) but can move around the board using the "wsad" keys of the keyboard.  This introduces a problem for our model.  We were able to get the mouse input because it is a _continuous_ variable.  I.e. it always has some value, so whenever we ask for it, we will certainly get an answer.  Key presses are momentary and thus we may not be able to ask at the time they are pressed.  We need to move to an event model to deal with this.  Java has an event model built in but it comes with a great deal of baggage and we are doing everything ourselves.  Thus we will engage with the Java event model only in the `Main.java` file and create our own event system for the rest of the application.

Another name for an event system is a "publish-subscribe" system and thus we will be using the observer pattern to implement it.

To hook into Java's event system we need to make the `Canvas` class implement the `java.awt.event.KeyListener` interface.  This will require implementations of three methods.  At this stage just leave them all empty.  Eventually the `keyTyped` method will call something in the `Stage` class to pass on the event.

~~~~~
@Override
public void keyTyped(KeyEvent e) {}

@Override
public void keyPressed(KeyEvent e) {}

@Override
public void keyReleased(KeyEvent e) {}
~~~~~


The last step to hooking into Java's event handling is to register the `Canvas` as the key listener.  This can be done in the `main` method with `this.addKeyListener(canvas)`.  I have included an example `Main.java` under the `src` directory of this repository to guide you.

Now lets add a "player".  The player does not make AI moves.  When it is the player's turn to move the program will wait for input from the human player (a key-press) indicating what move to make.  We need a new `Player` class with methods:
  * a constructor that takes the location (cell) of the character
  * a `paint` method that draws an orange circle for the player
  * an `inMove` method that returns `true` isf we are waiting for the human player to make their move
  * a `startMove` method that puts the player object into the state of waiting for the human to make their move.

That's all the scaffolding we need, now to apply the observer pattern to the task of notifying the player when a key is pressed.

## Task 18b

The built-in `Observer` and `Observable` classes in Java _are not appropriate for our purposes_  because they insist on dealing only with `Objects`.  There are not generic versions of them we might use like we use generic containers.  (🤔 Any idea why?).  Instead we will make a specific observer pattern.  We need an interface taking the role of the "observer" and an interface or class for the "subject".  Within the observer we need an "update" method (see the readings to find out what these terms all mean) specifically for being told when a key is pressed.

Your job is to design and implement an observer pattern that will fit into this application for this purpose.  When you are done, the stage will be notifying the player (via an observer pattern) that a key has been pressed and the player will use that information to make its move (if it is ready for a move).  Some tips:
  * Your "update" method will need to be given at least the character that was pressed but probably also the `GameBoard` since you need access to that to make your move.
  * A `Stage` will be  your concrete subject.
  * The `Player` will be your concrete observer.

## 🤔 Task 19

This task sits to the side of the other tasks.  Task 20 will build from the solution to Task 18, not this task.

Can you make the "make the observer pattern disappear" with lambdas while still maintaining the _intention_ of the pattern?

## Task 20

We are going to do a _design_ for adding Bees to the game.  A bee is a character that flies randomly around the game board unless it is in the vicinity of another bee.  If it is just one cell away, it will join that other bee and become a swarm of bees.  Swarms of bees also move around the same way, when they are next to another swarm they will join together to become an even bigger swarm (individual bees also join swarms when next to them).  Bees can sting the other characters and the player if they are on the same spot - get stung 3 times (or once by a swarm bigger than 2 bees) and you are out of the game.

The obvious design pattern for Bees is the composite pattern.  Do a design (using UML-ish notation) of how you might apply the composite pattern to this problem.

What other changes are required in the program?  How does the composite pattern fit into the current design?  Can you think of a better alternative than using the composite pattern for this problem?