package com.api.parking_control.repositories;

import com.api.parking_control.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepositry extends JpaRepository<ProductModel, UUID> {


}
