--Création de la table CATEGORIE
CREATE TABLE CATEGORIE (
    idCategorie SERIAL,
    nomCategorie VARCHAR(50) UNIQUE NOT NULL,
    descriptionC VARCHAR(100) NOT NULL,
    CONSTRAINT pk_categorie PRIMARY KEY (idCategorie)
);

--Création de la table EQUIPE 
CREATE TABLE EQUIPE (
    idEquipe SERIAL,
    nomEquipe VARCHAR(50) UNIQUE NOT NULL,
    CONSTRAINT pk_equipe PRIMARY KEY (idEquipe)
);

--Création de la table APPLICATION
CREATE TABLE APPLICATION (
    idApplication SERIAL,
    nomApplication VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(100) NOT NULL,
    version VARCHAR(10) NOT NULL,
    etat VARCHAR(50) NOT NULL,
    dateCreation DATE NOT NULL,
    dateMiseEnService DATE,
    dateObsolescence DATE,
    idCategorie INTEGER NOT NULL,
    idEquipe INTEGER NOT NULL,
    CONSTRAINT pk_application PRIMARY KEY (idApplication),
    CONSTRAINT fk_categorie_application FOREIGN KEY (idCategorie)
    REFERENCES CATEGORIE (idCategorie),
    CONSTRAINT fk_equipe_application FOREIGN KEY (idEquipe)
    REFERENCES EQUIPE (idEquipe),
    CONSTRAINT chk_etat_application CHECK (etat IN (
        'En projet',
        'En service',
        'Obsolète',
        'A décommissionner',
        'Retirée',
        'Abandonnée'
    ))
);

--Création de la table UTILISATEUR
CREATE TABLE UTILISATEUR (
    idUtilisateur SERIAL,
    nomUtilisateur VARCHAR(50) NOT NULL,
    prenomUtilisateur VARCHAR(50) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    motDePasse VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    idEquipe INTEGER NOT NULL,
    CONSTRAINT pk_utilisateur PRIMARY KEY (idUtilisateur),
    CONSTRAINT fk_equipe_utilisateur FOREIGN KEY (idEquipe)
    REFERENCES EQUIPE (idEquipe),
    CONSTRAINT chk_role_utilisateur CHECK (role IN (
        'Administrateur',
        'Support',
        'Lecteur'
    ))
);


--Création de la table DEPEND_DE
CREATE TABLE DEPEND_DE (
    idApplicationSource INTEGER NOT NULL,
    idApplicationCible INTEGER NOT NULL,
    type VARCHAR(50) NOT NULL,
    descriptionD VARCHAR(100) NOT NULL,
    dateCreationD DATE NOT NULL,
    CONSTRAINT pk_depend_de PRIMARY KEY (idApplicationSource, idApplicationCible),
    CONSTRAINT fk_application_source_depend_de FOREIGN KEY (idApplicationSource) 
    REFERENCES APPLICATION(idApplication),
    CONSTRAINT fk_application_cible_depend_de FOREIGN KEY (idApplicationCible) 
    REFERENCES APPLICATION(idApplication) 
);



--Création de la table HISTORIQUE
CREATE TABLE HISTORIQUE (
    idHistorique SERIAL,
    ancienEtat VARCHAR(50) NOT NULL,
    nouvelEtat VARCHAR(50) NOT NULL,
    dateChangementEtat DATE NOT NULL,
    CONSTRAINT pk_historique PRIMARY KEY (idHistorique)
);

--Création de la table AVOIR
CREATE TABLE AVOIR (
    idHistorique INTEGER,
    idApplication INTEGER,
    CONSTRAINT pk_avoir PRIMARY KEY (idHistorique, idApplication),
    CONSTRAINT fk_historique_avoir FOREIGN KEY (idHistorique)
    REFERENCES HISTORIQUE(idHistorique),
    CONSTRAINT fk_application_avoir FOREIGN KEY (idApplication)
    REFERENCES APPLICATION(idApplication)
);

ALTER TABLE HISTORIQUE
    RENAME COLUMN idHistorique TO id_historique;

ALTER table HISTORIQUE
    RENAME COLUMN ancienEtat TO ancien_etat;
ALTER table HISTORIQUE
    RENAME COLUMN nouvelEtat TO nouvel_etat;
ALTER table HISTORIQUE
    RENAME COLUMN dateChangementEtat TO date_changement_etat;

ALTER TABLE CATEGORIE
    RENAME COLUMN idCategorie TO id_categorie;

ALTER TABLE CATEGORIE
    RENAME COLUMN nomCategorie TO nom_categorie;

ALTER TABLE CATEGORIE
    RENAME COLUMN descriptionC TO description_categorie;

ALTER TABLE EQUIPE
RENAME COLUMN idEquipe to id_equipe;

ALTER TABLE EQUIPE
RENAME COLUMN nomEquipe to nom_equipe;

ALTER TABLE APPLICATION
RENAME idApplication to id_application;
ALTER TABLE APPLICATION
RENAME nomApplication to nom_application;
ALTER TABLE APPLICATION
RENAME dateCreation to date_creation;
ALTER TABLE APPLICATION
RENAME dateMiseEnService to date_mise_en_service;
ALTER TABLE APPLICATION
RENAME dateObsolescence to date_obsolescence;

ALTER TABLE UTILISATEUR
RENAME COLUMN idUtilisateur TO id_utilisateur;
LTER TABLE UTILISATEUR
RENAME COLUMN nomUtilisateur TO nom_utilisateur;
LTER TABLE UTILISATEUR
RENAME COLUMN prenomUtilisateur TO prenom_utilisateur;
LTER TABLE UTILISATEUR
RENAME COLUMN motDePasse TO mot_de_passe;


ALTER TABLE DEPEND_DE 
RENAME COLUMN idApplicationSource TO id_application_source;  
ALTER TABLE DEPEND_DE 
RENAME COLUMN idApplicationCible TO id_application_cible;  
ALTER TABLE DEPEND_DE 
RENAME COLUMN descriptionD TO description_d;  
ALTER TABLE DEPEND_DE 
RENAME COLUMN dateCreationD TO date_creation_d;    

ALTER TABLE AVOIR
    RENAME COLUMN idHistorique TO id_historique;

ALTER TABLE AVOIR
RENAME idApplication to id_application;

ALTER TABLE HISTORIQUE
RENAME COLUMN ancienEtat TO ancien_etat;

ALTER TABLE APPLICATION
RENAME COLUMN idEquipe to id_equipe;

ALTER TABLE DEPEND_DE
RENAME COLUMN description_d TO description_dependance;

ALTER TABLE DEPEND_DE
RENAME COLUMN date_creation_d TO date_creation_dependance;

ALTER TABLE UTILISATEUR
RENAME COLUMN idEquipe TO id_equipe;
