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
drop table CandidateCareerTech;
drop table Credentials;

CREATE TABLE Image(
  imageId INT PRIMARY KEY AUTO_INCREMENT,
  fileName VARCHAR(255) NOT NULL,
  imageGroup VARCHAR(64),
  data LONGBLOB,
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
  iconId INT NOT NULL,
  version INT,
  FOREIGN KEY fk_lang_icon (iconId) REFERENCES image(imageId)
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
  addressId INT,
  photoId INT,
  version INT,
  FOREIGN KEY (addressId) REFERENCES Address(addressId),
  FOREIGN KEY (photoId) REFERENCES Image(imageId),
  UNIQUE KEY (email)
);

CREATE TABLE CandidateInterest(
  id INT PRIMARY KEY AUTO_INCREMENT,
  candiateId INT NOT NULL,
  interest VARCHAR(32) NOT NULL,
  version INT,
  FOREIGN KEY (candiateId) REFERENCES Candidate(candidateId),
  FOREIGN KEY (interest) REFERENCES Interest(code)
);

CREATE TABLE Company(
  companyId INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(64) NOT NULL ,
  description VARCHAR(255),
  companySize INT not NULL,
  industryId INT NOT NULL,
  defaultLogoId INT,
  version INT,
  FOREIGN KEY (industryId)  REFERENCES Industry(industryId),
  FOREIGN KEY (defaultLogoId) REFERENCES Image(imageId)
);

CREATE TABLE CompanyIndustry(
  id INT PRIMARY KEY AUTO_INCREMENT,
  companyId INT NOT NULL,
  industryId INT NOT NULL,
  version INT,
  UNIQUE KEY (companyId, industryId),
  FOREIGN KEY (companyId) REFERENCES Company(companyId),
  FOREIGN KEY (industryId) REFERENCES Industry(industryId)
);

CREATE TABLE CompanyLogo(
  id INT PRIMARY KEY AUTO_INCREMENT,
  companyId INT NOT NULL,
  logoId INT NOT NULL,
  ownerCandidateId INT,
  version INT,
  UNIQUE KEY(companyId, logoId),
  FOREIGN KEY (companyId) REFERENCES Company(companyId),
  FOREIGN KEY (logoId) REFERENCES Image(imageId),
  FOREIGN KEY (ownerCandidateId) REFERENCES Candidate(candidateId)
);

CREATE TABLE Technology(
  code VARCHAR(32) PRIMARY KEY,
  parent VARCHAR(32),
  fullname VARCHAR(64),
  type VARCHAR(32) NOT NULL ,
  defaultIconId INT,
  logoId INT,
  version INT,
  FOREIGN KEY (parent)  REFERENCES Technology(code),
  FOREIGN KEY (defaultIconId) REFERENCES Image(imageId),
  FOREIGN KEY (logoId) REFERENCES Image(imageId)
);

CREATE TABLE TechnologyVersion(
  verCode VARCHAR(32) NOT NULL PRIMARY KEY,
  techCode VARCHAR(32) NOT NULL,
  iconId INT,
  techVersion VARCHAR(16),
  version INT,
  FOREIGN KEY (techCode) REFERENCES Technology(code),
  FOREIGN KEY (iconId)  REFERENCES Image(imageId)
);

CREATE TABLE CandidateSkill(
  id INT PRIMARY KEY AUTO_INCREMENT,
  candidateId INT NOT NULL,
  techCode VARCHAR(32) NOT NULL,
  freeText VARCHAR(64),
  version INT,
  UNIQUE KEY (candidateId, techCode),
  FOREIGN KEY (candidateId) REFERENCES Candidate(candidateId),
  FOREIGN KEY (techCode) REFERENCES Technology(code)
);

CREATE TABLE CandidateLanguage(
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  candidateId INT NOT NULL,
  lang VARCHAR(3) NOT NULL,
  level INT NOT NULL,
  version INT,
  UNIQUE KEY (candidateId, lang),
  FOREIGN KEY (candidateId) REFERENCES Candidate(candidateId),
  FOREIGN KEY (lang) REFERENCES Language(lang)
);

CREATE TABLE CandidateCareer(
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  candidateId INT NOT NULL,
  companyId INT NOT NULL,
  careerType VARCHAR(16) NOT NULL DEFAULT 'permanent',
  careerBegin TIMESTAMP NOT NULL ,
  careerEnd TIMESTAMP NOT NULL,
  version INT,
  UNIQUE KEY (candidateId, companyId, careerType, careerBegin),
  FOREIGN KEY (candidateId) REFERENCES Candidate(candidateId),
  FOREIGN KEY (companyId) REFERENCES Company(companyId)
);

CREATE TABLE CandidateCareerTask(
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  candidateCareerId INT NOT NULL,
  summary VARCHAR(255) NOT NULL,
  version INT,
  FOREIGN KEY (candidateCareerId) REFERENCES CandidateCareer(id)
);

CREATE TABLE CandidateCareerTaskDetail(
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  candidateCareerTaskId INT NOT NULL,
  freeText VARCHAR(2048),
  version INT,
  FOREIGN KEY (candidateCareerTaskId)  REFERENCES CandidateCareerTask(id)
);

CREATE TABLE CandidateCareerTech(
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  candidateCareerId INT NOT NULL,
  techCode VARCHAR(32) NOT NULL,
  caption VARCHAR(32),
  version INT,
  UNIQUE KEY (candidateCareerId, techCode),
  FOREIGN KEY (candidateCareerId) REFERENCES CandidateCareer(id),
  FOREIGN KEY (techCode) REFERENCES Technology(code)
);

CREATE TABLE Credentials(
  username VARCHAR(128) not null PRIMARY KEY,
  password VARCHAR(128),
  provider VARCHAR(32),
  authCode VARCHAR(128),
  accessToken VARCHAR(2048),
  refreshToken VARCHAR(2048),
  version INT
);