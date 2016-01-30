package com.progsan.atlantis.model;

/**
 * Created by Erdal on 30.01.2016.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "CandidateCareerTask")
public class CandidateCareerTaskEntity {
    private int id;

    @javax.persistence.Id
    @javax.persistence.Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String summary;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "summary", nullable = false, length = 255)
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

        CandidateCareerTaskEntity that = (CandidateCareerTaskEntity) o;

        if (id != that.id) return false;
        if (summary != null ? !summary.equals(that.summary) : that.summary != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    private CandidateCareerEntity career;

    @javax.persistence.OneToOne(cascade = {})
    @javax.persistence.JoinColumn(name = "candidateCareerId", referencedColumnName = "id", nullable = false, table = "")
    public CandidateCareerEntity getCareer() {
        return career;
    }

    public void setCareer(CandidateCareerEntity career) {
        this.career = career;
    }
}
