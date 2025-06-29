package com.agenda.contato.Repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.agenda.contato.Entities.contato;

public interface ContatoRepository extends JpaRepository <contato, Long> {

    List<contato> findByFavoriteTrue(Sort sort);

    List<contato> findByNumber(String number);

    List<contato> findByFullnameContainingIgnoreCase(String fullname);

    List<contato> findByAddressIgnoreCase(String address);

    List<contato> findByNicknameContainingIgnoreCase(String nickname);
    
    List<contato> getContatoById(long id);
} 
