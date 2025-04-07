package com.fiap.CP05_LGSR_Products.controller;

import com.fiap.CP05_LGSR_Products.model.Produto;
import com.fiap.CP05_LGSR_Products.repository.CategoriaRepository;
import com.fiap.CP05_LGSR_Products.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/listar")
    public String listarProdutos(Model model) {
        model.addAttribute("listaProdutos", produtoRepository.findAll());
        return "produto-list";
    }

    @GetMapping("/novo")
    public String mostrarFormNovoProduto(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("listaCategorias", categoriaRepository.findAll());
        return "produto-form";
    }

    @PostMapping("/salvar")
    public String salvarProduto(
            @Valid @ModelAttribute("produto") Produto produto,
            BindingResult bindingResult,
            Model model,
            @RequestParam("imageFile") MultipartFile imageFile) {

        System.out.println(produto.toString());

        // Verifica erros de validação dos outros campos
        if (bindingResult.hasErrors()) {
            // Se houver erros, recarrega categorias e volta para o form
            model.addAttribute("listaCategorias", categoriaRepository.findAll());
            return "produto-form";
        }

        try {
            // Se o arquivo de imagem foi enviado e não está vazio
            if (imageFile != null && !imageFile.isEmpty()) {
                // Converte o arquivo para Base64
                String base64 = Base64.getEncoder().encodeToString(imageFile.getBytes());
                produto.setImagem(base64);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Tratar ou registrar o erro de forma adequada
        }

        // Salva o produto no banco
        produtoRepository.save(produto);

        return "redirect:/produtos/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable("id") Long id, Model model) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido:" + id));
        model.addAttribute("produto", produto);
        model.addAttribute("listaCategorias", categoriaRepository.findAll());
        return "produto-form";
    }

    @GetMapping("/excluir/{id}")
    public String excluirProduto(@PathVariable("id") Long id) {
        produtoRepository.deleteById(id);
        return "redirect:/produtos/listar";
    }
}