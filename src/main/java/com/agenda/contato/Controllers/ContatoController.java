package com.agenda.contato.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.agenda.contato.Service.*;


import com.agenda.contato.Entities.contato;

import java.util.List;

@CrossOrigin(origins = "https://main.d22u05ozk7cbs7.amplifyapp.com/home") 
@RestController
@RequestMapping("contato")
public class ContatoController {
    
    @Autowired
    private ContatoService service;
    
    @GetMapping
    public ResponseEntity<List<contato>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/ordenado-fullname")
    public ResponseEntity<List<contato>> getFullname( @RequestParam(defaultValue = "asc") String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), "fullname");
        return ResponseEntity.ok(service.getAllOrdenado(sort));
    }

    @GetMapping("/ordenado-nickname")
    public ResponseEntity<List<contato>> getNickname( @RequestParam(defaultValue = "asc") String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), "nickname");
        return ResponseEntity.ok(service.getAllOrdenado(sort));
    }

    
    @GetMapping("/favoritos")
    public ResponseEntity<List<contato>> getAllfavorite( @RequestParam (defaultValue = "favorite") String sortBy, @RequestParam(defaultValue = "asc") String direction){
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        return ResponseEntity.ok(service.getAllFavoritos(sort));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<contato>> buscarPorTermo(@RequestParam String termo) {
    return ResponseEntity.ok(service.findByAnything(termo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<contato> getContatoById(@PathVariable Long id) {
            contato contatoEncontrado = service.getContatoById(id);
            return ResponseEntity.ok(contatoEncontrado);         
    }

    @PostMapping
    public ResponseEntity<contato>save(@RequestBody contato Contato){
        return ResponseEntity.created(null).body(service.save(Contato));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void>delete(@PathVariable long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void>update(@PathVariable long id, @RequestBody contato Contato){
        service.update(Contato, id);
        return ResponseEntity.noContent().build();
    }


}
