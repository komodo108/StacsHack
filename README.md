# StacsHack
Project done for StacsHack 2019 over the course of 24 hours!

### Motivation
This project was done over the course of the StacsHack 2019 hackathon.
We want to *break the ice*.

### Cool BOI
Co-operative Multiplayer Game

Two players play against the clock to escape an icey maze, one player needs to support the ice breaker to break the ice blocking the path to the exit by getting them critical support items to *break the ice*.

Both players will need to answer questions about each other to be able to get through the maze! (Such as having to answer a question when breaking the ice, or having to answer a question when getting an item). They will also have to complete a **final challenge** to exit the maze and escape!

### Implementation
The implementation is based upon a custom network solution [from GUTS](https://github.com/komodo108/GUTS) and Java Swing for simple interaction.
The map implementation is a simple text based solution, described below:
```
x:y
W - Wall
. - Floor
Q - Question Block
F - Fire
f - Flask
P - Ice Pick
```
with `x` being the number of rows, and `y` being the number of columns, and below a grid of the allowed tiles as shown above.

### Networking
This app uses an message based packet system for sending application state across the network, the 5 implemented packet types serve the following purposes:
```
Hello - Idle Check packet, connection closes if this is not recieved
ItemRemove - Sent when an item is removed by the other player, item should be added to their state
Map - Sent when the map is updated, e.g. a block being removed (*Put answer to question here?*)
Player - Sent when a players state changes
Win - Sent when a player wins
```

### Road-map
To complete this project, the following will need to be done:
* Complete the network implementation 
    * Only allow the question block to be opened when the player is nearby and has an item held
        * For this, one will need to expand the player state across the network to include the held item
    * Allow the answer to random questions to be sent over the network
* Finish off the gameplay by bringing up the final challenge at the end with the other players previous answers; only can be done when players are close
* Implement a timer for the game overall
* Fix the map implementation by sending the servers map to the client when they select it (*or, dynamically send a generated map across the network to other client*)
 
### Contribution
The contributors to this project are listed in the page on GitHub.
After this event, **we do not intend to update this project**, and will not be accepting any pull requests.
If you would still like to contribute, please `fork` the project.
