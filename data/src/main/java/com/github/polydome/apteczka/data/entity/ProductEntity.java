package com.github.polydome.apteczka.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.github.polydome.apteczka.domain.model.Product;

@Entity(tableName = "product")
public class ProductEntity {
    private final String ean;
    private final String name;
    @ColumnInfo(name = "common_name")
    private final String commonName;
    private final String potency;
    private final String form;
    @ColumnInfo(name = "packaging_size")
    private final int packagingSize;
    @ColumnInfo(name = "packaging_unit")
    private final String packagingUnit;

    public ProductEntity(String ean, String name, String commonName, String potency, String form, int packagingSize, String packagingUnit) {
        this.ean = ean;
        this.name = name;
        this.commonName = commonName;
        this.potency = potency;
        this.form = form;
        this.packagingSize = packagingSize;
        this.packagingUnit = packagingUnit;
    }

    public ProductEntity(Product product) {
        this.commonName = product.getCommonName();
        this.ean = product.getEan();
        this.form = product.getForm();
        this.packagingSize = product.getPackagingSize();
        this.packagingUnit = product.getPackagingUnit();
        this.name = product.getName();
        this.potency = product.getPotency();
    }

    public Product toProduct() {
        return Product.builder()
                .commonName(commonName)
                .ean(ean)
                .form(form)
                .id(0)
                .name(name)
                .packagingSize(packagingSize)
                .packagingUnit(packagingUnit)
                .potency(potency)
                .build();
    }

    public String getEan() {
        return ean;
    }

    public String getName() {
        return name;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getPotency() {
        return potency;
    }

    public String getForm() {
        return form;
    }

    public int getPackagingSize() {
        return packagingSize;
    }

    public String getPackagingUnit() {
        return packagingUnit;
    }
}
