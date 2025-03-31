package com.example.contasapagar.service;

import com.example.contasapagar.DTO.ContaPagarDTO;
import com.example.contasapagar.DTO.ContaPagarResponseDTO;
import com.example.contasapagar.exception.ResourceNotFoundException;
import com.example.contasapagar.model.*;
import com.example.contasapagar.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContaPagarService {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private ContaPagarRepository contaPagarRepository;

    @Autowired
    private TipoTituloRepository tipoTituloRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public ContaPagarResponseDTO criarContaPagar(ContaPagarDTO contaPagarDTO, Long usuarioId) {
        if (contaPagarDTO.getValor() == null || contaPagarDTO.getDataVencimento() == null ||
                contaPagarDTO.getTipoTituloId() == null) {
            throw new IllegalArgumentException("Campos obrigatórios não informados");
        }

        ContaPagar contaPagar = new ContaPagar();

        //Mapear campos básicos
        contaPagar.setValor(contaPagarDTO.getValor());
        contaPagar.setDataVencimento(contaPagarDTO.getDataVencimento());

        //Data de pagamento é opcional
        if (contaPagarDTO.getDataPagamento() != null) {
            contaPagar.setDataPagamento(contaPagarDTO.getDataPagamento());
        }

        // Buscar e setar relacionamentos
        TipoTitulo tipoTitulo = tipoTituloRepository.findById(contaPagarDTO.getTipoTituloId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de título não encontrado"));
        contaPagar.setTipoTitulo(tipoTitulo);

        if (contaPagarDTO.getClienteId() != null) {
            Optional<Pessoa> cliente = pessoaRepository.findById(contaPagarDTO.getClienteId());
            cliente.ifPresent(contaPagar::setCliente);
        }

        if (contaPagarDTO.getFornecedorId() != null) {
            Optional<Pessoa> fornecedor = pessoaRepository.findById(contaPagarDTO.getFornecedorId());
            fornecedor.ifPresent(contaPagar::setFornecedor);
        }

        // Configurar informações de atualização
       Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        contaPagar.setAtualizadoPor(usuario);
        contaPagar.setAtualizadoEm(new Date());

        ContaPagar savedConta = contaPagarRepository.save(contaPagar);
        return convertToResponseDTO(savedConta);
    }

    public List<ContaPagarResponseDTO> listarTodasContas() {
        return contaPagarRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public ContaPagarResponseDTO buscarContaPorId(Long id) {
        ContaPagar contaPagar = contaPagarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conta a pagar não encontrada com id: " + id));
        return convertToResponseDTO(contaPagar);
    }

    @Transactional
    public ContaPagarResponseDTO atualizarConta(Long id, ContaPagarDTO contaPagarDTO, Long usuarioId) {
        ContaPagar contaPagar = contaPagarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conta a pagar não encontrada com id: " + id));

        // Atualizar campos
        if (contaPagarDTO.getValor() != null) {
            contaPagar.setValor(contaPagarDTO.getValor());
        }
        if (contaPagarDTO.getDataVencimento() != null) {
            contaPagar.setDataVencimento(contaPagarDTO.getDataVencimento());
        }
        if (contaPagarDTO.getDataPagamento() != null) {
            contaPagar.setDataPagamento(contaPagarDTO.getDataPagamento());
        }

        // Atualizar relacionamentos se necessário
        if (contaPagarDTO.getTipoTituloId() != null) {
            TipoTitulo tipoTitulo = tipoTituloRepository.findById(contaPagarDTO.getTipoTituloId())
                    .orElseThrow(() -> new ResourceNotFoundException("Tipo de título não encontrado"));
            contaPagar.setTipoTitulo(tipoTitulo);
        }

        if (contaPagarDTO.getClienteId() != null) {
            Optional<Pessoa> cliente = pessoaRepository.findById(contaPagarDTO.getClienteId());
            cliente.ifPresentOrElse(
                    contaPagar::setCliente,
                    () -> contaPagar.setCliente(null)
            );
        }

        if (contaPagarDTO.getFornecedorId() != null) {
            Optional<Pessoa> fornecedor = pessoaRepository.findById(contaPagarDTO.getFornecedorId());
            fornecedor.ifPresentOrElse(
                    contaPagar::setFornecedor,
                    () -> contaPagar.setFornecedor(null)
            );
        }

        // Atualizar informações de atualização
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        contaPagar.setAtualizadoPor(usuario);
        contaPagar.setAtualizadoEm(new Date());

        ContaPagar updatedConta = contaPagarRepository.save(contaPagar);
        return convertToResponseDTO(updatedConta);
    }

    @Transactional
    public void deletarConta(Long id) {
        if (!contaPagarRepository.existsById(id)) {
            throw new ResourceNotFoundException("Conta a pagar não encontrada com id: " + id);
        }
        contaPagarRepository.deleteById(id);
    }

    @Transactional
    public ContaPagarResponseDTO registrarPagamento(Long id, Date dataPagamento, Long usuarioId) {
        ContaPagar contaPagar = contaPagarRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conta a pagar não encontrada com id: " + id));

        contaPagar.setDataPagamento(dataPagamento);

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        contaPagar.setAtualizadoPor(usuario);
        contaPagar.setAtualizadoEm(new Date());

        ContaPagar updatedConta = contaPagarRepository.save(contaPagar);
        return convertToResponseDTO(updatedConta);
    }

    public List<ContaPagarResponseDTO> listarContasPorStatus(String status) {
        if (status == null) {
            return listarTodasContas();
        }

        List<ContaPagar> contas;

        switch (status.toLowerCase()) {
            case "pago":
                contas = contaPagarRepository.findByDataPagamentoIsNotNull();
                break;
            case "pendente":
                contas = contaPagarRepository.findByDataPagamentoIsNull();
                break;
            default:
                contas = contaPagarRepository.findAll();
        }

        return contas.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private ContaPagarResponseDTO convertToResponseDTO(ContaPagar contaPagar) {
        ContaPagarResponseDTO responseDTO = new ContaPagarResponseDTO();

        // Mapear campos básicos
        responseDTO.setId(contaPagar.getId());
        responseDTO.setValor(contaPagar.getValor());

        if (contaPagar.getDataVencimento() != null) {
            responseDTO.setDataVencimento(DATE_FORMAT.format(contaPagar.getDataVencimento()));
        }

        if (contaPagar.getDataPagamento() != null) {
            responseDTO.setDataPagamento(DATE_FORMAT.format(contaPagar.getDataPagamento()));
        }

        // Mapear relacionamentos
        if (contaPagar.getTipoTitulo() != null) {
            responseDTO.setTipoTitulo(contaPagar.getTipoTitulo().getDescricao());
        }

        responseDTO.setStatus(contaPagar.getDataPagamento() != null ? "Pago" : "Pendente");

        if (contaPagar.getCliente() != null) {
            responseDTO.setCliente(contaPagar.getCliente().getNome());
        }

        if (contaPagar.getFornecedor() != null) {
            responseDTO.setFornecedor(contaPagar.getFornecedor().getNome());
        }

        return responseDTO;
    }
}