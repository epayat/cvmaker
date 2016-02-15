package com.progsan.atlantis.jpa.model;

import javax.persistence.Version;
import java.sql.Timestamp;

/**
 * Created by Erdal on 30.01.2016.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "CandidateCareer")
public class CandidateCareerEntity {
    private int id;

    @javax.persistence.Id
    @javax.persistence.Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String careerType;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "careerType", nullable = false, length = 16)
    public String getCareerType() {
        return careerType;
    }

    public void setCareerType(String careerType) {
        this.careerType = careerType;
    }

    private Timestamp careerBegin;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "careerBegin", nullable = false)
    public Timestamp getCareerBegin() {
        return careerBegin;
    }

    public void setCareerBegin(Timestamp careerBegin) {
        this.careerBegin = careerBegin;
    }

    private Timestamp careerEnd;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "careerEnd", nullable = false)
    public Timestamp getCareerEnd() {
        return careerEnd;
    }

    public void setCareerEnd(Timestamp careerEnd) {
        this.careerEnd = careerEnd;
    }

    private Integer version;

    @Version
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

        CandidateCareerEntity that = (CandidateCareerEntity) o;

        if (id != that.id) return false;
        if (careerType != null ? !careerType.equals(that.careerType) : that.careerType != null) return false;
        if (careerBegin != null ? !careerBegin.equals(that.careerBegin) : that.careerBegin != null) return false;
        if (careerEnd != null ? !careerEnd.equals(that.careerEnd) : that.careerEnd != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (careerType != null ? careerType.hashCode() : 0);
        result = 31 * result + (careerBegin != null ? careerBegin.hashCode() : 0);
        result = 31 * result + (careerEnd != null ? careerEnd.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    private CandidateEntity candidate;

    @javax.persistence.ManyToOne(cascade = {})
    @javax.persistence.JoinColumn(name = "candidateId", referencedColumnName = "candidateId", nullable = false, table = "")
    public CandidateEntity getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateEntity candidate) {
        this.candidate = candidate;
    }

    private CompanyEntity company;

    @javax.persistence.OneToOne(cascade = {})
    @javax.persistence.JoinColumn(name = "companyId", referencedColumnName = "companyId", nullable = false, table = "")
    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }
}
