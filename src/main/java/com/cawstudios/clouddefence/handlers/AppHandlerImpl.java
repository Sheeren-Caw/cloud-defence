package com.cawstudios.clouddefence.handlers;

import com.cawstudios.clouddefence.helpers.ConsoleIOHelper;
import com.cawstudios.clouddefence.models.PackageModel;
import com.cawstudios.clouddefence.models.package_manager.PackageManagerPackageModel;
import com.cawstudios.clouddefence.models.sbom.cyclone_dx.SbomModel;
import com.cawstudios.clouddefence.models.vulnerability.VulnerablePackageModel;
import com.cawstudios.clouddefence.parsers.ParserService;
import com.cawstudios.clouddefence.services.outdated.OutdatedVersionService;
import com.cawstudios.clouddefence.services.package_manager.PackageManagerService;
import com.cawstudios.clouddefence.services.sbom.SBomService;
import com.cawstudios.clouddefence.services.vulnerability.VulnerabilityDetectionService;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class AppHandlerImpl implements AppHandler {

    private final VulnerabilityDetectionService vulnerabilityDetectionService;
    private final ConsoleIOHelper consoleIOHelper;
    private final SBomService sbomService;
    private final PackageManagerService packageManagerService;
    private final ParserService parserService;
    private final OutdatedVersionService outdatedVersionService;


    public AppHandlerImpl(
            VulnerabilityDetectionService vulnerabilityDetectionService,
            ConsoleIOHelper consoleIOHelper,
            SBomService sbomService,
            PackageManagerService packageManagerService,
            ParserService parserService,
            OutdatedVersionService outdatedVersionService
    ) {
        this.vulnerabilityDetectionService = vulnerabilityDetectionService;
        this.consoleIOHelper = consoleIOHelper;
        this.sbomService = sbomService;
        this.packageManagerService = packageManagerService;
        this.parserService = parserService;
        this.outdatedVersionService = outdatedVersionService;
    }

    @Override
    public void run() {
        printProjectDescription();

        String filePath = readFilePath();
        String fileName = readFileName();

        List<PackageManagerPackageModel> packageManagerPackageModels = fetchPackagesData(filePath, fileName);

        SbomModel sbomModel = generateCycloneDXSbom(packageManagerPackageModels, filePath);

        discoverPubliclyKnownVulnerabilities(sbomModel);

        discoverOutdatedVersions(packageManagerPackageModels);
    }

    private void printProjectDescription() {
        consoleIOHelper.generateBlankLines(30);
        consoleIOHelper.println("--------------------------------------------UTILITY----------------------------------------------------");
        consoleIOHelper.generateBlankLines(2);
        consoleIOHelper.println("CAW Studios");
        consoleIOHelper.generateBlankLines(1);
        consoleIOHelper.println("Java");
        consoleIOHelper.println("Spring Boot");
        consoleIOHelper.println("-------------------------------------------------------------------------------------------------------");
        consoleIOHelper.generateBlankLines(2);
        consoleIOHelper.println("This utility does following steps:");
        consoleIOHelper.println("1. Takes project folder path and package/requirement file name as input.");
        consoleIOHelper.println("2. Generates SBom file for project.");
        consoleIOHelper.println("3. Run package vulnerability checks.");
        consoleIOHelper.println("4. Run package outdated version checks.");
        consoleIOHelper.println("-------------------------------------------------------------------------------------------------------");
        consoleIOHelper.generateBlankLines(4);
    }

    private String readFilePath() {
        consoleIOHelper.println("Please enter the project path(eg: D:/CAW Project/Select10x/Repo/Select10X.CoreAPIs/):");
        String filePath = consoleIOHelper.readString();

        consoleIOHelper.println("-------------------------------------------------------------------------------------------------------");
        consoleIOHelper.generateBlankLines(2);
        consoleIOHelper.println("Fetching all Packages from your project...");
        consoleIOHelper.generateBlankLines(1);

        return filePath;
    }

    private String readFileName() {
        consoleIOHelper.println("Please enter the project requirement file path(eg: requirements.txt):");

        return consoleIOHelper.readString();
    }

    private List<PackageManagerPackageModel> fetchPackagesData(String filePath, String fileName) {

        consoleIOHelper.println("-------------------------------------------------------------------------------------------------------");
        consoleIOHelper.generateBlankLines(2);
        consoleIOHelper.println("Fetching all Packages from your project...");
        consoleIOHelper.generateBlankLines(1);

        List<PackageModel> packageModels = parserService.getPackagesByFilePath(filePath + fileName);
        return packageManagerService.getPackageAndDependencyDetails(packageModels);
    }

    private SbomModel generateCycloneDXSbom(List<PackageManagerPackageModel> packageManagerPackageModels, String filePath) {
        return sbomService.generateCycloneDXSBomFile(packageManagerPackageModels, filePath);
    }

    private void discoverPubliclyKnownVulnerabilities(SbomModel sbomModel) {
        consoleIOHelper.generateBlankLines(1);
        consoleIOHelper.println("Checking Vulnerable Packages...");
        List<VulnerablePackageModel> vulnerablePackageModels = vulnerabilityDetectionService.checkVulnerabilityByBomDto(sbomModel);

        consoleIOHelper.generateBlankLines(1);

        if (vulnerablePackageModels == null) {
            consoleIOHelper.println("No vulnerable Packages found");
            return;
        }

        consoleIOHelper.println("Below are the Vulnerable Packages:");
        vulnerablePackageModels.forEach(vulnerablePackageModel -> {
            if (!vulnerablePackageModel.getVulnerabilities().isEmpty()) {
                consoleIOHelper.println(vulnerablePackageModel.getCoordinates());
            }
        });
    }

    private void discoverOutdatedVersions(List<PackageManagerPackageModel> packageManagerPackageModels) {
        consoleIOHelper.generateBlankLines(1);
        consoleIOHelper.println("Below are the outdated Packages:");
        List<PackageManagerPackageModel> outdatedPackages = outdatedVersionService.getOutdatedPackages(packageManagerPackageModels);
        outdatedPackages.forEach(packageModel -> consoleIOHelper.println(packageModel.getPurl()));
    }
}
