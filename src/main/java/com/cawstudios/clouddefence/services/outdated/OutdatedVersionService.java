package com.cawstudios.clouddefence.services.outdated;

import com.cawstudios.clouddefence.models.package_manager.PackageManagerPackageModel;

import java.util.List;

public interface OutdatedVersionService {
    List<PackageManagerPackageModel> getOutdatedPackages(List<PackageManagerPackageModel> packageManagerPackageModels);
}
