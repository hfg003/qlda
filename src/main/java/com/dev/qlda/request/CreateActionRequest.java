package com.dev.qlda.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateActionRequest {
    private String assetCode;
    private String quantity;
    private String reason;
    private String action;
    private String assignee;
    private String assigneeId;
    private String status;
}
