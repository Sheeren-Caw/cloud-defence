package com.cawstudios.clouddefence.parsers;

import com.cawstudios.clouddefence.models.PackageModel;

import java.util.List;

public interface ParserService {
    /*
    This method parse the data to get required package details list
    like removes commented lines, blank lines etc.
    */
    List<String> parserFileData(List<String> lines);

    /*
    Extract packages, version from the lines
    */
    List<PackageModel> extractPackages(List<String> lines);

    List<PackageModel> getPackagesByFilePath(String filePath);
}
