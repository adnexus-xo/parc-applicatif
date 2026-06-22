--Création de la table CATEGORIE
CREATE TABLE CATEGORIE (
    id_categorie SERIAL,
    nom_categorie VARCHAR(50) UNIQUE NOT NULL,
    description_categorie VARCHAR(100) NOT NULL,
    CONSTRAINT pk_categorie PRIMARY KEY (id_categorie)
);

--Création de la table EQUIPE 
CREATE TABLE EQUIPE (
    id_equipe SERIAL,
    nom_equipe VARCHAR(50) UNIQUE NOT NULL,
    CONSTRAINT pk_equipe PRIMARY KEY (id_equipe)
);

--Création de la table APPLICATION
CREATE TABLE APPLICATION (
    id_application SERIAL,
    nom_application VARCHAR(50) UNIQUE NOT NULL,
    description TEXT NOT NULL,
    version VARCHAR(10) NOT NULL,
    etat VARCHAR(50) NOT NULL,
    criticite VARCHAR(20)  NOT NULL,
    date_creation_fiche TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_mise_en_service DATE,
    date_obsolescence DATE,
    id_categorie INTEGER NOT NULL,
    id_equipe_utilisatrice INTEGER NOT NULL,
    id_equipe_responsable INTEGER NOT NULL,
    CONSTRAINT pk_application PRIMARY KEY (id_application),
    CONSTRAINT fk_categorie_application FOREIGN KEY (id_categorie)
    REFERENCES CATEGORIE (id_categorie),
    CONSTRAINT fk_equipe_utilisatrice_application FOREIGN KEY (id_equipe_utilisatrice)
    REFERENCES EQUIPE (id_equipe),
    CONSTRAINT fk_equipe_responsable_application FOREIGN KEY (id_equipe_responsable)
    REFERENCES EQUIPE (id_equipe),
    CONSTRAINT chk_etat_application CHECK (etat IN (
        'En projet',
        'En service',
        'Obsolète',
        'A décommissionner',
        'Retirée',
        'Abandonnée'
    )),
    CONSTRAINT chk_criticite_application CHECK (criticite IN (
        'Vitale',
        'Importante',
        'Secondaire'
    ))
);

--Création de la table UTILISATEUR
CREATE TABLE UTILISATEUR (
    id_utilisateur SERIAL,
    nom_utilisateur VARCHAR(50) NOT NULL,
    prenom_utilisateur VARCHAR(50) NOT NULL,
    email VARCHAR(254) UNIQUE NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    id_equipe INTEGER,
    CONSTRAINT pk_utilisateur PRIMARY KEY (id_utilisateur),
    CONSTRAINT fk_equipe_utilisateur FOREIGN KEY (id_equipe)
    REFERENCES EQUIPE (id_equipe),
    CONSTRAINT chk_role_utilisateur CHECK (role IN (
        'Administrateur',
        'Support',
        'Lecteur'
    ))
);

--Création de la table DEPEND_DE
CREATE TABLE DEPEND_DE (
    id_application_source INTEGER NOT NULL,
    id_application_cible INTEGER NOT NULL,
    type_dependance VARCHAR(50) NOT NULL,
    description_dependance TEXT,
    date_creation_dependance TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_depend_de PRIMARY KEY (id_application_source, id_application_cible),
    CONSTRAINT fk_application_source_depend_de FOREIGN KEY (id_application_source) 
    REFERENCES APPLICATION(id_application),
    CONSTRAINT fk_application_cible_depend_de FOREIGN KEY (id_application_cible) 
    REFERENCES APPLICATION(id_application),
    CONSTRAINT chk_dependance_distincte CHECK (id_application_source <> id_application_cible),
    CONSTRAINT chk_type_dependance CHECK (type_dependance IN (
        'API',
        'Base de données',
        'Authentification',
        'Fichiers',
        'Messagerie',
        'Autre'
    ))
);


--Création de la table HISTORIQUE
CREATE TABLE HISTORIQUE (
    id_historique SERIAL,
    id_application INTEGER NOT NULL,
    id_utilisateur INTEGER NOT NULL,
    ancien_etat VARCHAR(50) NOT NULL,
    nouvel_etat VARCHAR(50) NOT NULL,
    date_changement_etat TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_historique PRIMARY KEY (id_historique),
    CONSTRAINT fk_application_historique FOREIGN KEY (id_application)
    REFERENCES APPLICATION (id_application),
    CONSTRAINT fk_utilisateur_historique FOREIGN KEY (id_utilisateur)
    REFERENCES UTILISATEUR (id_utilisateur),
    CONSTRAINT chk_ancien_etat_historique CHECK (ancien_etat IN (
        'En projet',
        'En service',
        'Obsolète',
        'A décommissionner',
        'Retirée',
        'Abandonnée'
    )),
    CONSTRAINT chk_nouvel_etat_historique CHECK (nouvel_etat IN (
        'En projet',
        'En service',
        'Obsolète',
        'À décommissionner',
        'Retirée',
        'Abandonnée'
    )),
    CONSTRAINT chk_etats_differents CHECK (ancien_etat <> nouvel_etat)
);

--Index pour les performances
CREATE INDEX idx_application_etat                ON APPLICATION (etat);
CREATE INDEX idx_application_criticite           ON APPLICATION (criticite);
CREATE INDEX idx_application_categorie           ON APPLICATION (id_categorie);
CREATE INDEX idx_application_equipe_utilisatrice ON APPLICATION (id_equipe_utilisatrice);
CREATE INDEX idx_application_equipe_responsable  ON APPLICATION (id_equipe_responsable);
CREATE INDEX idx_depend_de_source                ON DEPEND_DE (id_application_source);
CREATE INDEX idx_depend_de_cible                 ON DEPEND_DE (id_application_cible);
CREATE INDEX idx_historique_application          ON HISTORIQUE (id_application);
CREATE INDEX idx_historique_date                 ON HISTORIQUE (date_changement_etat);





