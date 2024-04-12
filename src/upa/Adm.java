package upa;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.EventQueue;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.swing.*;
import java.awt.*;



public class Adm extends JFrame {
	private static final String API_KEY = "AIzaSyCdTiSfkG084nUUSRKxDq9H4uOxxwtburA";
    private JTextField textFieldRua;
    private JTextField textFieldCEP;
    private JTextField textFieldNumero;
    private JTextField textFieldBairro;
    private JTextField textFieldCidade;
    private JTextField textFieldEstado;
    private JTextField textFieldNome;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtFieldCpf;
    private JTextField txtFieldTelefone;
    private JTextField txtFieldNome;
    private JTextField txtFieldEmail;
    private JTextField txtFieldSenha;
    private JComboBox<String> comboBoxIdUnidade;
    private JCheckBox checkBoxAdmin;
    private DefaultTableModel tableModelGestores;
    private JTable table_1;
    

    public Adm() {
    	


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        tabbedPane.addTab("Cadastro Unidade", null, contentPane, null);

        JLabel lblRua = new JLabel("Rua:");
        lblRua.setBounds(30, 30, 70, 20);
        contentPane.add(lblRua);

        textFieldRua = new JTextField();
        textFieldRua.setColumns(10);
        textFieldRua.setBounds(110, 30, 200, 20);
        contentPane.add(textFieldRua);

        JLabel lblCEP = new JLabel("CEP:");
        lblCEP.setBounds(30, 60, 70, 20);
        contentPane.add(lblCEP);

        textFieldCEP = new JTextField();
        textFieldCEP.setColumns(10);
        textFieldCEP.setBounds(110, 60, 200, 20);
        contentPane.add(textFieldCEP);

        JLabel lblNumero = new JLabel("Número:");
        lblNumero.setBounds(30, 90, 70, 20);
        contentPane.add(lblNumero);

        textFieldNumero = new JTextField();
        textFieldNumero.setColumns(10);
        textFieldNumero.setBounds(110, 90, 200, 20);
        contentPane.add(textFieldNumero);

        JLabel lblBairro = new JLabel("Bairro:");
        lblBairro.setBounds(30, 120, 70, 20);
        contentPane.add(lblBairro);

        textFieldBairro = new JTextField();
        textFieldBairro.setColumns(10);
        textFieldBairro.setBounds(110, 120, 200, 20);
        contentPane.add(textFieldBairro);

        JLabel lblCidade = new JLabel("Cidade:");
        lblCidade.setBounds(30, 150, 70, 20);
        contentPane.add(lblCidade);

        textFieldCidade = new JTextField();
        textFieldCidade.setColumns(10);
        textFieldCidade.setBounds(110, 150, 200, 20);
        contentPane.add(textFieldCidade);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(30, 180, 70, 20);
        contentPane.add(lblEstado);

        textFieldEstado = new JTextField();
        textFieldEstado.setColumns(10);
        textFieldEstado.setBounds(110, 180, 200, 20);
        contentPane.add(textFieldEstado);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(30, 210, 70, 20);
        contentPane.add(lblNome);

        textFieldNome = new JTextField();
        textFieldNome.setColumns(10);
        textFieldNome.setBounds(110, 210, 200, 20);
        contentPane.add(textFieldNome);

        JButton btnSalvar = createButton("Salvar Unidade", e -> salvarUnidade(), 701, 30, 253, 54);
        contentPane.add(btnSalvar);

        JButton btnVoltar = createButton("Excluir Unidade", e -> excluirUnidadeSelecionada(), 701, 103, 253, 55);
        contentPane.add(btnVoltar);



        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 299, 924, 427);
        contentPane.add(scrollPane);

        table = new JTable();
        table.setModel(new DefaultTableModel(
            new Object[][] {
                {null, "", null, null, null, null, null, null},
            },
            new String[] {
                "ID_unidade", "Rua", "Numero", "Bairro", "Cidade", "Estado", "Nome", "CEP"
            }
        ));

        // Adicione um MouseListener à tabela para preencher os campos ao clicar
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();

                // Verifica se o clique ocorreu em uma linha válida
                if (selectedRow >= 0) {
                    // Preencha os campos com os dados da linha clicada
                    textFieldRua.setText(table.getValueAt(selectedRow, 1).toString());
                    textFieldCEP.setText(table.getValueAt(selectedRow, 7).toString());
                    textFieldNumero.setText(table.getValueAt(selectedRow, 2).toString());
                    textFieldBairro.setText(table.getValueAt(selectedRow, 3).toString());
                    textFieldCidade.setText(table.getValueAt(selectedRow, 4).toString());
                    textFieldEstado.setText(table.getValueAt(selectedRow, 5).toString());
                    textFieldNome.setText(table.getValueAt(selectedRow, 6).toString());
                }
            }
        });
        
        
        scrollPane.setViewportView(table);

        JLabel lblUrl = new JLabel("URL:");
        lblUrl.setBounds(30, 241, 280, 20);
        contentPane.add(lblUrl);

        tableModel = (DefaultTableModel) table.getModel();
        preencherTabelaComUnidades();

        getContentPane().add(tabbedPane);
        
        JPanel contentPane_1 = new JPanel();
        contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
        tabbedPane.addTab("Cadastro Gestor", null, contentPane_1, null);
        contentPane_1.setLayout(null);

        JLabel lblCPF = new JLabel("CPF:");
        lblCPF.setBounds(40, 30, 70, 20);
        contentPane_1.add(lblCPF);

        txtFieldCpf = new JTextField();
        txtFieldCpf.setBounds(136, 31, 200, 20);
        txtFieldCpf.setColumns(10);
        contentPane_1.add(txtFieldCpf);

        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setBounds(40, 60, 70, 20);
        contentPane_1.add(lblTelefone);

        txtFieldTelefone = new JTextField();
        txtFieldTelefone.setBounds(136, 61, 200, 20);
        txtFieldTelefone.setColumns(10);
        contentPane_1.add(txtFieldTelefone);

        JLabel lblNome_1 = new JLabel("Nome:");
        lblNome_1.setBounds(40, 89, 70, 20);
        contentPane_1.add(lblNome_1);

        txtFieldNome = new JTextField();
        txtFieldNome.setBounds(136, 91, 200, 20);
        txtFieldNome.setColumns(10);
        contentPane_1.add(txtFieldNome);

        JLabel lblUnidadeID = new JLabel("ID da Unidade:");
        lblUnidadeID.setBounds(40, 183, 100, 20);
        contentPane_1.add(lblUnidadeID);


        comboBoxIdUnidade = new JComboBox<String>();
        comboBoxIdUnidade.setBounds(136, 182, 200, 22);
        contentPane_1.add(comboBoxIdUnidade);


        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(40, 296, 907, 419);
        contentPane_1.add(scrollPane_1);
        
        table_1 = new JTable();
        table_1.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, null, null, null, null, null},
        	},
        	new String[] {
        			"CPF", "NOME", "TELEFONE", "ID UNIDADE", "EMAIL", "Administrador"
        	}
        ));
        
     // Adicione um ListSelectionListener à tabela table_1
        table_1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = table_1.getSelectedRow();

                    if (selectedRow >= 0) {
                        // Obter os valores da linha selecionada na tabela
                    	String cpf = table_1.getValueAt(selectedRow, 0).toString();
                    	String nome = table_1.getValueAt(selectedRow, 1).toString();
                    	String telefone = table_1.getValueAt(selectedRow, 2).toString();
                    	String idUnidade = table_1.getValueAt(selectedRow, 3).toString();
                    	String email = table_1.getValueAt(selectedRow, 4).toString();
                    	String admin = table_1.getValueAt(selectedRow, 5).toString();

                    	// Preencher os campos com os valores obtidos
                    	txtFieldCpf.setText(cpf);
                    	txtFieldNome.setText(nome);
                    	txtFieldTelefone.setText(telefone);
                    	txtFieldEmail.setText(email);
                    	comboBoxIdUnidade.setSelectedItem(idUnidade);

                    	// Marcar ou desmarcar o checkBoxAdmin com base no valor "Sim" ou "Não"
                    	if (admin.equals("Sim")) {
                    	    checkBoxAdmin.setSelected(true);
                    	} else {
                    	    checkBoxAdmin.setSelected(false);
                    	}

                        // E assim por diante com os outros campos
                    }
                }
            }
        });

        
        preencherTabelaComGestores();
        scrollPane_1.setViewportView(table_1);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(40, 120, 70, 20);
        contentPane_1.add(lblEmail);

        txtFieldEmail = new JTextField();
        txtFieldEmail.setBounds(136, 121, 200, 20);
        txtFieldEmail.setColumns(10);
        contentPane_1.add(txtFieldEmail);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(40, 150, 70, 20);
        contentPane_1.add(lblSenha);

        txtFieldSenha = new JTextField();
        txtFieldSenha.setBounds(136, 151, 200, 20);
        txtFieldSenha.setColumns(10);
        contentPane_1.add(txtFieldSenha);

        checkBoxAdmin = new JCheckBox("Administrador");
        checkBoxAdmin.setBounds(40, 220, 106, 23);
        contentPane_1.add(checkBoxAdmin);

        JButton btnSalvarGestor = createButton1("Salvar Gestor", e -> salvarGestor(), 701, 30, 253, 54);
        contentPane_1.add(btnSalvarGestor);
        
        JButton btnExcluir = createButton("Excluir Gestor", e -> excluirGestorSelecionado(), 701, 103, 253, 55);
        contentPane_1.add(btnExcluir);

        contentPane_1.add(btnExcluir);




        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(43, 296, 681, 225);
        contentPane_1.add(scrollPane_2);
        

        	
        	preencherComboBoxComUnidades();

        	getContentPane().add(tabbedPane);
        	  // Inicializa a conexão com o banco de dados
            conectarAoBanco();



            // Fecha a conexão com o banco de dados ao fechar a janela
            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    desconectarDoBanco();
                }
            });

    }
    
    private JButton createButton(String text, ActionListener action, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(173, 216, 230));
        button.setForeground(Color.BLACK);
        button.setBorder(new RoundedBorder(10));
        return button;
    }
    private JButton createButton1(String text, ActionListener action, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(173, 216, 230));
        button.setForeground(Color.BLACK);
        button.setBorder(new RoundedBorder(10));
        return button;
    }

    class RoundedBorder implements Border {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }

        public boolean isBorderOpaque() {
            return true;
        }
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Adm frameAdm = new Adm();
                    frameAdm.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void salvarUnidade() {
        String rua = textFieldRua.getText();
        String cep = textFieldCEP.getText();
        String numero = textFieldNumero.getText();
        String bairro = textFieldBairro.getText();
        String cidade = textFieldCidade.getText();
        String estado = textFieldEstado.getText();
        String nome = textFieldNome.getText();

        try (Connection conn = Conexao.ConnectDb();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO unidade (rua_unidade, cep_unidade, numero_unidade, bairro_unidade, cidade_unidade, estado_unidade, nome_unidade) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, rua);
            stmt.setString(2, cep);
            stmt.setString(3, numero);
            stmt.setString(4, bairro);
            stmt.setString(5, cidade);
            stmt.setString(6, estado);
            stmt.setString(7, nome);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                int idUnidade = -1;
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idUnidade = generatedKeys.getInt(1);
                    }
                }

                JOptionPane.showMessageDialog(null, "Cadastro concluído com sucesso!");
                preencherComboBoxComUnidades();
                limparCampos();
                preencherTabelaComUnidades();
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar unidade!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    

    private void excluirUnidade(int idUnidadeParaExcluir) {
        try (Connection conn = Conexao.ConnectDb()) {
            String sql = "DELETE FROM unidade WHERE ID_unidade = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idUnidadeParaExcluir);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Unidade excluída com sucesso!");
                atualizarTabelaUnidade();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao excluir a unidade: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirUnidadeSelecionada() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
        	int idUnidadeParaExcluir = Integer.parseInt((String) table.getValueAt(selectedRow, 0)); // Supondo que a coluna 0 contenha o ID da unidade
            excluirUnidade(idUnidadeParaExcluir); // Chama o método de exclusão com o ID da unidade
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecione uma unidade para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }



    private void preencherTabelaComUnidades() {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.ConnectDb();
            String sql = "SELECT id_unidade, rua_unidade, numero_unidade, bairro_unidade, cidade_unidade, estado_unidade, nome_unidade, cep_unidade FROM unidade";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            tableModel.setRowCount(0);

            while (rs.next()) {
            	String id_unidade = rs.getString("id_unidade");
                String rua = rs.getString("rua_unidade");
                String numero = rs.getString("numero_unidade");
                String bairro = rs.getString("bairro_unidade");
                String cidade = rs.getString("cidade_unidade");
                String estado = rs.getString("estado_unidade");
                String nome = rs.getString("nome_unidade");
                String cep = rs.getString("cep_unidade");


                tableModel.addRow(new Object[]{id_unidade, rua, numero, bairro, cidade, estado, nome, cep});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
    
    private void limparCampos() {
        textFieldRua.setText("");
        textFieldCEP.setText("");
        textFieldNumero.setText("");
        textFieldBairro.setText("");
        textFieldCidade.setText("");
        textFieldEstado.setText("");
        textFieldNome.setText("");
    }
    private void editarGestor() {
        int selectedRow = table_1.getSelectedRow();

        if (selectedRow >= 0) {
            // Recupere os valores da linha selecionada
            int cpf = Integer.parseInt(table_1.getValueAt(selectedRow, 0).toString());
            String nome = table_1.getValueAt(selectedRow, 1).toString();
            String telefone = table_1.getValueAt(selectedRow, 2).toString();
            int idUnidade = Integer.parseInt(table_1.getValueAt(selectedRow, 3).toString());
            String email = table_1.getValueAt(selectedRow, 4).toString();
            String admin = table_1.getValueAt(selectedRow, 5).toString();

            // Preencha os campos com os valores obtidos
            txtFieldCpf.setText(String.valueOf(cpf));
            txtFieldNome.setText(nome);
            txtFieldTelefone.setText(telefone);
            txtFieldEmail.setText(email);
            comboBoxIdUnidade.setSelectedItem(idUnidade);
            checkBoxAdmin.setSelected(admin.equals("Sim"));

            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                conn = Conexao.ConnectDb();
                conn.setAutoCommit(false); // Desativa o autocommit

                String sql = "UPDATE gestor SET nome=?, telefone=?, id_unidade=?, email=?, adm=? WHERE cpf=?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, nome);
                stmt.setString(2, telefone);
                stmt.setInt(3, idUnidade);
                stmt.setString(4, email);
                stmt.setInt(5, (admin.equals("Sim")) ? 1 : 0);
                stmt.setInt(6, cpf);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    conn.commit(); // Realiza o commit após o update
                    JOptionPane.showMessageDialog(null, "Gestor atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    atualizarTabelaGestores();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar o gestor.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                if (conn != null) {
                    try {
                        conn.rollback(); // Se ocorrer algum erro, faça rollback
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (conn != null) {
                        conn.setAutoCommit(true); // Restaura o autocommit para true
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void atualizarTabelaGestores() {
        DefaultTableModel model = (DefaultTableModel) table_1.getModel();
        model.setRowCount(0); // Limpa a tabela

        // Realize a busca no banco de dados e atualize a tabela com os dados recuperados
        // Por exemplo:
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.ConnectDb();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM gestor";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String cpf = rs.getString("cpf");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String idUnidade = rs.getString("id_unidade");
                String email = rs.getString("email");
                String admin = rs.getInt("adm") == 1 ? "Sim" : "Não";

                model.addRow(new Object[]{cpf, nome, telefone, idUnidade, email, admin});
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

    private void excluirGestorSelecionado() {
        int selectedRow = table_1.getSelectedRow();

        if (selectedRow >= 0) {
            String cpfToDelete = table_1.getValueAt(selectedRow, 0).toString(); // Supondo que o CPF está na coluna 0

            // Implemente aqui a lógica para excluir o gestor com o CPF informado do banco de dados
            // Realize a exclusão no banco usando o CPF como identificador
            // Por exemplo:
            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                conn = Conexao.ConnectDb();
                String sql = "DELETE FROM gestor WHERE cpf = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, cpfToDelete);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    // Remova a linha da tabela
                    DefaultTableModel model = (DefaultTableModel) table_1.getModel();
                    model.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(null, "Gestor excluído com sucesso!");
                    atualizarTabelaGestores();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir o gestor.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
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
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecione um gestor para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void salvarGestor() {
        // Outros campos
        String cpf = txtFieldCpf.getText();
        String telefone = txtFieldTelefone.getText();
        String nome = txtFieldNome.getText();
        String idUnidade = (String) comboBoxIdUnidade.getSelectedItem();
        String email = txtFieldEmail.getText();
        String senhaGestor = txtFieldSenha.getText();
        
        // Use a variável de instância checkBoxAdmin
        int adm = checkBoxAdmin.isSelected() ? 1 : 0; // 1 para administrador, 0 para gestor

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.ConnectDb();
            String sql = "INSERT INTO gestor (cpf, nome, telefone, id_unidade, email, senha, adm) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            stmt.setString(2, nome);
            stmt.setString(3, telefone);
            stmt.setString(4, idUnidade);
            stmt.setString(5, email);
            stmt.setString(6, senhaGestor);
            stmt.setInt(7, adm);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
            	 JOptionPane.showMessageDialog(null, "Cadastro concluido com sucesso!");
            	 atualizarTabelaGestores();

                tableModel.addRow(new Object[]{cpf, nome, telefone, idUnidade, email, (adm == 1) ? "Sim" : "Não"});
                txtFieldCpf.setText("");
                txtFieldTelefone.setText("");
                txtFieldNome.setText("");
                txtFieldEmail.setText("");
                txtFieldSenha.setText("");
                checkBoxAdmin.setSelected(false); // Use a variável de instância para desmarcar o JCheckBox
            } else {
                System.out.println("Erro ao cadastrar o gestor de unidade.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    

    private void preencherComboBoxComUnidades() {
    	Connection conn = null;
        PreparedStatement stmt = null;

        try {
        	conn = Conexao.ConnectDb();
            String sql = "SELECT id_unidade FROM unidade";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Limpar o ComboBox
            comboBoxIdUnidade.removeAllItems();

            // Preencher o ComboBox com os IDs das unidades
            while (rs.next()) {
                String idUnidade = rs.getString("id_unidade");
                comboBoxIdUnidade.addItem(idUnidade);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
    
    private void atualizarDadosNoBanco(int idUniidade) {
        try (Connection conn = Conexao.ConnectDb()) {
            String sql = "UPDATE unidade SET rua_unidade=?, numero_unidade=?, bairro_unidade=?, cidade_unidade=?, estado_unidade=?, nome_unidade=?, cep_unidade=? WHERE ID_unidade=?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, textFieldRua.getText());
                stmt.setString(2, textFieldNumero.getText());
                stmt.setString(3, textFieldBairro.getText());
                stmt.setString(4, textFieldCidade.getText());
                stmt.setString(5, textFieldEstado.getText());
                stmt.setString(6, textFieldNome.getText());
                stmt.setString(7, textFieldCEP.getText());
                stmt.setInt(8, idUniidade);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
                    atualizarTabelaUnidade();
                } else {
                    JOptionPane.showMessageDialog(null, "Falha ao atualizar os dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar os dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void atualizarTabelaUnidade() {
        try (Connection conn = Conexao.ConnectDb()) {
            String sql = "SELECT ID_unidade, rua_unidade, numero_unidade, bairro_unidade, cidade_unidade, estado_unidade, nome_unidade, cep_unidade FROM unidade";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Cria um modelo de tabela vazio
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID_unidade", "Rua", "Numero", "Bairro", "Cidade", "Estado", "Nome", "CEP"}, 0);

            // Preenche o modelo da tabela com os dados do banco de dados
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("ID_unidade"),
                    rs.getString("rua_unidade"),
                    rs.getString("numero_unidade"),
                    rs.getString("bairro_unidade"),
                    rs.getString("cidade_unidade"),
                    rs.getString("estado_unidade"),
                    rs.getString("nome_unidade"),
                    rs.getString("cep_unidade")
                };
                model.addRow(row);
            }

            // Define o modelo atualizado na tabela
            table.setModel(model);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar a tabela: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void preencherTabelaComGestores() {
    	DefaultTableModel tableModel = (DefaultTableModel) table_1.getModel();

    	Connection conn = null;
        PreparedStatement stmt = null;

        try {
        	conn = Conexao.ConnectDb();
            // Consulta SQL para buscar os gestores existentes
            String sql = "SELECT cpf, nome, telefone, id_unidade, email, adm FROM gestor";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Limpar a tabela
            tableModel.setRowCount(0);

            // Preencher a tabela com os gestores existentes
            while (rs.next()) {
                String cpf = rs.getString("cpf");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                String idUnidade = rs.getString("id_unidade");
                String email = rs.getString("email");
                int adm = rs.getInt("adm");

                // Converter o valor "adm" em uma representação de texto
                String isAdm = (adm == 1) ? "Sim" : "Não";

                tableModel.addRow(new Object[]{cpf, nome, telefone, idUnidade, email, isAdm});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
    private void conectarAoBanco() {
        Connection conn = Conexao.ConnectDb();
    }

    private void desconectarDoBanco() {
    	 Connection conn = Conexao.ConnectDb();
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


