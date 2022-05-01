package dev.appladostudios.examples.finalassignment.web.controller.persistence.repository;

import dev.appladostudios.examples.finalassignment.web.controller.persistence.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    Product findByProductName(String productName);
}
