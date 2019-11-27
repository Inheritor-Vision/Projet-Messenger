email: cedric.lefebvre@laas.fr
email: yangui@insa-toulouse.fr

Le user case s'ouvre avec draw.io (open from other source : git)

Informations et hypothèses faites sur l'architecture:  

-La connexion se fait par la vérification du username - password et passe par le serveur.

-Afin de pouvoir récupérer l'historique des conversations quelle que soit le pc où l'utilisateur est connecté, il y a une récupération de cette historique auprès du serveur central à la connexion et une sauvegarde à la déconnexion de l'utilisateur.

-Les conversations sont aussi sauvegardés en local, ainsi que la liste des utilisateurs connectés. 

-La liste des utilisateurs est récupéré à la connexion, lorsque l'application signifie qu'elle est connectée au réseau interne. Elle    recoit en retour un paquet de tous les utilisateurs connectés afin de pouvoir créer la liste. 
 Nous pensons plus efficace, qu'au lieu d'avoir une réponse de toutes les personnes connectées, il serait plus simple de récupéré cette liste auprès d'une des applications déja connecté (Ceci n'apparait PAS dans les diagrammes)
 
-La connexion interne et externe se différencie par l'interposition d'un serveur
 
-En réseau externe, le serveur s'enterpose entre l'emetteur et le receveur (Ceci n'apparait pas dans le diagramme EnvoyerMessage)



Fermer App:

-Le Self-Message SupprimeCacheAEnvoyerCommun évite lors de la déconnexion d'envoyer au serveur une conversation qui a déja été envoyé par un autre agent qui s'est déja déconnecté.
 
