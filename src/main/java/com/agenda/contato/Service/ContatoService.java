package com.agenda.contato.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import com.agenda.contato.Entities.contato;
import com.agenda.contato.Repositories.ContatoRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.Function;

@Service
public class ContatoService {
    
    @Autowired
    private ContatoRepository repository;

    public List<contato> getAll(){
        return repository.findAll();
        
    }

    public List<contato> getAllOrdenado(Sort sort) {
    return repository.findAll(sort);
    }

    public List<contato> getAllFavoritos(Sort sort) {
    return repository.findByFavoriteTrue(sort);

    }

    public List<contato> findByAnything(String termo) {
    return Stream.<Function<String, List<contato>>>of(
        repository::findByNumber,
        repository::findByAddressIgnoreCase,
        repository::findByFullnameContainingIgnoreCase,
        repository::findByNicknameContainingIgnoreCase
    )
    .flatMap(f -> f.apply(termo).stream())
    .distinct()
    .collect(Collectors.toList());
    }
    
    public contato save(contato Contato){
        List<contato> existingContato = repository.findByNumber(Contato.getNumber());
        if (!existingContato.isEmpty()) {
            throw new IllegalArgumentException("Já existe um contato com este número.");
        }
        return repository.save(Contato);
    }

    public void update (contato Contato, Long id){
        contato aux = repository.getReferenceById(id);
        aux.setNickname(Contato.getNickname());
        aux.setFullname(Contato.getFullname());
        aux.setOccupation(Contato.getOccupation());
        aux.setBirthday(Contato.getBirthday());
        aux.setAddress(Contato.getAddress());
        aux.setEmail(Contato.getEmail());
        aux.setNumber(Contato.getNumber());
        aux.setType(Contato.getType());
        aux.setFavorite(Contato.getFavorite());
        repository.save(aux);
    }

    public void delete(long id){
        if(repository.existsById(id))
        {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Not found");
        }
    }

    public contato getContatoById(Long id) {
        return repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Contato com ID " + id + " não encontrado."));
    }
}
