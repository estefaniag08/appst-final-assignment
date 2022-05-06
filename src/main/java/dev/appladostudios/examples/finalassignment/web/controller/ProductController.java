package dev.appladostudios.examples.finalassignment.web.controller;

import dev.appladostudios.examples.finalassignment.common.dto.ProductDto;
import dev.appladostudios.examples.finalassignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @RequestMapping( method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    List<ProductDto> getProductList(){
        return productService.getAll();
    }
}
