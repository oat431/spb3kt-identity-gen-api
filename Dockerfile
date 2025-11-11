FROM azul/zulu-openjdk:21
EXPOSE 8001

ARG DEPENDENCY=build
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/org /app/org
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app/classes:app/lib/*:app/org/*:app","org.springframework.boot.loader.launch.JarLauncher"]