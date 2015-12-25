	# Howl_Away
	PAD Project

FRONT-END MILESTONE:	
	Project Milestone 1: 
		December 29, 2016
	Jj: Bone(normal and golden); Vehicle(cars and bus) obstacles
	Aikee: Wolf(normal state and 5 power-up states), 5 power-up icons
	
	Project milestone 2: 
		January 4, 2016
	Jj: Humans(hostile and civilians), 2 background
	Aikee: Complete Wolf states and power-up icons, 2 background
	
	Project milestone 3: 
		January 11, 2016
	Front-end:
	Jj: 6 skins, buttons(pause, shop, exit, high score, buy, retry)
	Aikee: 6 skins, background panels, shop, starting screen, logo
	
	Project deadline: 
		January 19, 2016
	//any additional objects needed
	
	
BACK-END MILESTONE:
	A. 1st week (Dec23 - Dec29):
	  1. Study up:
	    a. should already know the basics of libGDX and the IDE
	    b. praktisado haha
	  2. Make proper abstraction for the game
	  3. Implement game system framework
	  4. Start implementing the WOLF:
	    a. actually making it appear on screen
	    b. no input from user yet
	    c. no animation yet
	B. 2nd week (Dec30 - Jan5):
	  1. Fully implement WOLF:
	    a. excluding power up transformations
	    // will be implemented along side power ups
	    b .including run/jump animation
	    c. can tap to jump
	    d. runs infinitely
	  2. Fully implement BONES
	    a. random placement along the course
	    b. proper collision detection from the wolf
	    c. can count total bones collected in a run
	  3. Start implementing OBSTACLES (Humans):
	  a. Civilians
	    i. random placement along the course
	    ii. collision detection from wolf (kill/drop bone)
	  b. Hostiles(Soldier/Animal Catcher(?)):
	    i. random placement along the course
	    ii. collision detection from wolf (game over)
	    // animation for these can be moved the next week
	C. 3rd week (Jan6 - Jan12):
	  1. Fully implement OBSTACLES (Vehicles(?)):
	    a. full animation
	    b. game over if collided head on
	    c. can act as ground (wolf can land on vehicle)
	  2. Fully implement POWER-UPS:
	    a. Full Moon:
	      i. add new sprite to wolf
	      ii. new collision detection (can destroy anything)
	      iii. other small details (speed inc, duration, etc)
	    b. Hermes:
	      i. add new sprite to wolf
	      ii. change in input processing (can now hold to glide)
	    c. Golden Bones:
	      i. all bones have isGolden boolean
	      ii. adjust sprite/score accordingly
	    d. Multiplier:
	    i. multiply current score (?)
	    e. Head start:
	    i. di ko pa alam haha
	    f. Magnet
	      i. sets up circumference around
	  wolf for pre-collision detection
	      ii. if bones collide with the circumference,
	  they will go to wolf
	    g. Bubble
	      i. new sprite
	      ii. every possible-death collision while active will check
	  first if wolf has bubble
	    h. Wolf Pack: // kasama pa rin ba 'to?
	      i. animation for wolves clearing the screen
	      ii. if obstacle within camera, kill it
	  3. Start implementing other Screens(menu screen, highscore etc)
	  4. Start implementing SHOP:
	    a. can at least try on a set and use it in game
	D. 4th week (Jan13 - Jan19):
	  1. Fully implement screens(UI stuff lang 'to)
	  2. Fully Implement SHOP:
	    a. uses in game currency(bones)
	    b. has all possible sets and
	    has corresponding prices
	    c. saves user's currently owned sets
	  3. Trying out on different devices:
	    a. resolution differences
	    b. general debugging
