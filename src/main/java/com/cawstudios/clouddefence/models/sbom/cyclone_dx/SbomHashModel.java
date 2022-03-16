package com.cawstudios.clouddefence.models.sbom.cyclone_dx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SbomHashModel {

    private String alg;

    private String content;
}
