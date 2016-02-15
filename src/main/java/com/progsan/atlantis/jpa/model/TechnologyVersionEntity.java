package com.progsan.atlantis.jpa.model;

import javax.persistence.Version;

/**
 * Created by Erdal on 30.01.2016.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "TechnologyVersion")
public class TechnologyVersionEntity {
    private String verCode;

    @javax.persistence.Id
    @javax.persistence.Column(name = "verCode", nullable = false, length = 32)
    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    private String techVersion;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "techVersion", nullable = true, length = 16)
    public String getTechVersion() {
        return techVersion;
    }

    public void setTechVersion(String techVersion) {
        this.techVersion = techVersion;
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

        TechnologyVersionEntity that = (TechnologyVersionEntity) o;

        if (verCode != null ? !verCode.equals(that.verCode) : that.verCode != null) return false;
        if (techVersion != null ? !techVersion.equals(that.techVersion) : that.techVersion != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = verCode != null ? verCode.hashCode() : 0;
        result = 31 * result + (techVersion != null ? techVersion.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    private TechnologyEntity technology;

    @javax.persistence.ManyToOne(cascade = {})
    @javax.persistence.JoinColumn(name = "techCode", referencedColumnName = "code", nullable = false, table = "")
    public TechnologyEntity getTechnology() {
        return technology;
    }

    public void setTechnology(TechnologyEntity technology) {
        this.technology = technology;
    }

    private ImageEntity icon;

    @javax.persistence.OneToOne(cascade = {})
    @javax.persistence.JoinColumn(name = "iconId", referencedColumnName = "imageId", nullable = true, table = "")
    public ImageEntity getIcon() {
        return icon;
    }

    public void setIcon(ImageEntity icon) {
        this.icon = icon;
    }
}
