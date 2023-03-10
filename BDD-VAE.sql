create database vae;
create role vae login password 'vae';
alter database vae owner to vae;
CREATE TABLE Admin (
  idAdmin SERIAL NOT NULL, 
  email   varchar(75) NOT NULL, 
  mdp     varchar(75) NOT NULL, 
  PRIMARY KEY (idAdmin)
  );

CREATE TABLE Categorie (
  idCategorie  SERIAL NOT NULL, 
  nomCategorie varchar(75) NOT NULL, 
  PRIMARY KEY (idCategorie)
  );

CREATE TABLE Client (
  idClient SERIAL NOT NULL, 
  nom      varchar(75), 
  prenom   varchar(75) NOT NULL, 
  email    varchar(75) NOT NULL, 
  mdp      varchar(75) NOT NULL, 
  PRIMARY KEY (idClient)
  );

CREATE TABLE Commission (
  idCommission SERIAL NOT NULL, 
  pourcentage  int4, 
  PRIMARY KEY (idCommission)
  );

CREATE TABLE Enchere (
  idEnchere            SERIAL NOT NULL, 
  nomProduit           varchar(255) NOT NULL, 
  dateDebut            timestamp NOT NULL, 
  dateFin              timestamp NOT NULL, 
  prixMin              float8 NOT NULL, 
  description          varchar(255) NOT NULL, 
  idCategorie int4 NOT NULL, 
  idClient       int4 NOT NULL, 
  PRIMARY KEY (idEnchere),
  Foreign key(idCategorie) REFERENCES Categorie(idCategorie),
  Foreign key(idClient) REFERENCES Client(idClient)
  );

CREATE TABLE Mise (
  idMise           SERIAL NOT NULL, 
  dateMise         timestamp NOT NULL, 
  soldeMise        float8 NOT NULL, 
  idClient   int4 NOT NULL, 
  idEnchere int4 NOT NULL, 
  PRIMARY KEY (idMise),
  Foreign key(idClient) REFERENCES Client(idClient),
  Foreign key(idEnchere) REFERENCES Enchere(idEnchere)
  );

CREATE TABLE MvmtCompte (
  idMvmt         SERIAL NOT NULL, 
  montant        float8 NOT NULL, 
  dateMvmt       timestamp NOT NULL, 
  statut         int4 NOT NULL, 
  idClient int4 NOT NULL, 
  PRIMARY KEY (idMvmt),
  FOREIGN key(idClient) REFERENCES Client(idClient)
  );

CREATE TABLE PhotoEnchere (
  idPhotoEnchere   SERIAL NOT NULL, 
  photo            varchar(255) NOT NULL, 
  idEnchere int4 NOT NULL, 
  PRIMARY KEY (idPhotoEnchere),
  FOREIGN key(idEnchere) REFERENCES Enchere(idEnchere)
  );

CREATE TABLE ResultatEnchere (
  idResultat       SERIAL NOT NULL, 
  idEnchere int4 NOT NULL, 
  MiseidMise       int4 NOT NULL, 
  PRIMARY KEY (idResultat),
  FOREIGN key(idEnchere) REFERENCES Enchere(idEnchere)
  );

CREATE TABLE SoldeClient (
  idSolde        SERIAL NOT NULL, 
  montant        float8, 
  idClient int4 NOT NULL, 
  PRIMARY KEY (idSolde),
  Foreign key(idClient) REFERENCES Client(idClient)
  );

CREATE VIEW v_detailEnchere AS
  SELECT e.*,nomCategorie
  FROM Enchere e join Categorie c on e.idCategorie=c.idCategorie;

CREATE VIEW v_HistoriqueEnchere AS
  SELECT nom,prenom,soldeMise,dateMise
  FROM Client c join Mise m on c.idClient=m.idClient;






ALTER TABLE Enchere DROP CONSTRAINT FKEnchere39417;
ALTER TABLE PhotoEnchere DROP CONSTRAINT FKPhotoEnche405191;
ALTER TABLE Mise DROP CONSTRAINT FKMise233586;
ALTER TABLE Mise DROP CONSTRAINT FKMise453344;
ALTER TABLE MvmtCompte DROP CONSTRAINT FKMvmtCompte470086;
ALTER TABLE Enchere DROP CONSTRAINT FKEnchere553311;
ALTER TABLE SoldeClient DROP CONSTRAINT FKSoldeClien569343;
ALTER TABLE ResultatEnchere DROP CONSTRAINT FKResultatEn121037;
ALTER TABLE ResultatEnchere DROP CONSTRAINT FKResultatEn664810;
DROP VIEW IF EXISTS v_HistoriqueEnchere;
DROP VIEW IF EXISTS v_detailEnchere;
DROP TABLE IF EXISTS Admin CASCADE;
DROP TABLE IF EXISTS Categorie CASCADE;
DROP TABLE IF EXISTS Client CASCADE;
DROP TABLE IF EXISTS Commission CASCADE;
DROP TABLE IF EXISTS Enchere CASCADE;
DROP TABLE IF EXISTS Mise CASCADE;
DROP TABLE IF EXISTS MvmtCompte CASCADE;
DROP TABLE IF EXISTS PhotoEnchere CASCADE;
DROP TABLE IF EXISTS ResultatEnchere CASCADE;
DROP TABLE IF EXISTS SoldeClient CASCADE;
