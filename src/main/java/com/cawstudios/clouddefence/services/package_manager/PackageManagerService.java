package com.cawstudios.clouddefence.services.package_manager;

import com.cawstudios.clouddefence.models.PackageModel;
import com.cawstudios.clouddefence.models.package_manager.PackageManagerPackageModel;

import java.util.List;

/*
Package Manager Service is used to get all package related information.
*/
public interface PackageManagerService {
    List<PackageManagerPackageModel> getPackageAndDependencyDetails(List<PackageModel> packageModels);

    PackageManagerPackageModel getPackageDetail(PackageModel packageModel);
}
