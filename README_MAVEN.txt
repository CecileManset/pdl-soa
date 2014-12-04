#####GÉNÉRATION DES WSDL#####

faire sur le dossier pdl-soa ou sur chaque projet
-un "clean and build" ou "build" sur netbeans 
-un "mvn clean install" ou "mvn install" en ligne de commande

je sais plus s'il faut produire un wsdl par web service ou par méthode de web service
pour l'instant c'est par WS mais c'est facile à corriger au pire

#####DÉPLOIEMENT#####

dans le dossier maven ( apache-maven-x.x.x) récupérer le fichier settings.xml dans conf
l'ajouter au .m2 (normalement dans votre dossier courant)
et ajouter entre <servers>

		<server>
			<id>glass1</id>
			<configuration>
				<cargo.hostname>localhost</cargo.hostname>
				<cargo.remote.username></cargo.remote.username>
				<cargo.remote.password></cargo.remote.password> 
				<cargo.glassfish.admin.port>4848</cargo.glassfish.admin.port>
				<cargo.glassfish.domain.name>domain1</cargo.glassfish.domain.name>
			</configuration>
		</server>
		
ça a pas un grand intérêt pour l'instant 
parce que c'est la config par default mais pour plus tard ça sera utile

je l'ai déployé sur glassfish4x donc dont le pom du provider et du consumer y'a un glassfish4x
si vous voulez le déployer sur une autre version changez le nom
(ex: pour glassfish3.1 --> glassfish3x)

pour le déployer 
faire un "mvn deploy" en ligne de commande sur le dossier parent (j'ai supprimé le déploiement de l'orchestrator 
donc pas de soucis) ou sur chaque WS

je suis pas sur de ce que fait le run de netbeans donc pour l'instant ça revient
au même mais je crois que c'est parce que c'est en local

PS: j'ai pas relu et j'en ai marre donc si y'a des fautes c'est (presque) normal.