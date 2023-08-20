//package com.github.ratel.repositories;
//
//import org.hamcrest.collection.IsIterableContainingInAnyOrder;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//import java.util.Optional;
//
//@DataJpaTest
//@RunWith(SpringRunner.class)
//@ActiveProfiles(value = "test")
//public class BrandRepositoryTest {
//
//    @Autowired
//    private BrandRepository brandRepository;
//
//    @Test
//    @Sql(value = {"schema-brand.sql", "ratel-brand-data.sql"})
//    public void findAllTest() {
//        List<Brand> exp = BrandRepositoryMock.expBrandList();
//        List<Brand> act = this.brandRepository.findAll();
//        Assert.assertThat(act, IsIterableContainingInAnyOrder.containsInAnyOrder(exp.toArray()));
//    }
//
//    @Test
//    @Sql(value = {"schema-brand.sql", "ratel-brand-data.sql"})
//    public void findByIdTest(){
//        Brand act = brandRepository.findById(2L).orElseThrow();
//        Brand exp = BrandRepositoryMock.brand_2();
//        Assert.assertEquals(exp, act);
//    }
//
//    @Test
//    @Sql(value = {"schema-brand.sql"})
//    public void createAllBrandAndCompareThem() {
//        List<Brand> exp = BrandRepositoryMock.expBrandList();
//        List<Brand> data = BrandRepositoryMock.prepareBrandList();
//        this.brandRepository.saveAll(data);
//        List<Brand> act = this.brandRepository.findAll();
//        Assert.assertThat(act, IsIterableContainingInAnyOrder.containsInAnyOrder(exp.toArray()));
//    }
//
////    @Test
////    public void createBrandTest() {
////        this.brandRepository.save(new Brand("Ceresit", EntityStatus.on));
////    }
//
//    @Test
//    @Sql(value = {"schema-brand.sql", "ratel-brand-data.sql"})
//    public void findByNameTest() {
//        Optional<Brand> act = this.brandRepository.findByName("Feber");
//        Optional<Brand> exp = Optional.of(BrandRepositoryMock.brand_3());
//        Assert.assertEquals(exp, act);
//    }
//
//}