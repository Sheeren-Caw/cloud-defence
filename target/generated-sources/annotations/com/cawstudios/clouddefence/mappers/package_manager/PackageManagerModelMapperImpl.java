package com.cawstudios.clouddefence.mappers.package_manager;

import com.cawstudios.clouddefence.models.package_manager.PackageManagerPackageInfoModel;
import com.cawstudios.clouddefence.models.package_manager.PackageManagerPackageModel;
import com.cawstudios.clouddefence.models.package_manager.pypi.PYPIInfoModel;
import com.cawstudios.clouddefence.models.package_manager.pypi.PYPIModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.inject.Named;
import javax.inject.Singleton;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-17T13:45:32+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.14 (Ubuntu)"
)
@Singleton
@Named
public class PackageManagerModelMapperImpl implements PackageManagerModelMapper {

    @Override
    public PackageManagerPackageModel toPackageManagerModel(PYPIModel model) {
        if ( model == null ) {
            return null;
        }

        PackageManagerPackageModel packageManagerPackageModel = new PackageManagerPackageModel();

        packageManagerPackageModel.setInfo( pypiInfoToPkgInfo( model.getInfo() ) );
        packageManagerPackageModel.setLastSerial( model.getLastSerial() );
        packageManagerPackageModel.setPurl( model.getPurl() );
        List<String> list = model.getDependencies();
        if ( list != null ) {
            packageManagerPackageModel.setDependencies( new ArrayList<String>( list ) );
        }
        packageManagerPackageModel.setReleases( toProfileResponse( model.getReleases() ) );

        return packageManagerPackageModel;
    }

    @Override
    public PackageManagerPackageInfoModel pypiInfoToPkgInfo(PYPIInfoModel info) {
        if ( info == null ) {
            return null;
        }

        PackageManagerPackageInfoModel packageManagerPackageInfoModel = new PackageManagerPackageInfoModel();

        packageManagerPackageInfoModel.setAuthor( info.getAuthor() );
        packageManagerPackageInfoModel.setAuthorEmail( info.getAuthorEmail() );
        packageManagerPackageInfoModel.setDescription( info.getDescription() );
        packageManagerPackageInfoModel.setLicense( info.getLicense() );
        packageManagerPackageInfoModel.setName( info.getName() );
        packageManagerPackageInfoModel.setPackageUrl( info.getPackageUrl() );
        packageManagerPackageInfoModel.setProjectUrl( info.getProjectUrl() );
        packageManagerPackageInfoModel.setVersion( info.getVersion() );
        packageManagerPackageInfoModel.setSummary( info.getSummary() );
        packageManagerPackageInfoModel.setReleaseUrl( info.getReleaseUrl() );
        List<String> list = info.getRequiresDist();
        if ( list != null ) {
            packageManagerPackageInfoModel.setRequiresDist( new ArrayList<String>( list ) );
        }

        return packageManagerPackageInfoModel;
    }

    @Override
    public List<PackageManagerPackageModel> toPackageManagerModels(List<PYPIModel> models) {
        if ( models == null ) {
            return null;
        }

        List<PackageManagerPackageModel> list = new ArrayList<PackageManagerPackageModel>( models.size() );
        for ( PYPIModel pYPIModel : models ) {
            list.add( toPackageManagerModel( pYPIModel ) );
        }

        return list;
    }
}
