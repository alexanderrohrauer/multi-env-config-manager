# quarkus-service

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./gradlew build
```

It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./gradlew build -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./gradlew build -Dquarkus.native.enabled=true
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./gradlew build -Dquarkus.native.enabled=true -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/quarkus-service-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/gradle-tooling>.

## Provided Code

### REST

## Already Added Extensions
Kubernetes
Kubernetes-Client
Kubernetes-config
kubernetes-service-binding

## Adding Extensions
Zum file build.gradle wechseln und unter dependencies steht add extensions

## Quarkus in Kubernets
## Docker Image der Quarkus-Anwendung:
./gradlew build
docker build -f src/main/docker/Dockerfile.jvm -t dein-benutzername/quarkus-service:1.0 .
docker push dein-benutzername/quarkus-service:1.0

## Zentrale Konfigurationsdatei
Die Datei build/kubernetes/kubernetes.yml wird beim Build von Quarkus automatisch erzeugt und
ist die zentrale Konfigurationsdatei mit der die Quarkus-App in Kubernetes deployed werden kann.
Kubernetes startet somit auch Pods auf Basis dieser Datei

## Deployment in Kubernetes anwenden
kubectl apply -f build/kubernetes/kubernetes.yml -n namespace

## Pod überprüfen
kubectl get pods -n namespace

## Konfiguration des Kubernetes Client
Konfiguration in src/main/application.properties:
# Vertrauen gegenüber unsicheren Zertifikaten
quarkus.kubernetes-client.trust-certs=true

# Standard-Namespace für den Client
quarkus.kubernetes-client.namespace=default

# Timeout für Verbindungen (optional)
quarkus.kubernetes-client.request-timeout=5000

# API-Server (optional, falls manuell gesetzt)
quarkus.kubernetes-client.master-url=https://your-k8s-api-server


Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
