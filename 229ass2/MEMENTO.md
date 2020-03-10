Memento design pattern is a design pattern which allows the programmer to restore the objects at the previous state when it was initially captured.

In my program in order to implement the quick save functionality I used the memento design pattern.  
I created two new classes 
	1. Memento class
	2. careTaker class
The stage class is the originator as it is the class that holds all the necessary characteristics in the game as the location of the characters and the players that we would like to save and restore it when specified.

In order to save and restore the state we want I created two methods in stage 
	1. saveMemento()
	2. setMemento()
	
The saveMemento() metho creates a new memento and saves the locations each characters and the player at any instance of time. When this method is called the instant of the state in the game will be saved 
The setMemento() metho restores the games state using the memento object in the saveMemento field. When this function is called then the saved state of the game is restored.

Memento class is used to define all the fields in the game that needs to be saved. It does not need to know about any unnecessary fields. So in the Memento class i have defined the location fields of the characters and the player. I have initialized the Memento class with the locations of the three characters and  the player.

In the careTaker class it calls the saveMemento() method and the setMemento() method defined on stage class. careTaker class defines the stage and the memento. When the space button (' ') is pressed a new memento is created and the it stores the new object in the saved Memento field. And when the 'r' key is pressed the it restores the saved game state. And in order to avoid the null exception from occurring this if condition is placed inside a condition that is will only occur if the memento is nut null.  

Memento patter is not a good solution for general save states as games have very large number of variables so if save game stated between games, it will require to save huge number of variables as a result of which the lots of memory will be required and then the game will become slower.
