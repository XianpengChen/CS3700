# Project 5: Distributed, Replicated Key-Value Store, milestone

## Description

Please refer to https://course.ccs.neu.edu/cs3700f19/projects/project5.html for details.


## Approach

Based on the starter code provided by professor and the Raft paper, only a skeletone was built for now. 
Still trying to figure out why replicas won't respond to leader after applying a command to it state machines.
Leader use append entries RPC to send requests from clients to replicas, then collect response of appending entries
and applying commands to state machines; using vote request RPC to collect votes from other replicas after an election
timeout. 

## Challenges

1. why leader did not respond to clients? time to find out the new NO.8 wonder in the world.
2. what does it mean: too few messages between replicas?



## Build

No need to build, 3700kvstore is a python file, give it executable permission it can be executed.
 

## Run

Please refer to <https://course.ccs.neu.edu/cs3700f19/projects/project5.html>

## Testing

Please refer to <https://course.ccs.neu.edu/cs3700f19/projects/project5.html>