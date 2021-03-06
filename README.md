# CodenamesPKb
COMP354 - Intro to Software Engineering - Term Project Team PK-b

Team members:
William Ngo - 40061003
Rosy Teasdale - 40043570
Zijian Wang - 40011864
Ihsann Malek - 40024975
John Paulo Valerio - 40031628
Olivier Racette - 40017231
Daniel Vellucci - 27416288
Chavind Mungun - 40062544

Information about the demo:
Launch the game from MainApp.java

The enter button is the only interactable function for the user in this current iteration.
When you click the button, the game plays a turn which includes a spymaster giving a hint followed by an
operative picking a card. There is no relationship between a clue and an operative picking a card
because that logic is not to be implemented in the current iteration.

The red operative has a pickNextCard Strategy which will make him play the first card located on the
top left of the board. He will then pick the next one to the right for the remaining moves. When he reaches the last card
on the row, it will switch down to te next row. If the next card picked has already been picked by blue, it will skip that card
and pick the following one afer.

The blue operative has the pickRandomCard Strategy. When it picks a card at random, it checks if the card has already
been flipped. If it has, it will pick another card at random until he finds one that hasn't been flipped.

When a card is picked, the game checks its type (red, blue, bystander or assassin). If the type is a color, that team's amount of cards left 
decrease. If it's a bystander, nothing happens for now, the next player will play its turn. The game ends when one team's remaining amount of cards goes down to 0.
Additionally, the game also ends when either team picks the assassin.

### NOTE for missing .jar files on compile
All needed .jar files are included with this project in "\resources\External Libraries\". In case of errors on compile time, please add the following files to the build path:
```
resources/External Libraries/gson-2.8.5.jar
resources/External Libraries/jgrapht-1.3.0/lib/antlr4-runtime-4.7.1.jar
resources/External Libraries/jgrapht-1.3.0/lib/commons-lang3-3.8.1.jar
resources/External Libraries/jgrapht-1.3.0/lib/commons-text-1.5.jar
resources/External Libraries/jgrapht-1.3.0/lib/fastutil-8.2.2.jar
resources/External Libraries/jgrapht-1.3.0/lib/guava-26.0-jre.jar
resources/External Libraries/jgrapht-1.3.0/lib/jgrapht-bundle-1.3.0.jar
resources/External Libraries/jgrapht-1.3.0/lib/jgrapht-core-1.3.0.jar
resources/External Libraries/jgrapht-1.3.0/lib/jgrapht-demo-1.3.0.jar
resources/External Libraries/jgrapht-1.3.0/lib/jgrapht-ext-1.3.0.jar
resources/External Libraries/jgrapht-1.3.0/lib/jgrapht-guava-1.3.0.jar
resources/External Libraries/jgrapht-1.3.0/lib/jgrapht-io-1.3.0.jar
resources/External Libraries/jgrapht-1.3.0/lib/jgrapht-opt-1.3.0.jar
resources/External Libraries/jgrapht-1.3.0/lib/jgraphx-3.4.1.3.jar
resources/External Libraries/jgrapht-1.3.0/lib/jheaps-0.9.jar
```
  
