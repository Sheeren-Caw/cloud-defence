package com.cawstudios.clouddefence.models.sbom.cyclone_dx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SbomDependencyModel {

    private UUID ref;

    private List<UUID> dependsOn;
}
