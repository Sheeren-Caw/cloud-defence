package com.cawstudios.clouddefence.models.package_manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageManagerPackageReleaseModel {

    private Map<String, String> digests;

    private String filename;

    private String packageType;

    private String uploadTime;
}
