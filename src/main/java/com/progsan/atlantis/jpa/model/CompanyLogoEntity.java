package com.progsan.atlantis.jpa.model;

import javax.persistence.Version;

/**
 * Created by Erdal on 30.01.2016.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "CompanyLogo")
public class CompanyLogoEntity {
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

        CompanyLogoEntity that = (CompanyLogoEntity) o;

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

    private CompanyEntity company;

    @javax.persistence.ManyToOne(cascade = {})
    @javax.persistence.JoinColumn(name = "companyId", referencedColumnName = "companyId", nullable = false, table = "")
    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    private ImageEntity logo;

    @javax.persistence.OneToOne(cascade = {})
    @javax.persistence.JoinColumn(name = "logoId", referencedColumnName = "imageId", nullable = false, table = "")
    public ImageEntity getLogo() {
        return logo;
    }

    public void setLogo(ImageEntity logo) {
        this.logo = logo;
    }
}
