package com.github.rafaelcardosodev.domain.repository;

import com.github.rafaelcardosodev.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
