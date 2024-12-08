package com.dev.qlda.request;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateAssetRequest {
    private String name;
    private String type;
    private String manufacturer;
    private Double value;
    private String description;
    private Long quantity;
    private Date boughtDate;
}
