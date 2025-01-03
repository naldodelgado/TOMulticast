FROM amazoncorretto:23-alpine
MAINTAINER baeldung.com
COPY /out /out
LABEL authors="nnmd2"

ENTRYPOINT ["java", "-cp", "/out/production/TOMulticast", "peer.Peer"]