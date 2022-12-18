/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prova2;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedWriter;
import java.io.IOException;
import static java.lang.String.valueOf;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.ButtonGroup;

/**
 *
 * @author Flavia
 */
public class GuiUser extends JFrame{
    private final javax.swing.JLabel lbIdade;
    private final javax.swing.JLabel lbNome;
    private javax.swing.JTextField txtIdade;
    private final javax.swing.JTextField txtNome;
    private final javax.swing.JButton btnAlterar;
    private final javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnGeraCsv;
    private final javax.swing.JButton btnInserir;
    private final javax.swing.JRadioButton jrFeminino;
    private final javax.swing.JRadioButton jrMasculino;    
    private javax.swing.JScrollPane jScrollPane1;   
    private java.awt.ScrollPane scrollPane1;
    private javax.swing.ButtonGroup group;
    private String [] colunas = {"Codigo", "Nome", "Idade", "Sexo"};
    private Object [][]  dados = new Object[0][4];
    String sexo= null;
    DefaultTableModel dtm = new DefaultTableModel(dados, colunas);
    JTable table  = new JTable(dtm);
    public GuiUser() {
        setLayout(new FlowLayout());
	setSize(new Dimension(600, 300));
	setLocationRelativeTo(null);
	setTitle("Prova Flávia");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                
		//Criando Label e Textfields
		lbNome = new JLabel("Nome Completo:");
                txtNome = new JTextField(40);
                lbIdade = new JLabel("Idade:");
                txtIdade = new JTextField(5);
                add(lbNome);
		add(txtNome);
                add(lbIdade);
		add(txtIdade);
               
                //RADIOBUTTON, para seleção de feminino ou masculino
                jrFeminino = new JRadioButton("feminino", false);
                jrMasculino = new JRadioButton("masculino", false);
                add(jrFeminino);
                add(jrMasculino);
                 //Faz com que só possa ser selecionada uma opção ou feminino ou masculino
                group  = new ButtonGroup();
                group.add(jrFeminino);
                group.add(jrMasculino);
                
                //Botões inserir, Aleterar e deletar
		btnInserir = new JButton("Cadastrar");
                btnAlterar = new JButton("Alterar");
                btnDeletar = new JButton("Deletar");
                btnGeraCsv = new JButton("Salvar em .CSV");
		add(btnInserir);   
		add(btnAlterar);
               	add(btnDeletar);
                add(btnGeraCsv);
                
                //Tabela   
                table.setPreferredScrollableViewportSize(new Dimension(500,100));
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
                table.setFillsViewportHeight(true);
		add(scrollPane);
                
                //adicionando eventos
                jrFeminino.addActionListener(new jrFemininoActionPerformed());
                jrMasculino.addActionListener(new jrMasculinoActionPerformed());
                btnInserir.addActionListener(new btnInserirActionPerformed());
                btnAlterar.addActionListener(new btnAlterarActionPerformed());
                btnDeletar.addActionListener(new btnDeletarActionPerformed());
                btnGeraCsv.addActionListener(new btnGeraCsvActionPerformed());
                table.addMouseListener(new tableMouseClicked());
                 
    }
                //adicionando eventos
    class jrFemininoActionPerformed implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            sexo = "Feminino";
            //jrMasculino.setSelected(false);
            
            //System.out.println("Feminino");
        }
    
    }
    class jrMasculinoActionPerformed implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            sexo= "Masculino";
            //jrFeminino.setSelected(false);
        }
    }
    class btnInserirActionPerformed implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel dtm = (DefaultTableModel) table.getModel();
            String codigo = valueOf( table.getRowCount()+1);
            Object[] dados = {codigo ,txtNome.getText(),txtIdade.getText(),sexo};
            if (dados!= null){
                dtm.addRow(dados);
                System.out.println(codigo +";"+ txtNome.getText()+";"+ txtIdade.getText()+";"+sexo);
             }
            txtNome.setText(null);
            txtIdade.setText(null);
            sexo=null;
            group.clearSelection();
            
         } 
    }
    class btnAlterarActionPerformed implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(table.getSelectedRow()!=-1){
                table.setValueAt(txtNome.getText(), table.getSelectedRow(),1);
                table.setValueAt(txtIdade.getText(), table.getSelectedRow(),2);

                if(jrFeminino.isSelected()){
                     table.setValueAt("Feminino", table.getSelectedRow(),3);
                }else if(jrMasculino.isSelected()){
                    table.setValueAt("Masculino", table.getSelectedRow(),3);
                }
            }
            txtNome.setText(null);
            txtIdade.setText(null);
            sexo=null;
            group.clearSelection();
        }
        
    }    
    class btnDeletarActionPerformed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(table.getSelectedRow()>=0){
                DefaultTableModel dtm = (DefaultTableModel) table.getModel();
                dtm.removeRow(table.getSelectedRow());
            }else{
                JOptionPane.showMessageDialog(null,"Selecione um produto para Excluir. ");
            } 
            txtNome.setText(null);
            txtIdade.setText(null);
            sexo=null;
            group.clearSelection();
        }                                          
    }
    class btnGeraCsvActionPerformed extends Exception implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            Path path = Paths.get("tabela.csv");
            Charset utf8 = StandardCharsets.UTF_8;
            try(BufferedWriter writer = Files.newBufferedWriter(path, utf8)){
                int linhasTotais= table.getRowCount();
                for(int i = 0; i<linhasTotais;i++){

                    writer.write(table.getValueAt(i,0).toString()+";"+table.getValueAt(i,1).toString()+
                            ";"+table.getValueAt(i,2).toString()+";"+table.getValueAt(i,3).toString());
                    writer.newLine();
                }
                writer.flush();
                writer.close();
                JOptionPane.showMessageDialog(null,"Dados Salvos com Sucesso!!!");
            
            }catch(IOException a){
                JOptionPane.showMessageDialog(null,a.getMessage()+"\n Desculpa!!! Não foi possível salvar suas informações. " 
                        +"\nCaso o problema persista chame um técnico.");
            }
        } 
    }    
    class tableMouseClicked implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(table.getSelectedRow()>-1){
                txtNome.setText(table.getValueAt(table.getSelectedRow(),1).toString());
                txtIdade.setText(table.getValueAt(table.getSelectedRow(),2).toString());
            if(table.getValueAt(table.getSelectedRow(),3).equals("Feminino")){
                jrFeminino.setSelected(true);
                jrMasculino.setSelected(false);
            }else if(table.getValueAt(table.getSelectedRow(),3).equals("Masculino")){
                jrFeminino.setSelected(false);
                jrMasculino.setSelected(true);
            }
            //txtIdade.setText(table.getValueAt(table.getSelectedRow(),2).toString());
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}       
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
        
    }    
	public static void main (String []args) {
	   new GuiUser().setVisible(true);
                   
         }
}
