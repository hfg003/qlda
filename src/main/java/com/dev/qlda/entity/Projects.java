package com.dev.qlda.entity;

import com.dev.qlda.model.ProjectProgress;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Table(name = "projects")
public class Projects {
    @Id
    private String id;
    private String name;
    private Double price;
    private Date startDate;
    private Date endDate;
    private Double realPrice;
    private String status;
    private String takeChargeName;
    private String takeChargeId;

    @Column(columnDefinition = "jsonb")
    @Type(type = "jsonb")
    @Builder.Default
    private List<ProjectProgress> projectProgresses = new ArrayList<>();
}
