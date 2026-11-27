# Simulation-code-for-Regeneron
Personal code for my Regeneron Project to simulate the physics of a ball bouncing around in a 2D cone with one side having gravity and the other side without. 

Running: 
If you choose the run this here are a few important things to keep in mind:

  1: This was made in VS studios so I can not guarentee it'll work in other enviroments
  
  2: The files paths within the code it self are finicky and won't work reliably unless you have the Regeneron folder as your work space. 
  
  3: The version uploaded here is a version that was made for live tracking. The analysis code does work but you'll need to make make a few edits
  
      - uncomment the code that has a doc string talking about the secondary plot made for the project
      
      - uncomment the code that directly plots the ball to it's next collision site 
      
      - comment the code that does an Euler approximation 
      
      - rerun the Java code with this updated settings 
      
      - run the python code 
      
  4: The input files have a formatting doc in the data folder. If you wish to run your own tests or edit the ones already inside the input docs make sure you follow the input instructions or the code will break 

If you want to run my older model where gravity is uniform on all sides it'll be in the regeneron_copy(post verificaiton) folder. The same instructions for dealing with that apply as I stated above. 

LAST WARNING 
I do not know why but sometimes when I run the code the ball will randomly have "jumps" where it's position or velocity change substantially without reason you'll see it if you use the Euler approximation method of my code. I've yet to locate the cause of this behavior but seeing as the code is stable otherwise I've ignored this issue and applied a data filter for my analysis. 
