# XChat
Projet scolaire de chat en réseau contenant Serveur et Client.

Pour communiquer entre deux clients vous n'avez donc besoin que de deux client et un serveur à lancer.
Vous pouvez modifier si besoin les fichiers sh afin d'utiliser un autre port/host.

___________________________

Linux :

Donner l'accès à user pour exécuter les scripts shell : chmod u+x ./fichier.sh

- Compiler les fichiers : ./compiler.sh
- Exécuter un serveur :   ./server.sh
- Exécuter un client :    ./client.sh


Windows :

Se placer dans Powershell
Au lieu de 'port' vous pouvez mettre 8132
Et pour 'host' si vous souhaitez l'hoster de manière locale mettez simplement : localhost

- Compiler les fichiers : javac *.java -encoding UTF-8
- Exécuter un serveur :   java XchatServer port
- Exécuter un client :    java XchatClient host port
___________________________
