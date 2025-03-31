package com.example.contasapagar.controller;

import com.example.contasapagar.DTO.ContaPagarDTO;
import com.example.contasapagar.DTO.ContaPagarResponseDTO;
import com.example.contasapagar.service.ContaPagarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/contas-a-pagar")
public class ContaPagarController {

    @Autowired
    private ContaPagarService contaPagarService;

    @PostMapping
    public ResponseEntity<ContaPagarResponseDTO> criarConta(@RequestBody ContaPagarDTO contaPagarDTO,
                                                            @RequestHeader("X-Usuario-Id") Long usuarioId) {
        ContaPagarResponseDTO responseDTO = contaPagarService.criarContaPagar(contaPagarDTO, usuarioId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ContaPagarResponseDTO>> listarTodasContas() {
        return ResponseEntity.ok(contaPagarService.listarTodasContas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaPagarResponseDTO> buscarContaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(contaPagarService.buscarContaPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaPagarResponseDTO> atualizarConta(@PathVariable Long id,
                                                                @RequestBody ContaPagarDTO contaPagarDTO,
                                                                @RequestHeader("X-Usuario-Id") Long usuarioId) {
        return ResponseEntity.ok(contaPagarService.atualizarConta(id, contaPagarDTO, usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConta(@PathVariable Long id) {
        contaPagarService.deletarConta(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/pagar")
    public ResponseEntity<ContaPagarResponseDTO> registrarPagamento(@PathVariable Long id,
                                                                    @RequestParam String dataPagamento,
                                                                    @RequestHeader("X-Usuario-Id") Long usuarioId) {
        return ResponseEntity.ok(contaPagarService.registrarPagamento(id, java.sql.Date.valueOf(dataPagamento), usuarioId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ContaPagarResponseDTO>> listarContasPorStatus(@PathVariable String status) {
        return ResponseEntity.ok(contaPagarService.listarContasPorStatus(status));
    }
}
