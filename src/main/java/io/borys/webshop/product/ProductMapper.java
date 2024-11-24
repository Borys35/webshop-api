package io.borys.webshop.product;

import io.borys.webshop.brand.Brand;
import io.borys.webshop.brand.BrandRepository;
import io.borys.webshop.category.Category;
import io.borys.webshop.category.CategoryRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {BrandRepository.class, CategoryRepository.class})
public interface ProductMapper {

    @Mapping(target = "productId", source = "id")
    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "brandName", source = "brand.name")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "currency", source = "price.currency")
    @Mapping(target = "priceAmount", source = "price.amount")
    ProductDto toProductDto(Product product);

    @Mapping(source = "brandId", target = "brand", qualifiedByName = "fetchBrandById")
    @Mapping(source = "categoryId", target = "category", qualifiedByName = "fetchCategoryById")
    @Mapping(target = "price", expression = "java(new Price(dto.getCurrency(), dto.getPriceAmount()))")
    Product toProduct(ProductDto dto, @Context BrandRepository brandRepository, @Context CategoryRepository categoryRepository);

    @Named("fetchBrandById")
    default Brand fetchBrandById(Long id, @Context BrandRepository brandRepository) {
        return brandRepository.findById(id).orElseThrow();
    }

    @Named("fetchCategoryById")
    default Category fetchCategoryById(Long id, @Context CategoryRepository categoryRepository) {
        return categoryRepository.findById(id).orElseThrow();
    }
}
