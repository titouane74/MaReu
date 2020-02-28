# OpenClassrooms

Le projet :
===========
Titre du projet : Ma réu
------------------------------
L'objectif de ce projet est de créer une application de gestion de réunions.
Les tests unitaires et instrumentalisés doivent s'exécutér sans échouer.

Fonctionnalités créées :
    - Ecran principale listant les réunions réservées :
        - alimenter la liste des réunions
        - ajouter un menu permetant de filtre les réunions par date ou par salle de réunion
        - ajouter un bouton d'ajout d'une nouvelle réunion
        - supprimer une réunion de la liste

    - Créer un écran permettant la création d'une nouvelle réunion :
        - sélectionner la salle de réunion parmis une liste prédéfinie
        - sélectionner la date et le créneau horaire via des boîtes de dialogue
        - gérer l'ajout des participants à la réunion : adresse valide
        - ajouter des boutons pour enregistrer la réunion ou annuler la saisie d'une réunion
        - s'assurer que tous les champs sont remplis au moment de la création
        - s'assurer que la salle de réunion est disponible

    - Vider la liste des réunions sur fermeture et rotation de l'application

    - Gérer différentes tailles d'écran et l'affichage portrait/paysage
        - l'affichage pour le mode téléphone est géré via des fragments
        - l'affichage pour le mode tablette est géré via des fragements


Le programme :
==============
Adresse GitHub du programme :
-----------------------------
    https://github.com/titouane74/MaReu.git

Installation :
------------
    - Télécharger le repository dans votre environnement local
    - Ouvrir Android Studio  et importer le projet MaReu

Exécution :
-----------
    - Choisir l'item "app" du Run/Debug configuration
    - Exécuter l'application


Les tests :
===========
Exécution et résultat des tests unitaires :
-------------------------------------------

    Dans le repository : MaReu/app/src/test/java/com/ocr/mareu/service/

	- ouvrir le fichier : AddMeetingTest.java
	- lancer l'exécution sur la class AddMeetingTest
	- résultat d'exécution dans le reposotiry MaReu/Test_Results/ :  Unitaires - Test Results - AddMeetingTest.html

	- ouvrir le fichier : InitializeRoomTest.java
	- lancer l'exécution sur la class InitializeRoomTest
	- résultat d'exécution dans le reposotiry MaReu/Test_Results/ :  Unitaires - Test Results - InitializeRoomTest.html

	- ouvrir le fichier : MeetingApiServiceTest.java
	- lancer l'exécution sur la class MeetingApiServiceTest
	- résultat d'exécution dans le reposotiry MaReu/Test_Results/ :  Unitaires - Test Results - MeetingApiServiceTest.html

    Dans le repository : MaReu/app/src/test/java/com/ocr/mareu/utils/

	- ouvrir le fichier : SortOrFilterTest.java
	- lancer l'exécution sur la class SortOrFilterTest
	- résultat d'exécution dans le reposotiry MaReu/Test_Results/ :  Unitaires - Test Results - SortOrFilterTest.html

	- ouvrir le fichier : ValidationTest.java
	- lancer l'exécution sur la class ValidationTest
	- résultat d'exécution dans le reposotiry MaReu/Test_Results/ :  Unitaires - Test Results - ValidationTest.html


Exécutions et résultats des tests instrumentalisés :
----------------------------------------------------

	Dans le repository : MaReu/app/src/androidTest/java/com/ocr/mareu/ui/activities/

	- ouvrir le fichier : AddMeetingRoomAvailabilityTest.java
	- lancer l'exécution sur la class AddMeetingRoomAvailabilityTest
	- résultat d'exécution dans le reposotiry MaReu/Test_Results/ : Instrumentalisés - Test Results - AddMeetingRoomAvaila___.html

	- ouvrir le fichier : AddMeetingTest.java
	- lancer l'exécution sur la class AddMeetingTest
	- résultat d'exécution dans le reposotiry MaReu/Test_Results/ : Instrumentalisés - Test Results - AddMeetingTest.html

	- ouvrir le fichier : MainActivityTest.java
	- lancer l'exécution sur la class MainActivityTest
	- résultat d'exécution dans le reposotiry MaReu/Test_Results/ : Instrumentalisés - Test Results - MainActivityTest.html

	- ouvrir le fichier : MainActivityWith10MeetingTest.java
	- lancer l'exécution sur la class MainActivityWith10MeetingTest
	- résultat d'exécution dans le reposotiry MaReu/Test_Results/ : Instrumentalisés - Test Results - MainActivityWith10Me___.html


La License :
============
OpenClassrooms
