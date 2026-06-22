--Création de la table CATEGORIE
CREATE TABLE categorie (
    id_categorie          SERIAL,
    nom_categorie         VARCHAR(50)  UNIQUE NOT NULL,
    description_categorie VARCHAR(255) NOT NULL,
    CONSTRAINT pk_categorie PRIMARY KEY (id_categorie)
);


-- ---------------------------------------------------------------------
-- Table EQUIPE
-- ---------------------------------------------------------------------
CREATE TABLE equipe (
    id_equipe  SERIAL,
    nom_equipe VARCHAR(50) UNIQUE NOT NULL,
    CONSTRAINT pk_equipe PRIMARY KEY (id_equipe)
);


-- ---------------------------------------------------------------------
-- Table APPLICATION
-- ---------------------------------------------------------------------
-- Changements :
--   • Deux clés étrangères vers EQUIPE : utilisatrice et responsable
--     (cohérent avec le diagramme de classes, deux associations distinctes).
--   • date_creation_fiche se remplit automatiquement à l'insertion :
--     c'est la date de création de la fiche dans l'application,
--     à distinguer de date_mise_en_service qui est une donnée métier.
--   • Le nom passe à 100 caractères (50 était court pour des noms réels).
--   • La description passe à TEXT pour ne pas se limiter à une phrase.
-- ---------------------------------------------------------------------
CREATE TABLE application (
    id_application          SERIAL,
    nom_application         VARCHAR(100) UNIQUE NOT NULL,
    description             TEXT         NOT NULL,
    version                 VARCHAR(20)  NOT NULL,
    etat                    VARCHAR(50)  NOT NULL,
    criticite               VARCHAR(20)  NOT NULL,
    date_creation_fiche     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    date_mise_en_service    DATE,
    date_fin_support        DATE,
    id_categorie            INTEGER      NOT NULL,
    id_equipe_utilisatrice  INTEGER      NOT NULL,
    id_equipe_responsable   INTEGER      NOT NULL,

    CONSTRAINT pk_application PRIMARY KEY (id_application),

    CONSTRAINT fk_categorie_application FOREIGN KEY (id_categorie)
        REFERENCES categorie (id_categorie),

    CONSTRAINT fk_equipe_utilisatrice_application FOREIGN KEY (id_equipe_utilisatrice)
        REFERENCES equipe (id_equipe),

    CONSTRAINT fk_equipe_responsable_application FOREIGN KEY (id_equipe_responsable)
        REFERENCES equipe (id_equipe),

    CONSTRAINT chk_etat_application CHECK (etat IN (
        'En projet',
        'En service',
        'Obsolète',
        'À décommissionner',
        'Retirée',
        'Abandonnée'
    )),

    CONSTRAINT chk_criticite_application CHECK (criticite IN (
        'Vitale',
        'Importante',
        'Secondaire'
    ))
);


-- ---------------------------------------------------------------------
-- Table UTILISATEUR
-- ---------------------------------------------------------------------
-- Changements :
--   • id_equipe devient nullable : un administrateur de l'outil n'est
--     pas forcément rattaché à une équipe métier.
--   • email passe à 254 caractères (norme RFC).
--   • mot_de_passe stocke un HASH (bcrypt, argon2…), jamais en clair.
-- ---------------------------------------------------------------------
CREATE TABLE utilisateur (
    id_utilisateur     SERIAL,
    nom_utilisateur    VARCHAR(50)  NOT NULL,
    prenom_utilisateur VARCHAR(50)  NOT NULL,
    email              VARCHAR(254) UNIQUE NOT NULL,
    mot_de_passe       VARCHAR(255) NOT NULL,
    role               VARCHAR(20)  NOT NULL,
    id_equipe          INTEGER,

    CONSTRAINT pk_utilisateur PRIMARY KEY (id_utilisateur),

    CONSTRAINT fk_equipe_utilisateur FOREIGN KEY (id_equipe)
        REFERENCES equipe (id_equipe),

    CONSTRAINT chk_role_utilisateur CHECK (role IN (
        'Administrateur',
        'Support',
        'Lecteur'
    ))
);


-- ---------------------------------------------------------------------
-- Table DEPEND_DE
-- ---------------------------------------------------------------------
-- Changements :
--   • Une dépendance ne peut pas pointer sur elle-même
--     (chk_dependance_distincte).
--   • Le type est contraint à une liste fermée plutôt qu'un texte libre.
-- ---------------------------------------------------------------------
CREATE TABLE depend_de (
    id_application_source    INTEGER     NOT NULL,
    id_application_cible     INTEGER     NOT NULL,
    type_dependance          VARCHAR(50) NOT NULL,
    description_dependance   VARCHAR(255),
    date_creation_dependance TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_depend_de PRIMARY KEY (id_application_source, id_application_cible),

    CONSTRAINT fk_application_source_depend_de FOREIGN KEY (id_application_source)
        REFERENCES application (id_application),

    CONSTRAINT fk_application_cible_depend_de FOREIGN KEY (id_application_cible)
        REFERENCES application (id_application),

    CONSTRAINT chk_dependance_distincte
        CHECK (id_application_source <> id_application_cible),

    CONSTRAINT chk_type_dependance CHECK (type_dependance IN (
        'API',
        'Base de données',
        'Authentification',
        'Fichiers',
        'Messagerie',
        'Autre'
    ))
);


-- ---------------------------------------------------------------------
-- Table HISTORIQUE
-- ---------------------------------------------------------------------
-- Changements :
--   • Lien direct vers APPLICATION via id_application : la table AVOIR
--     n'a plus lieu d'être (un changement d'état concerne une seule
--     application, donc une simple clé étrangère suffit).
--   • Ajout de id_utilisateur : on veut savoir QUI a fait le changement.
--   • TIMESTAMP au lieu de DATE : deux changements le même jour sont
--     distinguables et ordonnables dans le temps.
--   • CHECK sur ancien_etat et nouvel_etat pour cohérence avec APPLICATION.
-- ---------------------------------------------------------------------
CREATE TABLE historique (
    id_historique        SERIAL,
    id_application       INTEGER     NOT NULL,
    id_utilisateur       INTEGER     NOT NULL,
    ancien_etat          VARCHAR(50) NOT NULL,
    nouvel_etat          VARCHAR(50) NOT NULL,
    date_changement_etat TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    commentaire          TEXT,

    CONSTRAINT pk_historique PRIMARY KEY (id_historique),

    CONSTRAINT fk_application_historique FOREIGN KEY (id_application)
        REFERENCES application (id_application),

    CONSTRAINT fk_utilisateur_historique FOREIGN KEY (id_utilisateur)
        REFERENCES utilisateur (id_utilisateur),

    CONSTRAINT chk_ancien_etat_historique CHECK (ancien_etat IN (
        'En projet','En service','Obsolète','À décommissionner','Retirée','Abandonnée'
    )),

    CONSTRAINT chk_nouvel_etat_historique CHECK (nouvel_etat IN (
        'En projet','En service','Obsolète','À décommissionner','Retirée','Abandonnée'
    )),

    CONSTRAINT chk_etats_differents CHECK (ancien_etat <> nouvel_etat)
);


-- ---------------------------------------------------------------------
-- Index utiles pour les performances
-- ---------------------------------------------------------------------
-- Ces index ne sont pas obligatoires mais accélèrent les requêtes
-- fréquentes du tableau de bord et de la recherche.
-- ---------------------------------------------------------------------
CREATE INDEX idx_application_etat                ON application (etat);
CREATE INDEX idx_application_criticite           ON application (criticite);
CREATE INDEX idx_application_categorie           ON application (id_categorie);
CREATE INDEX idx_application_equipe_utilisatrice ON application (id_equipe_utilisatrice);
CREATE INDEX idx_application_equipe_responsable  ON application (id_equipe_responsable);
CREATE INDEX idx_depend_de_source                ON depend_de (id_application_source);
CREATE INDEX idx_depend_de_cible                 ON depend_de (id_application_cible);
CREATE INDEX idx_historique_application          ON historique (id_application);
CREATE INDEX idx_historique_date                 ON historique (date_changement_etat);


 