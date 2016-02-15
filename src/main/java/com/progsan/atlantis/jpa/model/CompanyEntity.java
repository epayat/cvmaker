package com.progsan.atlantis.jpa.model;

import javax.persistence.Version;
import java.util.Collection;

/**
 * Created by Erdal on 30.01.2016.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "Company")
public class CompanyEntity {
    private Integer companyId;

    @javax.persistence.Id
    @javax.persistence.Column(name = "companyId", nullable = true)
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    private String description;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private int companySize;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "companySize", nullable = false)
    public int getCompanySize() {
        return companySize;
    }

    public void setCompanySize(int companySize) {
        this.companySize = companySize;
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

        CompanyEntity that = (CompanyEntity) o;

        if (companySize != that.companySize) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = companyId != null ? companyId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + companySize;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    private ImageEntity logo;

    @javax.persistence.OneToOne(cascade = {})
    @javax.persistence.JoinColumn(name = "defaultLogoId", referencedColumnName = "imageId", nullable = true, table = "")
    public ImageEntity getLogo() {
        return logo;
    }

    public void setLogo(ImageEntity logo) {
        this.logo = logo;
    }

    private IndustryEntity industry;

    @javax.persistence.ManyToOne(cascade = {})
    @javax.persistence.JoinColumn(name = "industryId", referencedColumnName = "industryId", nullable = false, table = "")
    public IndustryEntity getIndustry() {
        return industry;
    }

    public void setIndustry(IndustryEntity industry) {
        this.industry = industry;
    }

    private Collection<CompanyIndustryEntity> industries;

    @javax.persistence.OneToMany(cascade = {}, mappedBy = "company")
    public Collection<CompanyIndustryEntity> getIndustries() {
        return industries;
    }

    public void setIndustries(Collection<CompanyIndustryEntity> industries) {
        this.industries = industries;
    }

    private Collection<CompanyLogoEntity> logos;

    @javax.persistence.OneToMany(cascade = {}, mappedBy = "company")
    public Collection<CompanyLogoEntity> getLogos() {
        return logos;
    }

    public void setLogos(Collection<CompanyLogoEntity> logos) {
        this.logos = logos;
    }
}
