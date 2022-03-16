package com.cawstudios.clouddefence.models.package_manager.pypi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.TreeMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PYPIModel {
    private PYPIInfoModel info;

    @JsonProperty("last_serial")
    private int lastSerial;

    private String purl;

    private List<String> dependencies;

    private TreeMap<String, List<PYPIReleaseModel>> releases;
}
