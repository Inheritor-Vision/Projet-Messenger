![](https://cdn.iconscout.com/icon/free/png-256/java-23-225999.png)![](https://3.bp.blogspot.com/--IDvjPRCaic/Vs9FDDfvHOI/AAAAAAAABDU/5umla_6QjBI/s1600/Eclipse-luna.png)
# Rapport POO : Projet Messenger
## Guide d'administration
Cette partie décrit comment déployer et installer l'application. 
### Spécification de l'application : 
 - **Nom** : MessengerApp.jar
 - **Taille** : 7,5 Mo 
 - **Ports utilisés** : [6666-6669] 
 - **Version de la JDK** : Java SE Development Kit 11.0.5+10-LTS 
 - **Commande d'exécution** : java -jar MessengerApp.jar 
 - **Librairies** :  
	* mysql-connector.jar 
	* sqlite-jdbc-3.21.0.jar  

### Spécification de la base de données centrale :
- **Nom du serveur** : srv-bdens.insa-toulouse.fr 
- ** IP du serveur (Dernier accès : 05/01/20) **: 10.10.40.6 
- ** Nom de la base de donnée**s : tpservlet_13 
- **Mot de passe** : La1yah4k 
- **Commande de connexion** : mysql -h srv-bdens.insa-toulouse.fr -D tpservlet_13 -u tpservlet_13 –p 
- **Version du client mySQL** : Ver 14.14 Distrib 5.7.29, for Linux (x86_64) using  EditLine wrapper 
- **Version du serveur** : 5.7.29-0ubuntu0.16.04.1 
- **Database engine** : innoDB version 5.7.29 
### Spécification du serveur de présence : 
- **Nom du serveur** : srv-bdens.insa-toulouse.fr 
- ** IP du serveur (Dernier accès : 05/01/20)** : 10.1.5.2 
- ** Version du serveur** : Apache Tomcat/9.0.16 (Ubuntu) 
- **Version de la JVM** : 11.0.4+11-post-Ubuntu-1ubuntu218.04.3 
- **Adresse du servlet** : hrv-gei-tomcat.insa-toulouse.fr/Messenger/ 
- **Librairie utilisée pour générer le servlet** :  
	- servlet-api-tomcat9.jar 
	- Common.jar (librairie créée pour ce projet) 
	
## Guide de déploiement de la base de données centrale :
Une fois le serveur mySQL déployé, il faut créer une base de données du nom de tpservlet_13 et un compte éponyme avec le code La1yah4k.  
Si vous désirez changer l'un de ces paramètres, vous devrez modifier les champs suivants dans le fichier Application.DBCentrale: 
```java
public class DBCentrale{
	private static String login = "tpservlet_13";
	private static String pswd = "La1yah4k";
	private static String URL = "jdbc:mysql://srv.bdens.insa-toulouse.fr:3306/"+login;
}
```
Il n'y a aucune manipulation à faire ensuite. Les tables sont gérées automatiquement par les différents agents. 
Pour voir si le serveur fonctionne, simplement se connecter via un client SQL avec la commande ci-dessus. 

## Guide de déploiement du serveur de présence :  
Une fois le serveur TOMCAT9 déployé, il suffit d'insérer le fichier Messenger.war fournit par l'interface manager de TOMCAT. Si celle-ci n'est pas installé, il suffit d'insérer le fichier Messenger dans le répertoire WEB racine de TOMCAT. Il faudra alors relancer TOMCAT pour que la modification soit prise en compte. 
Si vous désirez changer l'adresse du serveur, vous devrez modifier le champ suivant dans le fichier Application.InternalSocket : 

```java
public class InternalSocket{
	private static String PresenceServer = "https://srv-gei-tomcat.insa-toulouse.fr/Messenger/PresenceServer";
}
```
Il n'y a aucune autre manipulation à faire. Le servlet se met automatiquement à jour avec les différents agents. 
Pour voir si le servlet fonctionne, taper l'URL suivant : 
https://srv-gei-tomcat.insa-toulouse.fr/Messenger/PresenceServer 
Il retourne la liste des utilisateurs connectés. 

## Guide de déploiement de l'application : 
Pour déployer l’application, il faut que la machine sur laquelle est exécuté le fichier MessengerApp.jar possède au moins l'environnement JRE 11, disponible sur le site d'ORACLE. 
Un fois installé, il suffit de lancer la commande java -jar MessengerApp.jar et l'application se lance. Il est possible de copier le fichier jar n'importe où. 
