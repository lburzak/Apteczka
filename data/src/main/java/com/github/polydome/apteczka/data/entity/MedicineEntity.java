package com.github.polydome.apteczka.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.github.polydome.apteczka.domain.model.Medicine;

@Entity(tableName = "medicine")
public class MedicineEntity {
    @PrimaryKey(autoGenerate = true)
    private final long id;
    private final String title;

    public MedicineEntity(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public MedicineEntity(Medicine medicine) {
        this.id = medicine.getId();
        this.title = medicine.getTitle();
    }

    public Medicine toMedicine() {
        return Medicine
                .builder()
                .title(title)
                .id(id)
                .build();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
