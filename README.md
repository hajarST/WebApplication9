
******Application Web de Gestion des Employés et Services******
Cette application web JSF (JavaServer Faces) avec Hibernate a pour objectif de gérer les services et les employés au sein d'une entreprise.


--Structure du Projet:
Le projet est structuré en packages pour une organisation claire du code source.


Package "entities"
Dans le package "entities", vous trouverez les entités Employe et Service. Ces entités sont utilisées pour représenter les employés et les services de l'entreprise.


Package "services"
Le package "services" contient les classes suivantes :


a. AbstractFacade
Une classe abstraite générique AbstractFacade qui implémente les fonctionnalités CRUD (Create, Read, Update, Delete) d'une entité générique T.


b. EmployeService et ServiceService
Deux classes, EmployeService et ServiceService, qui héritent de AbstractFacade pour fournir des fonctionnalités spécifiques de gestion des employés et des services.


--Pages Web:
*Page de Gestion des Services
La page web de gestion des services permet d'afficher, ajouter, éditer et supprimer des services. Elle utilise des composants JSF pour interagir avec la couche service.


*Page d'Ajout d'Employé
La page web d'ajout d'employé propose un formulaire avec les informations nécessaires telles que le nom, prénom, date de naissance, chef, service et une photo.


--Affichage des Collaborateurs d'un Service:
Une fonctionnalité est mise en place pour afficher les collaborateurs d'un service. Elle indique le chef de service ainsi que les employés relevant de sa responsabilité. Des composants PrimeFaces sont utilisés pour une interface utilisateur améliorée.


--Technologies Utilisées:
JavaServer Faces (JSF)
Hibernate
PrimeFaces (pour une interface utilisateur riche)
Base de données (configurée dans le fichier de configuration Hibernate)


--Instructions d'Exécution
Assurez-vous que Java, Apache Tomcat (ou un autre serveur compatible JSF), et une base de données compatible Hibernate sont installés sur votre machine.

Configurez le fichier de configuration Hibernate avec les paramètres de connexion à votre base de données.

Déployez l'application sur votre serveur d'applications.

Accédez à l'application à l'aide de l'URL appropriée (par exemple, http://localhost:8080/WebApplication9).

Utilisez les différentes pages web pour gérer les services et les employés.


--Démonstration simple:




https://github.com/hajarST/WebApplication9/assets/119755584/757e9024-42e1-4dc0-8fe4-584bd78ceeeb



