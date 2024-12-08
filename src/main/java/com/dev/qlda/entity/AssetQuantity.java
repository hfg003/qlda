package com.dev.qlda.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "asset_quantity")
public class AssetQuantity {
    @Id
    private String id;
    private String assetId;
    private String assetCode;
    private String assetName;
    private String assetType;
    private String manufacturer;
    private String location;
    @Builder.Default
    private Long quantity = 0L;
}
