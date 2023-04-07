package com.challenge.b4a.repository;

import com.challenge.b4a.domain.Endereco;
import com.challenge.b4a.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
