package com.cawstudios.clouddefence.services.outdated;

import com.cawstudios.clouddefence.models.package_manager.PackageManagerPackageModel;
import com.cawstudios.clouddefence.models.package_manager.PackageManagerPackageReleaseModel;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Singleton
public class OutdatedVersionServiceImpl implements OutdatedVersionService {
    @Override
    public List<PackageManagerPackageModel> getOutdatedPackages(List<PackageManagerPackageModel> packageManagerPackageModels) {
        List<PackageManagerPackageModel> managerPackageModels = new ArrayList<>();

        packageManagerPackageModels.forEach(x -> {
            if (isOutdatedPackage(x)) {
                managerPackageModels.add(x);
            }
        });
        return managerPackageModels;
    }

    private boolean isOutdatedPackage(PackageManagerPackageModel packageManagerPackageModel) {
        boolean isOutdatedVersion = false;

        TreeMap<String, List<PackageManagerPackageReleaseModel>> releases = packageManagerPackageModel.getReleases();

        if (releases != null) {
            String currentVersion = packageManagerPackageModel.getInfo().getVersion();
            List<String> keys = new ArrayList<>(releases.keySet());
            int indexOfLatestVersion =  keys.size() - 1;
            int indexOfCurrentVersion = keys.indexOf(currentVersion);

            if (indexOfCurrentVersion < indexOfLatestVersion) isOutdatedVersion = true;
        }

        return isOutdatedVersion;
    }
}
