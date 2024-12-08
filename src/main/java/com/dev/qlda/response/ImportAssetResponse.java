package com.dev.qlda.response;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ImportAssetResponse {
    private int index;
    private String name;
    private String type;
    private String manufacturer;
    private Double value;
    private String description;
    private Long quantity;
    private Date boughtDate;

    private boolean success;
    private String errorDesc;
}
