package com.progsan.atlantis.jpa.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Erdal on 30.01.2016.
 */
@Entity
@Table(name = "Technology", schema = "", catalog = "")
public class TechnologyEntity {
    private String code;
    private String fullname;
    private String type;
    private Integer version;
    private ImageEntity defaultIcon;
    private ImageEntity logo;
    private TechnologyEntity parent;
    private Collection<TechnologyEntity> children;
    private Collection<TechnologyVersionEntity> versions;
    private Collection<CandidateCareerTechEntity> careers;

    @Id
    @Column(name = "code", nullable = true, length = 32)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "fullname", nullable = true, length = 64)
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 32)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "version", nullable = true)
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

        TechnologyEntity that = (TechnologyEntity) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (fullname != null ? !fullname.equals(that.fullname) : that.fullname != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (fullname != null ? fullname.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @OneToOne(cascade = {})
    @JoinColumn(name = "defaultIconId", referencedColumnName = "imageId", nullable = true, table = "")
    public ImageEntity getDefaultIcon() {
        return defaultIcon;
    }

    public void setDefaultIcon(ImageEntity defaultIcon) {
        this.defaultIcon = defaultIcon;
    }

    @OneToOne(cascade = {})
    @JoinColumn(name = "logoId", referencedColumnName = "imageId", nullable = true, table = "")
    public ImageEntity getLogo() {
        return logo;
    }

    public void setLogo(ImageEntity logo) {
        this.logo = logo;
    }

    @ManyToOne(cascade = {})
    @JoinColumn(name = "parent", referencedColumnName = "code", nullable = true, table = "")
    public TechnologyEntity getParent() {
        return parent;
    }

    public void setParent(TechnologyEntity parent) {
        this.parent = parent;
    }

    @OneToMany(cascade = {}, mappedBy = "parent")
    public Collection<TechnologyEntity> getChildren() {
        return children;
    }

    public void setChildren(Collection<TechnologyEntity> children) {
        this.children = children;
    }

    @OneToMany(cascade = {}, mappedBy = "technology")
    public Collection<TechnologyVersionEntity> getVersions() {
        return versions;
    }

    public void setVersions(Collection<TechnologyVersionEntity> versions) {
        this.versions = versions;
    }

    @OneToMany(mappedBy = "technology")
    public Collection<CandidateCareerTechEntity> getCareers() {
        return careers;
    }

    public void setCareers(Collection<CandidateCareerTechEntity> careers) {
        this.careers = careers;
    }
}
