package com.cawstudios.clouddefence.parsers;

import com.cawstudios.clouddefence.helpers.FileIOHelper;
import com.cawstudios.clouddefence.models.PackageModel;
import jakarta.inject.Singleton;

import java.util.*;

@Singleton
public class PythonRequirementParserServiceImpl implements ParserService{
    private static final List<Character> COMMENTED_LINES = List.of(Character.MAX_VALUE, ' ', '#');

    @Override
    public List<String> parserFileData(List<String> lines) {
        List<String> parsedList = new ArrayList<>();
        lines.forEach(line -> {
            line = line.strip();

            // Remove comment or blank line
            if (Objects.equals(line, "") || COMMENTED_LINES.contains(line.charAt(0))) {
                return;
            }

            // If comment is not at first char split it
            String[] arrOfStr = line.split("#", 2);
            if (arrOfStr.length <= 0) {
                return;
            }
            line = arrOfStr[0].strip();

            parsedList.add(line);
        });
        return parsedList;
    }

    @Override
    public List<PackageModel> extractPackages(List<String> lines) {
        Set<PackageModel> packageModels = new HashSet<>();
        lines.forEach(line -> {
            if (!line.contains("==")) {
                return;
            }
            String[] data = line.split("==", 2);
            PackageModel packageModel = new PackageModel();
            packageModel.setName(data[0]);
            packageModel.setVersion(data[1]);
            packageModel.setPurl(packageModel.getName(), packageModel.getVersion());
            packageModels.add(packageModel);
        });
        return new ArrayList<> (packageModels);
    }

    @Override
    public List<PackageModel> getPackagesByFilePath(String filePath) {
        List<String> lines = FileIOHelper.readFileByPath(filePath);

        List<String> parsedLines = this.parserFileData(lines);

        return this.extractPackages(parsedLines);
    }
}
