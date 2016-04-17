package com.progsan.atlantis.jpa.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Erdal on 30.01.2016.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "Industry")
@NamedQuery(name = "industry.list", query = "select p from IndustryEntity p")
public class IndustryEntity implements Serializable {
    private Integer industryId;

    @javax.persistence.Id
    @javax.persistence.Column(name = "industryId", nullable = true)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndustryEntity that = (IndustryEntity) o;

        if (industryId != null ? !industryId.equals(that.industryId) : that.industryId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = industryId != null ? industryId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    private Collection<CompanyEntity> companies;

    @javax.persistence.OneToMany(cascade = {}, mappedBy = "industry")
    public Collection<CompanyEntity> getCompanies() {
        return companies;
    }

    public void setCompanies(Collection<CompanyEntity> companies) {
        this.companies = companies;
    }
}
