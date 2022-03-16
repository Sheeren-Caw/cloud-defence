package com.cawstudios.clouddefence.mappers.package_manager;

import com.cawstudios.clouddefence.models.package_manager.PackageManagerPackageInfoModel;
import com.cawstudios.clouddefence.models.package_manager.PackageManagerPackageModel;
import com.cawstudios.clouddefence.models.package_manager.PackageManagerPackageReleaseModel;
import com.cawstudios.clouddefence.models.package_manager.pypi.PYPIInfoModel;
import com.cawstudios.clouddefence.models.package_manager.pypi.PYPIModel;
import com.cawstudios.clouddefence.models.package_manager.pypi.PYPIReleaseModel;
import jakarta.inject.Singleton;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Singleton
@Mapper(componentModel = "jsr330")
public interface PackageManagerModelMapper {
    @Mapping(target = "info", source = "model.info", qualifiedByName = "pypiInfoToPkgInfo")
    PackageManagerPackageModel toPackageManagerModel(PYPIModel model);

    default  TreeMap<String, List<PackageManagerPackageReleaseModel>> toProfileResponse (TreeMap<String, List<PYPIReleaseModel>> pypiReleaseModels) {
        TreeMap<String, List<PackageManagerPackageReleaseModel>> packageManagerPackageReleaseModels = new TreeMap<>();
        pypiReleaseModels.forEach((key, value) -> {
            List<PackageManagerPackageReleaseModel> packageReleaseModels = new ArrayList<>();
            ModelMapper modelMapper = new ModelMapper();

            value.forEach(x -> {
                packageReleaseModels.add(modelMapper.map(x, PackageManagerPackageReleaseModel.class));
            });
            packageManagerPackageReleaseModels.put(key, packageReleaseModels);
        });

        return packageManagerPackageReleaseModels;
    }

    @Named("pypiInfoToPkgInfo")
    PackageManagerPackageInfoModel pypiInfoToPkgInfo(PYPIInfoModel info);

    List<PackageManagerPackageModel> toPackageManagerModels(List<PYPIModel> models);
}
