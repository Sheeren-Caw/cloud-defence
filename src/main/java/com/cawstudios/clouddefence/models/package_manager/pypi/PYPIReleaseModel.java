package com.cawstudios.clouddefence.models.package_manager.pypi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PYPIReleaseModel {

    private Map<String, String> digests;

    private String filename;

    @JsonProperty("packagetype")
    private String packageType;

    @JsonProperty("upload_time")
    private String uploadTime;
}
