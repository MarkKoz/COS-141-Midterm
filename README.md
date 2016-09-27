# COS-141-Midterm
Midterm for COS 141 "Programming in JAVA". Math game that displays simple random math problems and records users' statistics to personal files.

Requirements
---------
1. Generate simple math fact problems:
	a. Addition (the total must be an integer >= 0)
	b. Subtraction (the difference must be an integer >= 0)
	c. Multiplication (the product must be an integer >= 0)
	d. Division (the quotient must be an integer >= 0)
2. Validate user input at every opportunity.
3. The program should keep track of the following statistics:
	a. The user’s name
	b. The total correct answers
	c. The total wrong answers
	d. The total earnings ($0.05 is awarded for every correct response and $0.03 is subtracted from every incorrect response)
4. A separate text file must be created for every user:
	a. Statistics are read from the file at the start of the game (if the user is a returning player).
	b. Statistics are recorded at the end of every game.
5. The program must be developed using functions so that the __main__ function __*consists mostly of function calls.*__

Restrictions
---------
1. You may not use global variables
2. You may not use GOTO statements
3. You may (not?) use use infinite loops
	a. for(;;)
	b. while(true)
4. You may not use the break statement to exit a loop
5. __You may NOT use code from the Web__

Program Flow
---------
1. At the start of the game, an initial “Splash” screen must be displayed which includes:
	a. The game’s title
	b. Your name
2. After the “Splash” screen a prompt must ask the user for his/her name (validate input: no numbers, no blanks)
3. Once you have the user’s name, display a menu with the following options:
	a. Add
	b. Subtract
	c. Multiply
	d. Divide
	e. Stats
	f. q/Q to Quit
4. If the user’s answer is correct/incorrect, a screen is displayed with proper feedback.
6. If the user chooses to display statistics, show the following:
	a. Name
	b. Total Correct
	c. Total Wrong
	d. Total Earnings
