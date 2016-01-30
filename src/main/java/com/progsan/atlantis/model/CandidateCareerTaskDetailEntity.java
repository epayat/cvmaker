package com.progsan.atlantis.model;

/**
 * Created by Erdal on 30.01.2016.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "CandidateCareerTaskDetail")
public class CandidateCareerTaskDetailEntity {
    private int id;

    @javax.persistence.Id
    @javax.persistence.Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String freeText;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "freeText", nullable = true, length = 2048)
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

        CandidateCareerTaskDetailEntity that = (CandidateCareerTaskDetailEntity) o;

        if (id != that.id) return false;
        if (freeText != null ? !freeText.equals(that.freeText) : that.freeText != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (freeText != null ? freeText.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    private CandidateCareerTaskEntity task;

    @javax.persistence.OneToOne(cascade = {})
    @javax.persistence.JoinColumn(name = "candidateCareerTaskId", referencedColumnName = "id", nullable = false, table = "")
    public CandidateCareerTaskEntity getTask() {
        return task;
    }

    public void setTask(CandidateCareerTaskEntity task) {
        this.task = task;
    }
}
