package com.progsan.atlantis.model;

/**
 * Created by Erdal on 30.01.2016.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "CandidateLanguage", schema = "", catalog = "")
public class CandidateLanguageEntity {
    private int id;

    @javax.persistence.Id
    @javax.persistence.Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int level;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "level", nullable = false)
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

        CandidateLanguageEntity that = (CandidateLanguageEntity) o;

        if (id != that.id) return false;
        if (level != that.level) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + level;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    private LanguageEntity language;

    @javax.persistence.OneToOne(cascade = {})
    @javax.persistence.JoinColumn(name = "lang", referencedColumnName = "lang", nullable = false, table = "")
    public LanguageEntity getLanguage() {
        return language;
    }

    public void setLanguage(LanguageEntity language) {
        this.language = language;
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
