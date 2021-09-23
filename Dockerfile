FROM adoptopenjdk:11-jre AS builder
##WORKDIR ~/scalaProjects/scala_fitness_app
COPY . .
EXPOSE 9000
CMD ["/bin/sh", "-c", "target/universal/stage/bin/play-heroku-playground && -Dplay.evolutions.db.default.autoApply=true"]