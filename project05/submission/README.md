# Project 5: Distributed, Replicated Key-Value Store, milestone

## Description

Please refer to https://course.ccs.neu.edu/cs3700f19/projects/project5.html for details.


## Approach

Based on the starter code provided by professor and the Raft paper, a basic implementation was built for now. 
First, store incoming messages to a message queue(first in, first out), then deal with messages according to the server's
status(follower, leader, candidate), which would generate responses to replicas(stored in msg_to_replicas_queue) or 
clients(stored in msg_to_clients_queue). Messages have the following types:

##### put, get, and their responses
please refer to <https://course.ccs.neu.edu/cs3700f19/projects/project5.html> for specifics.

#### append and its responses
append (from Raft paper) can only be sent from a leader to other replicas. append is used to send out heartbeats or put
requests. Responses to append are send out to leader (it may include MID if recent append includes a put request).

#### vote request and it's responses
vote requests (from Raft paper) are sent out by a candidate to other replicas for leader election. Responses to vote requests
will be sent back to that candidate. Obtaining over half votes will make this candidate a leader.
  
## Challenges

1. always got "Total Messages Between Replicas: 4187 < 5000, Partial credit, needs improvement", how to improve?
2. also "Duplicate Responses to Clients: 2 < 10, Partial credit, needs improvement".

## Build

No need to build, 3700kvstore is a python file, give it executable permission it can be executed.
 

## Run

Please refer to <https://course.ccs.neu.edu/cs3700f19/projects/project5.html>

## Testing

Please refer to <https://course.ccs.neu.edu/cs3700f19/projects/project5.html>