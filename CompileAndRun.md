## How to compile and run the code

### Compile
This project is not using neither Maven nor Gradle. I'm using IntelliJ to compile the code. All compiled classes are in the `out` directory.
The SDK used to build the project is `openjdk-23`.

### Run
I'm using Docker to run the code. The docker-compose.yml is in the root directory. <br>
The dockefile for the peers is at `TOMulticast/src/ds/assign/tom/peer`. <br>

To run the project, please run the docker-compose.yml file. <br>

---
#### Additional considerations regarding the outcome of the project
I consider the 'Totally Ordered Multicast' the most challenging between all the other projects.
I was able to implement the network as desired and the`Lamport Clock` as well. Although all peers seem to be synchronized at first, I noticed that some tuning needs to be done in order for them to be fully synchronized.
The synchronization process is very challenging and I think that initializing each peer's clock with a small randomization would significantly improve the synchronization of the peers.
This is not my case, since all clock's initial value is `0`. 

For more details on project's implementation and architecture, please refer to [TOMulticast](TOMulticast.md)
