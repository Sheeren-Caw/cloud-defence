package com.cawstudios.clouddefence.sbom;

public enum CycloneDXSbomVersionEnum {
    SPEC_VERSION1_4("1.4"),
    SCHEMA("http://cyclonedx.org/schema/bom-1.4.schema.json");

    public final String label;

    CycloneDXSbomVersionEnum(String label) {
        this.label = label;
    }
}
