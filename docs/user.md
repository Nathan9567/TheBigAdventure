# The Big Adventure - Manuel Utilisateur

--------------------

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

## Lancement de l'Application

<!-- Comment exécuter le programme -->
Pour lancer le jeu, vous devez exécuter le fichier jar `thebigadventure.jar`.\
Pour cela utilisez la commande `java -jar thebigadventure.jar` dans le répertoire où se trouve le fichier jar.\
Vous verrez apparaitre un message d'erreur vous indiquant que vous n'avez pas saisi le bon nombre d'arguments.\
Les voici aussi :

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

## Chargement de Fichiers map

<!-- Format attendu du fichier -->
Vous pouvez créer vos propres maps. Il vous suffira de respecter le format textuel donné et placer la map dans un fichier de texte brut que vous lancerez avec le jeu.

## Jouer au Jeu

<!-- Règles du jeu -->
Dans ce jeu, vous pouvez écouter des personnages vous parler, faire des échanges avec eux, passer des portes et combattre des ennemis.

Les ennemis se déplacent périodiquement : les plus aggressifs suivent même votre position. Lorsqu'ils se situent devant vous, ils peuvent vous attaquer et vous infliger des dégâts.
Vous pouvez également les attaquer lorsque vous vous situer devant eux et les tuer.

Attention, si vous perdez toute votre vie, le jeu est terminé (presque comme dans la vraie vie) !
Des mets vous permettront de regagner de la vie en les mangeant au cours du jeu.

Certain personnages vous laisseront échanger des objets avec eux. Le premier objet correspondant dans votre inventaire sera remplacé par l'objet échangé.

<!-- Contrôles et commandes -->
Afin de jouer au jeu, vous aurez besoin des quatres flèches de votre clavier. Celles-ci permettent de se déplacer dans la carte, dans l'inventaire et dans les échanges.
Pour effectuer une action, comme utiliser son épée, manger sa nourriture ou parler avec un personnage, vous devrez utiliser la barre d'espace.
Pour ouvrir votre inventaire, utilisez la touche `I`. Une fois dans celui-ci, vous pourrez vous déplacer dedans avec les flèches et sélectionner l'élément que vous souhaitez prendre en main avec la barre d'espace.
La touche `Q` permet de fermer le jeu (il n'y a pas de sauvegarde).

## Conclusion

<!-- Résumé des informations clés pour l'utilisateur -->
<!-- Encouragement à fournir des retours -->
