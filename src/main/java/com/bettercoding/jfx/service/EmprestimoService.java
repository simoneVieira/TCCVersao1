/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bettercoding.jfx.service;

import com.bettercoding.jfx.model.Cliente;
import com.bettercoding.jfx.model.Emprestimo;
import com.bettercoding.jfx.repository.EmprestimoRepository;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author SimoneBarbosa
 */
@Service
public class EmprestimoService {

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
    public List<Emprestimo>somaComissao(String banco, float valorComissao){
        return emprestimoRepository.findBancoAndValorComissao(banco, valorComissao);
    }

}
