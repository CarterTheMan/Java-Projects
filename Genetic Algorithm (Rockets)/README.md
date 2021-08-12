# Genetic Algorithm (Rockets)

This program has three main classes but really only two are needed. The Main class and Rockets class are both required, but the avgPoints class is not necessary for this program to work. This is a program the somewhat follows evolution in real life to arrive at the end point. The objects moving in this program are called “rockets” which are represented as red ovals on the screen.

The way the rockets work is in 4 basic steps that repeat themselves.

1. The first step is the rockets actually moving. Now the rockets change their position based on two arrays of X and Y values, respectively. So each frame the rocket’s position changes according to the X and Y value in the array at that position. These X and Y arrays could be seen as the genes of the rocket (since this is similar to evolution). After the amount of time in each generation runs out the final position is recorded.

2. Next the program determines the fitness of each rocket based on how close its final position is to the target. The closer to the target the rocket is, the higher its fitness is and based on that an array-list is created and it is added into this array list equal to the number of its fitness. So if a rocket has a fitness of 55, it is added into the fitness array-list 55 times. This makes it more likely that this rocket is chosen in the next step.

3. The next step is creating a child. Two parents are chosen, hopefully ones of higher fitness, and a a child is created. This child contains a random mix of the X and Y values that the parents had. The choosing of two random parents ensures that each child is unique and hopefully has a higher fitness than the ones of its parents.

4. The fourth step is mutation. This is completely random and changes based on each child. Basically, each child’s genes are put in and random parts of those genes could be changed. This helps to ensure that there is an element of randomness in each rocket’s genetics and this hopefully will guide the child to have a higher fitness.

So that was how the rockets work, but the next part are the average point (called avgPoints). These are represented as green (current generation) and yellow (last generation). This combines the median points of all rockets in each frame and makes a dot at that average point. This allows the user to more visually see how the rockets are getting closer and closer to the target goal in the end. After many generations, the rockets can usually get within 20 points of the target. This is very good, but the closer the average end pint is, the smaller the change will be between this point and the last one. To change the target point, You only need to change the targetX and targetY variables.