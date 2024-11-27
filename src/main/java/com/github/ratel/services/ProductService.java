package com.github.ratel.services;

import com.github.ratel.entity.Product;
import com.github.ratel.entity.Subcategory;
import com.github.ratel.payload.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    Page<Product> findAllInSubcategory(long subcategoryId, Pageable pageable);

    List<Product> findListForIds(List<Long> products);

    Page<Product> findAllForAdmin(Pageable pageable);

    Product findById(long id);

    Product findByIdForAdmin(long id);

    Product create(ProductRequest productRequest, List<MultipartFile> files);

    Product editProduct(long id, ProductRequest productRequest);

    Product addImageToImageList(long id, List<MultipartFile> files);

    Product deleteImagesFromImageList(long productId, List<Long> imageIdsList);

    Product update(Product product);

    void deleteById(long id);
}
