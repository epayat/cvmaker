package com.progsan.atlantis.jpa.model;

/**
 * Created by Erdal on 30.01.2016.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "Language")
public class LanguageEntity {
    private String lang;

    @javax.persistence.Id
    @javax.persistence.Column(name = "lang", nullable = false, length = 3)
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    private String name;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "name", nullable = false, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        LanguageEntity that = (LanguageEntity) o;

        if (lang != null ? !lang.equals(that.lang) : that.lang != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lang != null ? lang.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    private ImageEntity icon;

    @javax.persistence.OneToOne(cascade = {})
    @javax.persistence.JoinColumn(name = "iconId", referencedColumnName = "imageId", nullable = false, table = "")
    public ImageEntity getIcon() {
        return icon;
    }

    public void setIcon(ImageEntity icon) {
        this.icon = icon;
    }
}
