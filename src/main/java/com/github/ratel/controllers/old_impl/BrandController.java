//package com.github.ratel.controllers.impl;
//
//import com.github.ratel.controllers.ApiSecurityHeader;
//import com.github.ratel.dto.BrandDto;
//import com.github.ratel.entity.Brand;
//import com.github.ratel.entity.enums.EntityStatus;
//import com.github.ratel.services.BrandService;
//import com.github.ratel.utils.TransferObj;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.web.bind.annotation.*;
//
//import javax.persistence.EntityNotFoundException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/brand")
//public class BrandController implements ApiSecurityHeader {
//
//    private final BrandService brandService;
//
//    @Autowired
//    public BrandController(BrandService brandService) {
//        this.brandService = brandService;
//    }
//
//    @Secured("ROLE_ADMIN")
//    @GetMapping
//    @SecurityRequirement(name = "Authorization")
//    public List<Brand> findAllBrand() {
//        return this.brandService.findAllBrand();
//    }
//
////    @Secured("ROLE_USER")
////    @GetMapping("/user")
////    @SecurityRequirement(name = "Authorization")
////    public List<BrandDto> findAllBrandFromUser() {
////        List<Brand> brands = this.brandService.findAllBrand().stream()
////                .filter(brand -> brand.getStatus().equals(EntityStatus.on))
////                .collect(Collectors.toList());
////        return TransferObj.toAllBrandDto(brands);
////    }
//
////    @Secured("ROLE_ADMIN")
////    @GetMapping("/{brandId}")
////    @SecurityRequirement(name = "Authorization")
////    public Brand findByIdBrand(@PathVariable long brandId) {
////        Brand brand = this.brandService.findBrandById(brandId);
////        if (brand.getStatus().equals(EntityStatus.on)) {
////            return brand;
////        } else {
////            throw new EntityNotFoundException("Нет такого бренда");
////        }
////    }
////
////    @Secured("ROLE_USER")
////    @GetMapping("/name/{brandName}")
////    @SecurityRequirement(name = "Authorization")
////    public BrandDto findByName(@PathVariable String brandName) {
////        Brand brand = this.brandService.findBrandByName(brandName);
////        if (brand.getStatus().equals(EntityStatus.on)) {
////            return TransferObj.toBrand(brand);
////        } else {
////            throw new EntityNotFoundException("Нет такого бренда");
////        }
////    }
////
////    @Secured("ROLE_ADMIN")
////    @PostMapping
////    @SecurityRequirement(name = "Authorization")
////    public void createBrand(@RequestBody BrandDto brandDto) {
////        Brand brand = TransferObj.toBrand(brandDto);
////        brandService.saveBrand(brand);
////    }
////
////    @Secured("ROLE_ADMIN")
////    @PutMapping
////    @SecurityRequirement(name = "Authorization")
////    public void updateBrand(@RequestBody Brand brand) {
////        brandService.updateBrandById(brand);
////    }
////
////    @Secured("ROLE_ADMIN")
////    @DeleteMapping("/{brandId}")
////    @SecurityRequirement(name = "Authorization")
////    public void deleteBrand(@PathVariable long brandId) {
////        brandService.deleteBrandById(brandId);
////    }
//
//}
