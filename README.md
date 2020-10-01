# Vingt-Un (21) game
[![Build Status](https://travis-ci.com/contrapost/vingtun.svg?branch=master)](https://travis-ci.com/contrapost/vingtun)

### Intro
Implementation of traditional card game **Twenty-One** (Vingt-Un). Game logic is based on the following rules:
1. Number of players is 2 (dealer and player/*punter*).
2. Dealer should shuffle the deck of cards at the beginning of the game 
3. Both players receive 2 cards at the start of the game (in specific order: punter-dealer-punter-dealer)
4. Game ends with original cards if:
    - punter or dealer has 21 (BlackJack)
    - if both of them have 21, the winner is punter
    - if both of them have 22, the winner is dealer
5. If game continues, punter receives additional cards until the score is equal or greater than 17:
    - if punter's score is greater than 21, dealer wins the game
6. If game continues, dealer receives additional cards until the score is greater than punter's score:
    - if dealer's score is greater than 21, punter wins the game
7. The winner is the one with higher score (practically it could be only dealer, otherwise dealer should lose the game 
in the previous step)

#### Deck of cards
Deck of cards can be loaded from a file or initiated dynamically. A new deck is shuffled before a game. A deck loaded
from a file is used in a game without shuffling. 

### How to build and run
You can build the game app by running:
```shell script
mvn clean intall
```
To run the game just execute:
```shell script
cd target && target java -jar vingtun-1.0-SNAPSHOT-jar-with-dependencies.jar
```
Notice: it's necessary to run "fat" jar because the application has external dependencies, and it's more convenient than
to add all external libs to class path as cli arguments.
#### Running with flags
You can run the game app with following flags:

| Flag                   |      Description   |
|------------------------|:-------------------|
| **-f**, **--deckFile** |  to specify file name that contains deck of cards in the format "C3, SA, HJ...". NB! If the file name is not specified, the default file `defaultDeckFile.txt` placed in target folder would be used.      |
| **-h, --help** <img width=400/>         |  to print info        |


NB! If you run the game app without **-f** flag, new deck of cards would be generated dynamically.

### Test coverage
You can find test coverage report, when the game app is built, in `target/site/jacoco/index.html`

### Documentation
You can find documentation after you run:
```shell script
mvn javadoc:javadoc
```
in `target/site/apidocs/index.html`
