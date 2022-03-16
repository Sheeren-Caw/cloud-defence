package com.cawstudios.clouddefence.models.sbom.cyclone_dx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SbomMetaDataModel {

    private String timestamp;

    public void setTimestamp() {
        this.timestamp = new Date().toInstant().toString();
    }

}
