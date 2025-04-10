package com.fiap.CP05_LGSR_Products.repository;

import com.fiap.CP05_LGSR_Products.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}