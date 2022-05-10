package dev.applaudostudios.examples.finalassignment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.applaudostudios.examples.finalassignment.common.dto.ProductDto;
import dev.applaudostudios.examples.finalassignment.persistence.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ProductService extends CrudRepositoryService<Product, ProductDto, Long> {

    @Autowired
    private ObjectMapper objectMapper;
    public ProductService(CrudRepository<Product, Long> crudRepository) {
        super(crudRepository);
    }

    private void updateStock(ProductDto entityDto, int stock) {
        entityDto.setStock(stock);
        super.update(entityDto);
    }

    public boolean reserveStock(Long idProduct, int newStock){
        Optional<ProductDto> foundEntity = this.get(idProduct);
        if(newStock >= 0 && newStock<= foundEntity.get().getStock()){
            updateStock(foundEntity.get(), foundEntity.get().getStock()- newStock);
            return true;
        }
        return false;
    }

    public boolean returnStock(Long idProduct, int stockToReturn){
        Optional<ProductDto> foundEntity = this.get(idProduct);
        if(stockToReturn >= 0 && foundEntity.isPresent()){
            updateStock(foundEntity.get(), stockToReturn + foundEntity.get().getStock());
            return true;
        }
        return false;
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
