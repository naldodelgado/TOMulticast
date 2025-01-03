# TOTALLY ORDERED MULTICAST

#### Description
A network of 6 peers where every peer knows every other peer.
The goal is that all peers print the same sequence of words. To do this, the peers must
agree on a global order for the messages before they process them (print the words
therein). This is not trivial, as factors such as communication latency and varying
network topology affect message delivery even in small networks.
=⇒ To achieve this you must implement the Totally-Ordered Multicast (TOM) Algorithm
using Lamport clocks to timestamp the messages

---
#### Peer class
The peer class represents a peer in the network. It initializes the classes InPipe, OutPipe, and MessageProcessor.
It initializes an instance of InPipe in a separate thread when it receives communication from other peers.
It initializes one instance of OutPipe in a separate thread to send messages to other peers.
It initializes an instance of MessageProcessor in a separate thread to process the messages received from the InPipe.

#### InPipe class
The InPipe class is responsible for receiving messages from other peers, and funnel them into the MessageProcessor.

#### OutPipe class
The OutPipe class is responsible for extracting random words from `ds/assign/tom/peer/resources/words.txt` and sending them to other peers.
It also forwards the messages to the MessageProcessor.

#### MessageProcessor class
The MessageProcessor is a singleton responsible for receiving messages from the InPipe and OutPipe, and processing them.
It is initialized with a cold start timeout so that the PriorityQueue where the words are stored is ordered correctly.
This is where the Lamport Clock is handled.

