package com.github.polydome.apteczka.data.entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.github.polydome.apteczka.domain.model.Medicine;
import com.github.polydome.apteczka.domain.model.ProductLinkedMedicine;

@Entity(tableName = "medicine")
public class MedicineEntity {
    @PrimaryKey(autoGenerate = true)
    private final long id;
    private final String title;

    @Embedded
    private final ProductEntity product;

    public MedicineEntity(long id, String title, ProductEntity product) {
        this.id = id;
        this.title = title;
        this.product = product;
    }

    public MedicineEntity(Medicine medicine) {
        this(medicine.getId(), medicine.getTitle(), null);
    }

    public MedicineEntity(ProductLinkedMedicine medicine) {
        this(medicine.getId(), medicine.getTitle(), new ProductEntity(medicine.getProduct()));
    }

    public Medicine toMedicine() {
        if (product != null) {
            return new ProductLinkedMedicine.Builder()
                    .id(id)
                    .title(title)
                    .product(product.toProduct())
                    .build();
        } else {
            return Medicine
                    .builder()
                    .title(title)
                    .id(id)
                    .build();
        }

    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ProductEntity getProduct() {
        return product;
    }
}
