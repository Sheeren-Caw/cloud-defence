package com.cawstudios.clouddefence.models.package_manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageManagerPackageInfoModel {

    private String author;

    private String authorEmail;

    private String description;

    private String license;

    private String name;

    private String packageUrl;

    private String projectUrl;

    private String version;

    private String summary;

    private String releaseUrl;

    private List<String> requiresDist;
}
