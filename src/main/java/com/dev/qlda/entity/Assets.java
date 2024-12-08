package com.dev.qlda.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "assets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Assets {
    @Id
    private String id;
    private String code;
    private String name;
    private String type;
    private String manufacturer;
    private Double value;
    private String description;
    private Long quantity;
    private Date boughtDate;
}
