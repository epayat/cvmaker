package com.progsan.atlantis.model;

/**
 * Created by Erdal on 30.01.2016.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "CandidateSkill", schema = "", catalog = "")
public class CandidateSkillEntity {
    private Integer id;

    @javax.persistence.Id
    @javax.persistence.Column(name = "id", nullable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String freeText;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "freeText", nullable = true, length = 64)
    public String getFreeText() {
        return freeText;
    }

    public void setFreeText(String freeText) {
        this.freeText = freeText;
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

        CandidateSkillEntity that = (CandidateSkillEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (freeText != null ? !freeText.equals(that.freeText) : that.freeText != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (freeText != null ? freeText.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    private TechnologyEntity technology;

    @javax.persistence.OneToOne(cascade = {})
    @javax.persistence.JoinColumn(name = "techCode", referencedColumnName = "code", nullable = false, table = "")
    public TechnologyEntity getTechnology() {
        return technology;
    }

    public void setTechnology(TechnologyEntity technology) {
        this.technology = technology;
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
}
