package com.brandlogs.suparmarket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Item implements Serializable {
    private static final long serialVersionUID = 2842598520185366295L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min=5, max=100)
    @Column(name = "ItemName")
    private String itemName;

    @Min(1)
    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "ItemCondition")
    private String itemCondition;

    @Column(name = "Deleted")
    private Boolean deletionState;

    @Column(name = "ReceivedDate",nullable = true)
    private Date receivedDate;

    @Column(name = "CreatedDate",nullable = false)
    private Date createdDate;

    @Column(name = "ReleasedDate",nullable = true)
    private Date releasedDate;

    @Column(name = "ReturnedDate",nullable = true)
    private Date returnedDate;

    @Column(name = "VendorName")
    private String vendorName;

    @Column(name = "Action")
    private String action;
}
