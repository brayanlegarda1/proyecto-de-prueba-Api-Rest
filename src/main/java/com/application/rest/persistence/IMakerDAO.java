package com.application.rest.persistence;

import com.application.rest.entities.Maker;

import java.util.List;
import java.util.Optional;

public interface IMakerDAO {

    List<Maker> findAll();
    Optional<Maker> findById(Long id);  //Este metodo del repositorio devuelve un optional

    void save(Maker maker);
    void deleteById(Long id);


}
