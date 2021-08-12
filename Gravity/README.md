# Gravity 

This is a Java program that uses the JPanel, JFrame, Color and and Graphics class to show the effect gravity has on planets in the program. The program is split into three main class. Each one has a very important role.

1. The Main class which extends JPanel. This class has three main methods within it.
   1. The first method if the paintComponent() method which is responsible for showing the actual graphics on the window. It always sets the background to white, setting the colors of each planet, actually drawing the planet and then having each planet feel the force of attraction of each other planet. Finally, when the time to reset happens, this method also handles that
   2. Then second method is the dist() method which just finds the distance between two methods using the distance formula
   3. The final method in this class is the main() method. This method is responsible for creating the window to display the graphics in and creates the all the initial planets.
2. The second class it the planet class which is controls the color, size (mass), position, velocity, acceleration and effect of gravity for each planet. (If the explanation here doesn’t make sense, check the third class which is called PVector)
   1. The first method is update() which calls another methods edges, adds the acceleration vector to the velocity vector and the adds the velocity vector onto the position vector.
   2. The second method is attract(). This method is actually responsible for the gravity between planets. The way this works takes a lot of explanation but the most basic way to explain that it finds the directs of effect from gravity and then finds the force of gravity and using that direction and force, creates a vector of the effect of gravity. This vector is then added to the acceleration vector.
   3. The next method is edges() which is responsible for stopping the planets when they reach the border of the window so that they don’t go off screen.
   4. The last method is color which is responsible for keeping the color of the planet.
3. The last class is the PVector class. This class represents a vector by taking in a x and y value and each value represents a distance. These explained methods will explain the non-set/get methods.
   1. The first method is mag() which calculates the magnitude of the vector using the Pythagorean theorem
   2. The next method is the normalize() method which sets the magnitude of the vector to 1
   3. The add(), subtract(), multiply() and divide() methods all change the vector as a whole depending on an input. For example, the multiply(double d) method multiplies the vector by a scalar which in this case is d.

You cant change the amount of planets spawned in each generation by changing the size variable. You can also change the range of sizes by changing the maxSize and minSize variables. To change how long each generation lasts you can change the resetRate variable.