package com.dev.qlda.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "transfers")
public class Transfers {
    @Id
    private String id;
    private String assetCode;
    private String assetType;
    private String assetName;
    private Long quantity;
    private String originalPosition;
    private String currentPosition;
    private Date handOverDate;
}
