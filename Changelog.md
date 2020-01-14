Suite à notre soutenance, nous avons retenue plusieurs points:


## Les remarques de la soutenance et bugs trouver entre temps

1. Notre classe TeamBuilder est transmise partout mais son utilisation est mal intégré, on l'utilise en même temps en tant que "pokedex" (dans la mesure ou il contient tous les pokemons et leurs mouvements) et en tant que créateur d'équipe pour le joueur principal et on l'utilisait comme tel pour les sauvegarde

2. Il existe quelques petits bug qui relève des exceptions (tel que si le pokemon n'a que 3 capacité et que l'on clique sur la 4ème capacité vide)

3. La classe TeamBuilder manquait à sa vocation d'être correctement implémenter dans le MVC (on gérait les modifications de la vue à l'interieur de la classe qui était modèle)

4. Il manquait des écrans sur la défaite et la victoire solo

# Bug
 
5. Lors d'un load de sauvegarde depuis un combat, toute l'équipe était reset en terme de stat, ce qui ajoutait la feature de ne jamais pouvoir perdre

## Ce que nous avons réaliser depuis la soutenance

1. Notre classe teamBuilder à été correctement séparé entre la création de l'équipe et son rôle d'information sur les pokemons. Cette classe est maintenant, selon les recommandations de M. Duris, un singleton. Maintenant, pour construire une équipe, nous utilisons directement une instance de la classe Player, ce qui à régler le bug numéro 5 puisque maintenant on sauvegarde le joueur principal au lieu du teamBuilder de son équipe.

2. Tous les bugs connus on été résolue

3. La classe à été libéré de ses poids et maintenant, c'est le controlleur qui modifie correctement tout ça.

4. Les écrans ont été ajouter et ce grâce à un système d'énumération de donnée spéciales, entre chaques pages une données spéciale est passée et permet de spécifier un état précis tel que la victoire, la défaite ou (si contre une ligue) de passer à la suite. Ou de, comme nous voulions l'ajouter permettre d'utiliser la page de pokedex dont on se sert pour créer l'équipe du joueur, comme un vrai pokedex en passant une donnée spéciale comme "VIEWPOKEDEX" et la page s'adapterait en fonction de cette vue.

5. Le problème est donc résolu avec le point 1.

6. Nous avons ajouter une javadoc complète sur notre projet (ce fut long, très long)
