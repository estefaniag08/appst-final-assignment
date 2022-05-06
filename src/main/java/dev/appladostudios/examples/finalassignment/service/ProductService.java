package dev.appladostudios.examples.finalassignment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.appladostudios.examples.finalassignment.common.dto.ProductDto;
import dev.appladostudios.examples.finalassignment.persistence.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ProductService extends CrudService<Product, ProductDto, Long>{

    @Autowired
    private ObjectMapper objectMapper;
    public ProductService(CrudRepository<Product, Long> crudRepository) {
        super(crudRepository);
    }

    public boolean updateStock(ProductDto entityDto) {
        Optional<ProductDto> foundEntity = this.get(entityDto.getId());
        if(foundEntity.isPresent() && validateStock(foundEntity.get().getStock(), entityDto.getStock())){
            foundEntity.get().setStock(entityDto.getStock());
            super.update(foundEntity.get());
            return true;
        } else {
            return false;
        }
    }

    private boolean validateStock(int currentStock, int newStock){
        return newStock >= 0 && newStock < currentStock;
    }
    @Override
    public ProductDto mapToDto(Product entity) {
        return objectMapper.convertValue(entity, ProductDto.class);
    }

    @Override
    public Product mapToEntity(ProductDto entityDto) {
        return objectMapper.convertValue(entityDto, Product.class);
    }
}
