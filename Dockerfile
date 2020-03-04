FROM adoptopenjdk/openjdk12

ENV APP_DIR /opt/ticketbooking

COPY bootstrap.sh /bootstrap.sh
COPY target/universal/stage ${APP_DIR}/target/universal/stage

WORKDIR ${APP_DIR}

ENV PATH="$APP_DIR/target/universal/stage/bin:${PATH}"

USER root
EXPOSE 8082

ENTRYPOINT ["/bin/bash", "/bootstrap.sh"]
