package com.progsan.atlantis.jpa.model;

import javax.persistence.*;

/**
 * Created by Erdal on 30.01.2016.
 */
@Entity
@Table(name = "CandidateCareerTech", schema = "", catalog = "")
public class CandidateCareerTechEntity {
    private int id;
    private String caption;
    private TechnologyEntity technology;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "caption", nullable = true, length = 32)
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
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

        CandidateCareerTechEntity that = (CandidateCareerTechEntity) o;

        if (id != that.id) return false;
        if (caption != null ? !caption.equals(that.caption) : that.caption != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (caption != null ? caption.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "techCode", referencedColumnName = "code", nullable = false)
    public TechnologyEntity getTechnology() {
        return technology;
    }

    public void setTechnology(TechnologyEntity technology) {
        this.technology = technology;
    }
}
