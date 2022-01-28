package com.stortor.spring.web.core.controllers;

import com.stortor.spring.web.api.exceptions.ResourceNotFoundException;
import com.stortor.spring.web.core.converters.ProductConverter;
import com.stortor.spring.web.core.dto.ProductDto;
import com.stortor.spring.web.core.entity.Product;
import com.stortor.spring.web.core.servieces.ProductsService;
import com.stortor.spring.web.core.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductsService productService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;

    @GetMapping()
    public Page<ProductDto> getAllProducts(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "title_part", required = false) String titlePart,
            @RequestParam(name = "title_part_category", required = false) String titlePartCategory
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.findAll(minPrice, maxPrice, titlePart, titlePartCategory, page)
                .map(p -> productConverter.entityToDto(p));
    }

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
        return productConverter.entityToDto(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @PostMapping()
    public ProductDto addNewProduct(@RequestBody ProductDto productDto) {
        productDto.setId(null);
        productValidator.validate(productDto);
        Product product = productConverter.dtoToEntity(productDto);
        return productConverter.entityToDto(productService.save(product));
    }

    @PutMapping()
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productService.update(productDto);
        return productConverter.entityToDto(product);
    }

    @PatchMapping("/change_price")
    public ProductDto changePrice(@RequestParam Long productId, @RequestParam Integer delta) {
        Product product = productService.changePrice(productId, delta);
        productValidator.validatePrice(product);
        return productConverter.entityToDto(product);
    }

}