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
        - gérer l'ajout des participants à la réunion : adresse valable
        - ajouter des boutons pour enregistrer la réunion ou annuler la saisie d'une réunion
        - s'assurer que tous les champs sont remplis au moment de la création
        - s'assurer que la salle de réunion est disponible

    - Vider la liste des réunions sur fermeture et rotation de l'application

    - Gérer différentes tailles d'écran et l'affichage portrait/paysage
        - l'affichage pour le mode téléphone est géré via les activités
            qui sont alimentés par des fragments
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

    Dans le repository : Entrevoisins/app/src/test/java/com/openclassrooms/entrevoisins/service

	- ouvrir le fichier : NeighbourServiceTest.java du repository
	- lancer l'exécution sur la class NeighbourServiceTest
	- résultat d'exécution dans le reposotiry Entrevoisins/TestResult/ :  Passed - Test Results - NeighbourServiceTest.html

Exécutions et résultats des tests instrumentalisés :
----------------------------------------------------

	Dans le repository : Entrevoisins/app/src/androidTest/java/com/openclassrooms/entrevoisins/ui/neihgbour_list/

	- ouvrir le fichier : FavoriFragmentTest.java
	- lancer l'exécution sur la class FavoriFragmentTest
	- résultat d'exécution dans le reposotiry Entrevoisins/TestResult/ :  Passed UI - Test Results - FavoriFragmentTest.html

	- ouvrir le fichier : NeighbourActivityTest.java
	- lancer l'exécution sur la class NeighbourActivityTest
	- résultat d'exécution dans le reposotiry Entrevoisins/TestResult/ :  Passed UI - Test Results - NeighbourActivityTest.html

La License :
============
OpenClassrooms
