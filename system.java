import java.util.*; 
import java.awt.EventQueue;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.border.BevelBorder;

public class system {

	private JFrame frame;
	private JTabbedPane tabbedPane;
	private JPanel verificationPanel;
	private JPanel OwnerPanel;
	private JPanel profileOwnerPanel;
	private JPanel homeOwnerPanel;
	private JPanel addPlaygroundOwnerPanel;
	private JPanel viewBookingsOwnerPanel;
	private JTextField emailLoginField;
	private JPasswordField passwordLoginField;
	private JTextField nameSignupField;
	private JTextField emailSignupField;
	private JPasswordField passwordSignupField;
	private JComboBox typeSignupCombo;
	private JLabel errorMsgSignup;
	private JLabel errorMsgLogin;
	private JLabel errorMsgVerification;
	private JTextField verificationCodeField;
	private JPanel newSlotsPanel;
	private JTextField namePlaygroundField;
	private JTextArea desciptionPlaygroundField;
	private JTextField sizePlaygroundField;
	private JTextField pricePlaygroundField;
	private JTextField cancellationPlaygroundField;
	private JTextField locationPlaygroundField;
	private JTextField fromField;
	private JTextField toField;
	private JLabel errorMsgNewPlayground;
	private JLabel invalidLabel;
	private JLabel invalidLabel_1;
	private JLabel invalidLabel_2;
	private JPanel playgroundsOwnerPanel;
	private JPanel editPlaygroundPanel;
	private JTextField nameEditField;
	private JTextArea descriptionEditField;
	private JTextField sizeEditField;
	private JTextField priceEditField;
	private JTextField locationEditField;
	private JTextField toEditField;
	private JTextField fromEditField;
	private JTextField cancelEditField;
	private JPanel slotsEditPanel;
	private JLabel errorEditMsg;
	private JLabel invalidLabel_3;
	private JLabel invalidLabel_4;
	private JLabel invalidLabel_5;
	private JButton btnUpdateO;
	private JLabel errorMsgProfile;
	private JButton btnDepositO;
	private JButton btnWithdrawO;
	private JLabel invalidLabel_6;
	private JLabel invalidLabel_7;
	private JPanel playerPanel;
	private JPanel playgroundsPlayerPanel;
	private JPanel homePlayerPanel;
	private JPanel profilePlayerPanel;
	private JPanel bookedSlotsPlayerPanel;
	private JPanel teamPlayerPanel;
	private JPanel infoPanel;
	private JLabel playgroundNameInfoLabel;
	private JLabel emailInfoLabel;
	private JLabel sizeInfoLabel;
	private JLabel priceInfoLabel;
	private JLabel cancelInfoLabel;
	private JTextArea descriptionInfoLabel;
	private JLabel locationInfoLabel;
	private JLabel ownerNameInfoLabel;
	private JLabel numberInfoLabel;
	
	private static ArrayList<player> players = new ArrayList<player>(); // Contains all the players
	private static ArrayList<playgroundOwner> owners = new ArrayList<playgroundOwner>(); // Contains all the playground owners
	private ArrayList<slot> newSlots = new ArrayList<slot>();
	private ArrayList<JLabel> newSlotLabels = new ArrayList<JLabel>();
	private ArrayList<JButton> deleteSlotBtns = new ArrayList<JButton>();
	private ArrayList<JButton> editPlaygroundBtns = new ArrayList<JButton>();
	private static int currentUserID;
	private static String currentUserType = "";
	private int verificationCode, slotNum = 1;
	private JTextField nameProfileFieldO;
	private JTextField emailProfileFieldO;
	private JTextField locationProfileFieldO;
	private JTextField walletProfileFieldO;
	private JTextField numberProfileFieldO;
	private JTextField depositField;
	private JTextField withdrawField;
	private JTextField dateEditField;
	private JTextField locationSearchField;
	private JTextField fromSearchField;
	private JTextField toSearchField;
	private JTextField dateSearchField;
	private JTextField dateField;
	private JTextField nameProfileFieldP;
	private JTextField emailProfileFieldP;
	private JTextField numberProfileFieldP;
	private JTextField locationProfileFieldP;
	private JTextField walletProfileFieldP;
	private JTextField depositField2;
	private JTextField withdrawField2;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					system window = new system();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public system() {
		initialize();
	}
	// Validates the sign up data. If the data is valid it'll create a new user, otherwise it'll display an error message
	public void validateUserData(String name, String email, String password) {
		if (name.equals("")) {
			errorMsgSignup.setText("Enter your username");
		} else if (email.equals("")) {
			errorMsgSignup.setText("Enter your email");
		} else if (!(email.contains("@") && email.contains(".com") && !email.contains(" "))) {
			errorMsgSignup.setText("Enter a valid email");
		} else if (password.equals("")) {
			errorMsgSignup.setText("Enter your password");
		} else {
			errorMsgSignup.setText("");
			String subject = "GoFo Email Verification";
			verificationCode = (int)(Math.random() * (3000 - 1000 + 1) + 1000); // Generate random number between 1000 & 3000
			String content = "<html> <span style='font-size:50px;'> <b> Verification code: "+ verificationCode + "</b> </span> </html>";
			sendMail(email, subject, content);
			
		}
	}
	public void sendMail(String email, String subject, String content) {
        Properties properties = new Properties();    
        properties.put("mail.smtp.host", "smtp.gmail.com");    
        properties.put("mail.smtp.socketFactory.port", "587");       
        properties.put("mail.smtp.auth", "true");    
        properties.put("mail.smtp.port", "587");    
        properties.put("mail.smtp.starttls.enable", "true");
        //get Session   
        Session session = Session.getDefaultInstance(properties,    
         new javax.mail.Authenticator() {    
         protected PasswordAuthentication getPasswordAuthentication() {    
         return new PasswordAuthentication("email.validation65@gmail.com","marwaomar2001");  
         }    
        });    
        //compose message    
        try {    
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress("Go Football <GoFootball@gmail.com>"));
         message.addRecipient(Message.RecipientType.TO,new InternetAddress(email));    
         message.setSubject(subject);    
         message.setText(content, "utf-8", "html");    
         //send message  
         Transport.send(message);    
         System.out.println("message sent successfully");  
         tabbedPane.setSelectedComponent(verificationPanel);
        } catch (MessagingException e) {throw new RuntimeException(e);}    
	}
	// Checks whether the new user is a player or playground owner the creates the suitable object and adds it to the array list
	public void createUser(String name, String email, String password) {
		String accountType = (String) typeSignupCombo.getSelectedItem();
		System.out.print(accountType);
		if (accountType.equals("Player")){
			player newPlayer = new player();
			newPlayer.signUp(name, password, email);
			players.add(newPlayer);
		} else {
			playgroundOwner newOwner = new playgroundOwner();
			newOwner.signUp(name, password, email);
			owners.add(newOwner);
		}
	}
	// Checks if the email and password exist in the owners array list or players array list then takes the user to the appropriate screen
	public void login(String email, String password) {
		System.out.println("Log in email: " + email + "   Log in password: " + password);
		boolean found = false;
		for (int i = 0; i < players.size(); i++) {
			System.out.println("email: " + players.get(i).getEmail() + "   password: " + players.get(i).getPassword());
			if (players.get(i).getEmail().equals(email) && players.get(i).getPassword().equals(password)) {
				currentUserID = i;
				currentUserType = "player";
				found = true;
				errorMsgLogin.setText("");
				tabbedPane.setSelectedComponent(playerPanel);
				displayAllPlaygrounds("", "", "", "");
				homePlayerPanel.setVisible(true);
				profilePlayerPanel.setVisible(false);
				bookedSlotsPlayerPanel.setVisible(false);
				teamPlayerPanel.setVisible(false);
				break;
			}
		}
		if (found == false) {
			for (int j = 0; j < owners.size(); j++) {
				System.out.println("email: " + owners.get(j).getEmail() + "   password: " + owners.get(j).getPassword());
				if (owners.get(j).getEmail().equals(email) && owners.get(j).getPassword().equals(password)) {
					currentUserID = j;
					currentUserType = "playgroundOwner";
					found = true;
					errorMsgLogin.setText("");
					tabbedPane.setSelectedComponent(OwnerPanel);
					displayOwnerPlaygrounds();
					homeOwnerPanel.setVisible(true);
					profileOwnerPanel.setVisible(false);
					addPlaygroundOwnerPanel.setVisible(false);
					viewBookingsOwnerPanel.setVisible(false);
					break;
				}
			}
		}
		if (found == false) {
			System.out.println("Not found");
			errorMsgLogin.setText("Incorrect email or password");
		}
	}
	// Checks if the user left any empty fields then if everything is correct adds the playground to the owner and resets the fields
	void validatePlaygroundInfo(){
		if (namePlaygroundField.getText().equals("")) {
			errorMsgNewPlayground.setText("Enter the playground name");
		} else if (desciptionPlaygroundField.getText().equals("")){
			errorMsgNewPlayground.setText("Enter the playground description");
		} else if (sizePlaygroundField.getText().equals("")) {
			errorMsgNewPlayground.setText("Enter the playground size");
		} else if (pricePlaygroundField.getText().equals("")) {
			errorMsgNewPlayground.setText("Enter the price per hours");
		} else if (cancellationPlaygroundField.getText().equals("")) {
			errorMsgNewPlayground.setText("Enter the cancellation period");
		} else if (locationPlaygroundField.getText().equals("")) {
			errorMsgNewPlayground.setText("Enter the playground location");
		} else if (newSlots.size() < 1){
			errorMsgNewPlayground.setText("You must add at least 1 slot");
		} else if ((invalidLabel.getText().equals("")) && (invalidLabel_1.getText().equals("")) && (invalidLabel_2.getText().equals(""))){
			errorMsgNewPlayground.setText("");
			float size = Float.parseFloat(sizePlaygroundField.getText());
			double price = Double.parseDouble(pricePlaygroundField.getText());
			int cancel = Integer.parseInt(cancellationPlaygroundField.getText());
			playground newPlayground = new playground(namePlaygroundField.getText(), desciptionPlaygroundField.getText(), locationPlaygroundField.getText(), size, cancel, price);
			for (int i = 0; i < newSlots.size(); i++) {
				newPlayground.addSlot(newSlots.get(i));
			}
			owners.get(currentUserID).addPlayground(newPlayground);
			// Reset everything in the Add Playground page
			for (int i = 0; i < deleteSlotBtns.size(); i++) {
				newSlotsPanel.remove(newSlotLabels.get(i));
				newSlotsPanel.remove(deleteSlotBtns.get(i));
			}
			newSlots.clear();
			newSlotLabels.clear();
			deleteSlotBtns.clear();
			// Refreshes the slots panel
			newSlotsPanel.revalidate();
			newSlotsPanel.repaint();
			
			namePlaygroundField.setText("");
			desciptionPlaygroundField.setText("");
			sizePlaygroundField.setText("");
			pricePlaygroundField.setText("");
			cancellationPlaygroundField.setText("");
			locationPlaygroundField.setText("");
			slotNum = 1;
		}
	}
	public void displayOwnerPlaygrounds() {
		editPlaygroundBtns.clear();
		playgroundsOwnerPanel.removeAll();
		playgroundsOwnerPanel.setBackground(new Color(51, 153, 102));
		playgroundsOwnerPanel.setLayout(null);
		
		for (int i = 0; i <  owners.get(currentUserID).playgrounds.size(); i++) {
			JPanel slotsPanel = new JPanel();
			slotsPanel.setBackground(new Color(153, 204, 153));
			slotsPanel.setLayout(null);
			JScrollPane slotScrollPane = new JScrollPane(slotsPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			slotScrollPane.setBounds(74, 241 + (325* i), 329, 61);
			playgroundsOwnerPanel.add(slotScrollPane);
			JLabel namelabel = new JLabel ("Playground #" + (i+1) + " " +  owners.get(currentUserID).playgrounds.get(i).getName());
			namelabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
			namelabel.setBounds(10, 11 + (325*i), 302, 14);
			playgroundsOwnerPanel.add(namelabel);
			int pgdIndex = i;
			JButton btnUpdateEdit = new JButton("Update");
			btnUpdateEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("the update button is run");
					if (nameEditField.getText().equals("")) {
						errorEditMsg.setText("Enter the playground name");
					} else if (descriptionEditField.getText().equals("")) {
						errorEditMsg.setText("Enter the playground description");
					} else if (sizeEditField.getText().equals("")) {
						errorEditMsg.setText("Enter the size of the playground");
					} else if (priceEditField.getText().equals("")) {
						errorEditMsg.setText("Enter the price of the playground");
					} else if (locationEditField.getText().equals("")) {
						errorEditMsg.setText("Enter the location of the playground");
					} else if (cancelEditField.getText().equals("")) {
						errorEditMsg.setText("Enter the cancellation period");
					} else if ((invalidLabel_3.getText().equals("")) && (invalidLabel_4.getText().equals("")) && (invalidLabel_5.getText().equals(""))){
						errorEditMsg.setText("");
						float size = Float.parseFloat(sizeEditField.getText());
						double price = Double.parseDouble(priceEditField.getText());
						int cancel = Integer.parseInt(cancelEditField.getText());
						owners.get(currentUserID).playgrounds.get(pgdIndex).updateInfo(nameEditField.getText(), descriptionEditField.getText(), locationEditField.getText(), size, cancel, price);
						System.out.println("Current playground index: " + pgdIndex);
						
						editPlaygroundPanel.remove(btnUpdateEdit);
						displayOwnerPlaygrounds();
						editPlaygroundPanel.setVisible(false);
						playgroundsOwnerPanel.setVisible(true);
					}
				}
			});
			btnUpdateEdit.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
			btnUpdateEdit.setBounds(184, 555, 96, 26);
			editPlaygroundPanel.add(btnUpdateEdit);
			JButton editBtn = new JButton("Edit");
			editBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					slotsEditPanel.removeAll();
					editPlaygroundPanel.setVisible(true);
					playgroundsOwnerPanel.setVisible(false);
					System.out.println("Number of edit buttons: " + editPlaygroundBtns.size());
					System.out.println("Number of playgrounds: " + owners.get(currentUserID).playgrounds.size());
					String size = ""+owners.get(currentUserID).playgrounds.get(pgdIndex).getSize();
					String price = ""+owners.get(currentUserID).playgrounds.get(pgdIndex).getPrice();
					String cancel = ""+owners.get(currentUserID).playgrounds.get(pgdIndex).getCancellationPeriod();
					nameEditField.setText(owners.get(currentUserID).playgrounds.get(pgdIndex).getName());
					descriptionEditField.setText(owners.get(currentUserID).playgrounds.get(pgdIndex).getDescription());
					sizeEditField.setText(size);
					priceEditField.setText(price);
					locationEditField.setText(owners.get(currentUserID).playgrounds.get(pgdIndex).getLocation());
					cancelEditField.setText(cancel);
					for (int j = 0; j <  owners.get(currentUserID).playgrounds.get(pgdIndex).slots.size(); j++) {
						JLabel slotLabel = new JLabel("  slot #"+(j+1) + "   From: " + owners.get(currentUserID).playgrounds.get(pgdIndex).slots.get(j).getFrom() + "   To: " + owners.get(currentUserID).playgrounds.get(pgdIndex).slots.get(j).getTo() + "   Date: " + owners.get(currentUserID).playgrounds.get(pgdIndex).slots.get(j).getDate());
						slotLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
						slotLabel.setBounds(10, 11+(40 * j), 320, 14); // Gap between the labels
						slotsEditPanel.add(slotLabel);
						JButton deleteBtn = new JButton("Delete");
						deleteBtn.setFont(new Font("Showcard Gothic", Font.PLAIN, 10));
						deleteBtn.setBounds(340, 7+(40 * j), 72, 23);
						int slotIndex = j;
						deleteBtn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								System.out.println("Slot index: " + slotIndex);
								System.out.println("Slot size before: " + owners.get(currentUserID).playgrounds.get(pgdIndex).slots.size());
								owners.get(currentUserID).playgrounds.get(pgdIndex).slots.remove(slotIndex);
								System.out.println("Slot size after: " + owners.get(currentUserID).playgrounds.get(pgdIndex).slots.size());
								slotsEditPanel.removeAll();
								for (int j = 0; j <  owners.get(currentUserID).playgrounds.get(pgdIndex).slots.size(); j++) {
									JLabel slotLabel = new JLabel("  slot #"+(j+1) + "   From: " + owners.get(currentUserID).playgrounds.get(pgdIndex).slots.get(j).getFrom() + "   To: " + owners.get(currentUserID).playgrounds.get(pgdIndex).slots.get(j).getTo() + "   Date: " + owners.get(currentUserID).playgrounds.get(pgdIndex).slots.get(j).getDate());
									slotLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
									slotLabel.setBounds(10, 11+(40 * j), 320, 12); // Gap between the labels
									slotsEditPanel.add(slotLabel);
									JButton deleteBtn = new JButton("Delete");
									deleteBtn.setFont(new Font("Showcard Gothic", Font.PLAIN, 10));
									deleteBtn.setBounds(340, 7+(40 * j), 72, 23);
									slotsEditPanel.add(deleteBtn);
								}
								slotsEditPanel.setPreferredSize(new Dimension(100, 44*owners.get(currentUserID).playgrounds.get(pgdIndex).slots.size())); // Increases the size of the panel
								slotsEditPanel.revalidate();
								slotsEditPanel.repaint();
							}
						});
						slotsEditPanel.add(deleteBtn);
					}
					slotsEditPanel.setPreferredSize(new Dimension(100, 44*owners.get(currentUserID).playgrounds.get(pgdIndex).slots.size())); // Increases the size of the panel
				}
			});
			JButton editAddBtn = new JButton("Add");
			editAddBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.out.println("the edit add button is run");
					if (toEditField.getText().equals("") || fromEditField.getText().equals("") || dateEditField.getText().equals("")){
						errorEditMsg.setText("Enter the From and To and Date fields");
					} else {
						errorEditMsg.setText(""); // rests the error message
						slot newSlot = new slot(fromEditField.getText(), toEditField.getText(), dateEditField.getText());
						owners.get(currentUserID).playgrounds.get(pgdIndex).addSlot(newSlot);
						// Reset the fields
						slotsEditPanel.removeAll();
						// Refreshes the panel
						for (int j = 0; j <  owners.get(currentUserID).playgrounds.get(pgdIndex).slots.size(); j++) {
							JLabel slotLabel = new JLabel("  slot #"+(j+1) + "   From: " + owners.get(currentUserID).playgrounds.get(pgdIndex).slots.get(j).getFrom() + "   To: " + owners.get(currentUserID).playgrounds.get(pgdIndex).slots.get(j).getTo() + "  Date: " + owners.get(currentUserID).playgrounds.get(pgdIndex).slots.get(j).getDate());
							slotLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
							slotLabel.setBounds(10, 11+(40 * j), 320, 14); // Gap between the labels
							slotsEditPanel.add(slotLabel);
							JButton deleteBtn = new JButton("Delete");
							deleteBtn.setFont(new Font("Showcard Gothic", Font.PLAIN, 10));
							deleteBtn.setBounds(340, 7+(40 * j), 72, 23);
							int slotIndex = j;
							deleteBtn.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									System.out.println("Slot index: " + slotIndex);
									System.out.println("Slot size before: " + owners.get(currentUserID).playgrounds.get(pgdIndex).slots.size());
									owners.get(currentUserID).playgrounds.get(pgdIndex).slots.remove(slotIndex);
									System.out.println("Slot size after: " + owners.get(currentUserID).playgrounds.get(pgdIndex).slots.size());
									slotsEditPanel.removeAll();
									for (int j = 0; j <  owners.get(currentUserID).playgrounds.get(pgdIndex).slots.size(); j++) {
										JLabel slotLabel = new JLabel("  slot #"+(j+1) + "   From: " + owners.get(currentUserID).playgrounds.get(pgdIndex).slots.get(j).getFrom() + "   To: " + owners.get(currentUserID).playgrounds.get(pgdIndex).slots.get(j).getTo() + "  Date: " + owners.get(currentUserID).playgrounds.get(pgdIndex).slots.get(j).getDate());
										slotLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
										slotLabel.setBounds(10, 11+(40 * j), 320, 12); // Gap between the labels
										slotsEditPanel.add(slotLabel);
										JButton deleteBtn = new JButton("Delete");
										deleteBtn.setFont(new Font("Showcard Gothic", Font.PLAIN, 10));
										deleteBtn.setBounds(340, 7+(40 * j), 72, 23);
										slotsEditPanel.add(deleteBtn);
									}
									slotsEditPanel.setPreferredSize(new Dimension(100, 44*owners.get(currentUserID).playgrounds.get(pgdIndex).slots.size())); // Increases the size of the panel
									slotsEditPanel.revalidate();
									slotsEditPanel.repaint();
								}
							});
							slotsEditPanel.add(deleteBtn);
						}
						slotsEditPanel.setPreferredSize(new Dimension(100, 44*owners.get(currentUserID).playgrounds.get(pgdIndex).slots.size())); // Increases the size of the panel
						toEditField.setText("");
						fromEditField.setText("");
						dateEditField.setText("");
						slotsEditPanel.revalidate();
						slotsEditPanel.repaint();
					}
				}
			});
			editAddBtn.setFont(new Font("Showcard Gothic", Font.PLAIN, 11));
			editAddBtn.setBounds(202, 426, 81, 23);
			editPlaygroundPanel.add(editAddBtn);
			
			editBtn.setFont(new Font("Showcard Gothic", Font.PLAIN, 11));
			editBtn.setBounds(322, 7 + (325*i), 77, 23);
			editPlaygroundBtns.add(editBtn);
			playgroundsOwnerPanel.add(editBtn);
			
			JTextArea descriptionLabel = new JTextArea();
			descriptionLabel.setLineWrap(true);
			descriptionLabel.setEditable(false);
			descriptionLabel.setBackground(new Color(51, 153, 102));
			descriptionLabel.setText("Description: " + owners.get(currentUserID).playgrounds.get(i).getDescription());
			descriptionLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
			descriptionLabel.setBounds(74, 36 + (325*i), 340, 50);
			playgroundsOwnerPanel.add(descriptionLabel);
			JLabel sizeLabel= new JLabel("Size: " +  owners.get(currentUserID).playgrounds.get(i).getSize());
			sizeLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
			sizeLabel.setBounds(74, 97 + (325*i), 340, 14);
			playgroundsOwnerPanel.add(sizeLabel);
			JLabel priceLabel = new JLabel("Price/Hour: " +  owners.get(currentUserID).playgrounds.get(i).getPrice());
			priceLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
			priceLabel.setBounds(74, 122 + (325*i), 340, 14);
			playgroundsOwnerPanel.add(priceLabel);
			JLabel cancelLabel = new JLabel("Cancellation Period: " +  owners.get(currentUserID).playgrounds.get(i).getCancellationPeriod());
			cancelLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
			cancelLabel.setBounds(74, 147 + (325*i), 340, 14);
			playgroundsOwnerPanel.add(cancelLabel);
			JLabel locationLabel = new JLabel("Location: " +  owners.get(currentUserID).playgrounds.get(i).getLocation());
			locationLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
			locationLabel.setBounds(74, 172 + (325*i), 340, 14);
			playgroundsOwnerPanel.add(locationLabel);
			String status;
			if (owners.get(currentUserID).playgrounds.get(i).getStatus() == true) {
				status = "Active";
			} else {
				status = "Not Active";
			}
			JLabel statusLabel = new JLabel("Status: " + status);
			statusLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
			statusLabel.setBounds(74, 197 + (325*i), 340, 14);
			playgroundsOwnerPanel.add(statusLabel);
			JLabel slotsLabel = new JLabel("Slots:");
			slotsLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
			slotsLabel.setBounds(74, 222 + (325*i), 340, 14);
			playgroundsOwnerPanel.add(slotsLabel);
			
			slotsPanel.removeAll();
			for (int j = 0; j <  owners.get(currentUserID).playgrounds.get(i).slots.size(); j++) {
				JLabel slotLabel = new JLabel("  slot #"+(j+1) + "   From: " + owners.get(currentUserID).playgrounds.get(i).slots.get(j).getFrom() + "   To: " + owners.get(currentUserID).playgrounds.get(i).slots.get(j).getTo() + "   Date: " + owners.get(currentUserID).playgrounds.get(i).slots.get(j).getDate());
				slotLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 14));
				slotLabel.setBounds(10, 17+(40 * j), 291, 14); // Gap between the labels
				slotsPanel.add(slotLabel);
			}
			slotsPanel.setPreferredSize(new Dimension(100, 44* owners.get(currentUserID).playgrounds.get(i).slots.size()));
			slotsPanel.revalidate();
			slotsPanel.repaint();
		}
		playgroundsOwnerPanel.setPreferredSize(new Dimension(150, 350* owners.get(currentUserID).playgrounds.size()));
		playgroundsOwnerPanel.revalidate();
		playgroundsOwnerPanel.repaint();
	}
	// Gets the owner details and puts them in the appropriate fields
	public void ownerProfile() {
		nameProfileFieldO.setText(owners.get(currentUserID).getName());
		emailProfileFieldO.setText(owners.get(currentUserID).getEmail());
		locationProfileFieldO.setText(owners.get(currentUserID).getLocation());
		numberProfileFieldO.setText(owners.get(currentUserID).getPhone());
		String balance = ""+owners.get(currentUserID).checkWallet();
		walletProfileFieldO.setText(balance);
		
		btnDepositO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (invalidLabel_6.getText().equals("")) {
					double amount = Double.parseDouble(depositField.getText());
					owners.get(currentUserID).wallet.deposite(amount);
					depositField.setText("");
					String balance = ""+owners.get(currentUserID).checkWallet();
					walletProfileFieldO.setText(balance);
				}
			}
		});
		btnWithdrawO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (invalidLabel_7.getText().equals("")) {
					double amount = Double.parseDouble(withdrawField.getText());
					owners.get(currentUserID).wallet.withdraw(amount);
					withdrawField.setText("");
					String balance = ""+owners.get(currentUserID).checkWallet();
					walletProfileFieldO.setText(balance);
				}
			}
		});
		btnUpdateO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (nameProfileFieldO.getText().equals("")) {
					errorMsgProfile.setText("Enter your name");
				} else if (emailProfileFieldO.getText().equals("")) {
					errorMsgProfile.setText("Enter your email");
				} else if (locationProfileFieldO.getText().equals("")) {
					errorMsgProfile.setText("Enter your location");
				} else if (numberProfileFieldO.getText().equals("")) {
					errorMsgProfile.setText("Enter your phone number");
				} else {
					errorMsgProfile.setText("");
					owners.get(currentUserID).setName(nameProfileFieldO.getText());
					owners.get(currentUserID).setEmail(emailProfileFieldO.getText());
					owners.get(currentUserID).setPhone(numberProfileFieldO.getText());
					owners.get(currentUserID).setLocation(locationProfileFieldO.getText());
					nameProfileFieldO.setBackground(new Color(51, 153, 102));
					nameProfileFieldO.setEditable(false);
					emailProfileFieldO.setBackground(new Color(51, 153, 102));
					emailProfileFieldO.setEditable(false);
					locationProfileFieldO.setBackground(new Color(51, 153, 102));
					locationProfileFieldO.setEditable(false);
					numberProfileFieldO.setBackground(new Color(51, 153, 102));
					numberProfileFieldO.setEditable(false);
				}
			}
		});
	}
	public void ownerBookedSlots() {
		// Loops through the owner's playgrounds
		int slotGap = 0;
		for (int i = 0; i < owners.get(currentUserID).playgrounds.size(); i++) {
			// Loops through that playground's slots
			for (int j = 0; j < owners.get(currentUserID).playgrounds.get(i).slots.size(); j++) {
				if (owners.get(currentUserID).playgrounds.get(i).slots.get(j).isBooked()) {
					JLabel newSlot = new JLabel("Playground #" + (i+1) + "  Slot #" + (j+1) + " - From: " + owners.get(currentUserID).playgrounds.get(i).slots.get(j).getFrom() + "  To: " + owners.get(currentUserID).playgrounds.get(i).slots.get(j).getTo() + "   Date: " +  owners.get(currentUserID).playgrounds.get(i).slots.get(j).getDate() + "  Player: " + owners.get(currentUserID).playgrounds.get(i).slots.get(j).getPlayer());
					newSlot.setForeground(Color.WHITE);
					newSlot.setFont(new Font("Showcard Gothic", Font.PLAIN, 14));
					newSlot.setBounds(21, 176 +(46*slotGap), 457, 14);
					viewBookingsOwnerPanel.add(newSlot);
					slotGap++;
				}
			}
		}
	}
	public void displayAllPlaygrounds(String location, String from, String to, String date) {
		playgroundsPlayerPanel.removeAll();
		int slotNum = 0, currentSlotNum = 0, gap = 0;
		for (int ownerIndex = 0; ownerIndex < owners.size(); ownerIndex++) {
			for (int playgroundIndex = 0; playgroundIndex < owners.get(ownerIndex).playgrounds.size(); playgroundIndex++) {
				if (owners.get(ownerIndex).playgrounds.get(playgroundIndex).getLocation().contains(location)) {
					currentSlotNum = 0;
					JLabel playerground = new JLabel("Playground #" + (playgroundIndex+1) + " " + owners.get(ownerIndex).playgrounds.get(playgroundIndex).getName());
					playerground.setForeground(Color.WHITE);
					playerground.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
					playerground.setBounds(10, 11 + gap, 342, 22);
					playgroundsPlayerPanel.add(playerground);
					int currentOwner = ownerIndex, currentPlayground = playgroundIndex;
					JButton btnViewInfo = new JButton("View Info");
					btnViewInfo.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							String size = ""+owners.get(currentOwner).playgrounds.get(currentPlayground).getSize();
							String price = ""+owners.get(currentOwner).playgrounds.get(currentPlayground).getPrice();
							String cancel = ""+owners.get(currentOwner).playgrounds.get(currentPlayground).getCancellationPeriod();
							playgroundNameInfoLabel.setText(owners.get(currentOwner).playgrounds.get(currentPlayground).getName());
							sizeInfoLabel.setText(size);
							priceInfoLabel.setText(price);
							cancelInfoLabel.setText(cancel);
							descriptionInfoLabel.setText(owners.get(currentOwner).playgrounds.get(currentPlayground).getDescription());
							locationInfoLabel.setText(owners.get(currentOwner).playgrounds.get(currentPlayground).getLocation());
							ownerNameInfoLabel.setText(owners.get(currentOwner).getName());
							numberInfoLabel.setText(owners.get(currentOwner).getPhone());
							emailInfoLabel.setText(owners.get(currentOwner).getEmail());
							infoPanel.setVisible(true);
							playgroundsPlayerPanel.setVisible(false);
						}
					});
					btnViewInfo.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
					btnViewInfo.setBounds(358, 11 + gap, 107, 23);
					playgroundsPlayerPanel.add(btnViewInfo);
					for (int slotIndex = 0; slotIndex < owners.get(ownerIndex).playgrounds.get(playgroundIndex).slots.size(); slotIndex++) {
						if ((!owners.get(ownerIndex).playgrounds.get(playgroundIndex).slots.get(slotIndex).isBooked()) && (owners.get(ownerIndex).playgrounds.get(playgroundIndex).slots.get(slotIndex).getFrom().contains(from)) && (owners.get(ownerIndex).playgrounds.get(playgroundIndex).slots.get(slotIndex).getTo().contains(to)) && (owners.get(ownerIndex).playgrounds.get(playgroundIndex).slots.get(slotIndex).getDate().contains(date))) {
							JLabel slot = new JLabel("Slot #" + (slotIndex + 1) + "   From: " + owners.get(ownerIndex).playgrounds.get(playgroundIndex).slots.get(slotIndex).getFrom() + "   To: " +  owners.get(ownerIndex).playgrounds.get(playgroundIndex).slots.get(slotIndex).getTo() + "   Date: " +  owners.get(ownerIndex).playgrounds.get(playgroundIndex).slots.get(slotIndex).getDate());
							slot.setForeground(Color.WHITE);
							slot.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
							slot.setBounds(20, 44 + (25*currentSlotNum) + gap, 362, 22);
							playgroundsPlayerPanel.add(slot);
							int currentSlot = slotIndex;
							JButton btnBook = new JButton("Book");
							btnBook.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent arg0) {
									owners.get(currentOwner).playgrounds.get(currentPlayground).slots.get(currentSlot).book();
									players.get(currentUserID).bookSlot(owners.get(currentOwner).playgrounds.get(currentPlayground).slots.get(currentSlot));
									displayAllPlaygrounds("", "", "", "");
								}
							});
							btnBook.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
							btnBook.setBounds(384, 44 + (25*currentSlotNum) + gap, 83, 23);
							playgroundsPlayerPanel.add(btnBook);
							slotNum++;
							currentSlotNum++;
						}
					}
					gap += (45 * currentSlotNum) + 30;
					playgroundsPlayerPanel.setPreferredSize(new Dimension(150, 60 * slotNum));
				}
			}
		}
		playgroundsPlayerPanel.revalidate();
		playgroundsPlayerPanel.repaint();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 737, 708);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 731, 680);
		frame.getContentPane().add(tabbedPane);
		
		JPanel startupPanel= new JPanel();
		startupPanel.setBackground(new Color(51, 153, 102));
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(new Color(0, 102, 51));
		JPanel signupPanel = new JPanel();
		signupPanel.setForeground(new Color(255, 255, 255));
		signupPanel.setBackground(new Color(0, 102, 51));
		verificationPanel = new JPanel();
		verificationPanel.setBackground(new Color(0, 102, 51));
		OwnerPanel = new JPanel();
		OwnerPanel.setBackground(new Color(51, 153, 102));
		playerPanel = new JPanel();
		playerPanel.setBackground(new Color(51, 153, 102));
		tabbedPane.addTab("Start up", null, startupPanel, null);
		startupPanel.setLayout(null);
		
		JButton btnLogIn = new JButton("Log in");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setSelectedComponent(loginPanel); // Go to login tab
			}
		});
		btnLogIn.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
		btnLogIn.setBounds(289, 302, 144, 52);
		startupPanel.add(btnLogIn);
		
		JButton btnLogIn_1 = new JButton("Sign up");
		btnLogIn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setSelectedComponent(signupPanel); // Go to Sign up tab
			}
		});
		btnLogIn_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
		btnLogIn_1.setBounds(291, 409, 144, 52);
		startupPanel.add(btnLogIn_1);
		
		JLabel lblWelcomeToGo = new JLabel("Welcome to Go Football!");
		lblWelcomeToGo.setFont(new Font("Showcard Gothic", Font.BOLD | Font.ITALIC, 35));
		lblWelcomeToGo.setForeground(new Color(255, 255, 255));
		lblWelcomeToGo.setBounds(96, 120, 568, 95);
		startupPanel.add(lblWelcomeToGo);
		tabbedPane.addTab("login", null, loginPanel, null);
		loginPanel.setLayout(null);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String email = emailLoginField.getText();
				String password = passwordLoginField.getText();
				login(email, password);
			}
		});
		btnSubmit.setFont(new Font("Showcard Gothic", Font.PLAIN, 18));
		btnSubmit.setBounds(462, 513, 115, 40);
		loginPanel.add(btnSubmit);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Times New Roman", Font.BOLD, 34));
		lblEmail.setForeground(new Color(255, 255, 255));
		lblEmail.setBounds(127, 274, 107, 46);
		loginPanel.add(lblEmail);
		
		emailLoginField = new JTextField();
		emailLoginField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		emailLoginField.setBounds(306, 286, 282, 30);
		loginPanel.add(emailLoginField);
		emailLoginField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 34));
		lblPassword.setBounds(127, 357, 150, 46);
		loginPanel.add(lblPassword);
		
		passwordLoginField = new JPasswordField();
		passwordLoginField.setBounds(306, 367, 282, 30);
		loginPanel.add(passwordLoginField);
		
		JLabel lblLogIn = new JLabel("Log in");
		lblLogIn.setForeground(new Color(255, 255, 255));
		lblLogIn.setFont(new Font("Showcard Gothic", Font.BOLD, 40));
		lblLogIn.setBounds(278, 105, 150, 88);
		loginPanel.add(lblLogIn);
		
		JButton backToStartupBtn2 = new JButton("Back");
		backToStartupBtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emailLoginField.setText("");
				passwordLoginField.setText("");
				errorMsgLogin.setText("");
				tabbedPane.setSelectedComponent(startupPanel); // Go to the start up tab
			}
		});
		backToStartupBtn2.setFont(new Font("Showcard Gothic", Font.PLAIN, 18));
		backToStartupBtn2.setBounds(151, 513, 115, 40);
		loginPanel.add(backToStartupBtn2);
		
		errorMsgLogin = new JLabel("", SwingConstants.CENTER);
		errorMsgLogin.setForeground(new Color(255, 102, 102));
		errorMsgLogin.setFont(new Font("Times New Roman", Font.BOLD, 22));
		errorMsgLogin.setBounds(210, 443, 308, 30);
		loginPanel.add(errorMsgLogin);
		tabbedPane.addTab("Signup", null, signupPanel, null);
		signupPanel.setLayout(null);
		
		nameSignupField = new JTextField();
		nameSignupField.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		nameSignupField.setBounds(348, 204, 279, 33);
		signupPanel.add(nameSignupField);
		nameSignupField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 33));
		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setBounds(100, 201, 184, 40);
		signupPanel.add(lblUsername);
		
		JLabel lblEmail_1 = new JLabel("Email:");
		lblEmail_1.setFont(new Font("Times New Roman", Font.BOLD, 33));
		lblEmail_1.setForeground(new Color(255, 255, 255));
		lblEmail_1.setBounds(100, 273, 163, 32);
		signupPanel.add(lblEmail_1);
		
		JLabel lblPassword_1 = new JLabel("Password:");
		lblPassword_1.setFont(new Font("Times New Roman", Font.BOLD, 33));
		lblPassword_1.setForeground(new Color(255, 255, 255));
		lblPassword_1.setBounds(100, 329, 184, 40);
		signupPanel.add(lblPassword_1);
		
		emailSignupField = new JTextField();
		emailSignupField.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		emailSignupField.setColumns(10);
		emailSignupField.setBounds(348, 272, 279, 32);
		signupPanel.add(emailSignupField);
		
		passwordSignupField = new JPasswordField();
		passwordSignupField.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		passwordSignupField.setBounds(348, 335, 279, 33);
		signupPanel.add(passwordSignupField);
		
		JLabel lblSignUp = new JLabel("Sign Up");
		lblSignUp.setForeground(new Color(255, 255, 255));
		lblSignUp.setFont(new Font("Showcard Gothic", Font.BOLD, 40));
		lblSignUp.setBounds(259, 77, 184, 84);
		signupPanel.add(lblSignUp);
		
		// Submit sign up Information
		JButton signupSubmitBtn = new JButton("Submit");
		signupSubmitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameSignupField.getText();
				String email = emailSignupField.getText();
				String password = passwordSignupField.getText();
				validateUserData(name, email, password);
			}
		});
		signupSubmitBtn.setFont(new Font("Showcard Gothic", Font.PLAIN, 18));
		signupSubmitBtn.setBounds(476, 535, 115, 40);
		signupPanel.add(signupSubmitBtn);
		
		JLabel typeSignupLabel = new JLabel("Account Type:");
		typeSignupLabel.setForeground(Color.WHITE);
		typeSignupLabel.setFont(new Font("Times New Roman", Font.BOLD, 33));
		typeSignupLabel.setBounds(100, 403, 228, 40);
		signupPanel.add(typeSignupLabel);
		
		typeSignupCombo = new JComboBox();
		typeSignupCombo.setFont(new Font("Times New Roman", Font.BOLD, 25));
		typeSignupCombo.setBounds(348, 411, 279, 32);
		typeSignupCombo.addItem("Player");
		typeSignupCombo.addItem("Playground Owner");
		signupPanel.add(typeSignupCombo);
		
		errorMsgSignup = new JLabel("", SwingConstants.CENTER);
		errorMsgSignup.setForeground(new Color(255, 102, 102));
		errorMsgSignup.setFont(new Font("Times New Roman", Font.BOLD, 22));
		errorMsgSignup.setBounds(206, 480, 308, 30);
		signupPanel.add(errorMsgSignup);
		
		JButton backToStartupBtn1 = new JButton("Back");
		backToStartupBtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameSignupField.setText("");
				emailSignupField.setText("");
				passwordSignupField.setText("");
				errorMsgSignup.setText("");
				tabbedPane.setSelectedComponent(startupPanel); // Go to the start up tab
			}
		});
		backToStartupBtn1.setFont(new Font("Showcard Gothic", Font.PLAIN, 18));
		backToStartupBtn1.setBounds(139, 535, 115, 40);
		signupPanel.add(backToStartupBtn1);
		
		
		tabbedPane.addTab("Verification", null, verificationPanel, null);
		verificationPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter the Verification code sent to your email");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 34));
		lblNewLabel.setBounds(33, 203, 683, 65);
		verificationPanel.add(lblNewLabel);
		
		verificationCodeField = new JTextField();
		verificationCodeField.setHorizontalAlignment(JTextField.CENTER);
		verificationCodeField.setFont(new Font("210 Luckysuper L", Font.BOLD, 25));
		verificationCodeField.setBounds(239, 295, 221, 47);
		verificationPanel.add(verificationCodeField);
		verificationCodeField.setColumns(10);
		
		JButton btnSubmit_1 = new JButton("Submit");
		btnSubmit_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String code = Integer.toString(verificationCode);
				if (code.equals(verificationCodeField.getText())) {
					String name = nameSignupField.getText();
					String email = emailSignupField.getText();
					String password = passwordSignupField.getText();
					createUser(name, email, password);
					tabbedPane.setSelectedComponent(loginPanel); // Go to the login tab
				} else {
					errorMsgVerification.setText("Incorrect code");
				}
			}
		});
		btnSubmit_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
		btnSubmit_1.setBounds(437, 428, 126, 40);
		verificationPanel.add(btnSubmit_1);
		
		JButton btnSubmit_1_1 = new JButton("Back to sign up");
		btnSubmit_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setSelectedComponent(signupPanel); // Go to Sign up tab
			}
		});
		btnSubmit_1_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
		btnSubmit_1_1.setBounds(71, 428, 212, 40);
		verificationPanel.add(btnSubmit_1_1);
		
		errorMsgVerification = new JLabel("", SwingConstants.CENTER);
		errorMsgVerification.setForeground(new Color(255, 102, 102));
		errorMsgVerification.setFont(new Font("Times New Roman", Font.BOLD, 22));
		errorMsgVerification.setBounds(193, 373, 308, 30);
		verificationPanel.add(errorMsgVerification);
		
		tabbedPane.addTab("Playground Owner", null, OwnerPanel, null);
		OwnerPanel.setLayout(null);
		
		JPanel menuOwnerPanel = new JPanel();
		menuOwnerPanel.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		menuOwnerPanel.setBounds(0, 0, 227, 675);
		menuOwnerPanel.setBackground(new Color(0, 102, 51));
		OwnerPanel.add(menuOwnerPanel);
		menuOwnerPanel.setLayout(null);
		
		JLabel lblGo = new JLabel("<html><div style='text-align: center;'>Go Football</div></html>", SwingConstants.CENTER);
		lblGo.setForeground(new Color(255, 255, 255));
		lblGo.setFont(new Font("Showcard Gothic", Font.PLAIN, 35));
		lblGo.setBounds(10, 0, 205, 138);
		menuOwnerPanel.add(lblGo);
		
		JButton btnOHome = new JButton("Home");
		btnOHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayOwnerPlaygrounds();
				homeOwnerPanel.setVisible(true);
				profileOwnerPanel.setVisible(false);
				addPlaygroundOwnerPanel.setVisible(false);
				viewBookingsOwnerPanel.setVisible(false);
			}
		});
		btnOHome.setForeground(new Color(255, 255, 255));
		btnOHome.setBackground(new Color(0, 0, 0));
		btnOHome.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		btnOHome.setBounds(10, 174, 205, 49);
		menuOwnerPanel.add(btnOHome);
		
		JButton btnOProfile = new JButton("Profile");
		btnOProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ownerProfile();
				profileOwnerPanel.setVisible(true);
				homeOwnerPanel.setVisible(false);
				addPlaygroundOwnerPanel.setVisible(false);
				viewBookingsOwnerPanel.setVisible(false);
			}
		});
		btnOProfile.setForeground(new Color(255, 255, 255));
		btnOProfile.setBackground(new Color(0, 0, 0));
		btnOProfile.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		btnOProfile.setBounds(10, 246, 205, 49);
		menuOwnerPanel.add(btnOProfile);
		
		JButton btnOAddPlayground = new JButton("Add Playground");
		btnOAddPlayground.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addPlaygroundOwnerPanel.setVisible(true);
				homeOwnerPanel.setVisible(false);
				profileOwnerPanel.setVisible(false);
				viewBookingsOwnerPanel.setVisible(false);
			}
		});
		btnOAddPlayground.setForeground(new Color(255, 255, 255));
		btnOAddPlayground.setBackground(new Color(0, 0, 0));
		btnOAddPlayground.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		btnOAddPlayground.setBounds(10, 319, 205, 49);
		menuOwnerPanel.add(btnOAddPlayground);
		
		JButton btnOViewBookings = new JButton("View Bookings");
		btnOViewBookings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ownerBookedSlots();
				viewBookingsOwnerPanel.setVisible(true);
				homeOwnerPanel.setVisible(false);
				profileOwnerPanel.setVisible(false);
				addPlaygroundOwnerPanel.setVisible(false);
			}
		});
		btnOViewBookings.setForeground(new Color(255, 255, 255));
		btnOViewBookings.setBackground(new Color(0, 0, 0));
		btnOViewBookings.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		btnOViewBookings.setBounds(10, 397, 205, 49);
		menuOwnerPanel.add(btnOViewBookings);
		
		JButton btnOLogOut = new JButton("Log Out");
		btnOLogOut.setForeground(new Color(255, 255, 255));
		btnOLogOut.setBackground(new Color(0, 0, 0));
		btnOLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Reset login and sign up text fields
				emailLoginField.setText("");
				passwordLoginField.setText("");
				nameSignupField.setText("");
				emailSignupField.setText("");
				passwordSignupField.setText("");
				errorMsgLogin.setText("");
				errorMsgSignup.setText("");
				// Open up home first when logging in next time
				homeOwnerPanel.setVisible(true);
				profileOwnerPanel.setVisible(false);
				addPlaygroundOwnerPanel.setVisible(false);
				viewBookingsOwnerPanel.setVisible(false);
				// Go back to start up page
				tabbedPane.setSelectedComponent(startupPanel);
			}
		});
		btnOLogOut.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		btnOLogOut.setBounds(10, 528, 205, 49);
		menuOwnerPanel.add(btnOLogOut);
		
		homeOwnerPanel = new JPanel();
		homeOwnerPanel.setBounds(227, 0, 499, 652);
		homeOwnerPanel.setBackground(new Color(51, 153, 102));
		OwnerPanel.add(homeOwnerPanel);
		homeOwnerPanel.setLayout(null);
		
		editPlaygroundPanel = new JPanel();
		editPlaygroundPanel.setVisible(false);
		editPlaygroundPanel.setForeground(new Color(0, 0, 0));
		editPlaygroundPanel.setBackground(new Color(153, 204, 153));
		editPlaygroundPanel.setBounds(10, 37, 479, 592);
		homeOwnerPanel.add(editPlaygroundPanel);
		editPlaygroundPanel.setLayout(null);
		
		JLabel lblEditPlayground = new JLabel("Edit Playground");
		lblEditPlayground.setForeground(new Color(0, 0, 0));
		lblEditPlayground.setFont(new Font("Showcard Gothic", Font.BOLD, 25));
		lblEditPlayground.setBounds(118, 11, 266, 81);
		editPlaygroundPanel.add(lblEditPlayground);
		
		JLabel playgroundNameLabel_1 = new JLabel("Name:");
		playgroundNameLabel_1.setForeground(new Color(0, 0, 0));
		playgroundNameLabel_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		playgroundNameLabel_1.setBounds(23, 76, 56, 30);
		editPlaygroundPanel.add(playgroundNameLabel_1);
		
		JLabel lblDescription_1 = new JLabel("Description:");
		lblDescription_1.setForeground(new Color(0, 0, 0));
		lblDescription_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		lblDescription_1.setBounds(23, 126, 119, 30);
		editPlaygroundPanel.add(lblDescription_1);
		
		JLabel lblSize_1 = new JLabel("Size:");
		lblSize_1.setForeground(new Color(0, 0, 0));
		lblSize_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		lblSize_1.setBounds(23, 190, 56, 30);
		editPlaygroundPanel.add(lblSize_1);
		
		JLabel lblPricehour = new JLabel("Price/Hour:");
		lblPricehour.setForeground(new Color(0, 0, 0));
		lblPricehour.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		lblPricehour.setBounds(23, 231, 119, 30);
		editPlaygroundPanel.add(lblPricehour);
		
		JLabel lblCancellationPeriod = new JLabel("Cancellation Period:");
		lblCancellationPeriod.setForeground(new Color(0, 0, 0));
		lblCancellationPeriod.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		lblCancellationPeriod.setBounds(23, 282, 203, 30);
		editPlaygroundPanel.add(lblCancellationPeriod);
		
		JLabel playgroundNameLabel_6 = new JLabel("Location:");
		playgroundNameLabel_6.setForeground(new Color(0, 0, 0));
		playgroundNameLabel_6.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		playgroundNameLabel_6.setBounds(23, 323, 103, 30);
		editPlaygroundPanel.add(playgroundNameLabel_6);
		
		JLabel playgroundNameLabel_7 = new JLabel("Slots:");
		playgroundNameLabel_7.setForeground(new Color(0, 0, 0));
		playgroundNameLabel_7.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		playgroundNameLabel_7.setBounds(23, 360, 56, 30);
		editPlaygroundPanel.add(playgroundNameLabel_7);
		
		nameEditField = new JTextField();
		nameEditField.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		nameEditField.setBounds(143, 82, 272, 20);
		editPlaygroundPanel.add(nameEditField);
		nameEditField.setColumns(10);
		
		sizeEditField = new JTextField();
		sizeEditField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				try {
					int i = Integer.parseInt(sizeEditField.getText()+e.getKeyChar());
					invalidLabel_3.setText("");
				} catch(NumberFormatException e1) {
					invalidLabel_3.setText("Invalid Number");
				}
			}
		});
		sizeEditField.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		sizeEditField.setColumns(10);
		sizeEditField.setBounds(143, 196, 72, 20);
		editPlaygroundPanel.add(sizeEditField);
		
		priceEditField = new JTextField();
		priceEditField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				try {
					int i = Integer.parseInt(priceEditField.getText()+e.getKeyChar());
					invalidLabel_4.setText("");
				} catch(NumberFormatException e1) {
					invalidLabel_4.setText("Invalid Number");
				}
			}
		});
		priceEditField.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		priceEditField.setColumns(10);
		priceEditField.setBounds(143, 237, 103, 20);
		editPlaygroundPanel.add(priceEditField);
		
		locationEditField = new JTextField();
		locationEditField.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		locationEditField.setColumns(10);
		locationEditField.setBounds(143, 329, 272, 20);
		editPlaygroundPanel.add(locationEditField);
		
		toEditField = new JTextField();
		toEditField.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		toEditField.setColumns(10);
		toEditField.setBounds(211, 395, 72, 20);
		editPlaygroundPanel.add(toEditField);
		
		fromEditField = new JTextField();
		fromEditField.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		fromEditField.setColumns(10);
		fromEditField.setBounds(87, 395, 65, 20);
		editPlaygroundPanel.add(fromEditField);
		
		descriptionEditField = new JTextArea();
		descriptionEditField.setLineWrap(true);
		descriptionEditField.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		descriptionEditField.setBounds(143, 130, 272, 50);
		editPlaygroundPanel.add(descriptionEditField);
		
		cancelEditField = new JTextField();
		cancelEditField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				try {
					int i = Integer.parseInt(cancelEditField.getText()+e.getKeyChar());
					invalidLabel_5.setText("");
				} catch(NumberFormatException e1) {
					invalidLabel_5.setText("Invalid Number");
				}
			}
		});
		cancelEditField.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		cancelEditField.setColumns(10);
		cancelEditField.setBounds(211, 287, 86, 20);
		editPlaygroundPanel.add(cancelEditField);
		
		JLabel playgroundNameLabel_7_1 = new JLabel("From:");
		playgroundNameLabel_7_1.setForeground(Color.BLACK);
		playgroundNameLabel_7_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 14));
		playgroundNameLabel_7_1.setBounds(33, 390, 56, 30);
		editPlaygroundPanel.add(playgroundNameLabel_7_1);
		
		JLabel playgroundNameLabel_7_2 = new JLabel("To:");
		playgroundNameLabel_7_2.setForeground(Color.BLACK);
		playgroundNameLabel_7_2.setFont(new Font("Showcard Gothic", Font.PLAIN, 14));
		playgroundNameLabel_7_2.setBounds(174, 390, 35, 30);
		editPlaygroundPanel.add(playgroundNameLabel_7_2);
		
		JLabel playgroundNameLabel_7_1_1 = new JLabel("days");
		playgroundNameLabel_7_1_1.setForeground(Color.BLACK);
		playgroundNameLabel_7_1_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 14));
		playgroundNameLabel_7_1_1.setBounds(307, 282, 56, 30);
		editPlaygroundPanel.add(playgroundNameLabel_7_1_1);
		
		slotsEditPanel= new JPanel();
		slotsEditPanel.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane(slotsEditPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_2.setBounds(23, 450, 431, 63);
		editPlaygroundPanel.add(scrollPane_2);
		
		errorEditMsg = new JLabel("", SwingConstants.CENTER);
		errorEditMsg.setForeground(new Color(255, 102, 102));
		errorEditMsg.setFont(new Font("Showcard Gothic", Font.PLAIN, 18));
		errorEditMsg.setBounds(33, 524, 407, 20);
		editPlaygroundPanel.add(errorEditMsg);
		
		invalidLabel_3 = new JLabel("");
		invalidLabel_3.setForeground(new Color(255, 102, 102));
		invalidLabel_3.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		invalidLabel_3.setBounds(237, 211, 123, 14);
		editPlaygroundPanel.add(invalidLabel_3);
		
		invalidLabel_4 = new JLabel("");
		invalidLabel_4.setForeground(new Color(255, 102, 102));
		invalidLabel_4.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		invalidLabel_4.setBounds(261, 252, 123, 14);
		editPlaygroundPanel.add(invalidLabel_4);
		
		invalidLabel_5 = new JLabel("");
		invalidLabel_5.setForeground(new Color(255, 102, 102));
		invalidLabel_5.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		invalidLabel_5.setBounds(356, 300, 123, 14);
		editPlaygroundPanel.add(invalidLabel_5);
		
		JLabel dateLabel = new JLabel("Date:");
		dateLabel.setForeground(Color.BLACK);
		dateLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 14));
		dateLabel.setBounds(298, 389, 65, 30);
		editPlaygroundPanel.add(dateLabel);
		
		dateEditField = new JTextField();
		dateEditField.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		dateEditField.setColumns(10);
		dateEditField.setBounds(351, 395, 103, 20);
		editPlaygroundPanel.add(dateEditField);
		
		JLabel playgroundNameLabel_7_1_1_1 = new JLabel("pounds");
		playgroundNameLabel_7_1_1_1.setForeground(Color.BLACK);
		playgroundNameLabel_7_1_1_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 14));
		playgroundNameLabel_7_1_1_1.setBounds(256, 231, 81, 30);
		editPlaygroundPanel.add(playgroundNameLabel_7_1_1_1);
		
		JLabel lblHome = new JLabel("Home");
		lblHome.setForeground(Color.WHITE);
		lblHome.setFont(new Font("Showcard Gothic", Font.PLAIN, 32));
		lblHome.setBounds(198, 57, 109, 50);
		homeOwnerPanel.add(lblHome);
		
		JLabel lblYourPlaygrounds = new JLabel("Your Playgrounds:");
		lblYourPlaygrounds.setForeground(Color.WHITE);
		lblYourPlaygrounds.setFont(new Font("Showcard Gothic", Font.PLAIN, 22));
		lblYourPlaygrounds.setBounds(25, 157, 259, 50);
		homeOwnerPanel.add(lblYourPlaygrounds);
		

		playgroundsOwnerPanel = new JPanel();
		playgroundsOwnerPanel.setBackground(new Color(51, 153, 102));
		playgroundsOwnerPanel.setLayout(null);
		JScrollPane scrollPane_1 = new JScrollPane(playgroundsOwnerPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(47, 207, 426, 407);
		homeOwnerPanel.add(scrollPane_1);
		
		profileOwnerPanel = new JPanel();
		profileOwnerPanel.setBounds(227, 0, 499, 652);
		profileOwnerPanel.setBackground(new Color(51, 153, 102));
		OwnerPanel.add(profileOwnerPanel);
		profileOwnerPanel.setLayout(null);
		
		JLabel lblProfile = new JLabel("Profile");
		lblProfile.setForeground(Color.WHITE);
		lblProfile.setFont(new Font("Showcard Gothic", Font.PLAIN, 30));
		lblProfile.setBounds(173, 61, 139, 50);
		profileOwnerPanel.add(lblProfile);
		
		JLabel nameProfileLabel = new JLabel("Name :");
		nameProfileLabel.setForeground(Color.WHITE);
		nameProfileLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 19));
		nameProfileLabel.setBounds(34, 151, 75, 33);
		profileOwnerPanel.add(nameProfileLabel);
		
		JLabel emailProfileLabel = new JLabel("Email:");
		emailProfileLabel.setForeground(Color.WHITE);
		emailProfileLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 19));
		emailProfileLabel.setBounds(34, 203, 75, 33);
		profileOwnerPanel.add(emailProfileLabel);
		
		JLabel numberProfileLabel = new JLabel("Phone Number:");
		numberProfileLabel.setForeground(Color.WHITE);
		numberProfileLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 19));
		numberProfileLabel.setBounds(34, 267, 162, 33);
		profileOwnerPanel.add(numberProfileLabel);
		
		JLabel locationProfileLabel = new JLabel("Location:");
		locationProfileLabel.setForeground(Color.WHITE);
		locationProfileLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 19));
		locationProfileLabel.setBounds(34, 337, 106, 33);
		profileOwnerPanel.add(locationProfileLabel);
		
		JLabel walletProfileLabel = new JLabel("Wallet:");
		walletProfileLabel.setForeground(Color.WHITE);
		walletProfileLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 19));
		walletProfileLabel.setBounds(34, 395, 86, 33);
		profileOwnerPanel.add(walletProfileLabel);
		
		nameProfileFieldO = new JTextField();
		nameProfileFieldO.setFont(new Font("Times New Roman", Font.BOLD, 14));
		nameProfileFieldO.setBounds(119, 158, 215, 20);
		nameProfileFieldO.setBackground(new Color(51, 153, 102));
		nameProfileFieldO.setEditable(false);
		profileOwnerPanel.add(nameProfileFieldO);
		nameProfileFieldO.setColumns(10);
		
		emailProfileFieldO = new JTextField();
		emailProfileFieldO.setFont(new Font("Times New Roman", Font.BOLD, 14));
		emailProfileFieldO.setColumns(10);
		emailProfileFieldO.setBounds(119, 210, 215, 20);
		emailProfileFieldO.setBackground(new Color(51, 153, 102));
		emailProfileFieldO.setEditable(false);
		profileOwnerPanel.add(emailProfileFieldO);
		
		locationProfileFieldO = new JTextField();
		locationProfileFieldO.setFont(new Font("Times New Roman", Font.BOLD, 14));
		locationProfileFieldO.setColumns(10);
		locationProfileFieldO.setBounds(147, 345, 205, 20);
		locationProfileFieldO.setBackground(new Color(51, 153, 102));
		locationProfileFieldO.setEditable(false);
		profileOwnerPanel.add(locationProfileFieldO);
		
		walletProfileFieldO = new JTextField();
		walletProfileFieldO.setFont(new Font("Times New Roman", Font.BOLD, 14));
		walletProfileFieldO.setColumns(10);
		walletProfileFieldO.setBounds(130, 403, 205, 20);
		walletProfileFieldO.setBackground(new Color(51, 153, 102));
		walletProfileFieldO.setEditable(false);
		profileOwnerPanel.add(walletProfileFieldO);
		
		numberProfileFieldO = new JTextField();
		numberProfileFieldO.setFont(new Font("Times New Roman", Font.BOLD, 14));
		numberProfileFieldO.setColumns(10);
		numberProfileFieldO.setBounds(223, 274, 148, 20);
		numberProfileFieldO.setBackground(new Color(51, 153, 102));
		numberProfileFieldO.setEditable(false);
		profileOwnerPanel.add(numberProfileFieldO);
		
		JButton editNameBtnO = new JButton("Edit");
		editNameBtnO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nameProfileFieldO.setEditable(true);
				nameProfileFieldO.setBackground(new Color(255, 255, 255));
			}
		});
		editNameBtnO.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		editNameBtnO.setBounds(392, 157, 70, 23);
		profileOwnerPanel.add(editNameBtnO);
		
		JButton editEmailBtnO = new JButton("Edit");
		editEmailBtnO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emailProfileFieldO.setEditable(true);
				emailProfileFieldO.setBackground(new Color(255, 255, 255));
			}
		});
		editEmailBtnO.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		editEmailBtnO.setBounds(392, 209, 70, 23);
		profileOwnerPanel.add(editEmailBtnO);
		
		JButton editNumberBtnO = new JButton("Edit");
		editNumberBtnO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numberProfileFieldO.setEditable(true);
				numberProfileFieldO.setBackground(new Color(255, 255, 255));
			}
		});
		editNumberBtnO.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		editNumberBtnO.setBounds(392, 273, 70, 23);
		profileOwnerPanel.add(editNumberBtnO);
		
		JButton editLocationBtnO = new JButton("Edit");
		editLocationBtnO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				locationProfileFieldO.setEditable(true);
				locationProfileFieldO.setBackground(new Color(255, 255, 255));
			}
		});
		editLocationBtnO.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		editLocationBtnO.setBounds(392, 343, 70, 23);
		profileOwnerPanel.add(editLocationBtnO);
		
		btnDepositO = new JButton("Deposit");
		btnDepositO.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
		btnDepositO.setBounds(354, 454, 124, 23);
		profileOwnerPanel.add(btnDepositO);
		
		btnWithdrawO = new JButton("Withdraw");
		btnWithdrawO.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
		btnWithdrawO.setBounds(354, 500, 124, 23);
		profileOwnerPanel.add(btnWithdrawO);
		
		btnUpdateO = new JButton("Update");
		btnUpdateO.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		btnUpdateO.setBounds(176, 596, 121, 33);
		profileOwnerPanel.add(btnUpdateO);
		
		errorMsgProfile = new JLabel("", SwingConstants.CENTER);
		errorMsgProfile.setForeground(new Color(255, 102, 102));
		errorMsgProfile.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
		errorMsgProfile.setBounds(48, 543, 407, 30);
		profileOwnerPanel.add(errorMsgProfile);
		
		depositField = new JTextField();
		depositField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				try {
					double i = Double.parseDouble(depositField.getText()+e.getKeyChar());
					invalidLabel_6.setText("");
				} catch(NumberFormatException e1) {
					invalidLabel_6.setText("Invalid Number");
				}
			}
		});
		depositField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		depositField.setBounds(204, 455, 130, 22);
		profileOwnerPanel.add(depositField);
		depositField.setColumns(10);
		
		withdrawField = new JTextField();
		withdrawField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				try {
					double i = Double.parseDouble(withdrawField.getText()+e.getKeyChar());
					invalidLabel_7.setText("");
				} catch(NumberFormatException e1) {
					invalidLabel_7.setText("Invalid Number");
				}
			}
		});
		withdrawField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		withdrawField.setColumns(10);
		withdrawField.setBounds(204, 500, 130, 23);
		profileOwnerPanel.add(withdrawField);
		
		invalidLabel_6 = new JLabel("");
		invalidLabel_6.setForeground(new Color(255, 102, 102));
		invalidLabel_6.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		invalidLabel_6.setBounds(48, 458, 123, 14);
		profileOwnerPanel.add(invalidLabel_6);
		
		invalidLabel_7 = new JLabel("");
		invalidLabel_7.setForeground(new Color(255, 102, 102));
		invalidLabel_7.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		invalidLabel_7.setBounds(48, 505, 123, 14);
		profileOwnerPanel.add(invalidLabel_7);
		
		addPlaygroundOwnerPanel = new JPanel();
		addPlaygroundOwnerPanel.setBounds(227, 0, 499, 652);
		OwnerPanel.add(addPlaygroundOwnerPanel);
		addPlaygroundOwnerPanel.setLayout(null);
		addPlaygroundOwnerPanel.setBackground(new Color(51, 153, 102));
		
		JLabel lblNewPlayground = new JLabel("New Playground");
		lblNewPlayground.setForeground(Color.WHITE);
		lblNewPlayground.setFont(new Font("Showcard Gothic", Font.PLAIN, 30));
		lblNewPlayground.setBounds(121, 59, 288, 50);
		addPlaygroundOwnerPanel.add(lblNewPlayground);
		
		newSlotsPanel = new JPanel();
		newSlotsPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(newSlotsPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		scrollPane.setBounds(45, 503, 431, 63);
		addPlaygroundOwnerPanel.add(scrollPane);
		
		JButton btnAddSlot = new JButton("Add");
		btnAddSlot.setFont(new Font("Showcard Gothic", Font.BOLD, 10));
		btnAddSlot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (toField.getText().equals("") || fromField.getText().equals("") || dateField.getText().equals("")){
					errorMsgNewPlayground.setText("Enter the From and To and Date field");
				} else {
					errorMsgNewPlayground.setText(""); // rests the error message
					slot newSlot = new slot(fromField.getText(), toField.getText(), dateField.getText());
					newSlots.add(newSlot); // creates a new slot
					
					JLabel newlabel = new JLabel("slot #"+slotNum + "   From: " + fromField.getText() + "   To: " + toField.getText() + "  Date: " + dateField.getText());
					slotNum++;
					newlabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
					newlabel.setBounds(10, 11+(40 *(newSlots.size()-1)), 320, 14); // Gap between the labels
					newSlotLabels.add(newlabel); // creates a new label for the slot to display
					
					JButton deleteBtn = new JButton("Delete");
					deleteBtn.setFont(new Font("Showcard Gothic", Font.PLAIN, 10));
					deleteBtn.setBounds(340, 7+(40 *(newSlots.size()-1)), 72, 23);
					deleteBtn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int index = 0;
							// Removes all the labels and buttons and gets the index of the current button
							for (int i = 0; i < deleteSlotBtns.size(); i++) {
								newSlotsPanel.remove(newSlotLabels.get(i));
								newSlotsPanel.remove(deleteSlotBtns.get(i));
								if (deleteSlotBtns.get(i).equals(e.getSource())) {
									index = i;
								}
							}
							newSlots.remove(index);
							newSlotLabels.remove(index);
							deleteSlotBtns.remove(index);
							// Displays all the slot labels and delete button for each slot
							for (int j = 0; j < newSlotLabels.size(); j++) {
								newSlotLabels.get(j).setBounds(10, 11+(40 *j), 310, 14);
								deleteSlotBtns.get(j).setBounds(333, 7+(40 *j), 72, 23);
								newSlotsPanel.add(newSlotLabels.get(j));
								newSlotsPanel.add(deleteSlotBtns.get(j));
							}
							newSlotsPanel.setPreferredSize(new Dimension(100, 44*newSlotLabels.size())); // Increases the size of the panel
							// Refreshes the panel
							newSlotsPanel.revalidate();
							newSlotsPanel.repaint();
						}
					});
					deleteSlotBtns.add(deleteBtn);
					
					// Displays all the slot labels and delete button for each slot
					for (int j = 0; j < newSlotLabels.size(); j++) {
						newSlotsPanel.add(newSlotLabels.get(j));
						newSlotsPanel.add(deleteSlotBtns.get(j));
					}
					newSlotsPanel.setPreferredSize(new Dimension(100, 44*newSlotLabels.size())); // Increases the size of the panel
					// Reset the fields
					toField.setText("");
					fromField.setText("");
					dateField.setText("");
					// Refreshes the panel
					newSlotsPanel.revalidate();
					newSlotsPanel.repaint();
				}
			}
		});
		btnAddSlot.setBounds(219, 469, 70, 23);
		addPlaygroundOwnerPanel.add(btnAddSlot);	
		
		JLabel playgroundNameLabel = new JLabel("Name:");
		playgroundNameLabel.setForeground(new Color(255, 255, 255));
		playgroundNameLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		playgroundNameLabel.setBounds(45, 120, 56, 30);
		addPlaygroundOwnerPanel.add(playgroundNameLabel);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setForeground(Color.WHITE);
		lblDescription.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		lblDescription.setBounds(45, 161, 124, 30);
		addPlaygroundOwnerPanel.add(lblDescription);
		
		JLabel lblSize = new JLabel("Size:");
		lblSize.setForeground(Color.WHITE);
		lblSize.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		lblSize.setBounds(45, 225, 49, 30);
		addPlaygroundOwnerPanel.add(lblSize);
		
		JLabel lblPricehours = new JLabel("Price/Hours:");
		lblPricehours.setForeground(Color.WHITE);
		lblPricehours.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		lblPricehours.setBounds(45, 274, 124, 30);
		addPlaygroundOwnerPanel.add(lblPricehours);
		
		JLabel cancellationPeriodLabel = new JLabel("Cancellation Period:");
		cancellationPeriodLabel.setForeground(Color.WHITE);
		cancellationPeriodLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		cancellationPeriodLabel.setBounds(45, 325, 195, 30);
		addPlaygroundOwnerPanel.add(cancellationPeriodLabel);
		
		JLabel locationLabel = new JLabel("Location:");
		locationLabel.setForeground(Color.WHITE);
		locationLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		locationLabel.setBounds(45, 366, 98, 30);
		addPlaygroundOwnerPanel.add(locationLabel);
		
		JLabel slotsLabel = new JLabel("Slots:");
		slotsLabel.setForeground(Color.WHITE);
		slotsLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		slotsLabel.setBounds(45, 401, 56, 30);
		addPlaygroundOwnerPanel.add(slotsLabel);
		
		namePlaygroundField = new JTextField();
		namePlaygroundField.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		namePlaygroundField.setBounds(180, 126, 164, 20);
		addPlaygroundOwnerPanel.add(namePlaygroundField);
		namePlaygroundField.setColumns(10);
		
		desciptionPlaygroundField = new JTextArea();
		desciptionPlaygroundField.setLineWrap(true);
		desciptionPlaygroundField.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		desciptionPlaygroundField.setBounds(180, 165, 272, 50);
		addPlaygroundOwnerPanel.add(desciptionPlaygroundField);
		
		sizePlaygroundField = new JTextField();
		sizePlaygroundField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				try {
					float i = Float.parseFloat(sizePlaygroundField.getText()+e.getKeyChar());
					invalidLabel.setText("");
				} catch(NumberFormatException e1) {
					invalidLabel.setText("Invalid Number");
				}
			}
		});
		sizePlaygroundField.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		sizePlaygroundField.setBounds(180, 231, 86, 20);
		addPlaygroundOwnerPanel.add(sizePlaygroundField);
		sizePlaygroundField.setColumns(10);
		
		pricePlaygroundField = new JTextField();
		pricePlaygroundField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				try {
					double i = Double.parseDouble(pricePlaygroundField.getText()+e.getKeyChar());
					invalidLabel_1.setText("");
				} catch(NumberFormatException e1) {
					invalidLabel_1.setText("Invalid Number");
				}
			}
		});
		pricePlaygroundField.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		pricePlaygroundField.setColumns(10);
		pricePlaygroundField.setBounds(179, 280, 86, 20);
		addPlaygroundOwnerPanel.add(pricePlaygroundField);
		
		cancellationPlaygroundField = new JTextField();
		cancellationPlaygroundField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				try {
					int i = Integer.parseInt(cancellationPlaygroundField.getText()+e.getKeyChar());
					invalidLabel_2.setText("");
				} catch(NumberFormatException e1) {
					invalidLabel_2.setText("Invalid Number");
				}
			}
		});
		cancellationPlaygroundField.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		cancellationPlaygroundField.setColumns(10);
		cancellationPlaygroundField.setBounds(250, 328, 60, 20);
		addPlaygroundOwnerPanel.add(cancellationPlaygroundField);
		
		locationPlaygroundField = new JTextField();
		locationPlaygroundField.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		locationPlaygroundField.setColumns(10);
		locationPlaygroundField.setBounds(180, 372, 164, 20);
		addPlaygroundOwnerPanel.add(locationPlaygroundField);
		
		fromField = new JTextField();
		fromField.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		fromField.setBounds(114, 438, 60, 20);
		addPlaygroundOwnerPanel.add(fromField);
		fromField.setColumns(10);
		
		toField = new JTextField();
		toField.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		toField.setColumns(10);
		toField.setBounds(229, 438, 60, 20);
		addPlaygroundOwnerPanel.add(toField);
		
		JLabel lblFrom = new JLabel("From:");
		lblFrom.setForeground(new Color(255, 255, 255));
		lblFrom.setFont(new Font("Showcard Gothic", Font.PLAIN, 14));
		lblFrom.setBounds(55, 441, 46, 14);
		addPlaygroundOwnerPanel.add(lblFrom);
		
		JLabel lblTo = new JLabel("To:");
		lblTo.setForeground(new Color(255, 255, 255));
		lblTo.setFont(new Font("Showcard Gothic", Font.PLAIN, 14));
		lblTo.setBounds(195, 440, 24, 14);
		addPlaygroundOwnerPanel.add(lblTo);
		
		JButton btnSubmit_2 = new JButton("Submit");
		btnSubmit_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				validatePlaygroundInfo();
			}
		});
		btnSubmit_2.setFont(new Font("Showcard Gothic", Font.PLAIN, 17));
		btnSubmit_2.setBounds(200, 611, 98, 30);
		addPlaygroundOwnerPanel.add(btnSubmit_2);
		
		errorMsgNewPlayground = new JLabel("", SwingConstants.CENTER);
		errorMsgNewPlayground.setForeground(new Color(255, 102, 102));
		errorMsgNewPlayground.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
		errorMsgNewPlayground.setBounds(45, 577, 407, 23);
		addPlaygroundOwnerPanel.add(errorMsgNewPlayground);
		
		JLabel lblDays = new JLabel("days");
		lblDays.setForeground(Color.WHITE);
		lblDays.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
		lblDays.setBounds(320, 330, 46, 21);
		addPlaygroundOwnerPanel.add(lblDays);
		
		invalidLabel = new JLabel("");
		invalidLabel.setForeground(new Color(255, 102, 102));
		invalidLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		invalidLabel.setBounds(286, 258, 123, 14);
		addPlaygroundOwnerPanel.add(invalidLabel);
		
		invalidLabel_1 = new JLabel("");
		invalidLabel_1.setForeground(new Color(255, 102, 102));
		invalidLabel_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		invalidLabel_1.setBounds(286, 307, 123, 14);
		addPlaygroundOwnerPanel.add(invalidLabel_1);
		
		invalidLabel_2 = new JLabel("");
		invalidLabel_2.setForeground(new Color(255, 102, 102));
		invalidLabel_2.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		invalidLabel_2.setBounds(366, 358, 123, 14);
		addPlaygroundOwnerPanel.add(invalidLabel_2);
		
		dateField = new JTextField();
		dateField.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		dateField.setColumns(10);
		dateField.setBounds(373, 438, 103, 20);
		addPlaygroundOwnerPanel.add(dateField);
		
		JLabel dateLabel_1 = new JLabel("Date:");
		dateLabel_1.setForeground(new Color(255, 255, 255));
		dateLabel_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 14));
		dateLabel_1.setBounds(320, 432, 65, 30);
		addPlaygroundOwnerPanel.add(dateLabel_1);
		
		viewBookingsOwnerPanel = new JPanel();
		viewBookingsOwnerPanel.setBounds(227, 0, 499, 652);
		OwnerPanel.add(viewBookingsOwnerPanel);
		viewBookingsOwnerPanel.setLayout(null);
		viewBookingsOwnerPanel.setBackground(new Color(51, 153, 102));
		
		JLabel lblBookings = new JLabel("Bookings");
		lblBookings.setForeground(Color.WHITE);
		lblBookings.setFont(new Font("Showcard Gothic", Font.PLAIN, 30));
		lblBookings.setBounds(162, 83, 169, 50);
		viewBookingsOwnerPanel.add(lblBookings);
		
		tabbedPane.addTab("Player", null, playerPanel, null);
		playerPanel.setLayout(null);
		
		JPanel menuPlayerPanel = new JPanel();
		menuPlayerPanel.setLayout(null);
		menuPlayerPanel.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		menuPlayerPanel.setBackground(new Color(0, 102, 51));
		menuPlayerPanel.setBounds(0, 0, 227, 675);
		playerPanel.add(menuPlayerPanel);
		
		JLabel lblGo_1 = new JLabel("<html><div style='text-align: center;'>Go Football</div></html>", SwingConstants.CENTER);
		lblGo_1.setForeground(Color.WHITE);
		lblGo_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 35));
		lblGo_1.setBounds(10, 0, 205, 138);
		menuPlayerPanel.add(lblGo_1);
		
		JButton btnPHome = new JButton("Home");
		btnPHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayAllPlaygrounds("", "", "", "");
				homePlayerPanel.setVisible(true);
				profilePlayerPanel.setVisible(false);
				bookedSlotsPlayerPanel.setVisible(false);
				teamPlayerPanel.setVisible(false);
			}
		});
		btnPHome.setForeground(Color.WHITE);
		btnPHome.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		btnPHome.setBackground(Color.BLACK);
		btnPHome.setBounds(10, 174, 205, 49);
		menuPlayerPanel.add(btnPHome);
		
		JButton btnPProfile = new JButton("Profile");
		btnPProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				profilePlayerPanel.setVisible(true);
				homePlayerPanel.setVisible(false);
				bookedSlotsPlayerPanel.setVisible(false);
				teamPlayerPanel.setVisible(false);
			}
		});
		btnPProfile.setForeground(Color.WHITE);
		btnPProfile.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		btnPProfile.setBackground(Color.BLACK);
		btnPProfile.setBounds(10, 246, 205, 49);
		menuPlayerPanel.add(btnPProfile);
		
		JButton btnPBookedSlots = new JButton("Booked Slots");
		btnPBookedSlots.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookedSlotsPlayerPanel.setVisible(true);
				homePlayerPanel.setVisible(false);
				profilePlayerPanel.setVisible(false);
				teamPlayerPanel.setVisible(false);
			}
		});
		btnPBookedSlots.setForeground(Color.WHITE);
		btnPBookedSlots.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		btnPBookedSlots.setBackground(Color.BLACK);
		btnPBookedSlots.setBounds(10, 319, 205, 49);
		menuPlayerPanel.add(btnPBookedSlots);
		
		JButton btnPTeam = new JButton("Team");
		btnPTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teamPlayerPanel.setVisible(true);
				homePlayerPanel.setVisible(false);
				profilePlayerPanel.setVisible(false);
				bookedSlotsPlayerPanel.setVisible(false);
			}
		});
		btnPTeam.setForeground(Color.WHITE);
		btnPTeam.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		btnPTeam.setBackground(Color.BLACK);
		btnPTeam.setBounds(10, 397, 205, 49);
		menuPlayerPanel.add(btnPTeam);
		
		JButton btnPLogOut = new JButton("Log Out");
		btnPLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Reset login and sign up text fields
				emailLoginField.setText("");
				passwordLoginField.setText("");
				nameSignupField.setText("");
				emailSignupField.setText("");
				passwordSignupField.setText("");
				errorMsgLogin.setText("");
				errorMsgSignup.setText("");
				// Go back to start up page
				tabbedPane.setSelectedComponent(startupPanel);
			}
		});
		btnPLogOut.setForeground(Color.WHITE);
		btnPLogOut.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		btnPLogOut.setBackground(Color.BLACK);
		btnPLogOut.setBounds(10, 528, 205, 49);
		menuPlayerPanel.add(btnPLogOut);
		
		homePlayerPanel = new JPanel();
		homePlayerPanel.setForeground(Color.BLACK);
		homePlayerPanel.setLayout(null);
		homePlayerPanel.setBackground(new Color(51, 153, 102));
		homePlayerPanel.setBounds(227, 0, 499, 652);
		playerPanel.add(homePlayerPanel);
		
		infoPanel = new JPanel();
		infoPanel.setVisible(false);
		infoPanel.setBackground(new Color(153, 204, 153));
		infoPanel.setBounds(10, 37, 479, 604);
		homePlayerPanel.add(infoPanel);
		infoPanel.setLayout(null);
		
		JLabel lblPlaygroundInfo = new JLabel("Playground Info");
		lblPlaygroundInfo.setForeground(Color.BLACK);
		lblPlaygroundInfo.setFont(new Font("Showcard Gothic", Font.BOLD, 25));
		lblPlaygroundInfo.setBounds(108, 11, 252, 81);
		infoPanel.add(lblPlaygroundInfo);
		
		JLabel nameInfo = new JLabel("Name:");
		nameInfo.setForeground(Color.BLACK);
		nameInfo.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		nameInfo.setBounds(23, 107, 56, 30);
		infoPanel.add(nameInfo);
		
		JLabel descriptionInfo = new JLabel("Description:");
		descriptionInfo.setForeground(Color.BLACK);
		descriptionInfo.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		descriptionInfo.setBounds(23, 148, 123, 30);
		infoPanel.add(descriptionInfo);
		
		JLabel sizeInfo = new JLabel("Size:");
		sizeInfo.setForeground(Color.BLACK);
		sizeInfo.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		sizeInfo.setBounds(23, 246, 56, 30);
		infoPanel.add(sizeInfo);
		
		JLabel priceInfo = new JLabel("Price/Hour:");
		priceInfo.setForeground(Color.BLACK);
		priceInfo.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		priceInfo.setBounds(23, 287, 115, 30);
		infoPanel.add(priceInfo);
		
		JLabel cancelInfo = new JLabel("Cancellation Period:");
		cancelInfo.setForeground(Color.BLACK);
		cancelInfo.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		cancelInfo.setBounds(23, 328, 192, 30);
		infoPanel.add(cancelInfo);
		
		JLabel locationInfo = new JLabel("Location:");
		locationInfo.setForeground(Color.BLACK);
		locationInfo.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		locationInfo.setBounds(23, 369, 96, 30);
		infoPanel.add(locationInfo);
		
		JLabel ownerInfo = new JLabel("Owner:");
		ownerInfo.setForeground(Color.BLACK);
		ownerInfo.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		ownerInfo.setBounds(23, 410, 83, 30);
		infoPanel.add(ownerInfo);
		
		JLabel name2Info = new JLabel("Name:");
		name2Info.setForeground(Color.BLACK);
		name2Info.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		name2Info.setBounds(70, 440, 56, 30);
		infoPanel.add(name2Info);
		
		JLabel emailInfo = new JLabel("Email:");
		emailInfo.setForeground(Color.BLACK);
		emailInfo.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		emailInfo.setBounds(70, 467, 68, 30);
		infoPanel.add(emailInfo);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infoPanel.setVisible(false);
				playgroundsPlayerPanel.setVisible(true);
			}
		});
		btnClose.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
		btnClose.setBounds(185, 563, 89, 30);
		infoPanel.add(btnClose);
		
		JLabel numberInfo = new JLabel("Phone Number:");
		numberInfo.setForeground(Color.BLACK);
		numberInfo.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		numberInfo.setBounds(70, 497, 145, 30);
		infoPanel.add(numberInfo);
		
		playgroundNameInfoLabel = new JLabel("Wakanda");
		playgroundNameInfoLabel.setForeground(new Color(0, 0, 0));
		playgroundNameInfoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		playgroundNameInfoLabel.setBounds(85, 109, 384, 24);
		infoPanel.add(playgroundNameInfoLabel);
		
		sizeInfoLabel = new JLabel("Wakanda");
		sizeInfoLabel.setForeground(Color.BLACK);
		sizeInfoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		sizeInfoLabel.setBounds(70, 247, 115, 24);
		infoPanel.add(sizeInfoLabel);
		
		priceInfoLabel = new JLabel("Wakanda");
		priceInfoLabel.setForeground(Color.BLACK);
		priceInfoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		priceInfoLabel.setBounds(141, 288, 83, 24);
		infoPanel.add(priceInfoLabel);
		
		cancelInfoLabel = new JLabel("Wakanda");
		cancelInfoLabel.setForeground(Color.BLACK);
		cancelInfoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		cancelInfoLabel.setBounds(216, 329, 83, 24);
		infoPanel.add(cancelInfoLabel);
		
		locationInfoLabel = new JLabel("Wakanda");
		locationInfoLabel.setForeground(Color.BLACK);
		locationInfoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		locationInfoLabel.setBounds(117, 371, 352, 23);
		infoPanel.add(locationInfoLabel);
		
		ownerNameInfoLabel = new JLabel("Wakandag");
		ownerNameInfoLabel.setForeground(Color.BLACK);
		ownerNameInfoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		ownerNameInfoLabel.setBounds(136, 442, 334, 23);
		infoPanel.add(ownerNameInfoLabel);
		
		emailInfoLabel = new JLabel("Wakandag");
		emailInfoLabel.setForeground(Color.BLACK);
		emailInfoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		emailInfoLabel.setBounds(135, 468, 334, 24);
		infoPanel.add(emailInfoLabel);
		
		numberInfoLabel = new JLabel("Wakanda");
		numberInfoLabel.setForeground(Color.BLACK);
		numberInfoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		numberInfoLabel.setBounds(211, 498, 258, 24);
		infoPanel.add(numberInfoLabel);
		
		descriptionInfoLabel = new JTextArea("Wakanda");
		descriptionInfoLabel.setForeground(Color.BLACK);
		descriptionInfoLabel.setBackground(new Color(153, 204, 153));
		descriptionInfoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		descriptionInfoLabel.setBounds(141, 152, 328, 89);
		descriptionInfoLabel.setLineWrap(true);
		infoPanel.add(descriptionInfoLabel);
		
		JLabel lblPounds = new JLabel("pounds");
		lblPounds.setForeground(Color.BLACK);
		lblPounds.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
		lblPounds.setBounds(225, 290, 76, 24);
		infoPanel.add(lblPounds);
		
		JLabel lblDays_1 = new JLabel("days");
		lblDays_1.setForeground(Color.BLACK);
		lblDays_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
		lblDays_1.setBounds(296, 331, 56, 24);
		infoPanel.add(lblDays_1);
		
		JLabel lblHomeP = new JLabel("Home");
		lblHomeP.setForeground(Color.WHITE);
		lblHomeP.setFont(new Font("Showcard Gothic", Font.PLAIN, 30));
		lblHomeP.setBounds(196, 64, 97, 50);
		homePlayerPanel.add(lblHomeP);
		
		JLabel locationPLabel = new JLabel("Location:");
		locationPLabel.setForeground(Color.BLACK);
		locationPLabel.setFont(new Font("Showcard Gothic", Font.BOLD, 14));
		locationPLabel.setBounds(10, 150, 102, 26);
		homePlayerPanel.add(locationPLabel);
		
		JLabel fromPLabel = new JLabel("From:");
		fromPLabel.setForeground(Color.BLACK);
		fromPLabel.setFont(new Font("Showcard Gothic", Font.BOLD, 14));
		fromPLabel.setBounds(45, 187, 65, 26);
		homePlayerPanel.add(fromPLabel);
		
		locationSearchField = new JTextField();
		locationSearchField.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		locationSearchField.setBounds(97, 153, 147, 20);
		homePlayerPanel.add(locationSearchField);
		locationSearchField.setColumns(10);
		
		fromSearchField = new JTextField();
		fromSearchField.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		fromSearchField.setColumns(10);
		fromSearchField.setBounds(109, 190, 86, 20);
		homePlayerPanel.add(fromSearchField);
		
		JLabel toPLabel = new JLabel("To:");
		toPLabel.setForeground(Color.BLACK);
		toPLabel.setFont(new Font("Showcard Gothic", Font.BOLD, 14));
		toPLabel.setBounds(223, 187, 44, 26);
		homePlayerPanel.add(toPLabel);
		
		toSearchField = new JTextField();
		toSearchField.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		toSearchField.setColumns(10);
		toSearchField.setBounds(264, 190, 86, 20);
		homePlayerPanel.add(toSearchField);
		
		JLabel datePLabel = new JLabel("Date:");
		datePLabel.setForeground(Color.BLACK);
		datePLabel.setFont(new Font("Showcard Gothic", Font.BOLD, 14));
		datePLabel.setBounds(271, 150, 58, 26);
		homePlayerPanel.add(datePLabel);
		
		dateSearchField = new JTextField();
		dateSearchField.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		dateSearchField.setColumns(10);
		dateSearchField.setBounds(329, 150, 160, 20);
		homePlayerPanel.add(dateSearchField);
		
		JButton searchPlaygroundBtn = new JButton("Search");
		searchPlaygroundBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String location = locationSearchField.getText();
				String date = dateSearchField.getText();
				String from = fromSearchField.getText();
				String to = toSearchField.getText();
				displayAllPlaygrounds(location, from, to, date);
			}
		});
		searchPlaygroundBtn.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		searchPlaygroundBtn.setBounds(388, 188, 89, 25);
		homePlayerPanel.add(searchPlaygroundBtn);
		
		playgroundsPlayerPanel = new JPanel();
		playgroundsPlayerPanel.setBackground(new Color(51, 153, 102));
		
		JScrollPane scrollPane_3 = new JScrollPane(playgroundsPlayerPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		playgroundsPlayerPanel.setLayout(null);
		scrollPane_3.setBounds(10, 241, 479, 400);
		homePlayerPanel.add(scrollPane_3);
		
		profilePlayerPanel = new JPanel();
		profilePlayerPanel.setBounds(227, 0, 499, 652);
		playerPanel.add(profilePlayerPanel);
		profilePlayerPanel.setLayout(null);
		profilePlayerPanel.setForeground(Color.BLACK);
		profilePlayerPanel.setBackground(new Color(51, 153, 102));
		
		JLabel lblProfile_1 = new JLabel("Profile");
		lblProfile_1.setForeground(Color.WHITE);
		lblProfile_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 30));
		lblProfile_1.setBounds(171, 80, 137, 50);
		profilePlayerPanel.add(lblProfile_1);
		
		JLabel nameProfileLabel_1 = new JLabel("Name :");
		nameProfileLabel_1.setForeground(Color.WHITE);
		nameProfileLabel_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 19));
		nameProfileLabel_1.setBounds(29, 163, 75, 33);
		profilePlayerPanel.add(nameProfileLabel_1);
		
		nameProfileFieldP = new JTextField();
		nameProfileFieldP.setFont(new Font("Times New Roman", Font.BOLD, 14));
		nameProfileFieldP.setEditable(false);
		nameProfileFieldP.setColumns(10);
		nameProfileFieldP.setBackground(new Color(51, 153, 102));
		nameProfileFieldP.setBounds(114, 170, 215, 20);
		profilePlayerPanel.add(nameProfileFieldP);
		
		JButton editNameBtnO_1 = new JButton("Edit");
		editNameBtnO_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		editNameBtnO_1.setBounds(387, 169, 70, 23);
		profilePlayerPanel.add(editNameBtnO_1);
		
		JButton editEmailBtnO_1 = new JButton("Edit");
		editEmailBtnO_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		editEmailBtnO_1.setBounds(387, 221, 70, 23);
		profilePlayerPanel.add(editEmailBtnO_1);
		
		emailProfileFieldP = new JTextField();
		emailProfileFieldP.setFont(new Font("Times New Roman", Font.BOLD, 14));
		emailProfileFieldP.setEditable(false);
		emailProfileFieldP.setColumns(10);
		emailProfileFieldP.setBackground(new Color(51, 153, 102));
		emailProfileFieldP.setBounds(114, 222, 215, 20);
		profilePlayerPanel.add(emailProfileFieldP);
		
		JLabel emailProfileLabel_1 = new JLabel("Email:");
		emailProfileLabel_1.setForeground(Color.WHITE);
		emailProfileLabel_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 19));
		emailProfileLabel_1.setBounds(29, 215, 75, 33);
		profilePlayerPanel.add(emailProfileLabel_1);
		
		JLabel numberProfileLabel_1 = new JLabel("Phone Number:");
		numberProfileLabel_1.setForeground(Color.WHITE);
		numberProfileLabel_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 19));
		numberProfileLabel_1.setBounds(29, 279, 162, 33);
		profilePlayerPanel.add(numberProfileLabel_1);
		
		numberProfileFieldP = new JTextField();
		numberProfileFieldP.setFont(new Font("Times New Roman", Font.BOLD, 14));
		numberProfileFieldP.setEditable(false);
		numberProfileFieldP.setColumns(10);
		numberProfileFieldP.setBackground(new Color(51, 153, 102));
		numberProfileFieldP.setBounds(218, 286, 148, 20);
		profilePlayerPanel.add(numberProfileFieldP);
		
		JButton editNumberBtnO_1 = new JButton("Edit");
		editNumberBtnO_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		editNumberBtnO_1.setBounds(387, 285, 70, 23);
		profilePlayerPanel.add(editNumberBtnO_1);
		
		JButton editLocationBtnO_1 = new JButton("Edit");
		editLocationBtnO_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		editLocationBtnO_1.setBounds(387, 355, 70, 23);
		profilePlayerPanel.add(editLocationBtnO_1);
		
		locationProfileFieldP = new JTextField();
		locationProfileFieldP.setFont(new Font("Times New Roman", Font.BOLD, 14));
		locationProfileFieldP.setEditable(false);
		locationProfileFieldP.setColumns(10);
		locationProfileFieldP.setBackground(new Color(51, 153, 102));
		locationProfileFieldP.setBounds(142, 357, 205, 20);
		profilePlayerPanel.add(locationProfileFieldP);
		
		JLabel locationProfileLabel_1 = new JLabel("Location:");
		locationProfileLabel_1.setForeground(Color.WHITE);
		locationProfileLabel_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 19));
		locationProfileLabel_1.setBounds(29, 349, 106, 33);
		profilePlayerPanel.add(locationProfileLabel_1);
		
		JLabel walletProfileLabel_1 = new JLabel("Wallet:");
		walletProfileLabel_1.setForeground(Color.WHITE);
		walletProfileLabel_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 19));
		walletProfileLabel_1.setBounds(29, 407, 86, 33);
		profilePlayerPanel.add(walletProfileLabel_1);
		
		walletProfileFieldP = new JTextField();
		walletProfileFieldP.setFont(new Font("Times New Roman", Font.BOLD, 14));
		walletProfileFieldP.setEditable(false);
		walletProfileFieldP.setColumns(10);
		walletProfileFieldP.setBackground(new Color(51, 153, 102));
		walletProfileFieldP.setBounds(125, 415, 205, 20);
		profilePlayerPanel.add(walletProfileFieldP);
		
		depositField2 = new JTextField();
		depositField2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		depositField2.setColumns(10);
		depositField2.setBounds(199, 467, 130, 22);
		profilePlayerPanel.add(depositField2);
		
		JButton btnDepositP = new JButton("Deposit");
		btnDepositP.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
		btnDepositP.setBounds(349, 466, 124, 23);
		profilePlayerPanel.add(btnDepositP);
		
		JButton btnWithdrawP = new JButton("Withdraw");
		btnWithdrawP.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
		btnWithdrawP.setBounds(349, 512, 124, 23);
		profilePlayerPanel.add(btnWithdrawP);
		
		withdrawField2 = new JTextField();
		withdrawField2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		withdrawField2.setColumns(10);
		withdrawField2.setBounds(199, 512, 130, 23);
		profilePlayerPanel.add(withdrawField2);
		
		JButton btnUpdateP = new JButton("Update");
		btnUpdateP.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		btnUpdateP.setBounds(171, 608, 121, 33);
		profilePlayerPanel.add(btnUpdateP);
		
		bookedSlotsPlayerPanel = new JPanel();
		bookedSlotsPlayerPanel.setBounds(227, 0, 499, 652);
		playerPanel.add(bookedSlotsPlayerPanel);
		bookedSlotsPlayerPanel.setLayout(null);
		bookedSlotsPlayerPanel.setForeground(Color.BLACK);
		bookedSlotsPlayerPanel.setBackground(new Color(51, 153, 102));
		
		JLabel lblBookedSlots = new JLabel("Booked Slots");
		lblBookedSlots.setForeground(Color.WHITE);
		lblBookedSlots.setFont(new Font("Showcard Gothic", Font.PLAIN, 30));
		lblBookedSlots.setBounds(136, 58, 232, 50);
		bookedSlotsPlayerPanel.add(lblBookedSlots);
		
		teamPlayerPanel = new JPanel();
		teamPlayerPanel.setLayout(null);
		teamPlayerPanel.setForeground(Color.BLACK);
		teamPlayerPanel.setBackground(new Color(51, 153, 102));
		teamPlayerPanel.setBounds(227, 0, 499, 652);
		playerPanel.add(teamPlayerPanel);
		
		JLabel lbTeam = new JLabel("Team");
		lbTeam.setForeground(Color.WHITE);
		lbTeam.setFont(new Font("Showcard Gothic", Font.PLAIN, 34));
		lbTeam.setBounds(180, 73, 108, 50);
		teamPlayerPanel.add(lbTeam);
		
		JButton btnAddTeammate = new JButton("Add Teammate");
		btnAddTeammate.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
		btnAddTeammate.setBounds(145, 591, 200, 34);
		teamPlayerPanel.add(btnAddTeammate);;
	}
}
