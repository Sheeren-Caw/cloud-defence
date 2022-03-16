package com.cawstudios.clouddefence.models.sbom.cyclone_dx;

import com.cawstudios.clouddefence.sbom.CycloneDXSbomVersionEnum;
import com.cawstudios.clouddefence.sbom.SbomFormatEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Data
public class SbomModel {

    @JsonProperty("$schema")
    private String schema;

    private String bomFormat;

    private String specVersion;

    private String serialNumber;

    private int version;

    List<SbomComponentModel> components;

    List<SbomDependencyModel> dependencies;

    SbomMetaDataModel metadata;


    public SbomModel() {
        this.setSchema();
        this.setBomFormat();
        this.setSpecVersion();
        this.setSerialNumber();
        this.setVersion();
    }

    public void setBomFormat() {
        this.bomFormat = SbomFormatEnum.CYCLONE_DX.label;
    }

    public void setSchema() {
        this.schema = CycloneDXSbomVersionEnum.SCHEMA.label;
    }

    public void setSpecVersion() {
        this.specVersion = CycloneDXSbomVersionEnum.SPEC_VERSION1_4.label;
    }

    public void setSerialNumber() {
        this.serialNumber = "urn:uuid:" + UUID.randomUUID();
    }

    public void setVersion() {
        this.version = 1;
    }

}
