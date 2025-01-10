FROM amazoncorretto:23-alpine
MAINTAINER baeldung.com
COPY /out /out
LABEL authors="nnmd2"

#WORKDIR /out/production/TOMulticast

#ENTRYPOINT ["java", "-cp", ".", "ds.assign.tom.peer.Peer"]

ENTRYPOINT ["java", "-cp", "/out/production/TOMulticast", "ds.assign.tom.peer.Peer"]