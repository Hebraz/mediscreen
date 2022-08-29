PRE REQUIS

Les éléments suivants doivent être installés sur le poste. 
    • Docker et docker-compose 
    • JAVA 11 
    • Node package 
    • Agular 

GENERATION DES JAR & IMAGES DOCKER

1. Configurer la variable d’environnement JAVA_HOME avec le chemin vers jdk11

2. Générer le JAR person-0.0.1-SNAPSHOT.jar : à la racine du projet « person », lancer la commande :

		./mvnw clean install -DskipTests

3. Générer l’image docker hebraz/api-person : à la racine du projet « person », lancer la commande :

		docker build -t hebraz/api-person .

4. Générer le JAR notes-0.0.1-SNAPSHOT.jar : à la racine du projet «notes», lancer la commande :

		./mvnw clean install -DskipTests

5. Générer l’image docker hebraz/api-notes : à la racine du projet «notes», lancer la commande :

		docker build -t hebraz/api-notes .

6. Générer le JAR report-0.0.1-SNAPSHOT.jar : à la racine du projet «report», lancer la commande :

		./mvnw clean install -DskipTests

7. Générer l’image docker hebraz/api-report : à la racine du projet «report», lancer la commande :

		docker build -t hebraz/api-report .

8 Générer l’application Angular mediscreen UI : à la racine du projet « mediscreenUI », lancer la commande : 

		ng build

9. Générer l’image docker hebraz/mediscreen-ui  : à la racine du projet « mediscreenUI », lancer la commande :

		docker build -t hebraz/mediscreen-ui .

DEMARRAGE DE L’APPLICATION

1. A la racine du projet global « mediscreen » lancer la commande :

 		docker-compose up -d

2. Dans un explorateur web, accéder à la page principale de l'application : http://localhost:8080/
