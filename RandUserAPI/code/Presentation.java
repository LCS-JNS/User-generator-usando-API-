package RandUserAPI.code;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.*;

public class Presentation {
	private Main main = new Main();
	private JFrame frm;
	private Image image;
	private DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Presentation window = new Presentation();
					window.frm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Presentation() {
		initialize();
	}

	private void initialize() {

		frm = new JFrame();
		frm.setResizable(false);
		frm.setTitle("Informações dos usuários");
		frm.setBounds(100, 100, 453, 550);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.getContentPane().setLayout(null);

		JComboBox<String> cbUsers = new JComboBox<>();
		cbUsers.setBounds(10, 36, 216, 31);
		frm.getContentPane().add(cbUsers);

		JTextArea taInfosArea = new JTextArea();
		taInfosArea.setBounds(10, 305, 197, 180);
		frm.getContentPane().add(taInfosArea);
		taInfosArea.setLineWrap(true);

		JTextArea taAdmInfos = new JTextArea();
		taAdmInfos.setBounds(215, 266, 215, 130);
		frm.getContentPane().add(taAdmInfos);
		taAdmInfos.setLineWrap(true);
		taAdmInfos.setVisible(false);

		JLabel lblFoto = new JLabel("");
		lblFoto.setBounds(10, 80, 200, 200);
		frm.getContentPane().add(lblFoto);

		main.generateJSON();
		main.saveJson();
		main.getUsersFromJSON();
		cbUsers.setModel(model);
		for (int i = 0; i < main.getUsersListSize(); i++) {
			Result user = main.getUser(i);
			String name = main.getUserName(user);
			model.addElement(name);
		}

		int index = cbUsers.getSelectedIndex();
		Result user = main.getUser(index);
		image = main.getImage(user);
		Image newImg = image.getScaledInstance(lblFoto.getWidth(),
				lblFoto.getHeight(), java.awt.Image.SCALE_SMOOTH);
		ImageIcon imgIcon = new ImageIcon(newImg);
		lblFoto.setIcon(imgIcon);
		taInfosArea.setText(user.toString());
		taAdmInfos.setText(main.getInfoAdministrativa(user));

		cbUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model.getSize() > 0) {
					main.getUsersFromJSON();
					Result user = main.getUserByName(cbUsers.getSelectedItem().toString());
					taInfosArea.setText(user.toString());
					taAdmInfos.setText(main.getInfoAdministrativa(user));

					image = main.getImage(user);
					Image newImg = image.getScaledInstance(lblFoto.getWidth(),
							lblFoto.getHeight(), java.awt.Image.SCALE_SMOOTH);
					ImageIcon imgIcon = new ImageIcon(newImg);
					lblFoto.setIcon(imgIcon);
				}
			}
		});

		JButton btnCriar = new JButton("Adicionar novo usuário");
		btnCriar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.generateJSON();
				main.saveJson();
				main.getUsersFromJSON();
				int verify = 0;
				for (int i = 0; i < main.getUsersListSize(); i++) {
					Result user = main.getUser(i);
					String name = main.getUserName(user);
					if (model.getSize() == 0) {
						model.addElement(name);
					}
					for (int j = 0; j < model.getSize(); j++) {
						if (!model.getElementAt(j).equals(name)) {
							verify++;
						}
						if (verify == model.getSize()) {
							model.addElement(name);
						}
					}
					verify = 0;
				}
				cbUsers.setSelectedIndex(main.getUsersListSize() - 1);
				lblFoto.setVisible(true);
			}
		});
		btnCriar.setBounds(236, 36, 190, 31);
		frm.getContentPane().add(btnCriar);

		JButton btnApagar = new JButton("Apagar usuários já criados");
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.deleteUsers();
				model.removeAllElements();
				lblFoto.setVisible(false);
				taInfosArea.setText("Nenhum usuário criado ainda");
				taAdmInfos.setText("Nenhum usuário criado ainda");
			}
		});
		btnApagar.setBounds(236, 78, 190, 31);
		frm.getContentPane().add(btnApagar);

		JLabel lblNewLabel = new JLabel("Mostrar informações do usuário:");
		lblNewLabel.setBounds(10, 11, 216, 14);
		frm.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Acesso administrativo:");
		lblNewLabel_1.setBounds(236, 152, 135, 14);
		frm.getContentPane().add(lblNewLabel_1);

		JTextField tfPassword = new JTextField();
		tfPassword.setBounds(236, 188, 191, 20);
		frm.getContentPane().add(tfPassword);
		tfPassword.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Acesso negado");
		lblNewLabel_3.setBounds(236, 241, 190, 14);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblNewLabel_3.setForeground(new Color(255, 0, 0));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		frm.getContentPane().add(lblNewLabel_3);
		lblNewLabel_3.setVisible(false);

		JButton btnAcesso = new JButton("Acessar");
		btnAcesso.setBounds(282, 219, 89, 23);
		btnAcesso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfPassword.getText().equals("admpassword")) {
					taAdmInfos.setVisible(true);
					lblNewLabel_3.setVisible(false);
				} else {
					lblNewLabel_3.setVisible(true);
				}
			}
		});
		frm.getContentPane().add(btnAcesso);

	}
}