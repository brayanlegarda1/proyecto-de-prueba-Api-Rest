package com.application.rest.repository;

import com.application.rest.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    /*
        Con los Query Methods jps ya sabe como hacer las consulta basandose en los atributos en la firma del metódo
        además de las palabras claves entre dichos atributos como por ejemplo "By", "Between" etc
    */
    List<Product> findProductByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice); //Query Methods

    /*
       Con la Notación Query es simplemente nosotros le decimos a JPA una consulta JPQL->( similar a sql) que será ejecutada en la base de datos
    */
    @Query("SELECT p FROM product p WHERE p.price BETWEEN ?1 AND ?2 ")
    List<Product> findProductByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice); // Consulta Query




}
