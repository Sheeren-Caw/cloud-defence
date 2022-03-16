package com.cawstudios.clouddefence.models.package_manager.pypi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PYPIInfoModel {

    private String author;

    @JsonProperty("author_email")
    private String authorEmail;

    private String description;

    private String license;

    private String name;

    @JsonProperty("package_url")
    private String packageUrl;

    @JsonProperty("project_url")
    private String projectUrl;

    private String version;

    private String summary;

    @JsonProperty("release_url")
    private String releaseUrl;

    @JsonProperty("requires_dist")
    private List<String> requiresDist;
}
