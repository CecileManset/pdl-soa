#####G�N�RATION DES WSDL#####

faire sur le dossier pdl-soa ou sur chaque projet
-un "clean and build" ou "build" sur netbeans 
-un "mvn clean install" ou "mvn install" en ligne de commande

je sais plus s'il faut produire un wsdl par web service ou par m�thode de web service
pour l'instant c'est par WS mais c'est facile � corriger au pire

#####D�PLOIEMENT#####

dans le dossier maven ( apache-maven-x.x.x) r�cup�rer le fichier settings.xml dans conf
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
		
�a a pas un grand int�r�t pour l'instant 
parce que c'est la config par default mais pour plus tard �a sera utile

je l'ai d�ploy� sur glassfish4x donc dont le pom du provider et du consumer y'a un glassfish4x
si vous voulez le d�ployer sur une autre version changez le nom
(ex: pour glassfish3.1 --> glassfish3x)

pour le d�ployer 
faire un "mvn deploy" en ligne de commande sur le dossier parent (j'ai supprim� le d�ploiement de l'orchestrator 
donc pas de soucis) ou sur chaque WS

je suis pas sur de ce que fait le run de netbeans donc pour l'instant �a revient
au m�me mais je crois que c'est parce que c'est en local

PS: j'ai pas relu et j'en ai marre donc si y'a des fautes c'est (presque) normal.