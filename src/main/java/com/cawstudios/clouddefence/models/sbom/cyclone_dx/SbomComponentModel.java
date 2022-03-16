package com.cawstudios.clouddefence.models.sbom.cyclone_dx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SbomComponentModel {

    @JsonProperty("bom-ref")
    private UUID bomRef;

    private String publisher;

    private String name;

    private String version;

    private String purl;

    private String type;

    List<SbomHashModel> hashes;
}
