# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```


UML diagrams link
```https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdKUAGJITgwamURkwHRhOnAUaYRnElknUG4lTlNA+BAIHEiFRsyXM0kgSFyFD8uE3RkM7RS9Rs4ylBQcDh8jqM1VUPGnTUk1SlHUoPUKHxgVKw4C+1LGiWmrWs06W622n1+h1g9W5U6Ai5lCJQpFQSKqJVYFPAmWFI6XGDXDp3SblVZPQN++oQADW6ErU32jsohfgyHM5QATE4nN0y0MxWMYFXHlNa6l6020C3Vgd0BxTF5fP4AtB2OSYAAZCDRJIBNIZLLdvJF4ol6p1JqtAzqBJoIcDcuj3ZfF5vD6L9sgwr5iWw63O+nxPF+SwfgC5wFrKaooOUCAHjysL7oeqLorE2IJoYLphm6ZIUgatJvqMJpEuGFocuQACiu40XA9R2gK2hCmEr4jqMkycuENQALIwDcGiutKSaXvB5QGvGcq4QS+EsuUVDAMgWiZFU+ivEsJEsfIswQe85FMm6nY4eUMQAGrKUgK44Rq8mkjAnrekGAZBiGInmpG1EAJIAHJkDR4RMbGwbaE8opcTAPKQDA071Lo3COs6yYwSWaE8tmuaYIBIJwSU5QAFJkDUvlXH0cWNs246tl8EVjhOfR7DAf6dtkPYwP2g5oKkXocJQ7qlhxIGRQ1NZBrOVUNUunCmJ43h+IEXgoOge4Hr4zDHukmSYG1F5FNQ17SHRNH1DRzQtA+qhPt0FVzi1KVAiWPXAH1abARW1XVlO42VfOn1tjlbKmTASH2Otrl1r92EyXZFEETAkLDBANAhRDM5Q6GcOiYUloyMdp0wCFgoAGbeMMhPjQlhhpJQhhDR9AC8gmkWItliWcj1pmh62ZQgeapbl4n5UKEAcJYZWjOowDkuMUzc76MA5uGxNKo192nLtYB9gOL6S6o0v3HLa0K0rboqzs00rnN66BJCtq7tCMAAOKjqym2njt57MHl15Oyd532KON0-Xdxb-hzqblM9r1lbdk01dBnNAzJiHQi7ktoxNaDQ-BsNGQpMBKSpzl+pnGMeRGOPUQF9GMRTcasaTEDkyF8U6IlFcmSnUWqJZKlJYmclYw55JgOnaiwoZZqV+y5Q1wxTGB5LMBN+TS9qG3Hf2RGeXgs7rsO7EA+4Q9kf75Lh-80n7Nh4VxWlYN6+qLLyCxDUxPj6oBxh61Xva11IsxZlSfi-aE79P6W1XPNDc2AfBQGwNweAupMjn0MO7bamtk4HTTDeBoAcg7BBDugF869fKgV-D-U+wIo69SAuVIhf0pqzB0KLcWj9RxkK4v8bKAssFOnlI5ZBKBx5lznLMUho4c78NkjIbeikrKqRQKjOO2dMb508lXOex065E0bmTeuM4qZqOnl3CSPc+7WWPnnae5QXocBEevdy28qLlD8gFIKMAJGjBgH5eoNRPGu03mITuN9u7j28tIKx7McrlHHrzK+qZTHCyKiVYBo4IlQXVoUTW-9uiALYf0deGTuHLigTbAIlgUDKggMkGABUIA8lQYEduoAGyexyN7IW14qiUjvC0dewdIZzhfAg4AlSoBwAgEhKAH4nhFOkJkyhAEBY0JenQlRC59j5LKqM8ZkzpmzKmPMzJgNd4CIAFYNLQCIlRekEBjMoPs6A4j0nSCkc6Ie6iBpF2siXf0Kip6US8lo2uwU3J6ObgYoJxigVnIQuYhRUTPk2M8RSBxrzAXGWBbRUFATRgk30U-IJMAADqLBAo0TxSgayMAmbr0sSEuFsTRzSVznhYeA0-CKPRaMCJdyHkTKmc85mOlgCYuxrPPkVRpAKAJuPGAyQMipBgFUe54yfFoGJhAKJVCSz1J5HEtQWVTk+zTCwoBg09YG1ln0fVaAUC2lNiyc2CBLBq0oRrP+HUdZJHNQUvoVqZZVjtQ6xWPhlZKldWrUp1sFoBC8GMrsXpYDAGwAgwg8REgpC2meDpfDDr4zOq0Yw91lmc1WTHJNeAMIYh4dfIWe8QDcDwIyPQBhJ5ItkRy8ofVuCZAUMqRkHaQmaLxvRAmA6EDphAEsVkq8YDyHbqzGG0SVlVqzEavmdbEmhOweUP1EsWTWqrK2-Qhgm3Jode6q8gsuwdNyb61hh6pZBqmKegwjlm122jTNIAA```
