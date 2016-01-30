package com.progsan.atlantis.model;

/**
 * Created by Erdal on 30.01.2016.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "CandidateInterest", schema = "", catalog = "")
public class CandidateInterestEntity {
    private Integer id;

    @javax.persistence.Id
    @javax.persistence.Column(name = "id", nullable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

        CandidateInterestEntity that = (CandidateInterestEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    private CandidateEntity candidate;

    @javax.persistence.ManyToOne(cascade = {})
    @javax.persistence.JoinColumn(name = "candiateId", referencedColumnName = "candidateId", nullable = false, table = "")
    public CandidateEntity getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateEntity candidate) {
        this.candidate = candidate;
    }

    private InterestEntity interest;

    @javax.persistence.OneToOne(cascade = {})
    @javax.persistence.JoinColumn(name = "interest", referencedColumnName = "code", nullable = false, table = "")
    public InterestEntity getInterest() {
        return interest;
    }

    public void setInterest(InterestEntity interest) {
        this.interest = interest;
    }
}
