package com.cawstudios.clouddefence.sbom;

public enum SbomFormatEnum {
    CYCLONE_DX ("CycloneDX");

    public final String label;

    SbomFormatEnum(String label) {
        this.label = label;
    }
}
