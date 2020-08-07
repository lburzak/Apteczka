package com.github.polydome.apteczka.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.github.polydome.apteczka.domain.model.Medicine;

@Entity(tableName = "medicine")
public class MedicineEntity {
    @PrimaryKey
    private final long id;
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

    public MedicineEntity(Medicine medicine) {
        this.commonName = medicine.getCommonName();
        this.ean = medicine.getEan();
        this.form = medicine.getForm();
        this.id = medicine.getId();
        this.packagingSize = medicine.getPackagingSize();
        this.packagingUnit = medicine.getPackagingUnit();
        this.name = medicine.getName();
        this.potency = medicine.getPotency();
    }

    public Medicine toMedicine() {
        return Medicine.builder()
                .commonName(commonName)
                .ean(ean)
                .form(form)
                .id(id)
                .name(name)
                .packagingSize(packagingSize)
                .packagingUnit(packagingUnit)
                .potency(potency)
                .build();
    }
}
