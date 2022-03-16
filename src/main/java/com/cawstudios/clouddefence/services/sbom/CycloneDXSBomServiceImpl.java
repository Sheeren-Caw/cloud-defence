package com.cawstudios.clouddefence.services.sbom;

import com.cawstudios.clouddefence.helpers.ConsoleIOHelper;
import com.cawstudios.clouddefence.helpers.FileIOHelper;
import com.cawstudios.clouddefence.helpers.ObjectHelper;
import com.cawstudios.clouddefence.models.package_manager.PackageManagerPackageModel;
import com.cawstudios.clouddefence.models.package_manager.PackageManagerPackageReleaseModel;
import com.cawstudios.clouddefence.models.sbom.cyclone_dx.*;
import com.cawstudios.clouddefence.sbom.HashAlgoTypeEnum;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.*;

@Singleton
public class CycloneDXSBomServiceImpl implements SBomService {
    @Inject
    ConsoleIOHelper consoleIOHelper;

    @Override
    public SbomModel generateCycloneDXSBomFile(
            List<PackageManagerPackageModel> packageAndDependencyDetails,
            String filePath) {
        SbomModel sbomModel = new SbomModel();
        sbomModel.setBomFormat();
        sbomModel.setVersion();

        // Set components
        List<SbomComponentModel> sbomComponentModels = setComponent(packageAndDependencyDetails);
        sbomModel.setComponents(sbomComponentModels);

        // Set MetaData
        SbomMetaDataModel sBomMetaDataModel = new SbomMetaDataModel();
        sbomModel.setMetadata(sBomMetaDataModel);

        List<SbomDependencyModel> sbomDependencyModels = setDependency(packageAndDependencyDetails, sbomComponentModels);
        sbomModel.setDependencies(sbomDependencyModels);

        consoleIOHelper.println("Generating SBOM file for your project...");
        this.generateCycloneDXSBomFile(sbomModel, filePath + "sbom.json");

        return sbomModel;
    }

    @Override
    public boolean generateCycloneDXSBomFile(SbomModel sBomModel, String sbomFilePath) {
        String sbom = ObjectHelper.objectToString(sBomModel);
        boolean isFileCreated = FileIOHelper.createFile(sbomFilePath);
        if (isFileCreated) {
            FileIOHelper.writeToFile(sbomFilePath, sbom);
            return true;
        }
        return false;
    }

    private List<SbomDependencyModel> setDependency(
            List<PackageManagerPackageModel> packageManagerPackageModels,
            List<SbomComponentModel> sbomComponentModels) {

        List<SbomDependencyModel> sbomDependencyModels = new ArrayList<>();
        packageManagerPackageModels.forEach(packageModel -> {
            List<String> dependencies = packageModel.getDependencies();
            if (dependencies == null) {
                return;
            }
            List<UUID> dependsOn = new ArrayList<>();

            SbomDependencyModel sBomDependencyModel = new SbomDependencyModel();

            SbomComponentModel sBomComponentModel = sbomComponentModels.stream()
                    .filter(x -> Objects.equals(packageModel.getInfo().getName(), x.getName()))
                    .findFirst().orElse(new SbomComponentModel());

            sBomDependencyModel.setRef(sBomComponentModel.getBomRef());

            dependencies.forEach(packageName -> {
                SbomComponentModel sBomComponent;
                sBomComponent = sbomComponentModels.stream()
                        .filter(x -> Objects.equals(packageName, x.getName()))
                        .findFirst().orElse(new SbomComponentModel());

                if (sBomComponent.getBomRef() != null) {
                    dependsOn.add(sBomComponent.getBomRef());
                }
            });

            sBomDependencyModel.setDependsOn(dependsOn);
            sbomDependencyModels.add(sBomDependencyModel);
        });
        return sbomDependencyModels;
    }

    private SbomComponentModel setComponent(PackageManagerPackageModel packageManagerPackageModel) {
        SbomComponentModel sBomComponentModel = new SbomComponentModel();

        if (packageManagerPackageModel == null) {
            return sBomComponentModel;
        }
        TreeMap<String, List<PackageManagerPackageReleaseModel>> releases = packageManagerPackageModel.getReleases();

        if (releases != null) {
            String currentVersion = packageManagerPackageModel.getInfo().getVersion();
            List<PackageManagerPackageReleaseModel> packageManagerPackageReleaseModels = releases.get(currentVersion);
            sBomComponentModel.setHashes(setHashes(packageManagerPackageReleaseModels));
        }

        // Set SBom Component
        sBomComponentModel.setPublisher(packageManagerPackageModel.getInfo().getAuthor());
        sBomComponentModel.setName(packageManagerPackageModel.getInfo().getName());
        sBomComponentModel.setVersion(packageManagerPackageModel.getInfo().getVersion());
        sBomComponentModel.setType("library");
        sBomComponentModel.setPurl(packageManagerPackageModel.getPurl());
        sBomComponentModel.setBomRef(UUID.randomUUID());
        return sBomComponentModel;
    }

    private List<SbomComponentModel> setComponent(List<PackageManagerPackageModel> pypiModels) {
        List<SbomComponentModel> sbomComponentModels = new ArrayList<>();
        pypiModels.forEach(packageManagerPackageModel -> {
            if (packageManagerPackageModel.getInfo() == null) {
                return;
            }
            SbomComponentModel sBomComponentModel = setComponent(packageManagerPackageModel);
            sbomComponentModels.add(sBomComponentModel);
        });
        return sbomComponentModels;
    }

    private List<SbomHashModel> setHashes(List<PackageManagerPackageReleaseModel> packageManagerPackageReleaseModels) {
        PackageManagerPackageReleaseModel packageReleaseModel = packageManagerPackageReleaseModels.get(0);

        Map<String, String> hashes = packageReleaseModel.getDigests();
        List<SbomHashModel> hashModels = new ArrayList<>();

        hashes.forEach((key, value) -> {
            SbomHashModel sbomHashModel = new SbomHashModel();
            String alg;
            switch (key) {
                case "md5":
                    alg = HashAlgoTypeEnum.MD_5.label;
                    break;
                case "sha256":
                    alg = HashAlgoTypeEnum.SHA_256.label;
                    break;
                default:
                    alg = HashAlgoTypeEnum.SHA_1.label;
            }
            sbomHashModel.setAlg(alg);
            sbomHashModel.setContent(value);
            hashModels.add(sbomHashModel);
        });

        return hashModels;
    }
}
