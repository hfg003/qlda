package com.dev.qlda.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "liquidation")
public class Liquidation {

    @Id
    private String id;
    private String assetId;
    private String assetCode;
    private String assetName;
    private String assetType;
    private long quantity;
    private Double liquidationPrice;
    private Date liquidationDate;
}
