package com.cawstudios.clouddefence.services.sbom;

import com.cawstudios.clouddefence.models.package_manager.PackageManagerPackageModel;
import com.cawstudios.clouddefence.models.sbom.cyclone_dx.SbomModel;

import java.util.List;

public interface SBomService {
    SbomModel generateCycloneDXSBomFile(List<PackageManagerPackageModel> packageAndDependencyDetails, String filePath);

    boolean generateCycloneDXSBomFile(SbomModel sBomModel, String sbomFilePath);
}

