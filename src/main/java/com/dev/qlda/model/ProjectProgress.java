package com.dev.qlda.model;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProjectProgress {
    private Date updateDate;
    private String updateContent;
    private Double projectProgress;
    private Double projectPrice;
}
