# Manuel Utilisateur

--------------------

## Table des matières

- [Table des matières](#table-des-matières)
- [Introduction](#introduction)
- [Installation](#installation)
- [Lancement de l'Application](#lancement-de-lapplication)
- [Objectif du Jeu](#objectif-du-jeu)
- [Création de fichiers de carte](#création-de-fichiers-de-carte)
- [Jouer au Jeu](#jouer-au-jeu)
  - [Règles du jeu](#règles-du-jeu)
  - [Contrôles et commandes](#contrôles-et-commandes)
- [Résumé](#résumé)
- [Conclusion](#conclusion)

\
\

## Introduction

<!-- Objectif du manuel utilisateur -->
Dans ce manuel, nous allons vous expliquer comment jouer au jeu **The Big Adventure**.\
Vous découvrirez ainsi les règles du jeu, les commandes à utiliser, ainsi que les différentes fonctionnalités disponibles.

<!-- Public cible -->
Ce manuel s'adresse à toute personne souhaitant jouer au jeu **The Big Adventure**.\
Il est destiné à être lu avant de commencer à jouer, afin de comprendre les règles du jeu et les diverses fonctionnalités disponibles.

## Installation

<!-- Instructions d'installation du projet -->
Pour jouer au jeu, vous devez tout d'abord télécharger le projet.\
Dans celui-ci vous trouverez un fichier jar `thebigadventure.jar`, c'est ce fichier qui vous permettra de jouer au jeu.\

Ce jeu à été développé en Java 21, il vous faudra donc une version de Java 21 ou supérieure pour pouvoir y jouer.\
Vous pouvez télécharger la dernière version de Java [ici](https://www.java.com/fr/download/).\
\pagebreak

## Lancement de l'Application

<!-- Comment exécuter le programme -->
Pour lancer le jeu, vous devez exécuter le fichier jar `thebigadventure.jar`.\
Pour cela utilisez la commande `java -jar thebigadventure.jar` dans le répertoire où se trouve le fichier jar.\
Vous verrez apparaitre un message d'erreur vous indiquant que vous n'avez pas saisi le bon nombre d'arguments.\
C'est normal, il vous faut en effet préciser une carte à charger.\
Pour cela, vous pouvez utiliser l'option `-l` ou `--level` suivit du chemin vers le fichier `.map` de la carte à charger.

Voici aussi les autres options disponibles :

- `-h`, `--help` : Affiche le menu d'aide
- `-l`, `--level` : Charge une carte à partir d'un fichier .map
- `-d`, `--dry-run` : Les entités ne se déplacent pas
- `-v`, `--validate` : Valide la carte sans tenter de l'afficher

Pour lancer le jeu, vous devez donc utiliser la commande `java -jar thebigadventure.jar -l <fichier.map>` ou bien `java -jar thebigadventure.jar <fichier.map>`\

<!-- Cartes mises à disposition des utilisateurs -->
Deux cartes sont mises à votre disposition dans le dossier `maps` du projet. Une map `demo.map` qui vous permettra de découvrir le jeu et une map `adventure.map` qui est la map du jeu final.

## Objectif du Jeu

<!-- Explication des principales fonctionnalités -->
Le but du jeu est de survivre le plus longtemps possible et de s'amuser, bien sûr.\
Pour cela, vous devrez vous déplacer dans la map, parler avec les personnages, combattre des ennemis et remporter des combats.\
Vous pourrez également échanger des objets avec les personnages et utiliser des objets pour vous soigner ou attaquer vos ennemis.\

## Création de fichiers de carte

<!-- Format attendu du fichier -->
Vous pouvez créer vos propres maps. Il vous suffira de respecter le format textuel donné et placer la map dans un fichier de texte brut avec l'extension `.map` que vous lancerez avec le jeu.\
Vous trouverez plus d'informations sur le format de la map dans le sujet du projet accessible [ici](https://monge.univ-mlv.fr/ens/Licence/L3/2023-2024/Java/project.php).\
Vous pouvez également consulter les maps déjà existantes dans le dossier `maps` du projet, vous aurez un bel aperçu du format de la map et des différents éléments disponibles.

Afin de vérifier que les maps que vous créez sont correctes, utilisez l'option `--validate` afin de seulement afficher les erreurs de l'analyse de la map dans la console sans afficher le jeu.
Aussi, pour vérifier les positions des entités qui bougent et ne pas être embêté par les ennemis, vous pouvez utiliser l'option `--dry-run`.
\pagebreak

## Jouer au Jeu

### Règles du jeu

Dans ce jeu, vous pouvez écouter des personnages vous parler, faire des échanges avec eux, passer des portes et combattre des ennemis.

Les ennemis se déplacent périodiquement : les plus aggressifs suivent même votre position. Lorsqu'ils se situent devant vous, ils peuvent vous attaquer et vous infliger des dégâts.
Vous pouvez également les attaquer lorsque vous vous situer devant eux et les tuer.

Attention, si vous perdez toute votre vie, le jeu est terminé (presque comme dans la vraie vie) !
Des mets vous permettront de regagner de la vie en les mangeant au cours du jeu.

Certain personnages vous laisseront échanger des objets avec eux. Le premier objet correspondant dans votre inventaire sera remplacé par l'objet échangé.

### Contrôles et commandes

Afin de jouer au jeu, vous aurez besoin des quatres flèches de votre clavier. Celles-ci permettent de se déplacer dans la carte, dans l'inventaire et dans les échanges.
Pour effectuer une action, comme utiliser son épée, manger sa nourriture ou parler avec un personnage, vous devrez utiliser la barre d'espace.
Pour ouvrir votre inventaire, utilisez la touche `I`. Une fois dans celui-ci, vous pourrez vous déplacer dedans avec les flèches et sélectionner l'élément que vous souhaitez prendre en main avec la barre d'espace.
La touche `Q` permet de fermer le jeu (il n'y a pas de sauvegarde).

## Résumé

Vérifiez que vous possédez l'exécutable `java` dans sa version 21.\
Lancez le jeu : `java -jar thebigadventure.jar adventure.map`\
Utilisez les flèches du clavier pour vous déplacer, la barre d'espace pour intéragir et la touche `I` pour ouvrir votre inventaire.
Utilisez `Q` pour quitter le jeu.

## Conclusion

<!-- Résumé des informations clés pour l'utilisateur -->
Vous l'aurez compris, le jeu **The Big Adventure** est un jeu de type aventure dans lequel vous devrez survivre le plus longtemps possible en combattant des ennemis et en échangeant des objets avec les personnages.
Vous pouvez créer vos propres maps et les charger dans le jeu afin de jouer sur vos propres cartes.

<!-- Encouragement à fournir des retours -->

Nous espérons que vous apprécierez le jeu et que vous passerez un bon moment à jouer.\
N'hésitez pas à nous faire des retours sur le jeu, nous sommes ouverts à toutes suggestions d'améliorations.

<!-- Contact -->
Pour nous contacter, vous trouverez [ici](https://github.com/nathan9567/thebigadventure) le lien vers le dépôt GitHub du projet. Vous pouvez aussi nous contacter par Discord : `florianclume` et `nathanchpln`.
