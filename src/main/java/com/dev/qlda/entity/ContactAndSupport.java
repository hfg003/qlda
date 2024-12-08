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
@Table(name = "contact_and_support")
public class ContactAndSupport {
    @Id
    private String id;
    private String contact;
    private String title;
    private String description;
}
