package com.dev.qlda.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CreateProjectsRequest {
    private String name;
    private Double price;
    private Date startDate;
    private Date endDate;
    private String status;
}
