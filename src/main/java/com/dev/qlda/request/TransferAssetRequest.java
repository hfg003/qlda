package com.dev.qlda.request;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TransferAssetRequest {
    private String assetCode;
    private String assetType;
    private String assetName;
    private Long quantity;
    private String originalPosition;
    private String currentPosition;
    private Date handOverDate;
}
