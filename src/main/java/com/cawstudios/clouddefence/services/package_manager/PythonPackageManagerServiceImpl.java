package com.cawstudios.clouddefence.services.package_manager;

import com.cawstudios.clouddefence.helpers.ConsoleIOHelper;
import com.cawstudios.clouddefence.helpers.HttpClientHelper;
import com.cawstudios.clouddefence.mappers.package_manager.PackageManagerModelMapper;
import com.cawstudios.clouddefence.models.PackageModel;
import com.cawstudios.clouddefence.models.package_manager.PackageManagerPackageModel;
import com.cawstudios.clouddefence.models.package_manager.pypi.PYPIModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Singleton
public class PythonPackageManagerServiceImpl implements PackageManagerService {
    private static final String PACKAGE_MANAGER_URL = "https://pypi.org/pypi/%s/json";

    @Inject
    public PackageManagerModelMapper modelMapper;

    @Inject
    public HttpClientHelper httpClientHelper;

    @Inject
    ConsoleIOHelper consoleIOHelper;

    @Override
    public List<PackageManagerPackageModel> getPackageAndDependencyDetails(List<PackageModel> packageModels) {
        List<PYPIModel> pypiModels = new ArrayList<>();
        List<PackageModel> newDependentPackages = new ArrayList<>();

        int originalSize = packageModels.size();
        int depth = 0;
        int maxDepth = 1;
        int i = 0;
        while (packageModels.size() > i) {
            PackageModel packageModel = packageModels.get(i);
            i++;
            PYPIModel pypiModel = callPYPIAPI(packageModel);

            if (pypiModel == null || pypiModel.getInfo() == null) continue;

            List<PackageModel> subPackageModels = getAllDependentPackages(pypiModel);

            List<PackageModel> newDependentPackagesTemp = getNewDependentPackages(
                    packageModels,
                    subPackageModels,
                    newDependentPackages);
            newDependentPackages.addAll(newDependentPackagesTemp);

            pypiModel.setDependencies(getDependencies(subPackageModels));

            consoleIOHelper.println(packageModel.getName());

            pypiModels.add(pypiModel);

            if (i == originalSize && maxDepth > depth) {
                packageModels.addAll(newDependentPackages);
                newDependentPackages = new ArrayList<>();
                depth++;
            }
        }
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.convertValue(pypiModels, new TypeReference<>() {
//        });
        return modelMapper.toPackageManagerModels(pypiModels);
    }

    @Override
    public PackageManagerPackageModel getPackageDetail(PackageModel packageModel) {
        PYPIModel pypiModel = callPYPIAPI(packageModel);
//        ObjectMapper objectMapper = new ObjectMapper();
//        PackageManagerPackageModel packageManagerPackageModel = objectMapper.convertValue(pypiModel, new TypeReference<>() {
//        });
//        return  packageManagerPackageModel;
        return modelMapper.toPackageManagerModel(pypiModel);
    }

    private List<PackageModel> getAllDependentPackages(PYPIModel pypiModel) {
        List<PackageModel> packageModels = new ArrayList<>();
        if (pypiModel.getInfo() == null) {
            return packageModels;
        }
        List<String> dependencies = pypiModel.getInfo().getRequiresDist();
        if (dependencies == null) {
            return packageModels;
        }
        dependencies.forEach(dependency -> {
            PackageModel packageModel = new PackageModel();
            dependency = dependency.strip();
            String[] arr = dependency.split(" ", 2);
            String name = arr[0].strip();
            String version = "";
            if (arr.length > 1) {
                int vStart = arr[1].indexOf("(");
                int vEnd = arr[1].indexOf(")");
                if (vEnd > 0 && vStart >= 0) {
                    version = arr[1].substring(vStart + 1, vEnd);
                }
            }

            version = version.replace("=", "");
            version = version.replace("~", "");
            version = version.replace(">", "");
            version = version.replace("<", "");
            version = version.replace("!", "");
            String[] v = version.split(",", 2);
            if (v.length > 0) {
                version = v[0].strip();
            }
            if (!Pattern.matches("^\\w+(\\.\\w+)*$", version)) {
                version = "";
            }
            packageModel.setName(name);
            packageModel.setVersion(version);
            packageModel.setPurl(packageModel.getName(), packageModel.getVersion());

            packageModels.add(packageModel);
        });

        return packageModels;
    }

    private PYPIModel callPYPIAPI(PackageModel packageModel) {
        String packageUrl = String.format(PACKAGE_MANAGER_URL, packageModel.getName());
        try {
            HttpResponse<String> response = httpClientHelper.get(packageUrl, "");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            PYPIModel pypiModel = objectMapper.readValue(response.body(), new TypeReference<>() {
            });
            pypiModel.setPurl(packageModel.getPurl());

            return pypiModel;
        } catch (IOException | InterruptedException | IllegalArgumentException e) {
            return null;
        }
    }

    private List<PackageModel> getNewDependentPackages(
            List<PackageModel> packageModels,
            List<PackageModel> subPackageModels,
            List<PackageModel> newDependentPackages) {

        List<PackageModel> newDependentPackagesTemp = new ArrayList<>();

        for (PackageModel x : subPackageModels) {
            boolean notExistsInPackages = packageModels.stream()
                    .noneMatch(y -> Objects.equals(
                            y.getName().strip().toLowerCase(),
                            x.getName().strip().toLowerCase()));

            boolean notExistsInNewDependent = newDependentPackages.stream()
                    .noneMatch(y -> Objects.equals(
                            y.getName(),
                            x.getName()));

            boolean notExistsInTemp = newDependentPackagesTemp.stream()
                    .noneMatch(y -> Objects.equals(
                            y.getName(),
                            x.getName()));

            if ((notExistsInPackages) && (notExistsInNewDependent) && (notExistsInTemp))
                newDependentPackagesTemp.add(x);
        }
        return newDependentPackagesTemp;
    }


    private List<String> getDependencies(List<PackageModel> packageModels) {
        List<String> dependencies = new ArrayList<>();

        for (PackageModel x : packageModels) {
            dependencies.add(x.getName());
        }
        return dependencies;
    }
}
