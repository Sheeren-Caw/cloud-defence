package com.cawstudios.clouddefence.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageModel {
    private static final String PURL_PREFIX = "pkg:pypi/";

    private String name;

    private String version;

    private String purl;

    public void setPurl(String name, String version) {
        // Use this repo to generate purl github packageurl
        this.purl = PURL_PREFIX + name + "@" + version;
    }
}
