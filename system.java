import java.util.*; 
import java.awt.EventQueue;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.border.BevelBorder;


/**
 * This is the GoFo system that handles the GUI and most of the functions
 */
public class system {

	private JFrame frame;
	private JTabbedPane tabbedPane;
	private JPanel verificationPanel, OwnerPanel, profileOwnerPanel, homeOwnerPanel, addPlaygroundOwnerPanel, viewBookingsOwnerPanel;
	private JTextField emailLoginField, nameSignupField, emailSignupField;;
	private JPasswordField passwordLoginField, passwordSignupField;
	private JComboBox typeSignupCombo;
	private JLabel errorMsgSignup, errorMsgLogin, errorMsgVerification;
	private JTextField verificationCodeField;
	private JPanel newSlotsPanel, playgroundsOwnerPanel;;
	private JTextField namePlaygroundField;
	private JTextArea desciptionPlaygroundField;
	private JTextField sizePlaygroundField, pricePlaygroundField, cancellationPlaygroundField, locationPlaygroundField, fromField, toField;
	private JLabel errorMsgNewPlayground, invalidLabel, invalidLabel_1, invalidLabel_2;
	private JButton btnUpdateO,btnDepositO, btnWithdrawO;;
	private JLabel errorMsgProfile, invalidLabel_6, invalidLabel_7;
	private JPanel playerPanel, playgroundsPlayerPanel, homePlayerPanel, profilePlayerPanel, bookedSlotsPlayerPanel, teamPlayerPanel, infoPanel;
	private JLabel playgroundNameInfoLabel, emailInfoLabel, sizeInfoLabel, priceInfoLabel, cancelInfoLabel;
	private JTextArea descriptionInfoLabel;
	private JLabel locationInfoLabel, ownerNameInfoLabel, numberInfoLabel;
	private JButton btnDepositP, btnWithdrawP, btnUpdateP, btnAddTeammate;;
	private JLabel errorMsgProfile_1, invalidLabel_8, invalidLabel_9, errorMsgNewTeammate, errorMsgMoney, errorMsgBookedSlots;
	private JPanel bookedSlotsPanelP, bookedSlotsPanelO, teammatesPanel, newTeammatePanel;
	private JPanel adminPanel, activePlaygroundsPanel, inactivePlaygroundsPanel, displayActivePlaygroundsPanel, displayInactivePlaygroundsPanel;
	private JTextField nameProfileFieldO, emailProfileFieldO, locationProfileFieldO, walletProfileFieldO, numberProfileFieldO, depositField;
	private JTextField withdrawField, locationSearchField, fromSearchField, toSearchField, dateSearchField, dateField, nameProfileFieldP, emailProfileFieldP;
	private JTextField numberProfileFieldP, locationProfileFieldP, walletProfileFieldP, depositField2, withdrawField2, nameTeamField, emailTeamField;
	
	private static ArrayList<player> players = new ArrayList<player>(); // Contains all the players
	private static ArrayList<playgroundOwner> owners = new ArrayList<playgroundOwner>(); // Contains all the playground owners
	private ArrayList<slot> newSlots = new ArrayList<slot>();
	private ArrayList<JLabel> newSlotLabels = new ArrayList<JLabel>();
	private ArrayList<JButton> deleteSlotBtns = new ArrayList<JButton>();
	private ArrayList<JButton> editPlaygroundBtns = new ArrayList<JButton>();
	private static int currentUserID;
	private static String currentUserType = "";
	private int verificationCode, slotNum = 1;
	private static administrator admin = new administrator("admin@gofo.com", "admin"); // email is admin@gofo.com and password is admin
	
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
	/**
	 * The validateUserData method validates the sign up data.
	 * If the data is valid it'll generate a random 4 digit code and send it to the user's email then take the user to the verification panel,
	 * otherwise it'll display an error message.
	 * @param name this is the name entered by the user when signing up
	 * @param email this is the email entered by the user when signing up
	 * @param password this is the password entered by the user when signing up
	 */
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
			tabbedPane.setSelectedComponent(verificationPanel);
		}
	}
	/**
	 * The sendMail method sends an email using the JavaMail API
	 * @param email this is the email of the user we're going to be sending an email to
	 * @param subject the is the subject of the email
	 * @param content this is the content of the email
	 */
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
        } catch (MessagingException e) {throw new RuntimeException(e);}    
	}
	/**
	 * The createUser method checks whether the new user is a player or playground owner
	 * then creates the suitable object and adds it's to the array list
	 * @param name this is the name of the user
	 * @param email this is the email of the user
	 * @param password the is the password of the user
	 */
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
	/**
	 * The login method checks if the email and password exist in the owners array list or players array list 
	 * or if the email and password belong to the administrator.
	 * Once it finds an account that matches the email and password it takes the user to the appropriate screen.
	 * If the email and password don't match any account an error message will be displayed.
	 * @param email this is the email the user entered when logging in
	 * @param password this is the password the user entered when logging in
	 */
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
			if (admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
				tabbedPane.setSelectedComponent(adminPanel);
				displayActivePlaygrounds();
				activePlaygroundsPanel.setVisible(true);
				inactivePlaygroundsPanel.setVisible(false);
				found = true;
			}
		}
		if (found == false) {
			System.out.println("Not found");
			errorMsgLogin.setText("Incorrect email or password");
		}
	}
	/**
	 * The validatePlaygroundInfo method checks if the user left any empty fields and if the user added at least 1 slot,
	 * if everything is correct adds the playground to the owner and resets the fields.
	 * Otherwise an error message is displayed
	 */
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
	/**
	 * The displayOwnerPlaygrounds method loops around all the current user's playgrounds and displays them all on the panel
	 */
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
			namelabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
			namelabel.setBounds(10, 11 + (325*i), 302, 14);
			playgroundsOwnerPanel.add(namelabel);
			
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
	/**
	 * The ownerProfile method gets the current playground owner's details and puts them in the appropriate fields.
	 * It adds a deposit button which can be used to deposit money into the current playground owner's wallet and a withdraw button to
	 * withdraw money from the wallet. It also adds an update button which when pressed updates the current playground owner's info
	 * with all data from the appropriate fields then resets these fields.
	 */
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
					owners.get(currentUserID).wallet.deposit(amount);
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
	/**
	 * The ownerBookedSlots method loops through the current playground owner's playgrounds
	 * and displays all the booked slots in each playground
	 */
	public void ownerBookedSlots() {
		bookedSlotsPanelO.removeAll();
		// Loops through the owner's playgrounds
		int slotGap = 0;
		for (int i = 0; i < owners.get(currentUserID).playgrounds.size(); i++) {
			// Loops through that playground's slots
			for (int j = 0; j < owners.get(currentUserID).playgrounds.get(i).slots.size(); j++) {
				if (owners.get(currentUserID).playgrounds.get(i).slots.get(j).isBooked()) {
					JLabel newSlot = new JLabel("<html>Playground #" + (i+1) + "  Slot #" + (j+1) + " - From: " + owners.get(currentUserID).playgrounds.get(i).slots.get(j).getFrom() + "  To: " + owners.get(currentUserID).playgrounds.get(i).slots.get(j).getTo() + "   Date: " +  owners.get(currentUserID).playgrounds.get(i).slots.get(j).getDate() + "  <br><span style='color:black;'>Player: " + owners.get(currentUserID).playgrounds.get(i).slots.get(j).getPlayer().getName() + "&nbsp &nbsp Email:" + owners.get(currentUserID).playgrounds.get(i).slots.get(j).getPlayer().getEmail() + "</span></html>");
					newSlot.setForeground(new Color(255, 204, 0));
					newSlot.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
					newSlot.setBounds(10, 11 +(150*slotGap), 427, 75);
					bookedSlotsPanelO.add(newSlot);
					slotGap++;
					bookedSlotsPanelO.setPreferredSize(new Dimension(150, 70 * slotGap));
				}
			}
		}
	}
	/**
	 * The displayAllPlaygrounds method loops through all the owners and displays each owner's active playgrounds with all it's slots. If the user 
	 * filtered the playgrounds by location it displays only the playgrounds that contain that location, and the same thing applies to the time and date of the slots.
	 * It adds a ViewInfo button next to each playground, when clicked it shows a panel that has all the playgrounds information.
	 * It also adds a book button next to each slot, when clicked it changes the availability of that slot to false and adds the slot to the 
	 * current player's bookedSlots array list.
	 * @param location this is the location of the playground the player searched for
	 * @param from this is the time the slot begins from that the player searched for
	 * @param to this is the time the slot ends at that the player searched for
	 * @param date this is the date of the slot that the player searched for
	 */
	public void displayAllPlaygrounds(String location, String from, String to, String date) {
		playgroundsPlayerPanel.removeAll();
		int slotNum = 0, currentSlotNum = 0, gap = 0;
		for (int ownerIndex = 0; ownerIndex < owners.size(); ownerIndex++) {
			for (int playgroundIndex = 0; playgroundIndex < owners.get(ownerIndex).playgrounds.size(); playgroundIndex++) {
				if ((owners.get(ownerIndex).playgrounds.get(playgroundIndex).getStatus() == true) && (owners.get(ownerIndex).playgrounds.get(playgroundIndex).getLocation().contains(location))) {
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
							errorMsgMoney.setText("");
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
									if (players.get(currentUserID).checkWallet() >= owners.get(currentOwner).playgrounds.get(currentPlayground).getPrice()) {
										players.get(currentUserID).wallet.withdraw(owners.get(currentOwner).playgrounds.get(currentPlayground).getPrice());
										owners.get(currentOwner).wallet.deposit(owners.get(currentOwner).playgrounds.get(currentPlayground).getPrice());
										errorMsgMoney.setText("");
										owners.get(currentOwner).playgrounds.get(currentPlayground).slots.get(currentSlot).book();
										owners.get(currentOwner).playgrounds.get(currentPlayground).slots.get(currentSlot).setPlayer(players.get(currentUserID));
										players.get(currentUserID).bookSlot(owners.get(currentOwner).playgrounds.get(currentPlayground).slots.get(currentSlot));
										errorMsgMoney.setText("<html><span style='color:green;'>The slot has been booked!</span></html>");
										displayAllPlaygrounds("", "", "", "");
									} else {
										errorMsgMoney.setText("Insufficient balance to book that slot");
									}
								}
							});
							btnBook.setForeground(Color.WHITE);
							btnBook.setBackground(Color.BLACK);
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
	 * The playerProfile method gets the current player's details and puts them in the appropriate fields.
	 * It adds a deposit button which can be used to deposit money into the current player's wallet and a withdraw button to
	 * withdraw money from the wallet. It also adds an update button which when pressed updates the current player's info
	 * with all data from the appropriate fields then resets these fields.
	 */
	public void playerProfile() {
		nameProfileFieldP.setText(players.get(currentUserID).getName());
		emailProfileFieldP.setText(players.get(currentUserID).getEmail());
		locationProfileFieldP.setText(players.get(currentUserID).getLocation());
		numberProfileFieldP.setText(players.get(currentUserID).getPhone());
		String balance = ""+players.get(currentUserID).checkWallet();
		walletProfileFieldP.setText(balance);
		
		btnDepositP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (invalidLabel_8.getText().equals("")) {
					double amount = Double.parseDouble(depositField2.getText());
					players.get(currentUserID).wallet.deposit(amount);
					depositField2.setText("");
					String balance = ""+players.get(currentUserID).checkWallet();
					walletProfileFieldP.setText(balance);
				}
			}
		});
		btnWithdrawP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (invalidLabel_9.getText().equals("")) {
					double amount = Double.parseDouble(withdrawField2.getText());
					players.get(currentUserID).wallet.withdraw(amount);
					withdrawField2.setText("");
					String balance = ""+players.get(currentUserID).checkWallet();
					walletProfileFieldP.setText(balance);
				}
			}
		});
		btnUpdateP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (nameProfileFieldP.getText().equals("")) {
					errorMsgProfile_1.setText("Enter your name");
				} else if (emailProfileFieldP.getText().equals("")) {
					errorMsgProfile_1.setText("Enter your email");
				} else if (locationProfileFieldP.getText().equals("")) {
					errorMsgProfile_1.setText("Enter your location");
				} else if (numberProfileFieldP.getText().equals("")) {
					errorMsgProfile_1.setText("Enter your phone number");
				} else {
					errorMsgProfile_1.setText("");
					players.get(currentUserID).setName(nameProfileFieldP.getText());
					players.get(currentUserID).setEmail(emailProfileFieldP.getText());
					players.get(currentUserID).setPhone(numberProfileFieldP.getText());
					players.get(currentUserID).setLocation(locationProfileFieldP.getText());
					nameProfileFieldP.setBackground(new Color(51, 153, 102));
					nameProfileFieldP.setEditable(false);
					emailProfileFieldP.setBackground(new Color(51, 153, 102));
					emailProfileFieldP.setEditable(false);
					locationProfileFieldP.setBackground(new Color(51, 153, 102));
					locationProfileFieldP.setEditable(false);
					numberProfileFieldP.setBackground(new Color(51, 153, 102));
					numberProfileFieldP.setEditable(false);
				}
			}
		});
	}
	/**
	 * The playerBookedSlots method loops through the current player's booked slots and displays them all. Under each slot it adds a
	 * Cancel Booking button which when clicked changes the availability of that slot to true and removes the slot from the player's bookedSlots array list.
	 * It also adds an Invite Team button which when clicked loops through the player's team and send invitation e-mails to all of them using the sendMail method.
	 */
	public void playerBookedSlots() {
		bookedSlotsPanelP.removeAll();
		for (int i = 0; i < players.get(currentUserID).bookedSlots.size(); i++) {
			JLabel slot = new JLabel("Slot #" + (i+1) + "   Date: " + players.get(currentUserID).bookedSlots.get(i).getDate() + "  From: " + players.get(currentUserID).bookedSlots.get(i).getFrom() + "  To: " + players.get(currentUserID).bookedSlots.get(i).getTo());
			slot.setForeground(new Color(255, 204, 0));
			slot.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
			slot.setBounds(10, 11+(81 * i), 416, 14);
			bookedSlotsPanelP.add(slot);
			int currentSlot = i;
			JButton btnCancelBooking = new JButton("Cancel Booking");
			btnCancelBooking.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					errorMsgBookedSlots.setText("");
					boolean done = false;
					System.out.println("Current Slot: " + currentSlot);
					System.out.println("Number of booked slots: " + players.get(currentUserID).bookedSlots.size());
					for (int ownerIndex = 0; ownerIndex < owners.size(); ownerIndex++) {
						for (int playgroundIndex = 0; playgroundIndex < owners.get(ownerIndex).playgrounds.size(); playgroundIndex++) {
							for (int slotIndex = 0; slotIndex < owners.get(ownerIndex).playgrounds.get(playgroundIndex).slots.size(); slotIndex++) {
								System.out.println("Number of slots this owner has: " + owners.get(ownerIndex).playgrounds.get(playgroundIndex).slots.size());
								if (owners.get(ownerIndex).playgrounds.get(playgroundIndex).slots.get(slotIndex).equals(players.get(currentUserID).bookedSlots.get(currentSlot))) {
									double price = owners.get(ownerIndex).playgrounds.get(playgroundIndex).getPrice();
									owners.get(ownerIndex).playgrounds.get(playgroundIndex).slots.get(slotIndex).unBook();
									players.get(currentUserID).cancelBooking(currentSlot);
									owners.get(ownerIndex).wallet.withdraw(price);
									players.get(currentUserID).wallet.deposit(price);
									done = true;
									errorMsgBookedSlots.setText("<html><span style='color:green;'>Amount has been refunded!</span></html>");
									playerBookedSlots();
									break;
								}
								if (done == true) {
									break;
								}
							}
							if (done == true) {
								break;
							}
						}
					}
				}
			});
			btnCancelBooking.setForeground(Color.WHITE);
			btnCancelBooking.setBackground(Color.BLACK);
			btnCancelBooking.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
			btnCancelBooking.setBounds(51, 36+(81 * i), 151, 23);
			bookedSlotsPanelP.add(btnCancelBooking);
			JButton btnInviteTeam = new JButton("Invite Team");
			btnInviteTeam.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (players.get(currentUserID).team.get(0).size() < 1) {
						errorMsgBookedSlots.setText("No Teammates to invite");
					} else {
						for (int i = 0; i < players.get(currentUserID).team.get(0).size(); i++) {
							String content = "<html><h1>You have been invited!</h1><br> Dear " + players.get(currentUserID).team.get(0).get(i) + ", <br>"+ "<b> " + players.get(currentUserID).getName() +" </b> has invited you to join them in playing football on " + players.get(currentUserID).bookedSlots.get(currentSlot).getDate() +" from " + players.get(currentUserID).bookedSlots.get(currentSlot).getFrom() + " to " + players.get(currentUserID).bookedSlots.get(currentSlot).getTo() + "<br> Let them know if you'll be able to make it! </html>";
							sendMail(players.get(currentUserID).team.get(1).get(i), "GoFo Invitation", content);
						}
						errorMsgBookedSlots.setText("<html><span style='color:green;'>Invitations have been sent</span></html>");
					}
					playerBookedSlots();
				}
			});
			btnInviteTeam.setForeground(Color.WHITE);
			btnInviteTeam.setBackground(Color.BLACK);
			btnInviteTeam.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
			btnInviteTeam.setBounds(235, 36+(81 * i), 151, 23);
			bookedSlotsPanelP.add(btnInviteTeam);
			bookedSlotsPanelP.setPreferredSize(new Dimension(150, 100 * i));
		}
		bookedSlotsPanelP.revalidate();
		bookedSlotsPanelP.repaint();
	}
	/**
	 * The displayTeammates method loops through the current player's team array list and displays their names and e-mails.
	 * It also adds a Remove button next to each team mate which removes that team mate from the array list.
	 */
	public void displayTeammates() {
		teammatesPanel.removeAll();
		for (int i = 0; i < players.get(currentUserID).team.get(0).size(); i++) {
			JLabel lblTeammate = new JLabel("<html><span style='color:black;'>Teammate #" + (i+1) + "</span><br>&nbsp Name: " +  players.get(currentUserID).team.get(0).get(i) + "<br>&nbsp Email:" + players.get(currentUserID).team.get(1).get(i) + "</html>");
			lblTeammate.setForeground(new Color(255, 204, 0));
			lblTeammate.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
			lblTeammate.setBounds(10, 11 + (107 * i), 426, 75);
			teammatesPanel.add(lblTeammate);
			int currentTeammate = i;
			JButton btnRemove = new JButton("Remove");
			btnRemove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					 players.get(currentUserID).removeTeammate(currentTeammate);
					 displayTeammates();
				}
			});
			btnRemove.setForeground(Color.WHITE);
			btnRemove.setBackground(Color.BLACK);
			btnRemove.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
			btnRemove.setBounds(347, 21 + (107 * i), 89, 23);
			teammatesPanel.add(btnRemove);
			teammatesPanel.setPreferredSize(new Dimension(150, 100 * i));
		}
		teammatesPanel.revalidate();
		teammatesPanel.repaint();
	}
	/**
	 * The displayActivePlayground method loops through all the playground owners and displays all their active playgrounds.
	 * It adds a Deactivate button next to each playground which when clicked sets the playground's status to false.
	 * And it adds a Delete button which deletes that playground from the playground owner's list.
	 */
	public void displayActivePlaygrounds() {
		displayActivePlaygroundsPanel.removeAll();
		int playgroundNum = 0;
		for (int ownerIndex = 0; ownerIndex < owners.size(); ownerIndex++) {
			for (int playgroundIndex = 0; playgroundIndex < owners.get(ownerIndex).playgrounds.size(); playgroundIndex++) {
				if (owners.get(ownerIndex).playgrounds.get(playgroundIndex).getStatus() == true) {
					JLabel lblPlayground = new JLabel("<html>Playground #" + (playgroundNum+1) + " &nbsp <span style='color:#FFC107'>" + owners.get(ownerIndex).playgrounds.get(playgroundIndex).getName() + "</span><br> &nbsp &nbsp Size: <span style='color:#FFC107'> " + owners.get(ownerIndex).playgrounds.get(playgroundIndex).getSize() + " </span> <br>&nbsp &nbsp Price/Slot:  <span style='color:#FFC107'> " + owners.get(ownerIndex).playgrounds.get(playgroundIndex).getPrice() + " </span> <br>&nbsp &nbsp Cancellation Period:  <span style='color:#FFC107'> " + owners.get(ownerIndex).playgrounds.get(playgroundIndex).getCancellationPeriod() + " </span> <br>&nbsp &nbsp Location:  <span style='color:#FFC107'> " + owners.get(ownerIndex).playgrounds.get(playgroundIndex).getLocation() + " </span></html>");
					lblPlayground.setVerticalAlignment(SwingConstants.TOP);
					lblPlayground.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
					lblPlayground.setBounds(10, 11+(178*playgroundNum), 416, 108);
					displayActivePlaygroundsPanel.add(lblPlayground);
					int currentOwner = ownerIndex, currentPlayground = playgroundIndex;
					JButton btnDeactivate = new JButton("Deactivate");
					btnDeactivate.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							admin.deactivatePlayground(owners.get(currentOwner).playgrounds.get(currentPlayground));
							displayActivePlaygrounds();
						}
					});
					btnDeactivate.setForeground(Color.WHITE);
					btnDeactivate.setBackground(Color.BLACK);
					btnDeactivate.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
					btnDeactivate.setBounds(63, 126+(178*playgroundNum), 132, 23);
					displayActivePlaygroundsPanel.add(btnDeactivate);
					JButton btnDelete = new JButton("Delete");
					btnDelete.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							owners.get(currentOwner).playgrounds.remove(currentPlayground);
							displayActivePlaygrounds();
						}
					});
					btnDelete.setForeground(Color.WHITE);
					btnDelete.setBackground(Color.BLACK);
					btnDelete.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
					btnDelete.setBounds(259, 126+(178*playgroundNum), 132, 23);
					displayActivePlaygroundsPanel.add(btnDelete);
					playgroundNum++;
					displayActivePlaygroundsPanel.setPreferredSize(new Dimension(150, 100 * playgroundNum));
				}
			}
		}
		displayActivePlaygroundsPanel.revalidate();
		displayActivePlaygroundsPanel.repaint();
	}
	/**
	 * The displayInactivePlayground method loops through all the playground owners and displays all their inactive playgrounds.
	 * It adds a Activate button next to each playground which when clicked sets the playground's status to true.
	 * And it adds a Delete button which deletes that playground from the playground owner's list.
	 */
	public void displayInactivePlaygrounds() {
		displayInactivePlaygroundsPanel.removeAll();
		int playgroundNum = 0;
		for (int ownerIndex = 0; ownerIndex < owners.size(); ownerIndex++) {
			for (int playgroundIndex = 0; playgroundIndex < owners.get(ownerIndex).playgrounds.size(); playgroundIndex++) {
				if (owners.get(ownerIndex).playgrounds.get(playgroundIndex).getStatus() == false) {
					JLabel lblPlayground = new JLabel("<html>Playground #" + (playgroundNum+1) + " &nbsp <span style='color:#FFC107'>" + owners.get(ownerIndex).playgrounds.get(playgroundIndex).getName() + "</span><br> &nbsp &nbsp Size: <span style='color:#FFC107'> " + owners.get(ownerIndex).playgrounds.get(playgroundIndex).getSize() + " </span> <br>&nbsp &nbsp Price/Slot:  <span style='color:#FFC107'> " + owners.get(ownerIndex).playgrounds.get(playgroundIndex).getPrice() + " </span> <br>&nbsp &nbsp Cancellation Period:  <span style='color:#FFC107'> " + owners.get(ownerIndex).playgrounds.get(playgroundIndex).getCancellationPeriod() + " </span> <br>&nbsp &nbsp Location:  <span style='color:#FFC107'> " + owners.get(ownerIndex).playgrounds.get(playgroundIndex).getLocation() + " </span></html>");
					lblPlayground.setVerticalAlignment(SwingConstants.TOP);
					lblPlayground.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
					lblPlayground.setBounds(10, 11+(178*playgroundNum), 416, 108);
					displayInactivePlaygroundsPanel.add(lblPlayground);
					int currentOwner = ownerIndex, currentPlayground = playgroundIndex;
					JButton btnActivate = new JButton("Activate");
					btnActivate.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							admin.activatePlayground(owners.get(currentOwner).playgrounds.get(currentPlayground));
							displayInactivePlaygrounds();
						}
					});
					btnActivate.setForeground(Color.WHITE);
					btnActivate.setBackground(Color.BLACK);
					btnActivate.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
					btnActivate.setBounds(63, 126+(178*playgroundNum), 132, 23);
					displayInactivePlaygroundsPanel.add(btnActivate);
					JButton btnDelete = new JButton("Delete");
					btnDelete.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							owners.get(currentOwner).playgrounds.remove(currentPlayground);
							displayInactivePlaygrounds();
						}
					});
					btnDelete.setForeground(Color.WHITE);
					btnDelete.setBackground(Color.BLACK);
					btnDelete.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
					btnDelete.setBounds(259, 126+(178*playgroundNum), 132, 23);
					displayInactivePlaygroundsPanel.add(btnDelete);
					playgroundNum++;
					displayInactivePlaygroundsPanel.setPreferredSize(new Dimension(150, 100 * playgroundNum));
				}
			}
		}
		displayInactivePlaygroundsPanel.revalidate();
		displayInactivePlaygroundsPanel.repaint();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Go Football");
		frame.setResizable(false);
		frame.setBounds(100, 100, 737, 708);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, -26, 731, 706);
		frame.getContentPane().add(tabbedPane);
		
		JPanel startupPanel= new JPanel();
		startupPanel.setBackground(new Color(51, 153, 102));
		startupPanel.setLayout(null);
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
		adminPanel = new JPanel();
		adminPanel.setBackground(new Color(51, 153, 102));
		
		JButton btnLogIn = new JButton("Log in");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setSelectedComponent(loginPanel); // Go to login tab
			}
		});
		btnLogIn.setFont(new Font("Showcard Gothic", Font.PLAIN, 26));
		btnLogIn.setBounds(270, 297, 192, 65);
		startupPanel.add(btnLogIn);
		
		JButton btnLogIn_1 = new JButton("Sign up");
		btnLogIn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setSelectedComponent(signupPanel); // Go to Sign up tab
			}
		});
		btnLogIn_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 26));
		btnLogIn_1.setBounds(270, 408, 192, 70);
		startupPanel.add(btnLogIn_1);
		
		JLabel lblWelcomeToGo = new JLabel("Welcome to Go Football!");
		lblWelcomeToGo.setFont(new Font("Showcard Gothic", Font.BOLD | Font.ITALIC, 37));
		lblWelcomeToGo.setForeground(new Color(255, 255, 255));
		lblWelcomeToGo.setBounds(86, 130, 568, 95);
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
					verificationCodeField.setText("");
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
		lblGo.setFont(new Font("Showcard Gothic", Font.PLAIN, 37));
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
		btnOHome.setFont(new Font("Showcard Gothic", Font.PLAIN, 17));
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
		btnOProfile.setFont(new Font("Showcard Gothic", Font.PLAIN, 17));
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
		btnOAddPlayground.setFont(new Font("Showcard Gothic", Font.PLAIN, 17));
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
		btnOViewBookings.setFont(new Font("Showcard Gothic", Font.PLAIN, 17));
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
		btnOLogOut.setFont(new Font("Showcard Gothic", Font.PLAIN, 17));
		btnOLogOut.setBounds(10, 528, 205, 49);
		menuOwnerPanel.add(btnOLogOut);
		
		homeOwnerPanel = new JPanel();
		homeOwnerPanel.setBounds(227, 0, 499, 675);
		homeOwnerPanel.setBackground(new Color(51, 153, 102));
		OwnerPanel.add(homeOwnerPanel);
		homeOwnerPanel.setLayout(null);
		
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
		profileOwnerPanel.setBounds(227, 0, 499, 675);
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
		walletProfileFieldO.setFont(new Font("Times New Roman", Font.BOLD, 15));
		walletProfileFieldO.setColumns(10);
		walletProfileFieldO.setBounds(130, 403, 106, 20);
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
		
		JLabel walletProfileLabel_1_1_1 = new JLabel("pounds");
		walletProfileLabel_1_1_1.setForeground(Color.WHITE);
		walletProfileLabel_1_1_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 17));
		walletProfileLabel_1_1_1.setBounds(246, 406, 86, 20);
		profileOwnerPanel.add(walletProfileLabel_1_1_1);
		
		addPlaygroundOwnerPanel = new JPanel();
		addPlaygroundOwnerPanel.setBounds(227, 0, 499, 675);
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
		
		JLabel lblPricehours = new JLabel("Price/Slot:");
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
		invalidLabel.setBounds(286, 231, 123, 24);
		addPlaygroundOwnerPanel.add(invalidLabel);
		
		invalidLabel_1 = new JLabel("");
		invalidLabel_1.setForeground(new Color(255, 102, 102));
		invalidLabel_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		invalidLabel_1.setBounds(366, 280, 123, 24);
		addPlaygroundOwnerPanel.add(invalidLabel_1);
		
		invalidLabel_2 = new JLabel("");
		invalidLabel_2.setForeground(new Color(255, 102, 102));
		invalidLabel_2.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		invalidLabel_2.setBounds(366, 328, 123, 27);
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
		
		JLabel lblPounds_1 = new JLabel("pounds");
		lblPounds_1.setForeground(Color.WHITE);
		lblPounds_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
		lblPounds_1.setBounds(275, 283, 81, 21);
		addPlaygroundOwnerPanel.add(lblPounds_1);
		
		viewBookingsOwnerPanel = new JPanel();
		viewBookingsOwnerPanel.setBounds(227, 0, 499, 675);
		OwnerPanel.add(viewBookingsOwnerPanel);
		viewBookingsOwnerPanel.setLayout(null);
		viewBookingsOwnerPanel.setBackground(new Color(51, 153, 102));
		
		JLabel lblBookings = new JLabel("Bookings");
		lblBookings.setForeground(Color.WHITE);
		lblBookings.setFont(new Font("Showcard Gothic", Font.PLAIN, 30));
		lblBookings.setBounds(162, 83, 169, 50);
		viewBookingsOwnerPanel.add(lblBookings);
		
		bookedSlotsPanelO = new JPanel();
		bookedSlotsPanelO.setBackground(new Color(51, 153, 102));
		bookedSlotsPanelO.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane(bookedSlotsPanelO,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		JLabel lblSlotDate_1 = new JLabel("<html>Playground #1 Cairo<br>Slot #1    date: 22-22-2002   from: 12:20 am   to: 23:23 pm</html>");
		lblSlotDate_1.setForeground(new Color(255, 204, 0));
		lblSlotDate_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
		lblSlotDate_1.setBounds(10, 11, 427, 45);
		bookedSlotsPanelO.add(lblSlotDate_1);
		scrollPane_2.setBounds(25, 166, 449, 475);
		viewBookingsOwnerPanel.add(scrollPane_2);
		
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
		lblGo_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 37));
		lblGo_1.setBounds(10, 0, 205, 138);
		menuPlayerPanel.add(lblGo_1);
		
		JButton btnPHome = new JButton("Home");
		btnPHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errorMsgMoney.setText("");
				displayAllPlaygrounds("", "", "", "");
				homePlayerPanel.setVisible(true);
				profilePlayerPanel.setVisible(false);
				bookedSlotsPlayerPanel.setVisible(false);
				teamPlayerPanel.setVisible(false);
			}
		});
		btnPHome.setForeground(Color.WHITE);
		btnPHome.setFont(new Font("Showcard Gothic", Font.PLAIN, 17));
		btnPHome.setBackground(Color.BLACK);
		btnPHome.setBounds(10, 174, 205, 49);
		menuPlayerPanel.add(btnPHome);
		
		JButton btnPProfile = new JButton("Profile");
		btnPProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playerProfile();
				profilePlayerPanel.setVisible(true);
				homePlayerPanel.setVisible(false);
				bookedSlotsPlayerPanel.setVisible(false);
				teamPlayerPanel.setVisible(false);
			}
		});
		btnPProfile.setForeground(Color.WHITE);
		btnPProfile.setFont(new Font("Showcard Gothic", Font.PLAIN, 17));
		btnPProfile.setBackground(Color.BLACK);
		btnPProfile.setBounds(10, 246, 205, 49);
		menuPlayerPanel.add(btnPProfile);
		
		JButton btnPBookedSlots = new JButton("Booked Slots");
		btnPBookedSlots.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errorMsgBookedSlots.setText("");
				playerBookedSlots();
				bookedSlotsPlayerPanel.setVisible(true);
				homePlayerPanel.setVisible(false);
				profilePlayerPanel.setVisible(false);
				teamPlayerPanel.setVisible(false);
			}
		});
		btnPBookedSlots.setForeground(Color.WHITE);
		btnPBookedSlots.setFont(new Font("Showcard Gothic", Font.PLAIN, 17));
		btnPBookedSlots.setBackground(Color.BLACK);
		btnPBookedSlots.setBounds(10, 319, 205, 49);
		menuPlayerPanel.add(btnPBookedSlots);
		
		JButton btnPTeam = new JButton("Team");
		btnPTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayTeammates();
				teamPlayerPanel.setVisible(true);
				homePlayerPanel.setVisible(false);
				profilePlayerPanel.setVisible(false);
				bookedSlotsPlayerPanel.setVisible(false);
			}
		});
		btnPTeam.setForeground(Color.WHITE);
		btnPTeam.setFont(new Font("Showcard Gothic", Font.PLAIN, 17));
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
		btnPLogOut.setFont(new Font("Showcard Gothic", Font.PLAIN, 17));
		btnPLogOut.setBackground(Color.BLACK);
		btnPLogOut.setBounds(10, 528, 205, 49);
		menuPlayerPanel.add(btnPLogOut);
		
		profilePlayerPanel = new JPanel();
		profilePlayerPanel.setBounds(227, 0, 499, 675);
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
		editNameBtnO_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameProfileFieldP.setEditable(true);
				nameProfileFieldP.setBackground(new Color(255, 255, 255));
			}
		});
		editNameBtnO_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		editNameBtnO_1.setBounds(387, 169, 70, 23);
		profilePlayerPanel.add(editNameBtnO_1);
		
		JButton editEmailBtnO_1 = new JButton("Edit");
		editEmailBtnO_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emailProfileFieldP.setEditable(true);
				emailProfileFieldP.setBackground(new Color(255, 255, 255));
			}
		});
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
		editNumberBtnO_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numberProfileFieldP.setEditable(true);
				numberProfileFieldP.setBackground(new Color(255, 255, 255));
			}
		});
		editNumberBtnO_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		editNumberBtnO_1.setBounds(387, 285, 70, 23);
		profilePlayerPanel.add(editNumberBtnO_1);
		
		JButton editLocationBtnO_1 = new JButton("Edit");
		editLocationBtnO_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				locationProfileFieldP.setEditable(true);
				locationProfileFieldP.setBackground(new Color(255, 255, 255));
			}
		});
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
		walletProfileFieldP.setFont(new Font("Times New Roman", Font.BOLD, 15));
		walletProfileFieldP.setEditable(false);
		walletProfileFieldP.setColumns(10);
		walletProfileFieldP.setBackground(new Color(51, 153, 102));
		walletProfileFieldP.setBounds(125, 415, 106, 20);
		profilePlayerPanel.add(walletProfileFieldP);
		
		depositField2 = new JTextField();
		depositField2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		depositField2.setColumns(10);
		depositField2.setBounds(199, 467, 130, 22);
		profilePlayerPanel.add(depositField2);
		
		btnDepositP = new JButton("Deposit");
		btnDepositP.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
		btnDepositP.setBounds(349, 466, 124, 23);
		profilePlayerPanel.add(btnDepositP);
		
		btnWithdrawP = new JButton("Withdraw");
		btnWithdrawP.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
		btnWithdrawP.setBounds(349, 512, 124, 23);
		profilePlayerPanel.add(btnWithdrawP);
		
		withdrawField2 = new JTextField();
		withdrawField2.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		withdrawField2.setColumns(10);
		withdrawField2.setBounds(199, 512, 130, 23);
		profilePlayerPanel.add(withdrawField2);
		
		btnUpdateP = new JButton("Update");
		btnUpdateP.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		btnUpdateP.setBounds(171, 608, 121, 33);
		profilePlayerPanel.add(btnUpdateP);
		
		errorMsgProfile_1 = new JLabel("", SwingConstants.CENTER);
		errorMsgProfile_1.setForeground(new Color(255, 102, 102));
		errorMsgProfile_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
		errorMsgProfile_1.setBounds(50, 566, 407, 30);
		profilePlayerPanel.add(errorMsgProfile_1);
		
		invalidLabel_9 = new JLabel("");
		invalidLabel_9.setForeground(new Color(255, 102, 102));
		invalidLabel_9.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		invalidLabel_9.setBounds(33, 521, 123, 14);
		profilePlayerPanel.add(invalidLabel_9);
		
		invalidLabel_8 = new JLabel("");
		invalidLabel_8.setForeground(new Color(255, 102, 102));
		invalidLabel_8.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		invalidLabel_8.setBounds(33, 474, 123, 14);
		profilePlayerPanel.add(invalidLabel_8);
		
		JLabel walletProfileLabel_1_1 = new JLabel("pounds");
		walletProfileLabel_1_1.setForeground(Color.WHITE);
		walletProfileLabel_1_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 17));
		walletProfileLabel_1_1.setBounds(243, 414, 86, 20);
		profilePlayerPanel.add(walletProfileLabel_1_1);
		
		homePlayerPanel = new JPanel();
		homePlayerPanel.setForeground(Color.BLACK);
		homePlayerPanel.setLayout(null);
		homePlayerPanel.setBackground(new Color(51, 153, 102));
		homePlayerPanel.setBounds(227, 0, 499, 675);
		playerPanel.add(homePlayerPanel);
		
		infoPanel = new JPanel();
		infoPanel.setBounds(10, 37, 479, 604);
		homePlayerPanel.add(infoPanel);
		infoPanel.setVisible(false);
		infoPanel.setBackground(new Color(153, 204, 153));
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
		
		JLabel priceInfo = new JLabel("Price/Slot:");
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
				errorMsgMoney.setText("");
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
		scrollPane_3.setBounds(10, 241, 479, 364);
		homePlayerPanel.add(scrollPane_3);
		
		errorMsgMoney = new JLabel("", SwingConstants.CENTER);
		errorMsgMoney.setBounds(48, 616, 407, 23);
		homePlayerPanel.add(errorMsgMoney);
		errorMsgMoney.setForeground(new Color(255, 102, 102));
		errorMsgMoney.setFont(new Font("Showcard Gothic", Font.PLAIN, 18));
		
		teamPlayerPanel = new JPanel();
		teamPlayerPanel.setLayout(null);
		teamPlayerPanel.setForeground(Color.BLACK);
		teamPlayerPanel.setBackground(new Color(51, 153, 102));
		teamPlayerPanel.setBounds(227, 0, 499, 675);
		playerPanel.add(teamPlayerPanel);
		
		btnAddTeammate = new JButton("Add Teammate");
		btnAddTeammate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newTeammatePanel.setVisible(true);
				btnAddTeammate.setVisible(false);
				teamPlayerPanel.repaint();
			}
		});
		
		newTeammatePanel = new JPanel();
		newTeammatePanel.setVisible(false);
		newTeammatePanel.setBackground(new Color(153, 204, 153));
		newTeammatePanel.setBounds(35, 153, 434, 365);
		teamPlayerPanel.add(newTeammatePanel);
		newTeammatePanel.setLayout(null);
		
		JLabel lblNewTeammate = new JLabel("New Teammate");
		lblNewTeammate.setFont(new Font("Showcard Gothic", Font.PLAIN, 24));
		lblNewTeammate.setBounds(128, 33, 199, 51);
		newTeammatePanel.add(lblNewTeammate);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
		lblName.setBounds(50, 124, 75, 51);
		newTeammatePanel.add(lblName);
		
		JLabel lblEmailNew = new JLabel("Email:");
		lblEmailNew.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
		lblEmailNew.setBounds(50, 186, 75, 43);
		newTeammatePanel.add(lblEmailNew);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (nameTeamField.getText().equals("")) {
					errorMsgNewTeammate.setText("Enter the Name");
				} else if (emailTeamField.getText().equals("")) {
					errorMsgNewTeammate.setText("Enter the Email");
				} else {
					errorMsgNewTeammate.setText("");
					players.get(currentUserID).addTeammate(nameTeamField.getText(), emailTeamField.getText());
					nameTeamField.setText("");
					emailTeamField.setText("");
					displayTeammates();
					newTeammatePanel.setVisible(false);
					teamPlayerPanel.setVisible(true);
					btnAddTeammate.setVisible(true);
				}
			}
		});
		btnAdd.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		btnAdd.setBounds(163, 289, 114, 34);
		newTeammatePanel.add(btnAdd);
		
		nameTeamField = new JTextField();
		nameTeamField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		nameTeamField.setBounds(135, 135, 226, 26);
		newTeammatePanel.add(nameTeamField);
		nameTeamField.setColumns(10);
		
		emailTeamField = new JTextField();
		emailTeamField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		emailTeamField.setColumns(10);
		emailTeamField.setBounds(135, 196, 226, 26);
		newTeammatePanel.add(emailTeamField);
		
		errorMsgNewTeammate = new JLabel("", SwingConstants.CENTER);
		errorMsgNewTeammate.setForeground(new Color(255, 102, 102));
		errorMsgNewTeammate.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
		errorMsgNewTeammate.setBounds(10, 240, 414, 23);
		newTeammatePanel.add(errorMsgNewTeammate);
		
		JLabel lbTeam = new JLabel("Team");
		lbTeam.setForeground(Color.WHITE);
		lbTeam.setFont(new Font("Showcard Gothic", Font.PLAIN, 34));
		lbTeam.setBounds(194, 71, 108, 50);
		teamPlayerPanel.add(lbTeam);
		btnAddTeammate.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		btnAddTeammate.setBounds(148, 572, 200, 34);
		teamPlayerPanel.add(btnAddTeammate);
		
		teammatesPanel = new JPanel();
		teammatesPanel.setBackground(new Color(51, 153, 102));
		teammatesPanel.setLayout(null);
		
		JScrollPane scrollPane_5 = new JScrollPane(teammatesPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		JLabel lblTeammate = new JLabel("<html><span style=\"color:black;\">Teammate #1 </span><br>&nbsp Name: Marwa <br>&nbsp Email: farabi.marwa@gmail.com</html>");
		lblTeammate.setForeground(new Color(255, 204, 0));
		lblTeammate.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		lblTeammate.setBounds(10, 11, 426, 75);
		teammatesPanel.add(lblTeammate);
		
		JLabel lblTeammate_1 = new JLabel("<html><span style=\"color:black;\">Teammate #1 </span><br>&nbsp Name: Marwa <br>&nbsp Email: farabi.marwa@gmail.com</html>");
		lblTeammate_1.setForeground(new Color(255, 204, 0));
		lblTeammate_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 16));
		lblTeammate_1.setBounds(10, 118, 426, 75);
		teammatesPanel.add(lblTeammate_1);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
		btnDelete.setBounds(347, 21, 89, 23);
		teammatesPanel.add(btnDelete);
		
		JButton btnDelete_1 = new JButton("Delete");
		btnDelete_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
		btnDelete_1.setBounds(347, 128, 89, 23);
		teammatesPanel.add(btnDelete_1);
		scrollPane_5.setBounds(27, 153, 448, 365);
		teamPlayerPanel.add(scrollPane_5);
		
		bookedSlotsPlayerPanel = new JPanel();
		bookedSlotsPlayerPanel.setBounds(227, 0, 499, 675);
		playerPanel.add(bookedSlotsPlayerPanel);
		bookedSlotsPlayerPanel.setLayout(null);
		bookedSlotsPlayerPanel.setForeground(Color.BLACK);
		bookedSlotsPlayerPanel.setBackground(new Color(51, 153, 102));
		
		JLabel lblBookedSlots = new JLabel("Booked Slots");
		lblBookedSlots.setForeground(Color.WHITE);
		lblBookedSlots.setFont(new Font("Showcard Gothic", Font.PLAIN, 30));
		lblBookedSlots.setBounds(131, 71, 232, 50);
		bookedSlotsPlayerPanel.add(lblBookedSlots);
		
		bookedSlotsPanelP = new JPanel();
		bookedSlotsPanelP.setBackground(new Color(51, 153, 102));
		bookedSlotsPanelP.setLayout(null);
		
		JScrollPane scrollPane_4 = new JScrollPane(bookedSlotsPanelP,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		JLabel lblSlotDate = new JLabel("<html>Playground #1 Cairo<br>Slot #1    date: 22-22-2002   from: 12:20 am   to: 23:23 pm</html>");
		lblSlotDate.setForeground(new Color(255, 204, 0));
		lblSlotDate.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
		lblSlotDate.setBounds(10, 11, 416, 45);
		bookedSlotsPanelP.add(lblSlotDate);
		
		JButton btnCancelBooking = new JButton("Cancel Booking");
		btnCancelBooking.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		btnCancelBooking.setBounds(51, 67, 151, 23);
		bookedSlotsPanelP.add(btnCancelBooking);
		
		JButton btnInviteTeam = new JButton("Invite Team");
		btnInviteTeam.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		btnInviteTeam.setBounds(235, 67, 151, 23);
		bookedSlotsPanelP.add(btnInviteTeam);
		
		JLabel lblSlotDate_2 = new JLabel("Slot #2    date: 22-22-2002   from: 12:20 am   to: 23:23 pm");
		lblSlotDate_2.setForeground(new Color(255, 204, 0));
		lblSlotDate_2.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
		lblSlotDate_2.setBounds(10, 121, 416, 14);
		bookedSlotsPanelP.add(lblSlotDate_2);
		
		JButton btnCancelBooking_1 = new JButton("Cancel Booking");
		btnCancelBooking_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		btnCancelBooking_1.setBounds(51, 146, 151, 23);
		bookedSlotsPanelP.add(btnCancelBooking_1);
		
		JButton btnInviteTeam_1 = new JButton("Invite Team");
		btnInviteTeam_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 12));
		btnInviteTeam_1.setBounds(235, 146, 151, 23);
		bookedSlotsPanelP.add(btnInviteTeam_1);
		scrollPane_4.setBounds(33, 161, 438, 442);
		bookedSlotsPlayerPanel.add(scrollPane_4);
		
		errorMsgBookedSlots = new JLabel("", SwingConstants.CENTER);
		errorMsgBookedSlots.setForeground(new Color(255, 102, 102));
		errorMsgBookedSlots.setFont(new Font("Showcard Gothic", Font.PLAIN, 18));
		errorMsgBookedSlots.setBounds(43, 614, 407, 23);
		bookedSlotsPlayerPanel.add(errorMsgBookedSlots);;
		
		tabbedPane.addTab("Admin", null, adminPanel, null);
		adminPanel.setLayout(null);
		
		JPanel menuAdminPanel = new JPanel();
		menuAdminPanel.setLayout(null);
		menuAdminPanel.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		menuAdminPanel.setBackground(new Color(0, 102, 51));
		menuAdminPanel.setBounds(0, 0, 227, 675);
		adminPanel.add(menuAdminPanel);
		
		JLabel lblGo_1_1 = new JLabel("<html><div style='text-align: center;'>Go Football</div></html>", SwingConstants.CENTER);
		lblGo_1_1.setForeground(Color.WHITE);
		lblGo_1_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 37));
		lblGo_1_1.setBounds(10, 11, 205, 138);
		menuAdminPanel.add(lblGo_1_1);
		
		JButton btnActivePlaygrounds = new JButton("<html><div style='text-align: center;'>Active Playgrounds</div></html>");
		btnActivePlaygrounds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayActivePlaygrounds();
				activePlaygroundsPanel.setVisible(true);
				inactivePlaygroundsPanel.setVisible(false);
			}
		});
		btnActivePlaygrounds.setVerticalAlignment(SwingConstants.TOP);
		btnActivePlaygrounds.setForeground(Color.WHITE);
		btnActivePlaygrounds.setFont(new Font("Showcard Gothic", Font.PLAIN, 17));
		btnActivePlaygrounds.setBackground(Color.BLACK);
		btnActivePlaygrounds.setBounds(10, 221, 205, 59);
		menuAdminPanel.add(btnActivePlaygrounds);
		
		JButton btnALogOut = new JButton("Log Out");
		btnALogOut.addActionListener(new ActionListener() {
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
		btnALogOut.setForeground(Color.WHITE);
		btnALogOut.setFont(new Font("Showcard Gothic", Font.PLAIN, 17));
		btnALogOut.setBackground(Color.BLACK);
		btnALogOut.setBounds(10, 528, 205, 49);
		menuAdminPanel.add(btnALogOut);
		
		JButton btninactivePlaygrounds = new JButton("<html><div style='text-align: center;'>Inactive Playgrounds</div></html>");
		btninactivePlaygrounds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayInactivePlaygrounds();
				inactivePlaygroundsPanel.setVisible(true);
				activePlaygroundsPanel.setVisible(false);
			}
		});
		btninactivePlaygrounds.setVerticalAlignment(SwingConstants.TOP);
		btninactivePlaygrounds.setForeground(Color.WHITE);
		btninactivePlaygrounds.setFont(new Font("Showcard Gothic", Font.PLAIN, 17));
		btninactivePlaygrounds.setBackground(Color.BLACK);
		btninactivePlaygrounds.setBounds(10, 324, 205, 59);
		menuAdminPanel.add(btninactivePlaygrounds);
		
		inactivePlaygroundsPanel = new JPanel();
		inactivePlaygroundsPanel.setLayout(null);
		inactivePlaygroundsPanel.setForeground(Color.BLACK);
		inactivePlaygroundsPanel.setBackground(new Color(51, 153, 102));
		inactivePlaygroundsPanel.setBounds(227, 0, 499, 675);
		adminPanel.add(inactivePlaygroundsPanel);
		
		JLabel lblInactivePlaygrounds = new JLabel("Inactive Playgrounds");
		lblInactivePlaygrounds.setForeground(Color.WHITE);
		lblInactivePlaygrounds.setFont(new Font("Showcard Gothic", Font.PLAIN, 28));
		lblInactivePlaygrounds.setBounds(78, 78, 360, 50);
		inactivePlaygroundsPanel.add(lblInactivePlaygrounds);
		
		displayInactivePlaygroundsPanel = new JPanel();
		displayInactivePlaygroundsPanel.setBackground(new Color(51, 153, 102));
		displayInactivePlaygroundsPanel.setLayout(null);
		
		JScrollPane scrollPane_7 = new JScrollPane(displayInactivePlaygroundsPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_7.setBounds(33, 161, 438, 442);
		inactivePlaygroundsPanel.add(scrollPane_7);
		
		activePlaygroundsPanel = new JPanel();
		activePlaygroundsPanel.setLayout(null);
		activePlaygroundsPanel.setForeground(Color.BLACK);
		activePlaygroundsPanel.setBackground(new Color(51, 153, 102));
		activePlaygroundsPanel.setBounds(227, 0, 499, 678);
		adminPanel.add(activePlaygroundsPanel);
		
		JLabel lblActivePlaygrounds = new JLabel("Active Playgrounds");
		lblActivePlaygrounds.setForeground(Color.WHITE);
		lblActivePlaygrounds.setFont(new Font("Showcard Gothic", Font.PLAIN, 28));
		lblActivePlaygrounds.setBounds(95, 78, 323, 50);
		activePlaygroundsPanel.add(lblActivePlaygrounds);
		
		displayActivePlaygroundsPanel = new JPanel();
		displayActivePlaygroundsPanel.setBackground(new Color(51, 153, 102));
		displayActivePlaygroundsPanel.setLayout(null);
		
		JScrollPane scrollPane_6 = new JScrollPane(displayActivePlaygroundsPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JLabel lblPlayground = new JLabel("<html>Playground #1 &nbsp <span style='color:#FFC107'> Name</span><br> &nbsp &nbsp Size: <span style='color:#FFC107'> 45</span> <br>&nbsp &nbsp Price/Slot:  <span style='color:#FFC107'> 1</span> <br>&nbsp &nbsp Cancellation Period:  <span style='color:#FFC107'> 3 </span> <br>&nbsp &nbsp Location:  <span style='color:#FFC107'> Maadi </span></html>");
		lblPlayground.setVerticalAlignment(SwingConstants.TOP);
		lblPlayground.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
		lblPlayground.setBounds(10, 11, 416, 108);
		displayActivePlaygroundsPanel.add(lblPlayground);
		
		JButton btnDeactivate = new JButton("Deactivate");
		btnDeactivate.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
		btnDeactivate.setBounds(63, 126, 132, 23);
		displayActivePlaygroundsPanel.add(btnDeactivate);
		
		JButton btnDelete_2 = new JButton("Delete");
		btnDelete_2.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
		btnDelete_2.setBounds(259, 126, 132, 23);
		displayActivePlaygroundsPanel.add(btnDelete_2);
		
		JLabel lblPlayground_1 = new JLabel("<html>Playground #1 &nbsp <span style='color:#FFC107'> Name</span><br> &nbsp &nbsp Size: <span style='color:#FFC107'> 45</span> <br>&nbsp &nbsp Price/Slot:  <span style='color:#FFC107'> 1</span> <br>&nbsp &nbsp Cancellation Period:  <span style='color:#FFC107'> 3 </span> <br>&nbsp &nbsp Location:  <span style='color:#FFC107'> Maadi </span></html>");
		lblPlayground_1.setVerticalAlignment(SwingConstants.TOP);
		lblPlayground_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 15));
		lblPlayground_1.setBounds(10, 189, 416, 108);
		displayActivePlaygroundsPanel.add(lblPlayground_1);
		
		JButton btnDeactivate_1 = new JButton("Deactivate");
		btnDeactivate_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
		btnDeactivate_1.setBounds(63, 304, 132, 23);
		displayActivePlaygroundsPanel.add(btnDeactivate_1);
		
		JButton btnDelete_2_1 = new JButton("Delete");
		btnDelete_2_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
		btnDelete_2_1.setBounds(259, 304, 132, 23);
		displayActivePlaygroundsPanel.add(btnDelete_2_1);
		scrollPane_6.setBounds(33, 161, 438, 442);
		activePlaygroundsPanel.add(scrollPane_6);
	}
}
