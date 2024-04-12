package upa;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.UIManager;

public class tela extends JFrame {
    private static JComboBox<String> comboBoxCRM;
    private static JTextField txtDataHoraFim;
    private static JTextField txtdataHoraInicio;
    private static JLabel lblEspecialidade;
    private static JLabel lblNome;
    private static JLabel lblDataI;
    private static JLabel lblCRM;
    private static JTable table;
    private static JLabel lblusuario;
    private static JButton btnNewButton_4;
    private static JButton btnNewButton_5;
    private static JTextField txtNome;
    private static JTextField txtEspecialidade;
    private static JTabbedPane tabbedPane;
    private JPanel contentPane;
    private JLabel lblNome_1;
    private JTextField txtNomeMedico;
    private JLabel lblCrm;
    private JTextField txtCrm;
    private JButton btnSalvar;
    private JButton btnEditar;
    private JComboBox<String> comboBoxEspecialidade;
    private JLabel lblEspecialidade_1;
    private JButton btnExcluirMedico;
    private JScrollPane scrollPane_1;
    private JTable table_1;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
  
                try {
                    tela frameTela = new tela();
                    frameTela.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    
    
    
    
    private static String obterNomeDoGestor(int cpfGestor) {
    	Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String nomeGestor = null;

        try {
        	conn = Conexao.ConnectDb();
            String sql = "SELECT nome FROM gestor WHERE cpf = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, telaLogin.cpfGestor);
            rs = stmt.executeQuery();

            if (rs.next()) {
                nomeGestor = rs.getString("nome");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return nomeGestor;
    }

    private static String[] obterInfoDoMedicoPeloCRM(String crm) {
    	Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
        	conn = Conexao.ConnectDb();
            String sql = "SELECT m.nome_medico, e.descricao_esp " +
                    "FROM medico m " +
                    "JOIN especialidade_m e ON m.especialidade = e.id_especialidade_m " +
                    "WHERE m.crm_medico = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, crm);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String nomeMedico = rs.getString("nome_medico");
                String descricaoEspecialidade = rs.getString("descricao_esp");
                return new String[]{nomeMedico, descricaoEspecialidade};
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static void preencherNomesMedicos(JComboBox<String> comboBoxNome) {
    	Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
        	conn = Conexao.ConnectDb();
            String sql = "SELECT nome_medico FROM medico";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String nomeMedico = rs.getString("nome_medico");
                comboBoxNome.addItem(nomeMedico);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public static void preencherCRMMedicos(JComboBox<String> comboBoxCRM) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.ConnectDb();
            String sql = "SELECT crm_medico FROM medico WHERE id_unidade = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, telaLogin.unidade); // Filtrar pela unidade definida

            rs = stmt.executeQuery();

            // Limpar o JComboBox para evitar duplicatas
            comboBoxCRM.removeAllItems();

            while (rs.next()) {
                String crmMedico = rs.getString("crm_medico");
                comboBoxCRM.addItem(crmMedico);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    public static void atualizarTabela(DefaultTableModel model) {
        Connection conexao = Conexao.ConnectDb();

        if (conexao != null) {
            try {
                String consultaSQL = "SELECT dm.crm_medico, dm.dataHoraInicio, dm.dataHoraFim, m.nome_medico, e.descricao_esp " +
                        "FROM disponibilidade_medico dm " +
                        "JOIN medico m ON dm.crm_medico = m.crm_medico " +
                        "JOIN especialidade_m e ON m.especialidade = e.id_especialidade_m " +
                        "WHERE dm.id_unidade = ?";

                PreparedStatement pstmt = conexao.prepareStatement(consultaSQL);
                pstmt.setInt(1, telaLogin.unidade);

                ResultSet rs = pstmt.executeQuery();

                model.setRowCount(0);

                while (rs.next()) {
                    Object[] row = new Object[5];
                    row[0] = rs.getString("crm_medico");
                    row[1] = rs.getString("nome_medico");
                    row[2] = rs.getString("descricao_esp"); // Agora é a descrição da especialidade
                    row[3] = rs.getString("dataHoraInicio");
                    row[4] = rs.getString("dataHoraFim");
                    model.addRow(row);
                }

                rs.close();
                pstmt.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } finally {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public tela() {
        setTitle("Tela Upa");
        setSize(915, 755);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();

        getContentPane().add(tabbedPane);
        DefaultTableModel model = new DefaultTableModel(
            new Object[][] {
                {false, null, null, null, "", null, null, null},
            },
            new String[] {
                "CRM", "NOME", "ESPECIALIDADE", "DATA-HORA-INICIO", "DATA-HORA-FIM"
            }
        );
        atualizarTabela(model);
        String nomeGestor = obterNomeDoGestor(telaLogin.cpfGestor);
        
        
                JPanel novaAbaPanel = new JPanel();
                novaAbaPanel.setLayout(null);
                
                        tabbedPane.addTab("Disponibilidade Medica", novaAbaPanel);
                        
                                comboBoxCRM = new JComboBox<>();
                                comboBoxCRM.setBounds(10, 52, 148, 23);
                                novaAbaPanel.add(comboBoxCRM);
                                preencherCRMMedicos(comboBoxCRM);
                                
                                  txtDataHoraFim = new JTextField();
                                  txtDataHoraFim.setBounds(192, 166, 155, 20);
                                  txtDataHoraFim.setColumns(10);
                                  novaAbaPanel.add(txtDataHoraFim);
                                  
                                          txtdataHoraInicio = new JTextField();
                                          txtdataHoraInicio.setToolTipText("\r\n");
                                          txtdataHoraInicio.setBounds(192, 122, 155, 20);
                                          txtdataHoraInicio.setColumns(10);
                                          novaAbaPanel.add(txtdataHoraInicio);
                                          
                                                  lblEspecialidade = new JLabel("ESPECIALIDADE");
                                                  lblEspecialidade.setFont(new Font("Arial", Font.BOLD, 14));
                                                  lblEspecialidade.setBounds(33, 152, 125, 14);
                                                  novaAbaPanel.add(lblEspecialidade);
                                                  
                                                          lblNome = new JLabel("NOME");
                                                          lblNome.setFont(new Font("Arial", Font.BOLD, 14));
                                                          lblNome.setBounds(52, 97, 46, 14);
                                                          novaAbaPanel.add(lblNome);
                                                          
                                                                  lblDataI = new JLabel("DATA-HORA-INICIO");
                                                                  lblDataI.setFont(new Font("Arial", Font.BOLD, 14));
                                                                  lblDataI.setBounds(200, 109, 147, 14);
                                                                  novaAbaPanel.add(lblDataI);
                                                                  
                                                                          lblCRM = new JLabel("CRM");
                                                                          lblCRM.setFont(new Font("Arial", Font.BOLD, 14));
                                                                          lblCRM.setBounds(52, 38, 33, 14);
                                                                          novaAbaPanel.add(lblCRM);
                                                                          
                                                                                  JScrollPane scrollPane = new JScrollPane();
                                                                                  scrollPane.setBounds(33, 275, 805, 360);
                                                                                  novaAbaPanel.add(scrollPane);
                                                                                  
                                                                                          table = new JTable();
                                                                                          
                                                                                                  table.setModel(new DefaultTableModel(
                                                                                                  	new Object[][] {
                                                                                                  		{null, null, null, "", null},
                                                                                                  	},
                                                                                                  	new String[] {
                                                                                                  		"CRM", "NOME", "ESPECIALIDADE", "DATA-HORA-INICIO", "DATA-HORA-FIM"
                                                                                                  	}
                                                                                                  ));
                                                                                                  table.getColumnModel().getColumn(1).setPreferredWidth(113);
                                                                                                  table.getColumnModel().getColumn(2).setPreferredWidth(102);
                                                                                                  table.getColumnModel().getColumn(3).setPreferredWidth(114);
                                                                                                  table.getColumnModel().getColumn(4).setPreferredWidth(97);
                                                                                                  
                                                                                                          table.setModel(model);
                                                                                                          scrollPane.setViewportView(table);
                                                                                                          
                                                                                                                  JButton btnNewButton = new JButton("Adicionar");
                                                                                                                  btnNewButton.setBackground(UIManager.getColor("Button.light"));
                                                                                                                  btnNewButton.setFont(new Font("Arial", Font.BOLD, 14));
                                                                                                                  btnNewButton.setBackground(new Color(173, 216, 230)); // Cor de fundo azul-claro
                                                                                                                  btnNewButton.setForeground(Color.BLACK); // Cor do texto preto
                                                                                                                  btnNewButton.setBorder(new RoundedBorder(10));
                                                                                                                  btnNewButton.setBounds(15, 211, 167, 46);
                                                                                                                  novaAbaPanel.add(btnNewButton);
                                                                                                                  
                                                                                                                          btnNewButton.addActionListener(new ActionListener() {
                                                                                                                              public void actionPerformed(ActionEvent e) {
                                                                                                                                  Connection conexao = Conexao.ConnectDb();
                                                                                                                  
                                                                                                                                  if (conexao != null) {
                                                                                                                                      String CRM = (String) comboBoxCRM.getSelectedItem();
                                                                                                                                      String nome = txtNome.getText();
                                                                                                                                      String especialidade = txtEspecialidade.getText();
                                                                                                                                      String dataHoraInicio = txtdataHoraInicio.getText();
                                                                                                                                      String dataHoraFim = txtDataHoraFim.getText();
                                                                                                                  
                                                                                                                                      try {
                                                                                                                                          SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                                                                                                  
                                                                                                                                          Date parsedDataHoraInicio = dateTimeFormat.parse(dataHoraInicio);
                                                                                                                                          Date parsedDataHoraFim = dateTimeFormat.parse(dataHoraFim);
                                                                                                                  
                                                                                                                                          String horaInicio = dateTimeFormat.format(parsedDataHoraInicio);
                                                                                                                                          String horaFim = dateTimeFormat.format(parsedDataHoraFim);
                                                                                                                  
                                                                                                                                          String inserirSQL = "INSERT INTO disponibilidade_medico (crm_medico, dataHoraInicio, dataHoraFim, id_unidade) VALUES (?, ?, ?, ?)";
                                                                                                                                          PreparedStatement pstmt = conexao.prepareStatement(inserirSQL);
                                                                                                                                          pstmt.setString(1, CRM);
                                                                                                                                          pstmt.setTimestamp(2, new Timestamp(parsedDataHoraInicio.getTime()));
                                                                                                                                          pstmt.setTimestamp(3, new Timestamp(parsedDataHoraFim.getTime()));
                                                                                                                                          pstmt.setInt(4, telaLogin.unidade);
                                                                                                                  
                                                                                                                                          pstmt.executeUpdate();
                                                                                                                  
                                                                                                                                          // Agora, vamos buscar os dados da ViewDisponibilidadeMedico e enviar para o outro banc                     
                                                                                                                                          JOptionPane.showMessageDialog(null, "Médico inserido com sucesso!");
                                                                                                                  
                                                                                                                                          txtdataHoraInicio.setText("");
                                                                                                                                          txtDataHoraFim.setText("");
                                                                                                                  
                                                                                                                                      } catch (Exception ex) {
                                                                                                                                          ex.printStackTrace();
                                                                                                                                          JOptionPane.showMessageDialog(null, ex.getMessage());
                                                                                                                                      } finally {
                                                                                                                                          try {
                                                                                                                                              atualizarTabela(model);
                                                                                                                                              conexao.close();
                                                                                                                                          } catch (SQLException ex) {
                                                                                                                                              ex.printStackTrace();
                                                                                                                                          }
                                                                                                                                      }
                                                                                                                                  }
                                                                                                                              }
                                                                                                                          });
                                                                                                                          
                                                                                                                          
                                                                                                                                  JButton btnNewButton_1 = new JButton("Remover");
                                                                                                                                  btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 14));
                                                                                                                                  btnNewButton_1.setBackground(new Color(173, 216, 230)); // Cor de fundo azul-claro
                                                                                                                                  btnNewButton_1.setForeground(Color.BLACK); // Cor do texto preto
                                                                                                                                  btnNewButton_1.setBorder(new RoundedBorder(10));
                                                                                                                                  btnNewButton_1.setBounds(192, 211, 167, 46);
                                                                                                                                  novaAbaPanel.add(btnNewButton_1);
                                                                                                                                  
                                                                                                                                          btnNewButton_1.addActionListener(new ActionListener() {
                                                                                                                                              public void actionPerformed(ActionEvent e) {
                                                                                                                                                  int selectedRow = table.getSelectedRow();
                                                                                                                                  
                                                                                                                                                  if (selectedRow == -1) {
                                                                                                                                                  	
                                                                                                                                                      JOptionPane.showMessageDialog(null, "Selecione um médico para remover.");
                                                                                                                                                      return;
                                                                                                                                                  }
                                                                                                                                  
                                                                                                                                                  String CRM = (String) model.getValueAt(selectedRow, 0);
                                                                                                                                  
                                                                                                                                                  int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover o médico: " + CRM + "?");
                                                                                                                                  
                                                                                                                                                  if (confirm == JOptionPane.YES_OPTION) {
                                                                                                                                                      Connection conexao = Conexao.ConnectDb();
                                                                                                                                  
                                                                                                                                                      if (conexao != null) {
                                                                                                                                                          try {
                                                                                                                                                              String removerSQL = "DELETE FROM disponibilidade_medico WHERE crm_medico = ?";
                                                                                                                                                              PreparedStatement pstmt = conexao.prepareStatement(removerSQL);
                                                                                                                                                              pstmt.setString(1, CRM);
                                                                                                                                                              pstmt.executeUpdate();
                                                                                                                                                              JOptionPane.showMessageDialog(null, "Médico removido com sucesso.");
                                                                                                                                                              atualizarTabela(model);
                                                                                                                                                          } catch (SQLException ex) {
                                                                                                                                                              ex.printStackTrace();
                                                                                                                                                              JOptionPane.showMessageDialog(null, ex.getMessage());
                                                                                                                                                          } finally {
                                                                                                                                                          	atualizarTabela(model);
                                                                                                                                                              try {
                                                                                                                                                                  conexao.close();}
                                                                                                                                                              catch (SQLException ex) {
                                                                                                                                                                  ex.printStackTrace();
                                                                                                                                                              }
                                                                                                                                                          }
                                                                                                                                                      }
                                                                                                                                                  }
                                                                                                                                              }
                                                                                                                                          });
                                                                                                                                          
                                                                                                                                          
                                                                                                                                          
                                                                                                                                                  btnNewButton_4 = new JButton("Sair");
                                                                                                                                                  btnNewButton_4.setFont(new Font("Arial", Font.BOLD, 11));
                                                                                                                                                  btnNewButton_4.setBackground(new Color(173, 216, 230)); // Cor de fundo azul-claro
                                                                                                                                                  btnNewButton_4.setForeground(Color.BLACK); // Cor do texto preto
                                                                                                                                                  btnNewButton_4.setBorder(new RoundedBorder(10));
                                                                                                                                                  btnNewButton_4.setBounds(652, 91, 148, 28);
                                                                                                                                                  novaAbaPanel.add(btnNewButton_4);
                                                                                                                                                  
                                                                                                                                                          btnNewButton_4.addActionListener(new ActionListener() {
                                                                                                                                                              public void actionPerformed(ActionEvent e) {
                                                                                                                                                                  // Fechar a janela atual
                                                                                                                                                                  SwingUtilities.getWindowAncestor((Component)e.getSource()).dispose();
                                                                                                                                                  
                                                                                                                                                                  // Criar uma nova instância da tela de login e exibi-la
                                                                                                                                                                  telaLogin novaTelaLogin = new telaLogin(); // Substitua telaLogin pelo nome correto da sua classe de tela de login
                                                                                                                                                              }
                                                                                                                                                          });
                                                                                                                                                          JButton btnCsv = new JButton("Criar CSV");
                                                                                                                                                          btnCsv.setFont(new Font("Arial", Font.BOLD, 14));
                                                                                                                                                          btnCsv.setBackground(new Color(173, 216, 230)); // Cor de fundo azul-claro
                                                                                                                                                          btnCsv.setForeground(Color.BLACK); // Cor do texto preto
                                                                                                                                                          btnCsv.setBorder(new RoundedBorder(10));
                                                                                                                                                          btnCsv.setBounds(369, 211, 167, 46);
                                                                                                                                                          novaAbaPanel.add(btnCsv);
                                                                                                                                                          
                                                                                                                                                                  // Adicionando um ActionListener ao botão
                                                                                                                                                                  btnCsv.addActionListener(new ActionListener() {
                                                                                                                                                                      @Override
                                                                                                                                                                      public void actionPerformed(ActionEvent e) {
                                                                                                                                                                          // Chama a função exportarViewParaTexto() quando o botão for clicado
                                                                                                                                                                          exportarViewParaTexto();
                                                                                                                                                                      }
                                                                                                                                                                  });
                                                                                                                                                                  
                                                                                                                                                                          btnNewButton_5 = new JButton("Mudar Senha");
                                                                                                                                                                          btnNewButton_5.setFont(new Font("Arial", Font.BOLD, 11));
                                                                                                                                                                          btnNewButton_5.setBackground(new Color(173, 216, 230)); // Cor de fundo azul-claro
                                                                                                                                                                          btnNewButton_5.setForeground(Color.BLACK); // Cor do texto preto
                                                                                                                                                                          btnNewButton_5.setBorder(new RoundedBorder(10));
                                                                                                                                                                          btnNewButton_5.setBounds(652, 52, 148, 29); // Posição e tamanho do botão
                                                                                                                                                                          
                                                                                                                                                                          // Adicionando ActionListener ao botão
                                                                                                                                                                          btnNewButton_5.addActionListener(new ActionListener() {
                                                                                                                                                                              public void actionPerformed(ActionEvent e) {
                                                                                                                                                                                  // Lógica para a ação do botão "Mudar Senha"
                                                                                                                                                                                  String novaSenha = JOptionPane.showInputDialog(null, "Digite a nova senha:");
                                                                                                                                                                                  if (novaSenha != null && !novaSenha.isEmpty()) {
                                                                                                                                                                                      atualizarSenhaGestorNoBanco(novaSenha);
                                                                                                                                                                                      JOptionPane.showMessageDialog(null, "Senha alterada com sucesso!");
                                                                                                                                                                                  } else {
                                                                                                                                                                                      JOptionPane.showMessageDialog(null, "A senha não pode ficar em branco!");
                                                                                                                                                                                  }
                                                                                                                                                                              }
                                                                                                                                                                          });
                                                                                                                                                                          novaAbaPanel.add(btnNewButton_5);
                                                                                                                                                                          
                                                                                                                                                                          lblusuario = new JLabel("Usuário");
                                                                                                                                                                          lblusuario.setFont(new Font("Arial Black", Font.BOLD, 14));
                                                                                                                                                                          lblusuario.setBounds(671, 28, 148, 14);
                                                                                                                                                                          novaAbaPanel.add(lblusuario);
                                                                                                                                                                          lblusuario.setText("Usuário: " + nomeGestor);
                                                                                                                                                                          
                                                                                                                                                                                  txtNome = new JTextField();
                                                                                                                                                                                  txtNome.setColumns(10);
                                                                                                                                                                                  txtNome.setBounds(10, 109, 148, 23);
                                                                                                                                                                                  novaAbaPanel.add(txtNome);
                                                                                                                                                                                  
                                                                                                                                                                                          txtEspecialidade = new JTextField();
                                                                                                                                                                                          txtEspecialidade.setColumns(10);
                                                                                                                                                                                          txtEspecialidade.setBounds(10, 166, 148, 20);
                                                                                                                                                                                          novaAbaPanel.add(txtEspecialidade);
                                                                                                                                                                                          
                                                                                                                                                                                          JLabel lblDataI_1 = new JLabel("DATA-HORA-FIM");
                                                                                                                                                                                          lblDataI_1.setFont(new Font("Arial", Font.BOLD, 14));
                                                                                                                                                                                          lblDataI_1.setBounds(202, 152, 117, 14);
                                                                                                                                                                                          novaAbaPanel.add(lblDataI_1);
                                                                                                                                                                                          


                                                                                                                                                                                          contentPane = new JPanel();
                                                                                                                                                                                          contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                                                                                                                                                                                          tabbedPane.addTab("Gerenciar Medicos", null, contentPane, null);
                                                                                                                                                                                          contentPane.setLayout(null);
                                                                                                                                                                                          
                                                                                                                                                                                          lblNome_1 = new JLabel("Nome");
                                                                                                                                                                                          lblNome_1.setFont(new Font("Arial", Font.BOLD, 14));
                                                                                                                                                                                          lblNome_1.setBounds(151, 46, 70, 20);
                                                                                                                                                                                          contentPane.add(lblNome_1);
                                                                                                                                                                                          
                                                                                                                                                                                          txtNomeMedico = new JTextField();
                                                                                                                                                                                          txtNomeMedico.setBounds(79, 63, 200, 20);
                                                                                                                                                                                          txtNomeMedico.setColumns(10);
                                                                                                                                                                                          contentPane.add(txtNomeMedico);
                                                                                                                                                                                          
                                                                                                                                                                                          lblCrm = new JLabel("CRM");
                                                                                                                                                                                          lblCrm.setFont(new Font("Arial", Font.BOLD, 14));
                                                                                                                                                                                          lblCrm.setBounds(151, 95, 70, 20);
                                                                                                                                                                                          contentPane.add(lblCrm);
                                                                                                                                                                                          
                                                                                                                                                                                          txtCrm = new JTextField();
                                                                                                                                                                                          txtCrm.setBounds(79, 112, 200, 20);
                                                                                                                                                                                          txtCrm.setColumns(10);
                                                                                                                                                                                          contentPane.add(txtCrm);
                                                                                                                                                                                          

                                                                                                                                                                                          JButton btnSalvar_1 = new JButton("Salvar");
                                                                                                                                                                                          
                                                                                                                                                                                          btnSalvar_1.setBounds(585, 164, 167, 46);
                                                                                                                                                                                          btnSalvar_1.setFont(new Font("Arial", Font.BOLD, 14));
                                                                                                                                                                                          btnSalvar_1.setBackground(new Color(173, 216, 230)); // Cor de fundo azul-claro
                                                                                                                                                                                          btnSalvar_1.setForeground(Color.BLACK); // Cor do texto preto
                                                                                                                                                                                          btnSalvar_1.setBorder(new RoundedBorder(10));
                                                                                                                                                                                          btnSalvar_1.addActionListener(new ActionListener() {
                                                                                                                                                                                              public void actionPerformed(ActionEvent e) {
                                                                                                                                                                                                  salvarMedico();
                                                                                                                                                                                                  
                                                                                                                                                                                                  
                                                                                                                                                                                              }
                                                                                                                                                                                          });
                                                                                                                                                                                          contentPane.add(btnSalvar_1);
                                                                                                                                                                                          
                                                                                                                                                                                          
                                                                                                                                                                                          btnEditar = new JButton("Editar");
                                                                                                                                                                                          btnEditar.addActionListener(new ActionListener() {
                                                                                                                                                                                              public void actionPerformed(ActionEvent e) {
                                                                                                                                                                                                  int selectedRow = table_1.getSelectedRow();

                                                                                                                                                                                                  if (selectedRow >= 0) {
                                                                                                                                                                                                      String crmMedico = (String) table_1.getValueAt(selectedRow, 0);
                                                                                                                                                                                                      String novoNome = txtNomeMedico.getText();
                                                                                                                                                                                                      String novaEspecialidade = comboBoxEspecialidade.getSelectedItem().toString();

                                                                                                                                                                                                      int especialidadeId = buscarIdEspecialidade(novaEspecialidade); // Obtém o ID da nova especialidade

                                                                                                                                                                                                      Connection conexao = null;
                                                                                                                                                                                                      PreparedStatement pstmt = null;

                                                                                                                                                                                                      try {
                                                                                                                                                                                                          conexao = Conexao.ConnectDb();
                                                                                                                                                                                                          String updateSQL = "UPDATE medico SET nome_medico = ?, especialidade = ? WHERE crm_medico = ?";
                                                                                                                                                                                                          pstmt = conexao.prepareStatement(updateSQL);
                                                                                                                                                                                                          pstmt.setString(1, novoNome);
                                                                                                                                                                                                          pstmt.setInt(2, especialidadeId);
                                                                                                                                                                                                          pstmt.setString(3, crmMedico);
                                                                                                                                                                                                          pstmt.executeUpdate();

                                                                                                                                                                                                          JOptionPane.showMessageDialog(null, "Médico atualizado com sucesso.");
                                                                                                                                                                                                          preencherTabelaMedicos();

                                                                                                                                                                                                          txtNomeMedico.setText(""); // Limpa os campos
                                                                                                                                                                                                          txtCrm.setText("");
                                                                                                                                                                                                      } catch (SQLException ex) {
                                                                                                                                                                                                          ex.printStackTrace();
                                                                                                                                                                                                          JOptionPane.showMessageDialog(null, "Erro ao atualizar o médico.");
                                                                                                                                                                                                      } finally {
                                                                                                                                                                                                          try {
                                                                                                                                                                                                              if (pstmt != null) {
                                                                                                                                                                                                                  pstmt.close();
                                                                                                                                                                                                              }
                                                                                                                                                                                                              if (conexao != null) {
                                                                                                                                                                                                                  conexao.close();
                                                                                                                                                                                                              }
                                                                                                                                                                                                          } catch (SQLException ex) {
                                                                                                                                                                                                              ex.printStackTrace();
                                                                                                                                                                                                          }
                                                                                                                                                                                                      }
                                                                                                                                                                                                  } else {
                                                                                                                                                                                                      JOptionPane.showMessageDialog(null, "Selecione um médico para editar.");
                                                                                                                                                                                                  }
                                                                                                                                                                                              }
                                                                                                                                                                                          });
                                                                                                                                                                                          		  btnEditar.setFont(new Font("Arial", Font.BOLD, 14));
                                                                                                                                                                                          		  btnEditar.setBackground(new Color(173, 216, 230)); // Cor de fundo azul-claro
                                                                                                                                                                                          		  btnEditar.setForeground(Color.BLACK); // Cor do texto preto
                                                                                                                                                                                          		  btnEditar.setBorder(new RoundedBorder(10));
                                                                                                                                                                                                  btnEditar.setBounds(585, 36, 167, 46);
                                                                                                                                                                                                  contentPane.add(btnEditar);
                                                                                                                                                                                                  
                                                                                                                                                                                                          comboBoxEspecialidade = new JComboBox<String>();
                                                                                                                                                                                                          comboBoxEspecialidade.setBounds(79, 165, 200, 22);
                                                                                                                                                                                                          contentPane.add(comboBoxEspecialidade);
                                                                                                                                                                                                          
                                                                                                                                                                                                          lblEspecialidade_1 = new JLabel("Especialidade");
                                                                                                                                                                                                          lblEspecialidade_1.setFont(new Font("Arial", Font.BOLD, 14));
                                                                                                                                                                                                          lblEspecialidade_1.setBounds(130, 147, 103, 20);
                                                                                                                                                                                                          contentPane.add(lblEspecialidade_1);
                                                                                                                                                                                                          
                                                                                                                                                                                                          JButton btnExcluirMedico_1 = new JButton("Excluir Médico");
                                                                                                                                                                                                          btnExcluirMedico_1.setBounds(585, 95, 167, 52);
                                                                                                                                                                                                          btnExcluirMedico_1.setFont(new Font("Arial", Font.BOLD, 14));
                                                                                                                                                                                                          btnExcluirMedico_1.setBackground(new Color(173, 216, 230)); // Cor de fundo azul-claro
                                                                                                                                                                                                          btnExcluirMedico_1.setForeground(Color.BLACK); // Cor do texto preto
                                                                                                                                                                                                          btnExcluirMedico_1.setBorder(new RoundedBorder(10));
                                                                                                                                                                                                          contentPane.add(btnExcluirMedico_1);
                                                                                                                                                                                                          
                                                                                                                                                                                                          btnExcluirMedico_1.addActionListener(new ActionListener() {
                                                                                                                                                                                                              public void actionPerformed(ActionEvent e) {
                                                                                                                                                                                                                  int selectedRow = table_1.getSelectedRow();

                                                                                                                                                                                                                  if (selectedRow >= 0) {
                                                                                                                                                                                                                      String CRM = (String) table_1.getValueAt(selectedRow, 0);

                                                                                                                                                                                                                      int confirm = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover o médico com o CRM: " + CRM + "?");

                                                                                                                                                                                                                      if (confirm == JOptionPane.YES_OPTION) {
                                                                                                                                                                                                                          // Seu código de exclusão do médico usando o CRM selecionado
                                                                                                                                                                                                                          Connection conexao = null;
                                                                                                                                                                                                                          PreparedStatement pstmt = null;

                                                                                                                                                                                                                          try {
                                                                                                                                                                                                                              conexao = Conexao.ConnectDb();
                                                                                                                                                                                                                              String removerSQL = "DELETE FROM medico WHERE crm_medico = ?";
                                                                                                                                                                                                                              pstmt = conexao.prepareStatement(removerSQL);
                                                                                                                                                                                                                              pstmt.setString(1, CRM);
                                                                                                                                                                                                                              pstmt.executeUpdate();
                                                                                                                                                                                                                              JOptionPane.showMessageDialog(null, "Médico removido com sucesso.");
                                                                                                                                                                                                                              // Chame o método para atualizar a tabela após a exclusão
                                                                                                                                                                                                                              atualizarTabelaMedico((DefaultTableModel) table_1.getModel());
                                                                                                                                                                                                                          } catch (SQLException ex) {
                                                                                                                                                                                                                              ex.printStackTrace();
                                                                                                                                                                                                                              JOptionPane.showMessageDialog(null, ex.getMessage());
                                                                                                                                                                                                                          } finally {
                                                                                                                                                                                                                              try {
                                                                                                                                                                                                                                  if (pstmt != null) {
                                                                                                                                                                                                                                      pstmt.close();
                                                                                                                                                                                                                                  }
                                                                                                                                                                                                                                  if (conexao != null) {
                                                                                                                                                                                                                                      conexao.close();
                                                                                                                                                                                                                                  }
                                                                                                                                                                                                                              } catch (SQLException ex) {
                                                                                                                                                                                                                                  ex.printStackTrace();
                                                                                                                                                                                                                              }
                                                                                                                                                                                                                          }
                                                                                                                                                                                                                      }
                                                                                                                                                                                                                  } else {
                                                                                                                                                                                                                      JOptionPane.showMessageDialog(null, "Selecione um médico para remover.");
                                                                                                                                                                                                                  }
                                                                                                                                                                                                              }
                                                                                                                                                                                                          });
                                                                                                                                                                                                          
                                                                                                                                                                                                                  
                                                                                                                                                                                                          
                                                                                                                                                                                                          
                                                                                                                                                                                                                  scrollPane_1 = new JScrollPane();
                                                                                                                                                                                                                  scrollPane_1.setBounds(10, 276, 748, 363);
                                                                                                                                                                                                                  contentPane.add(scrollPane_1);
                                                                                                                                                                                                                  
     // Defina a tabela
                                                                                                                                                                                                                  table_1 = new JTable();
                                                                                                                                                                                                                  table_1.setModel(new DefaultTableModel(
                                                                                                                                                                                                                      new Object[][] {
                                                                                                                                                                                                                          {null, null, null},
                                                                                                                                                                                                                      },
                                                                                                                                                                                                                      new String[] {
                                                                                                                                                                                                                          "CRM", "NOME", "ESPECIALIDADE"
                                                                                                                                                                                                                      }
                                                                                                                                                                                                                  ));
                                                                                                                                                                                                                  scrollPane_1.setViewportView(table_1);
                                                                                                                                                                                                                  
                                                                                                                                                                                                                          // Adicione dados à tabela e configure outras configurações necessárias
                                                                                                                                                                                                                          atualizarTabelaMedico((DefaultTableModel) table_1.getModel());
                                                                                                                                                                                                                          
                                                                                                                                                                                                                                  // Adicione o ouvinte de seleção de linha após configurar completamente a tabela
                                                                                                                                                                                                                                  table_1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                                                                                                                                                                                                                                      @Override
                                                                                                                                                                                                                                      public void valueChanged(ListSelectionEvent event) {
                                                                                                                                                                                                                                          if (!event.getValueIsAdjusting()) {
                                                                                                                                                                                                                                              int selectedRow = table_1.getSelectedRow();
                                                                                                                                                                                                                          
                                                                                                                                                                                                                                              if (selectedRow >= 0) {
                                                                                                                                                                                                                                                  String nomeMedico = (String) table_1.getValueAt(selectedRow, 1);
                                                                                                                                                                                                                                                  String crmMedico = (String) table_1.getValueAt(selectedRow, 0);
                                                                                                                                                                                                                          
                                                                                                                                                                                                                                                  // Preencher os campos de texto com os valores do médico selecionado
                                                                                                                                                                                                                                                  txtNomeMedico.setText(nomeMedico);
                                                                                                                                                                                                                                                  txtCrm.setText(crmMedico);
                                                                                                                                                                                                                                              }
                                                                                                                                                                                                                                          }
                                                                                                                                                                                                                                      }
                                                                                                                                                                                                                                  });
                                                                                                                                                                                          
                                                                                                                                                                                                  comboBoxCRM.addActionListener(new ActionListener() {
                                                                                                                                                                                                      @Override
                                                                                                                                                                                                      public void actionPerformed(ActionEvent e) {
                                                                                                                                                                                                          String selectedCRM = (String) comboBoxCRM.getSelectedItem();
                                                                                                                                                                                                          if (selectedCRM != null) {
                                                                                                                                                                                                              String[] medicoInfo = obterInfoDoMedicoPeloCRM(selectedCRM);
                                                                                                                                                                                                              if (medicoInfo != null) {
                                                                                                                                                                                                                  txtNome.setText(medicoInfo[0]);
                                                                                                                                                                                                                  txtEspecialidade.setText(medicoInfo[1]);
                                                                                                                                                                                                              } else {
                                                                                                                                                                                                                  txtNome.setText("");
                                                                                                                                                                                                                  txtEspecialidade.setText(" ");
                                                                                                                                                                                                              }
                                                                                                                                                                                                          }
                                                                                                                                                                                                      }
                                                                                                                                                                                                  });
                                                                                                                                                                                                  
                                                                                                                                                                                                          novaAbaPanel.setVisible(true);
        preencherEspecialidades();
        preencherTabelaMedicos();
    }
    private void salvarMedico() {
        String nome = txtNomeMedico.getText();
        String crm = txtCrm.getText();
        String especialidade = (String) comboBoxEspecialidade.getSelectedItem();

        int especialidadeId = buscarIdEspecialidade(especialidade);

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
        	conn = Conexao.ConnectDb();
            String sql = "INSERT INTO medico (crm_medico, especialidade, nome_medico, id_unidade) VALUES (?, ?, ?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, crm);
            stmt.setInt(2, especialidadeId);
            stmt.setString(3, nome);
            stmt.setInt(4, telaLogin.unidade); 

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Médico cadastrado com sucesso!");
                preencherTabelaMedicos();
                preencherCRMMedicos(comboBoxCRM);

            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar o médico.");
            }

            stmt.close();
            conn.close();

            txtNomeMedico.setText("");
            txtCrm.setText("");

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar o médico.");
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void atualizarSenhaGestorNoBanco(String novaSenha) {
        Connection conn = Conexao.ConnectDb(); // Usando sua classe de conexão
        
        try {
            String sql = "UPDATE gestor SET senha = ? WHERE cpf = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, novaSenha);
            stmt.setInt(2, telaLogin.cpfGestor); // Supondo que telaLogin.cpfGestor contenha o CPF do gestor logado

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Senha do gestor atualizada com sucesso!");
            } else {
                System.out.println("Erro ao atualizar a senha do gestor.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private int buscarIdEspecialidade(String nomeEspecialidade) {
        int especialidadeId = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
        	conn = Conexao.ConnectDb();
            String sql = "SELECT id_especialidade_m FROM especialidade_m WHERE descricao_esp = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomeEspecialidade);
            rs = stmt.executeQuery();

            if (rs.next()) {
                especialidadeId = rs.getInt("id_especialidade_m");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return especialidadeId;
    }
    private void preencherMedicos() {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0); // Limpe os dados da tabela

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

        	conn = Conexao.ConnectDb();
            String sql = "SELECT crm_medico, nome_medico, descricao_esp " +
                         "FROM medico " +
                         "INNER JOIN especialidade_m ON medico.especialidade = especialidade_m.id_especialidade_m " +
                         "WHERE medico.id_unidade = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, telaLogin.unidade); // Filtra por unidade

            rs = stmt.executeQuery();

            while (rs.next()) {
                String crm = rs.getString("crm_medico");
                String nome = rs.getString("nome_medico");
                String especialidade = rs.getString("descricao_esp");

                // Adicione a linha à tabela
                tableModel.addRow(new Object[]{crm, nome, especialidade});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    private void preencherEspecialidades() {
        // Lógica para recuperar as especialidades do banco de dados
    	Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
        	conn = Conexao.ConnectDb();

            String sql = "SELECT descricao_esp FROM especialidade_m";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            List<String> especialidades = new ArrayList<>();
            while (rs.next()) {
                especialidades.add(rs.getString("descricao_esp"));
            }

            // Preencher o JComboBox com as especialidades
            for (String especialidade : especialidades) {
                comboBoxEspecialidade.addItem(especialidade);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private JButton createButton(String text, ActionListener action, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(173, 216, 230)); // Cor de fundo azul-claro
        button.setForeground(Color.BLACK); // Cor do texto preto
        button.setBorder(new RoundedBorder(10)); // Use sua classe RoundedBorder ou defina as bordas arredondadas aqui
        return button;
    }

    // Classe RoundedBorder (exemplo básico)
    static class RoundedBorder implements Border {
        private final int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public int getRadius() {
            return radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }
    }
    public void atualizarTabelaMedico(DefaultTableModel model) {
        Connection conexao = Conexao.ConnectDb();

        if (conexao != null) {
            try {
                String consultaSQL = "SELECT m.crm_medico, m.nome_medico, e.descricao_esp " +
                        "FROM medico m " +
                        "JOIN especialidade_m e ON m.especialidade = e.id_especialidade_m " +
                        "WHERE m.id_unidade = ?";

                PreparedStatement pstmt = conexao.prepareStatement(consultaSQL);
                pstmt.setInt(1, telaLogin.unidade);

                ResultSet rs = pstmt.executeQuery();

                model.setRowCount(0);

                while (rs.next()) {
                    String crm = rs.getString("crm_medico");
                    String nome = rs.getString("nome_medico");
                    String especialidade = rs.getString("descricao_esp");

                    // Adicione a linha à tabela
                    model.addRow(new Object[]{crm, nome, especialidade});
                }

                rs.close();
                pstmt.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } finally {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    private void preencherTabelaMedicos() {
        DefaultTableModel tableModel = (DefaultTableModel) table_1.getModel();
        tableModel.setRowCount(0); // Limpe os dados da tabela

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

        	conn = Conexao.ConnectDb();
            String sql = "SELECT crm_medico, nome_medico, descricao_esp " +
                         "FROM medico " +
                         "INNER JOIN especialidade_m ON medico.especialidade = especialidade_m.id_especialidade_m " +
                         "WHERE medico.id_unidade = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, telaLogin.unidade); // Filtra por unidade

            rs = stmt.executeQuery();

            while (rs.next()) {
                String crm = rs.getString("crm_medico");
                String nome = rs.getString("nome_medico");
                String especialidade = rs.getString("descricao_esp");

                // Adicione a linha à tabela
                tableModel.addRow(new Object[]{crm, nome, especialidade});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void exportarViewParaTexto() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.ConnectDb();
            String query = "SELECT * FROM `tcc_java`.`disponibilidade_medico_view`";

            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            // Caminho absoluto para o arquivo de texto
            String caminhoAbsoluto = "C:\\Users\\Magnu\\Desktop\\upa\\csv_file.csv";

            // Verifica se o arquivo já existe
            File arquivoExistente = new File(caminhoAbsoluto);
            if (arquivoExistente.exists()) {
                arquivoExistente.delete(); // Exclui o arquivo existente
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(caminhoAbsoluto))) {
                // Restante do seu código para escrever no arquivo...
                while (rs.next()) {
                    // Modifique essa parte para se adequar à sua view
                	String coluna1 = rs.getString("id_unidade");
                    String coluna2 = rs.getString("crm_medico");
                    String coluna3 = rs.getString("dataHoraInicio");
                    String coluna4 = rs.getString("dataHoraFim");
                    String coluna5 = rs.getString("id_especialidade_m");
                    String coluna6 = rs.getString("descricao_esp");
                    String coluna7 = rs.getString("rua_unidade");
                    String coluna8 = rs.getString("cep_unidade");
                    String coluna9 = rs.getString("numero_unidade");
                    String coluna10 = rs.getString("bairro_unidade");
                    String coluna11 = rs.getString("cidade_unidade");
                    String coluna12 = rs.getString("estado_unidade");
                    String coluna13 = rs.getString("nome_unidade");
                    String coluna14 = rs.getString("url_unidade");
                    

                    // Escreva no arquivo de texto
                    writer.println(coluna1 + ";" + coluna2 + ";" + coluna3 + ";" +
                            coluna4 + ";" + coluna5 + ";" + coluna6 + ";" +
                            coluna7 + ";" + coluna8 + ";" + coluna9 + ";" + coluna10 + ";" + coluna11
                            + ";" + coluna12 + ";" + coluna13 + ";" + coluna14);
                }

                JOptionPane.showMessageDialog(null, "Arquivo 'csv_file.csv' criado com sucesso em:\n" + caminhoAbsoluto);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Feche os recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public List<String> obterListaCRMsDoBancoDeDados() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String> listaCRMs = new ArrayList<>();

        try {
            conn = Conexao.ConnectDb();
            String sql = "SELECT crm_medico FROM medico WHERE id_unidade = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, telaLogin.unidade); // Filtrar pela unidade definida

            rs = stmt.executeQuery();

            while (rs.next()) {
                String crmMedico = rs.getString("crm_medico");
                listaCRMs.add(crmMedico);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        return listaCRMs;
    }

}

