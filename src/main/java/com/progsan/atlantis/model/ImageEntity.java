package com.progsan.atlantis.model;

import java.sql.Timestamp;

/**
 * Created by Erdal on 30.01.2016.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "Image")
public class ImageEntity {
    private Integer imageId;

    @javax.persistence.Id
    @javax.persistence.Column(name = "imageId", nullable = true)
    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    private String fileName;




    private String imageGroup;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "fileName", nullable = false, length = 255)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "imageGroup", nullable = true, length = 64)
    public String getImageGroup() {
        return imageGroup;
    }    private Timestamp modifiedOn;

    public void setImageGroup(String imageGroup) {
        this.imageGroup = imageGroup;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "modifiedOn", nullable = false)
    public Timestamp getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Timestamp modifiedOn) {
        this.modifiedOn = modifiedOn;
    }    private Integer version;

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

        ImageEntity that = (ImageEntity) o;

        if (imageId != null ? !imageId.equals(that.imageId) : that.imageId != null) return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        if (imageGroup != null ? !imageGroup.equals(that.imageGroup) : that.imageGroup != null) return false;
        if (modifiedOn != null ? !modifiedOn.equals(that.modifiedOn) : that.modifiedOn != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = imageId != null ? imageId.hashCode() : 0;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (imageGroup != null ? imageGroup.hashCode() : 0);
        result = 31 * result + (modifiedOn != null ? modifiedOn.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}
