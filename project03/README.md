# Project 3: Simple Transport Protocol

## Description
Please refer to https://course.ccs.neu.edu/cs3700f19/projects/project3.html for details.


## Approach
based on the starter code provided by professor, it was improved to handle situations where packets dropped, damaged,
duplicated, and delayed, and under a variety of different available bandwidths and link latencies.

sender-side: keep track of round trip time, update it for every ack arrived; set a initial sliding window size of 10, means sender
can at most send out 10 packets without receiving ACK from receiver, update the sliding window size for every packet sent out
and every ACK arrived; keep track of last sequence sent and ACKed; for duplicate ACKs, send the last packet sent again.

receiver-side: store packets from sender into a dictionary(to keep track of them, also later to print out all data of the file);
send redundant EOF ACKs to make sure sender receives at least one of them; for every timeout window(sent from sender), limits the number
of ACKS sent to the sender; ignore duplicates packets.
## Challenges
1. it's hard to deal with packets arriving out of order on the receiver side;
2. some difficulty with EOF packet sending and EOF ACK returning;
3. adjust RTT according to latency.


## Build
No need to build, 3700send and 3700recv are both python files, give them executable permission they can be executed.
 

## Run
Please refer to https://course.ccs.neu.edu/cs3700f19/projects/project3.html

## Testing
with run and test files in the same directory(can only run or test on gordon.ccs.neu.edu):

./run [--size (small|medium|large|huge)] [--printlog] [--timeout <seconds>]
1. <strong>size</strong>: The size of the data to send, including 1 KB (small), 10 KB (medium), 100 KB (large), MB (huge). Default is small.
2. <strong>printlog</strong>: Instructs the script to print a (sorted) log of the debug output of 3700send and 3700recv. This may add significant processing time, depending on the amount of output.
3. <strong>timeout</strong>: The maximum number of seconds to run the sender and receiver before killing them. Defaults to 30 seconds.

./test
