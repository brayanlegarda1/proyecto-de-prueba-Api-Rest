package com.application.rest.controller;

import com.application.rest.controller.dto.MakerDTO;
import com.application.rest.entities.Maker;
import com.application.rest.service.IMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/maker")
public class MakerController {

    @Autowired
    private IMakerService iMakerService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
       Optional<Maker> makerOptional = iMakerService.findById(id);


       //si encuentra un elemento con el id solicitado
       if(makerOptional.isPresent()){

           //Maker Entity
           Maker maker = makerOptional.get();
           // Convertir de maker(Entity) a maker(DTO)
           MakerDTO makerDTO = MakerDTO.builder()
                   .id(maker.getId())
                   .name(maker.getName())
                   .productList(maker.getProductList())
                   .build();

           return ResponseEntity.ok(makerDTO);
       }

       //si no lo encuentra elemento con el id solicitado
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/findall")
    public ResponseEntity<?> findAll(){

        //convertir los makers(Entity) en makers(DTO)
        List<MakerDTO> makerDTOList = iMakerService.findAll()
                .stream()
                .map(maker -> MakerDTO.builder()
                        .id(maker.getId())
                        .name(maker.getName())
                        .productList(maker.getProductList())
                        .build())
                .toList();

        return ResponseEntity.ok(makerDTOList);

    }


    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MakerDTO makerDTO) throws URISyntaxException {

        if(makerDTO.getName().isBlank()){
            return ResponseEntity.badRequest().build();
        }

        iMakerService.save(Maker.builder()
                .name(makerDTO.getName())
                .build());

        return ResponseEntity.created(new URI("/api/maker/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMaker(@PathVariable Long id, @RequestBody MakerDTO makerDTO){

        Optional<Maker> makerOptional = iMakerService.findById(id);

        if(makerOptional.isPresent()){
            Maker makerForUpdate = makerOptional.get();
            makerForUpdate.setName(makerDTO.getName());
            iMakerService.save(makerForUpdate);
            return ResponseEntity.ok("registration successfully updated");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){

        if(id != null){
            iMakerService.deleteById(id);
            return ResponseEntity.ok("Record successfully deleted");
        }

        return ResponseEntity.badRequest().build();

    }


}
