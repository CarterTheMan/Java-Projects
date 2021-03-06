# Maze with Search

This is a program that allows the user to either make a maze themselves or choose from a pre-made maze file. Once the maze is created the user can then choose from different search algorithms as to what they want to use to solve the maze. Currently, I have a breadth first search, a depth first search and an A* search working. The searches are slowed so the user cans see how the search works. The following is the longer explanation of how the program works.

The program has two main ways to create the maze.

1. The first way is completely visual. The user is presented with a window and clicking on that window will allow them to create the maze. The first click places the start cell, the second click places the end cell and any click after that places walls. This allows the user to make the maze look any way they want.
2. The second way is to choose the maze from pre-made files. The user only needs to enter the file of the maze that the user wants to run.

Next the user can choose which kind of search they want to use. Currently, the program has three main searches. The first is the breadth first search, the second is the depth first search and the last one is the A* search.

1. A breadth first search works by using a queue for choosing which cells to search. A queue is a FIFO data structure which means that it can only add items to the back and can only remove items from the front. Check the image below for an example. This search enqueues maze cells which are empty and are touching the cell that has just been searched. Anytime an empty cell is added to the queue, it is also given a parent which is the touching cell that has just been searched. Then it keeps searching the cells in the front of the queue until it finds the ending cell. Once the end is reached, it traces from the end to the beginning using the parent cells. This creates the path and gives a solution.
2. A depth first search works by using a stack for choosing which cells to search. A stack is a LIFO data structure which means that is can only add items to the to of the stack and can only remove items from the top. The best way to compare this is to a stack of pancakes. You can only add a pancake to the top and when you grab one, it would also be from the top. See the image below for an example. This search adds items to the top of the stack (Called pushing) and takes items off the top of the stack (called popping). With a search like this, the computer will follow a path through to its completion before moving on. When a cell is added to the stack, it is also given a parent which is the touching cell that has already been searched. It keeps searching the cell that was most recently add. This way, when the search reaches the end of a trail, it will search the next trail. It keeps doing this until it reaches the ending cell of the maze. Once the end is reached, it traces from the end to the beginning using the parent cells. This creates the path and gives a solution.
3. An A* (called an A Star) search algorithm is fairly easy to understand. It finds the distance between the searched cell and the starting cell (called the gCost) and the distance between the searched cell and the ending cell (called the hCost). To determine which cell the maze searches, the maze adds the hCost and gCost to find the fCost. Whichever cell has the lowest fCost is searched next. If two searchable cells have the same fCost then it chooses the one with the lowest hCost. Using this way of searching cells, it will arrive at the shortest path every time. The only disadvantage of this kind of search is that it requires the individual cell to know where the end is. The other two searches don???t require this so they can search any maze from just a starting point. This is the main disadvantage of this kind of search. However, since it???s using mathematics to choose which cell to search next, it is the fastest search. The images below show the math that the programs uses for the search.

You can change the size of the maze by changing a few of the variables. You can change the size of each maze cell by changing cellSize. This makes each cells size equal to cellSize * cellSize. You can also add on more maze cells by changing cellAmount. This makes the amount of cells in a maze equal to cellAmount * cellAmount. This means all cells must be perfect squares and all mazes are also perfect squares. Below is the color of maze cells and what they represent.

The following colors coordinate with the types of cells in the maze

- Green = Starting cell. This is where the search starts, as the name implies
- Red = Ending cell. When the search reaches this cell, the search traces a path back to the beginning
- Black = Wall cell. These are cells the searches won???t look at.
- White = Unsearched cell. Still needs to be looked at by the search. When searched by the computer, it becomes a searched cell.
- Light gray = Searched cell. The algorithm has looked at this cell and found it not to be the ending cell.
- Yellow = Trace cell. These are the cells that, when the end has been found, trace the route from the ending cell to the beginning.

To read more about this project, or to see pictures that help in the explanation, click [here](https://cartermoseley.com/java-maze-with-search/)
