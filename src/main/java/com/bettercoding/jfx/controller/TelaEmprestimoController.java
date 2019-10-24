/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bettercoding.jfx.controller;

import com.bettercoding.jfx.MyApp;
import com.bettercoding.jfx.model.Cliente;
import com.bettercoding.jfx.model.Emprestimo;
import com.bettercoding.jfx.model.Notificacao;
import com.bettercoding.jfx.service.EmprestimoService;
import com.bettercoding.jfx.service.NotificacaoService;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import util.TextFieldFormatter;

/**
 * FXML Controller class
 *
 * @author SimoneBarbosa
 */
@Controller
public class TelaEmprestimoController implements Initializable, ReceptorCliente {

    @FXML
    private RadioButton radioConta;

    @FXML
    private RadioButton radioOP;

    @FXML
    private TextField idParcelas;

    @FXML
    private TextField fieldMatricula;

    @FXML
    private MenuButton boxBanco;
    @FXML
    private Label labelAgendamento;

    @FXML
    private TextField fieldBeneficio;
    @FXML
    private TextField idCodigoEmprestimo;

    @FXML
    private TextField fieldValor;

    @FXML
    private TextField idTaxa;

    @FXML
    private TextField fieldDataInicio;

    @FXML
    private TextField fieldDataFinal;

    @FXML
    private TextField fieldNumContrato;
    @FXML
    private Label idAtualiza;

    @FXML
    private TextField idValorSolicitado;

    @FXML
    private TextField idValorLiberado;

    @FXML
    private TextField idComissão;

    @FXML
    private TextField fieldComi;

    @FXML
    private Button buttonSelecionarCliente;

    @FXML
    public TextField fieldNomeCliente;
    @FXML
    private TextField fieldCpfCli;

    @FXML
    private MenuButton boxFinan;
    @FXML
    private ToggleGroup grupo;
    @FXML
    private ToggleGroup grupoRadio;

    @FXML
    private TextArea idTextArea;

    @FXML
    private ImageView viewNovo;

    @FXML
    private Button buttonSalvar;
    @FXML
    private Button calcular;

    @FXML
    private Button buttonVoltar;
    @FXML
    private RadioButton radioSim;

    @FXML
    private RadioButton radioNao;

    @FXML
    private TextField fieldData;

//    @FXML
//    private TextField fieldHora;
    @FXML
    private Button buttonExcluir;

    @FXML
    private TableView<Emprestimo> tabela;

    @FXML
    private TextField fieldPesquisa;

    @FXML
    private Button buttonP;

    @FXML
    private ImageView viewSalvar;

    @FXML
    private ImageView viewVoltar;

    @FXML
    private ImageView viewCancelar;
    @FXML
    private TableColumn<Emprestimo, Cliente> colunaCliente;

    @FXML
    private TableColumn<Emprestimo, Cliente> colunaCpf;

    @FXML
    private TableColumn<Emprestimo, String> colunaEmprestimo;

    @FXML
    private TableColumn<Emprestimo, String> colunaBanco;

    @FXML
    private TableColumn<Emprestimo, String> colunaStatus;

    @FXML
    private TableColumn<Emprestimo, String> colunaConvenio;

    @FXML
    private TextField idCliente;
    private TextField tf;
    @FXML
    private ComboBox<String> comboEmprestimos;
    private List<Emprestimo> tipoEmprestimo = new ArrayList<>();
    private ObservableList<Emprestimo> obsEmprestimo;
    private List<Emprestimo> convenioEmprestimo = new ArrayList<>();
    private ObservableList<Emprestimo> observeEmprestimo;
    private List<Emprestimo> statusEmprestimo = new ArrayList<>();
    private ObservableList<Emprestimo> observeEmp;
    private List<Emprestimo> bancoEmprestimo = new ArrayList<>();
    private ObservableList<Emprestimo> observebanco;
    private List<Emprestimo> emprestimoFinanceira = new ArrayList<>();
    private ObservableList<Emprestimo> observeFinanceira;
    @FXML
    private ComboBox<String> comboBoxConvenio;
    @FXML
    private ComboBox<String> idStatus;
    @FXML
    private ComboBox<String> comboBanco;
    @FXML
    private ImageView viewPesquisa;
    @Autowired
    EmprestimoService emprestimoService;

    Emprestimo emprestimo = new Emprestimo();
    Cliente cliente;
    Notificacao notificacao;
    @Autowired
    NotificacaoService notificacaoService;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    // private SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");

    //  SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    DecimalFormat df = new DecimalFormat(",000.00");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image("/imagem/salvar.png");
        viewSalvar.setImage(image);
        Image imageVoltar = new Image("/imagem/voltar.png");
        viewVoltar.setImage(imageVoltar);
        Image imageCancelar = new Image("/imagem/Delete-icon-2.png");
        viewCancelar.setImage(imageCancelar);
        Image imagePesquisar = new Image("/imagem/pesquisa4.png");
        viewPesquisa.setImage(imagePesquisar);
        Image imageNovo = new Image("/imagem/cliente.png");
        viewNovo.setImage(imageNovo);
        carregaEmprestimo();
        comboStatus();
        carregaBancos();
        comboConvenio();
        initTable();

        tabela.getSelectionModel().selectedItemProperty().addListener(
                (Emprestimo, oldValue, newValue) -> selecionarItemTableViewEmprestimo(newValue));
        listarEmprestimos();
    }

    @FXML
    public void botaoVoltar() {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader fxml = new FXMLLoader();
            fxml.setControllerFactory(MyApp.springContext::getBean);
            fxml.setLocation(getClass().getResource("/fxml/TelaPrincipal.fxml"));
            root = fxml.load();
        } catch (IOException ex) {
            Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        TelaPrincipalController.retornaStage().close();

        UsuarioController lc = new UsuarioController();
        UsuarioController.retornaStage();
        TelaPrincipalController.fechaEmprestimo().close();
    }

    public void chamaTelaCliente() {

        ClienteController.setReceptor(this);
        TelaPrincipalController t = new TelaPrincipalController();
        t.botaoCliente();

    }

    public void initTable() {
        colunaCliente.setCellValueFactory(new PropertyValueFactory("nome"));
        colunaCpf.setCellValueFactory(new PropertyValueFactory("cpf"));
        colunaEmprestimo.setCellValueFactory(new PropertyValueFactory("Emprestimo"));
        colunaBanco.setCellValueFactory(new PropertyValueFactory("Banco"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory("Status"));
        colunaConvenio.setCellValueFactory(new PropertyValueFactory("Convenio"));
    }
    public ObservableList<Emprestimo> atualizaTabela = FXCollections.observableArrayList();

    public void listarEmprestimos() {
        if (!atualizaTabela.isEmpty()) {
            atualizaTabela.clear();

        }

        for (Emprestimo emprestimo : emprestimoService.emprestimos()) {
            atualizaTabela.add(emprestimo);

        }

        colunaCliente.setCellValueFactory((TableColumn.CellDataFeatures<Emprestimo, Cliente> p) -> new ReadOnlyObjectWrapper(p.getValue().getCliente().getNome()));
        colunaCpf.setCellValueFactory((TableColumn.CellDataFeatures<Emprestimo, Cliente> p) -> new ReadOnlyObjectWrapper(p.getValue().getCliente().getCpf()));
        colunaEmprestimo.setCellValueFactory(new PropertyValueFactory<>("formaContrato"));
        colunaBanco.setCellValueFactory(new PropertyValueFactory<>("Banco"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        colunaConvenio.setCellValueFactory(new PropertyValueFactory<>("Convenio"));
        tabela.setItems(atualizaTabela);
    }

    @FXML
    public void salvarEmprestimo() {
        emprestimo.setCliente(cliente);
        // emprestimo.setNotificacao(notificacao);

        emprestimo.setValorParcela(Float.parseFloat(fieldValor.getText().replace(".", "").replace(",", ".")));

        emprestimo.setObservacao(idTextArea.getText());

        emprestimo.setMatricula(Integer.parseInt(idParcelas.getText()));
        emprestimo.setBeneficio(fieldBeneficio.getText());
        emprestimo.setTaxa(Float.parseFloat(idTaxa.getText()));
        try {
            emprestimo.setDataInicio(LocalDate.parse(fieldDataInicio.getText(), formatter));
            emprestimo.setDataFim(LocalDate.parse(fieldDataFinal.getText(), formatter));
        } catch (DateTimeParseException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("AVISO");
            alert.setHeaderText("favor ,informar uma data");
            alert.show();
        }
        emprestimo.setValorComissao(Float.parseFloat((idComissão.getText().replace(".", ""))));

        emprestimo.setNumeroContrato(Integer.parseInt(fieldNumContrato.getText()));
        emprestimo.setPorcentagemComissao(Float.parseFloat(fieldComi.getText()));
        emprestimo.setQuantidadeParcela(Integer.parseInt(idParcelas.getText()));
        emprestimo.setValorLiberado(Double.parseDouble(idValorLiberado.getText().replace(".", "").replace(",", ".")));
        emprestimo.setValorSolicitado(Double.parseDouble(idValorSolicitado.getText().replace(".", "").replace(",", ".")));
        emprestimo.setConvenio("" + comboBoxConvenio.getSelectionModel().getSelectedItem());
        emprestimo.setFormaContrato("" + comboEmprestimos.getSelectionModel().getSelectedItem());
        emprestimo.setStatus("" + idStatus.getSelectionModel().getSelectedItem());
        emprestimo.setBanco("" + comboBanco.getSelectionModel().getSelectedItem());
        RadioButton radio = (RadioButton) grupo.getSelectedToggle();
        emprestimo.setFormaPagamento(radio.getText());
        emprestimo.setFormaPagamento(radio.getText());
        RadioButton rad = (RadioButton) grupoRadio.getSelectedToggle();
        emprestimo.setGerarNotificacao(rad.getText());
        emprestimo.setGerarNotificacao(rad.getText());

        //  radioButton();
        if (idCodigoEmprestimo.getText().equals("")) {

            if (radioSim.isSelected() && emprestimo.getNotificacao() == null) {
                emprestimo.setNotificacao(new Notificacao());
                emprestimo.getNotificacao().setData(LocalDateTime.parse(fieldData.getText(), formater));
                emprestimo.getNotificacao().setProximaAlerta(emprestimo.getNotificacao().getData());
                emprestimo.getNotificacao().setEmprestimo(emprestimo);
                emprestimo.getNotificacao().setStatus("Andamento");
            }
//            try {
//
//                 emprestimo.getNotificacao().setData(LocalDateTime.parse(fieldData.getText(), formatter));
//              
//            } catch (DateTimeParseException ex) {
//                System.out.println("error" + ex);
//            }
            // emprestimo.getNotificacao().setProximaAlerta(emprestimo.getNotificacao().getData());

            // emprestimo.getNotificacao() -> Coloca a data que está no campo no obj (texto para data) ( data normal) 
            // emprestimo.getNotificacao() -> copiar a data para data de próxima notificação
            //}
            emprestimo = emprestimoService.salvaEmprestimo(emprestimo);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("CONFIRMAÇÃO");
            alert.setHeaderText("DADOS SALVOS COM SUCESSO!");
            alert.show();
        } else {
            emprestimo.setId_Emprestimo((Long.parseLong(idCodigoEmprestimo.getText())));
            Emprestimo AlterarEmpres = emprestimoService.salvaEmprestimo(emprestimo);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("CONFIRMAÇÃO");
            alert.setHeaderText("DADOS ALTERADOS COM SUCESSO!");
            alert.show();
        }

    }

    @Override
    public void receberCliente(Cliente c) {
        this.cliente = c;
        fieldNomeCliente.setText(c.getNome());
        fieldCpfCli.setText(c.getCpf());

    }

    public void novoEmprestimo() {
        fieldValor.setText("");
        idComissão.setText("");
        idValorSolicitado.setText("");
        idValorLiberado.setText("");
        fieldComi.setText("");
        idTaxa.setText("");
        fieldNomeCliente.setText("");
        fieldCpfCli.setText("");
        fieldDataInicio.setText("");
        fieldDataFinal.setText("");
        comboBoxConvenio.setValue("");
        comboEmprestimos.setValue("");
        idStatus.setValue("");
        comboBanco.setValue("");
        fieldNumContrato.setText("");
        fieldMatricula.setText("");
        idParcelas.setText("");
        fieldBeneficio.setText("");
        idCodigoEmprestimo.setText("");
        fieldData.setText("");
        radioSim.setSelected(false);
        radioNao.setSelected(false);
        radioConta.setSelected(false);
        radioOP.setSelected(false);
        emprestimo = new Emprestimo();
    }

    public void somaComissao() {
        Double valorLiberado;
        Double valorTaxa;
        valorLiberado = Double.parseDouble(idValorLiberado.getText().replace(".", "").replace(",", "."));
        valorTaxa = Double.parseDouble(fieldComi.getText());
        Double soma = ((valorLiberado * valorTaxa) / 100);
        //String somaTotal = String.valueOf(soma);  
        idComissão.setText(String.valueOf(soma));
    }

    public void carregaEmprestimo() {
        ObservableList<String> itens = FXCollections.observableArrayList();
        itens.addAll("Novo", "Refinanciamento", "Portabilidade", "Cartao Consignado");
        comboEmprestimos.setItems(itens);
    }

    public void comboConvenio() {
        ObservableList<String> itens = FXCollections.observableArrayList();
        itens.addAll("inss", "siape", "estado", "marinha", "saneago", "exército");
        comboBoxConvenio.setItems(itens);
    }

    public void comboStatus() {
        ObservableList<String> itens = FXCollections.observableArrayList();
        itens.addAll("Andamento", "Pendente", "Pago", "Cancelado");
        idStatus.setItems(itens);
    }

    public void carregaBancos() {
        ObservableList<String> itens = FXCollections.observableArrayList();
        itens.addAll("Bmg", "Pan", "JV-Itau", "Bgn", "Safra", "Olé", "Mercantil", "Bradesco_Bmc", "Daycoval", "Intermedio,"
                + "Bv", "Banrisul", "Santander");
        comboBanco.setItems(itens);
    }

    @FXML
    private void botaoPesquisa() {

        String x = fieldPesquisa.getText();

        try {

            long cpf = Long.parseLong(x);

            listarEmprestimoPorNumero();

        } catch (NumberFormatException ignore) {

            listarClienteEmprestimo();

        }

    }

    public void listarClienteEmprestimo() {
        if (!atualizaTabela.isEmpty()) {
            atualizaTabela.clear();

        }
        List<Emprestimo> listaPeloNomeCli = emprestimoService.buscaEmprestimoObjCli(fieldPesquisa.getText(), fieldPesquisa.getText());

        if (listaPeloNomeCli.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("AVISO");
            alert.setHeaderText("Não foi possível encontrar um Empréstimo para esse Cliente!");
            alert.show();
            return;
        }

        for (Emprestimo emprestimo : listaPeloNomeCli) {
            atualizaTabela.add(emprestimo);
        }

        colunaCliente.setCellValueFactory((TableColumn.CellDataFeatures<Emprestimo, Cliente> p) -> new ReadOnlyObjectWrapper(p.getValue().getCliente().getNome()));
        colunaCpf.setCellValueFactory((TableColumn.CellDataFeatures<Emprestimo, Cliente> p) -> new ReadOnlyObjectWrapper(p.getValue().getCliente().getCpf()));
        colunaEmprestimo.setCellValueFactory(new PropertyValueFactory<>("formaContrato"));
        colunaBanco.setCellValueFactory(new PropertyValueFactory<>("Banco"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        colunaConvenio.setCellValueFactory(new PropertyValueFactory<>("Convenio"));
        tabela.setItems(atualizaTabela);

    }

    public void listarEmprestimoPorNumero() {
        if (!atualizaTabela.isEmpty()) {
            atualizaTabela.clear();

        }
        List<Emprestimo> listaPeloNomeCli = emprestimoService.buscaNumeroContrato(Integer.parseInt(fieldPesquisa.getText()));

        if (listaPeloNomeCli.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("AVISO");
            alert.setHeaderText("Não foi possível encontrar um Empréstimo para esse Cliente!");
            alert.show();
            return;
        }

        for (Emprestimo emprestimo : listaPeloNomeCli) {
            atualizaTabela.add(emprestimo);
        }

        colunaCliente.setCellValueFactory((TableColumn.CellDataFeatures<Emprestimo, Cliente> p) -> new ReadOnlyObjectWrapper(p.getValue().getCliente().getNome()));
        colunaCpf.setCellValueFactory((TableColumn.CellDataFeatures<Emprestimo, Cliente> p) -> new ReadOnlyObjectWrapper(p.getValue().getCliente().getCpf()));
        colunaEmprestimo.setCellValueFactory(new PropertyValueFactory<>("formaContrato"));
        colunaBanco.setCellValueFactory(new PropertyValueFactory<>("Banco"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        colunaConvenio.setCellValueFactory(new PropertyValueFactory<>("Convenio"));
        tabela.setItems(atualizaTabela);

    }

    public void selecionarItemTableViewEmprestimo(Emprestimo emprestimo) {
        fieldValor.setText(String.valueOf(emprestimo.getValorParcela()));
        idValorSolicitado.setText(String.valueOf(emprestimo.getValorSolicitado()));
        idValorLiberado.setText(String.valueOf(emprestimo.getValorLiberado()));
        idComissão.setText(String.valueOf(emprestimo.getValorComissao()));
        fieldComi.setText(String.valueOf(emprestimo.getPorcentagemComissao()));
        idTaxa.setText(String.valueOf(emprestimo.getTaxa()));
        fieldNomeCliente.setText(emprestimo.getCliente().getNome());
        fieldCpfCli.setText(String.valueOf(emprestimo.getCliente().getCpf()));
        fieldDataInicio.setText(formatter.format(emprestimo.getDataInicio()));
        fieldDataFinal.setText(formatter.format(emprestimo.getDataFim()));
        comboBoxConvenio.setValue(emprestimo.getConvenio());
        comboEmprestimos.setValue(emprestimo.getFormaContrato());
        idStatus.setValue(emprestimo.getStatus());
        comboBanco.setValue(emprestimo.getBanco());
        fieldNumContrato.setText(String.valueOf(emprestimo.getNumeroContrato()));
        fieldMatricula.setText(String.valueOf(emprestimo.getMatricula()));
        idParcelas.setText(String.valueOf(emprestimo.getQuantidadeParcela()));
        fieldBeneficio.setText(String.valueOf(emprestimo.getBeneficio()));
        idCodigoEmprestimo.setText(String.valueOf(emprestimo.getId_Emprestimo()));
        fieldData.setText(formatter.format(emprestimo.getNotificacao().getProximaAlerta()));
        cliente = emprestimo.getCliente();

        if (emprestimo.getFormaPagamento().equals("Contaaa")) {
            radioConta.setSelected(true);
        } else {
            radioOP.setSelected(true);
        }
        if (emprestimo.getNotificacao() != null) {
            radioSim.setSelected(true);
            fieldData.setText(formatter.format(emprestimo.getNotificacao().getProximaAlerta()));
            //fieldData.setText(emprestimo.getGerarNotificacao().format(formatter));

            //fieldData.setVisible(true);
//            fieldHora.setVisible(true);
        } else {
            radioNao.setSelected(true);
            // fieldData.setVisible(false);
//            fieldHora.setVisible(false);
        }

    }

    @FXML
    private void validaDataInicio() {
        TextFieldFormatter formata = new TextFieldFormatter();
        formata.setMask("##/##/####");
        formata.setCaracteresValidos("0123456789");
        formata.setTf(fieldDataInicio);
        formata.formatter();

    }

    @FXML
    public void formata() {

        TextFieldFormatter formata = new TextFieldFormatter();
        formata.setMask("#.###,##");
        formata.setCaracteresValidos("0123456789");
        formata.setTf(fieldValor);
        formata.formatter();

    }

    public void chamaTelaNotificacao() {
        ExecutaTarefa exect = new ExecutaTarefa();
        exect.run();
    }
//

//    public void radioButton() {
//        emprestimo.setNotificacao(notificacao);
//        boolean radio;
//        radio = radioSim.isSelected();
//        if (radio == true) {
//            fieldData.setVisible(true);
////            fieldHora.setVisible(true);
//            if (emprestimo.getNotificacao() == null) {
//                emprestimo.setNotificacao(new Notificacao());
//                emprestimo.getNotificacao().setData(LocalDateTime.parse(fieldData.getText(), formater));
//                emprestimo.getNotificacao().setProximaAlerta(emprestimo.getNotificacao().getData());
//                emprestimo.getNotificacao().setStatus("Andamento");
//                emprestimo = emprestimoService.salvaEmprestimo(emprestimo);
//            }
//        } else {
//            fieldData.setVisible(false);
//
//        }
//
//    }
    @FXML
    public void monetaryField() {
        fieldValor.setAlignment(Pos.CENTER_RIGHT);
        fieldValor.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            String value = fieldValor.getText();
            value = value.replaceAll("[^0-9]", "");
            value = value.replaceAll("([0-9]{1})([0-9]{14})$", "$1.$2");
            value = value.replaceAll("([0-9]{1})([0-9]{11})$", "$1.$2");
            value = value.replaceAll("([0-9]{1})([0-9]{8})$", "$1.$2");
            value = value.replaceAll("([0-9]{1})([0-9]{5})$", "$1.$2");
            value = value.replaceAll("([0-9]{1})([0-9]{2})$", "$1,$2");
            fieldValor.setText(value);
            System.out.println("teste");
            fieldValor.textProperty().addListener((ObservableValue<? extends String> observableValue, String oldValue1, String newValue1) -> {
                if (newValue1.length() > 17) {
                    fieldValor.setText(oldValue1);
                }
                System.out.println("mudou");
            });
        });
    }

    @FXML
    public void valorSolicitado() {
        idValorSolicitado.setAlignment(Pos.CENTER_RIGHT);
        idValorSolicitado.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                String value = idValorSolicitado.getText();
                value = value.replaceAll("[^0-9]", "");
                value = value.replaceAll("([0-9]{1})([0-9]{14})$", "$1.$2");
                value = value.replaceAll("([0-9]{1})([0-9]{11})$", "$1.$2");
                value = value.replaceAll("([0-9]{1})([0-9]{8})$", "$1.$2");
                value = value.replaceAll("([0-9]{1})([0-9]{5})$", "$1.$2");
                value = value.replaceAll("([0-9]{1})([0-9]{2})$", "$1,$2");
                idValorSolicitado.setText(value);
                idValorSolicitado.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                        if (newValue.length() > 17) {
                            idValorSolicitado.setText(oldValue);
                        }
                    }
                });
            }
        });
    }

    @FXML
    public void valorLiberado() {
        idValorLiberado.setAlignment(Pos.CENTER_RIGHT);
        idValorLiberado.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                String value = idValorLiberado.getText();
                value = value.replaceAll("[^0-9]", "");
                value = value.replaceAll("([0-9]{1})([0-9]{14})$", "$1.$2");
                value = value.replaceAll("([0-9]{1})([0-9]{11})$", "$1.$2");
                value = value.replaceAll("([0-9]{1})([0-9]{8})$", "$1.$2");
                value = value.replaceAll("([0-9]{1})([0-9]{5})$", "$1.$2");
                value = value.replaceAll("([0-9]{1})([0-9]{2})$", "$1,$2");
                idValorLiberado.setText(value);
                idValorLiberado.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                        if (newValue.length() > 17) {
                            idValorLiberado.setText(oldValue);
                        }
                    }
                });
            }
        });
    }

    @FXML
    public void valorComissao() {
        idComissão.setAlignment(Pos.CENTER_RIGHT);
        idComissão.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                String value = idComissão.getText();
                value = value.replaceAll("[^0-9]", "");
                value = value.replaceAll("([0-9]{1})([0-9]{14})$", "$1.$2");
                value = value.replaceAll("([0-9]{1})([0-9]{11})$", "$1.$2");
                value = value.replaceAll("([0-9]{1})([0-9]{8})$", "$1.$2");
                value = value.replaceAll("([0-9]{1})([0-9]{5})$", "$1.$2");
                value = value.replaceAll("([0-9]{1})([0-9]{2})$", "$1,$2");
                idComissão.setText(value);
                idComissão.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                        if (newValue.length() > 17) {
                            idComissão.setText(oldValue);
                        }
                    }
                });
            }
        });
    }

//        if ((notiData).equals (dataAtual)){
//            
//             Stage stage = new Stage();
//                        Parent root = null;
//                        try {
//                            FXMLLoader fxml = new FXMLLoader();
//                            fxml.setControllerFactory(MyApp.springContext::getBean);
//                            fxml.setLocation(getClass().getResource("/fxml/TelaNotificacao.fxml"));
//                            root = fxml.load();
//                        } catch (IOException ex) {
//                            Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        Scene scene = new Scene(root);
//                        stage.setScene(scene);
//                        stage.show();
//                        stage.setResizable(false);
//                        stage.setTitle("Tela de Messagem!");
//                        stage.setResizable(false);
//            
//        }
//    }
    @FXML
    public void atualizaEmprestimo() {

        if (!atualizaTabela.isEmpty()) {
            atualizaTabela.clear();

        }

        for (Emprestimo emprestimo : emprestimoService.emprestimos()) {
            atualizaTabela.add(emprestimo);

        }

    }

}
