package com.cawstudios.clouddefence.sbom;

public enum HashAlgoTypeEnum {
    MD_5("MD5"),
    SHA_256("SHA-256"),
    SHA_1("SHA-1");

    public final String label;

    HashAlgoTypeEnum(String label) {
        this.label = label;
    }
}
