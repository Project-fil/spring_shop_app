package com.github.ratel.services;

import com.github.ratel.entity.Product;
import com.github.ratel.payload.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    Page<Product> findAllInSubcategory(Long subcategoryId, Pageable pageable);

    List<Product> findListForIds(List<Long> products);

    Page<Product> findAllForAdmin(Pageable pageable);

    Product findById(Long id);

    Product findByIdForAdmin(Long id);

    Product create(ProductRequest productRequest, List<MultipartFile> files);

    Product editProduct(Long id, ProductRequest productRequest);

    Product addImageToImageList(Long id, List<MultipartFile> files);

    Product deleteImagesFromImageList(Long productId, List<Long> imageIdsList);

    Product update(Product product);

    void deleteById(Long id);

}
