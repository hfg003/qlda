package com.dev.qlda.request;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateLiquidationRequest {
    private String assetCode;
    private String assetName;
    private long quantity;
    private Double liquidationPrice;
    private Date liquidationDate;
}
