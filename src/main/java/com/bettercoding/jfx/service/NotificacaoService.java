/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bettercoding.jfx.service;

import com.bettercoding.jfx.model.Notificacao;
import com.bettercoding.jfx.repository.NotificacaoRepository;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author SimoneBarbosa
 */
@Service
public class NotificacaoService {
    
    @Autowired
    private NotificacaoRepository notificacaoRepository;
    
    
//    public Notificacao buscaStatus(String status) {
//        return notificacaoRepository.findByStatus(status);
//    }
    public List<Notificacao> buscaData(LocalDateTime proximaAlerta) {
        return notificacaoRepository.findByProximaAlertaLessThanEqual(proximaAlerta);
    }
    public Notificacao salvaNotificacao(Notificacao notifica){
       return notificacaoRepository.save(notifica);
    }
  
}
