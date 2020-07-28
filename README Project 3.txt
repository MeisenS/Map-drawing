* Name: Meisen Shu & Randy Zhang
* NetID: mshu4 & rzhang48
* Assignment number: Project 3
* Lab section: TR 6:15 - 7:30


In this project, me and my partner to create a rudimentary mapping program in Java with Eclipse.
The program is able to read in a list of intersections and roads from a file and create a graph from them.
Meanwhile, shortest path directions between any two intersections using Dijkstra's algorithm
and the minimum weight spanning tree created based on LinkedList is printed on initial graph with different colors, and will also be shown in the console. 

the main method is in Mapping.java, please enter command lines as required in terminal to launch the program.


Runtime:
The runtime for the shortest path is O(E log(V)). For The minimum weight spanning tree there it runs in O(E log(V)). TO draw the original map it is O(E), then for the minimum weight spanning tree it has a big O(E), and for the shortest path it has a big O(V). 


Files included:
Edge.java
Graph.java
Graphnode.java
Node.java
Mapping.java
README Project 3.txt
ur.txt
monroe.txt
nys.txt 