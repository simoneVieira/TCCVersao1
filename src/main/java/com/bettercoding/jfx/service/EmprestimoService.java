/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bettercoding.jfx.service;

import com.bettercoding.jfx.model.Cliente;
import com.bettercoding.jfx.model.Emprestimo;
import com.bettercoding.jfx.model.Notificacao;
import com.bettercoding.jfx.model.OrdemPagamento;
import com.bettercoding.jfx.repository.EmprestimoRepository;
import com.bettercoding.jfx.repository.NotificacaoRepository;
import com.bettercoding.jfx.repository.OrdemPagamentoRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author SimoneBarbosa
 */

@Service
public class EmprestimoService {

    @Autowired
    private OrdemPagamentoRepository ordemPagamentoRepository;
    @Autowired
    private NotificacaoRepository notificacaoRepository;
    @Autowired
    private EmprestimoRepository emprestimoRepository;

    public Emprestimo salvaEmprestimo(Emprestimo ep) {
        return emprestimoRepository.save(ep);
    }

    public List<Emprestimo> emprestimos() {
        return emprestimoRepository.findAll();

    }

    public List<Emprestimo> buscaNome(Cliente cl) {
        return emprestimoRepository.findByCliente(cl);
    }
    public List<Emprestimo> buscaEmprestimoObjCli(String nome,String cpf){
        return emprestimoRepository.findClienteByNome(nome,cpf);
        
    }
    
    public List<Emprestimo> buscaNumeroContrato(int numeroContrato){
        return emprestimoRepository.findByNumeroContrato(numeroContrato);
        
    }
    public Double somaComissao(){
        return emprestimoRepository.findBancoAndValorComissao();
    }
   public List<Emprestimo> buscaStatus(String status){
       return emprestimoRepository.findByStatus(status);
   }
      public void excluirEmprestimo(Long id) {
        emprestimoRepository.deleteById(id);
        
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setId_Emprestimo(id);
        Notificacao not = notificacaoRepository.findByEmprestimo(emprestimo) ;
        OrdemPagamento op =ordemPagamentoRepository.findByEmprestimo(emprestimo);
         
        if(not!= null){
            notificacaoRepository.delete(not);
        }
         if(op!= null){
            ordemPagamentoRepository.delete(op);
        }
        
    }
 public List<Emprestimo> buscaDadosRemovidosEmp(){
          return emprestimoRepository.FindAllEvenRemovede();
      }
    public List<Emprestimo> buscaPorContrato(int numeroContrato){
        return emprestimoRepository.FindAllEvenRemo(numeroContrato);
    }
}
