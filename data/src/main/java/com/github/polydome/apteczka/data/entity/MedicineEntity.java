package com.github.polydome.apteczka.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.github.polydome.apteczka.domain.model.Medicine;

@Entity(tableName = "medicine")
public class MedicineEntity {
    @PrimaryKey(autoGenerate = true)
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

    public MedicineEntity(long id, String ean, String name, String commonName, String potency, String form, int packagingSize, String packagingUnit) {
        this.id = id;
        this.ean = ean;
        this.name = name;
        this.commonName = commonName;
        this.potency = potency;
        this.form = form;
        this.packagingSize = packagingSize;
        this.packagingUnit = packagingUnit;
    }

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

    public long getId() {
        return id;
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
