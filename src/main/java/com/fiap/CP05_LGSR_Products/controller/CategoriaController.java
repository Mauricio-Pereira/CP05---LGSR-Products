package com.fiap.CP05_LGSR_Products.controller;

import com.fiap.CP05_LGSR_Products.model.Categoria;
import com.fiap.CP05_LGSR_Products.repository.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/listar")
    public String listarCategorias(Model model) {
        model.addAttribute("listaCategorias", categoriaRepository.findAll());
        return "categoria-list";
    }

    @GetMapping("/novo")
    public String novaCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "nova-categoria";
    }

    @PostMapping("/salvar")
    public String salvarCategoria(@Valid @ModelAttribute("categoria") Categoria categoria,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Se houver erros de validação, volta para o formulário
            return "categoria-form";
        }
        categoriaRepository.save(categoria);
        return "redirect:/categorias/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarCategoria(@PathVariable("id") Long id, Model model) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));
        model.addAttribute("categoria", categoria);
        return "categoria-form";
    }

    @GetMapping("/excluir/{id}")
    public String excluirCategoria(@PathVariable("id") Long id) {
        categoriaRepository.deleteById(id);
        return "redirect:/categorias/listar";
    }
}
