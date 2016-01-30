drop table candidatecareer;
drop table candidatecareertask;
drop table candidatecareertaskdetail;
drop table candidateinterest;
drop table candidatelanguage;
drop table candidateskill;
drop table company;
drop table companyindustry;
drop table companylogo;
drop table image;
drop table industry;
drop table interest;
drop table language;
drop table technology;
drop table technologyversion;

CREATE TABLE Image(
  imageId INT PRIMARY KEY AUTO_INCREMENT,
  fileName VARCHAR(255) NOT NULL,
  imageGroup VARCHAR(64),
  modifiedOn TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  version INT
);

CREATE TABLE Industry(
  industryId INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(64) NOT NULL
);

CREATE TABLE Language(
  lang VARCHAR(3) NOT NULL PRIMARY KEY ,
  name VARCHAR(64) NOT NULL ,
  iconId INT NOT NULL REFERENCES Image(iconId),
  version INT
);

CREATE TABLE Address(
  addressId INT PRIMARY KEY AUTO_INCREMENT,
  street1 VARCHAR(64) NOT NULL,
  street2 VARCHAR(64) NOT NULL,
  houseNr VARCHAR(16),
  postalCode VARCHAR(10),
  city VARCHAR(64),
  country VARCHAR(64),
  version INT
);

CREATE TABLE Interest(
  code VARCHAR(32) NOT NULL PRIMARY KEY,
  version INT
);

CREATE TABLE Candidate(
  candidateId INT PRIMARY KEY AUTO_INCREMENT,
  careerDesc VARCHAR(64) NOT NULL,
  firstName VARCHAR(64) NOT NULL,
  lastName VARCHAR(64) NOT NULL,
  email VARCHAR(128) NOT NULL,
  phone VARCHAR(64),
  maritalStatus VARCHAR(16),
  birthYear INT,
  salaryExpectation DOUBLE,
  salaryCurrency VARCHAR(3),
  earlyStartDate TIMESTAMP,
  addressId INT REFERENCES Address(addressId),
  photoId INT REFERENCES Image(imageId),
  version INT
);

CREATE TABLE CandidateInterest(
  id INT PRIMARY KEY AUTO_INCREMENT,
  candiateId INT NOT NULL  REFERENCES Candidate(candidateId),
  interest VARCHAR(32) NOT NULL REFERENCES Interest(code),
  version INT
);


CREATE TABLE Company(
  companyId INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(64) NOT NULL ,
  description VARCHAR(255),
  companySize INT not NULL,
  industryId INT NOT NULL REFERENCES Industry(industryId),
  defaultLogoId INT REFERENCES Image(imageId),
  version INT
);

CREATE TABLE CompanyIndustry(
  id INT PRIMARY KEY AUTO_INCREMENT,
  companyId INT NOT NULL REFERENCES Company(companyId),
  industryId INT NOT NULL REFERENCES Industry(industryId),
  version INT,
  UNIQUE KEY (companyId, industryId)
);

CREATE TABLE CompanyLogo(
  id INT PRIMARY KEY AUTO_INCREMENT,
  companyId INT NOT NULL REFERENCES Company(companyId),
  logoId INT NOT NULL REFERENCES Image(imageId),
  ownerCandidateId INT REFERENCES Candidate(candidateId),
  version INT,
  UNIQUE KEY(companyId, logoId)
);

CREATE TABLE Technology(
  code VARCHAR(32) PRIMARY KEY,
  parent VARCHAR(32) REFERENCES Technology(code),
  fullname VARCHAR(64),
  type VARCHAR(32) NOT NULL ,
  defaultIconId INT REFERENCES Image(imageId),
  logoId INT REFERENCES Image(imageId),
  version INT
);

CREATE TABLE TechnologyVersion(
  verCode VARCHAR(32) NOT NULL PRIMARY KEY,
  techCode VARCHAR(32) NOT NULL REFERENCES Technology(code),
  iconId INT REFERENCES Image(iconId),
  techVersion VARCHAR(16),
  version INT
);

CREATE TABLE CandidateSkill(
  id INT PRIMARY KEY AUTO_INCREMENT,
  candidateId INT NOT NULL REFERENCES Candidate(candidateId),
  techCode VARCHAR(32) NOT NULL REFERENCES Technology(code),
  freeText VARCHAR(64),
  version INT
);

CREATE TABLE CandidateLanguage(
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  candidateId INT NOT NULL REFERENCES Candidate(candidateId),
  lang VARCHAR(3) NOT NULL REFERENCES Language(lang),
  level INT NOT NULL,
  version INT
);

CREATE TABLE CandidateCareer(
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  candidateId INT NOT NULL REFERENCES Candidate(candidateId),
  companyId INT NOT NULL REFERENCES Company(companyId),
  careerType VARCHAR(16) NOT NULL DEFAULT 'permanent',
  careerBegin TIMESTAMP NOT NULL ,
  careerEnd TIMESTAMP NOT NULL,
  version INT
);

CREATE TABLE CandidateCareerTask(
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  candidateCareerId INT NOT NULL REFERENCES CandidateCareer(id),
  summary VARCHAR(255) NOT NULL,
  version INT
);

CREATE TABLE CandidateCareerTaskDetail(
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  candidateCareerTaskId INT NOT NULL REFERENCES CandidateCareerTask(id),
  freeText VARCHAR(2048),
  version INT
);