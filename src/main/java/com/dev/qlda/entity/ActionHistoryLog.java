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
@Table(name = "action_history")
public class ActionHistoryLog {
    @Id
    private String id;
    private String assetId;
    private String assetCode;
    private String assetType;
    private String assetName;
    private String quantity;
    private String reason;
    private String action;
    private Date actionDate;
    private String executor;
    private String executorId;
    private String assigneeId;
    private String assigneeName;
    private String originalActionId;

    private String status;
}
