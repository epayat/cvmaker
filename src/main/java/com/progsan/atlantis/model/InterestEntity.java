package com.progsan.atlantis.model;

/**
 * Created by Erdal on 30.01.2016.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "Interest", schema = "", catalog = "")
public class InterestEntity {
    private String code;

    @javax.persistence.Id
    @javax.persistence.Column(name = "code", nullable = false, length = 32)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

        InterestEntity that = (InterestEntity) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}
