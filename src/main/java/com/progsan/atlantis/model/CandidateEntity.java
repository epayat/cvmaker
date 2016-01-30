package com.progsan.atlantis.model;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 * Created by Erdal on 30.01.2016.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "Candidate", schema = "", catalog = "")
public class CandidateEntity {
    private Integer candidateId;

    @javax.persistence.Id
    @javax.persistence.Column(name = "candidateId", nullable = true)
    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    private String careerDesc;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "careerDesc", nullable = false, length = 64)
    public String getCareerDesc() {
        return careerDesc;
    }

    public void setCareerDesc(String careerDesc) {
        this.careerDesc = careerDesc;
    }

    private String firstName;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "firstName", nullable = false, length = 64)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String lastName;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "lastName", nullable = false, length = 64)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String email;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "email", nullable = false, length = 128)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String phone;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "phone", nullable = true, length = 64)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String maritalStatus;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "maritalStatus", nullable = true, length = 16)
    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    private Integer birthYear;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "birthYear", nullable = true)
    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    private Double salaryExpectation;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "salaryExpectation", nullable = true, precision = 0)
    public Double getSalaryExpectation() {
        return salaryExpectation;
    }

    public void setSalaryExpectation(Double salaryExpectation) {
        this.salaryExpectation = salaryExpectation;
    }

    private String salaryCurrency;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "salaryCurrency", nullable = true, length = 3)
    public String getSalaryCurrency() {
        return salaryCurrency;
    }

    public void setSalaryCurrency(String salaryCurrency) {
        this.salaryCurrency = salaryCurrency;
    }

    private Timestamp earlyStartDate;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "earlyStartDate", nullable = true)
    public Timestamp getEarlyStartDate() {
        return earlyStartDate;
    }

    public void setEarlyStartDate(Timestamp earlyStartDate) {
        this.earlyStartDate = earlyStartDate;
    }

    private Integer version;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "version", nullable = true)
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CandidateEntity that = (CandidateEntity) o;

        if (candidateId != null ? !candidateId.equals(that.candidateId) : that.candidateId != null) return false;
        if (careerDesc != null ? !careerDesc.equals(that.careerDesc) : that.careerDesc != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (maritalStatus != null ? !maritalStatus.equals(that.maritalStatus) : that.maritalStatus != null)
            return false;
        if (birthYear != null ? !birthYear.equals(that.birthYear) : that.birthYear != null) return false;
        if (salaryExpectation != null ? !salaryExpectation.equals(that.salaryExpectation) : that.salaryExpectation != null)
            return false;
        if (salaryCurrency != null ? !salaryCurrency.equals(that.salaryCurrency) : that.salaryCurrency != null)
            return false;
        if (earlyStartDate != null ? !earlyStartDate.equals(that.earlyStartDate) : that.earlyStartDate != null)
            return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = candidateId != null ? candidateId.hashCode() : 0;
        result = 31 * result + (careerDesc != null ? careerDesc.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (maritalStatus != null ? maritalStatus.hashCode() : 0);
        result = 31 * result + (birthYear != null ? birthYear.hashCode() : 0);
        result = 31 * result + (salaryExpectation != null ? salaryExpectation.hashCode() : 0);
        result = 31 * result + (salaryCurrency != null ? salaryCurrency.hashCode() : 0);
        result = 31 * result + (earlyStartDate != null ? earlyStartDate.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    private Collection<CandidateCareerEntity> careers;

    @javax.persistence.OneToMany(cascade = {}, mappedBy = "candidate")
    public Collection<CandidateCareerEntity> getCareers() {
        return careers;
    }

    public void setCareers(Collection<CandidateCareerEntity> careers) {
        this.careers = careers;
    }

    private ImageEntity photo;

    @javax.persistence.OneToOne(cascade = {})
    @javax.persistence.JoinColumn(name = "photoId", referencedColumnName = "imageId", nullable = true, table = "")
    public ImageEntity getPhoto() {
        return photo;
    }

    public void setPhoto(ImageEntity photo) {
        this.photo = photo;
    }

    private List<CandidateInterestEntity> interests;

    @javax.persistence.OneToMany(cascade = {}, mappedBy = "candidate")
    public List<CandidateInterestEntity> getInterests() {
        return interests;
    }

    public void setInterests(List<CandidateInterestEntity> interests) {
        this.interests = interests;
    }

    private Collection<CandidateLanguageEntity> languages;

    @javax.persistence.OneToMany(cascade = {}, mappedBy = "candidate")
    public Collection<CandidateLanguageEntity> getLanguages() {
        return languages;
    }

    public void setLanguages(Collection<CandidateLanguageEntity> languages) {
        this.languages = languages;
    }

    private Collection<CandidateSkillEntity> skills;

    @javax.persistence.OneToMany(cascade = {}, mappedBy = "candidate")
    public Collection<CandidateSkillEntity> getSkills() {
        return skills;
    }

    public void setSkills(Collection<CandidateSkillEntity> skills) {
        this.skills = skills;
    }
}
