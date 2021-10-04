FROM adoptopenjdk/openjdk11:alpine

ADD ./build/distributions/*.tar /

EXPOSE 80 443

ENTRYPOINT ["/simian-api/bin/simian-api"]