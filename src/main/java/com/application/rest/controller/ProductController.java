package com.application.rest.controller;

import com.application.rest.controller.dto.ProductDTO;
import com.application.rest.entities.Product;
import com.application.rest.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){

        Optional<Product> productOptional = iProductService.findById(id);

        //si encuentra un elemento con el id solicitado
        if(productOptional.isPresent()){

            //product Entity
            Product product = productOptional.get();
            // Convertir de Product(Entity) a Product(DTO)

            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .maker(product.getMaker())
                    .build();
            return ResponseEntity.ok(productDTO);
        }
        //si no lo encuentra elemento con el id solicitado
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findall")
    public ResponseEntity<?> findAll(){
        //convertir los products(Entity) en products(DTO)
        List<ProductDTO> productDTOList = iProductService.findAll()
                .stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .maker(product.getMaker())
                        .build()
                ).toList();
        return ResponseEntity.ok(productDTOList);

    }

    @PostMapping("/save")
    public ResponseEntity<?> saveProduct(@RequestBody ProductDTO productDTO) throws URISyntaxException {

        if(productDTO.getName().isBlank() || productDTO.getPrice() == null || productDTO.getMaker() == null){
            return  ResponseEntity.badRequest().build();
        }

        //Convertir de Product(DTO) a Product(Entity)
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .maker(productDTO.getMaker())
                .build();

        iProductService.save(newProduct);

        return ResponseEntity.created(new URI("/api/product/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){

        Optional<Product> productOptional = iProductService.findById(id);

        if(productOptional.isPresent()){

            Product productForUpdate = productOptional.get();
            productForUpdate.setName(productDTO.getName());
            productForUpdate.setPrice(productDTO.getPrice());
            productForUpdate.setMaker(productDTO.getMaker());

            iProductService.save(productForUpdate);
            return ResponseEntity.ok("registration successfully updated");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id){

        if (id != null){
            iProductService.deleteById(id);
            return ResponseEntity.ok("Record successfully deleted");
        }
        return ResponseEntity.badRequest().build();
    }

}
