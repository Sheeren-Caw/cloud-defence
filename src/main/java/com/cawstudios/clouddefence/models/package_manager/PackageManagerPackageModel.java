package com.cawstudios.clouddefence.models.package_manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.TreeMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageManagerPackageModel {

    private PackageManagerPackageInfoModel info;

    private int lastSerial;

    private String purl;

    private List<String> dependencies;

    private TreeMap<String, List<PackageManagerPackageReleaseModel>> releases;
}
