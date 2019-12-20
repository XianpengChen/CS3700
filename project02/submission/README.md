# Project 2: Simple BGP Router

## Description
Please refer to https://course.ccs.neu.edu/cs3700f19/projects/project2.html for details.


## Approach
Based on the skeleton code provided by professor, this project is aimed to pass level 1-6 test cases in tests folder.
 Since the skeleton code already laid out the structure, all I have to do is to implement functions which performs the routing
 logic of a BGP Router.

After implementing necessary functions(and thoroughly documented them), I tested the router on CCIS Linux machine, it passed
all level test cases.
## Challenges
1. it's hard to imagine the flow of packets between routers in mind, better draw them out on a paper.
2. not quite understand local preference, selfOrigin, ASPath and origin yet (figured out now).
3. can we test locally? not one a CCIS linux machine?


## Build
the Makefile just gives the router executable permission, does not create new file(s), does not need to clean.

build: make

run: /router <ip.add.re.ss-[peer,prov,cust]> [ip.add.re.ss-[peer,prov,cust]] ... [ip.add.re.ss-[peer,prov,cust]]


## Testing
test:

$ ./sim all

$ ./sim tests/<1-simple-send.conf or ...>