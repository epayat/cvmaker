package com.progsan.atlantis.model;

import java.util.Collection;

/**
 * Created by Erdal on 30.01.2016.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "Technology", schema = "", catalog = "")
public class TechnologyEntity {
    private String code;

    @javax.persistence.Id
    @javax.persistence.Column(name = "code", nullable = true, length = 32)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String fullname;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "fullname", nullable = true, length = 64)
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    private String type;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "type", nullable = false, length = 32)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    private ImageEntity defaultIcon;

    @javax.persistence.OneToOne(cascade = {})
    @javax.persistence.JoinColumn(name = "defaultIconId", referencedColumnName = "imageId", nullable = true, table = "")
    public ImageEntity getDefaultIcon() {
        return defaultIcon;
    }

    public void setDefaultIcon(ImageEntity defaultIcon) {
        this.defaultIcon = defaultIcon;
    }

    private ImageEntity logo;

    @javax.persistence.OneToOne(cascade = {})
    @javax.persistence.JoinColumn(name = "logoId", referencedColumnName = "imageId", nullable = true, table = "")
    public ImageEntity getLogo() {
        return logo;
    }

    public void setLogo(ImageEntity logo) {
        this.logo = logo;
    }

    private TechnologyEntity parent;

    @javax.persistence.ManyToOne(cascade = {})
    @javax.persistence.JoinColumn(name = "parent", referencedColumnName = "code", nullable = true, table = "")
    public TechnologyEntity getParent() {
        return parent;
    }

    public void setParent(TechnologyEntity parent) {
        this.parent = parent;
    }

    private Collection<TechnologyEntity> children;

    @javax.persistence.OneToMany(cascade = {}, mappedBy = "parent")
    public Collection<TechnologyEntity> getChildren() {
        return children;
    }

    public void setChildren(Collection<TechnologyEntity> children) {
        this.children = children;
    }

    private Collection<TechnologyVersionEntity> versions;

    @javax.persistence.OneToMany(cascade = {}, mappedBy = "technology")
    public Collection<TechnologyVersionEntity> getVersions() {
        return versions;
    }

    public void setVersions(Collection<TechnologyVersionEntity> versions) {
        this.versions = versions;
    }
}
