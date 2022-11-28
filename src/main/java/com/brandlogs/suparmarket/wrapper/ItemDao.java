package com.brandlogs.suparmarket.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ItemDao {
    @NotNull
    private String itemName;

    @Min(1)
    private int quantity;

    @NotNull
    private String vendorName;
}
