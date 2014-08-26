package com.amazon.ec2.connect;
/* -*-mode:java; c-basic-offset:2; indent-tabs-mode:nil -*- */
/**
 * This program will demonstrate X11 forwarding.
 *   $ CLASSPATH=.:../build javac X11Forwarding.java 
 *   $ CLASSPATH=.:../build java X11Forwarding
 * You will be asked username, hostname, displayname and passwd. 
 * If your X server does not run at 127.0.0.1, please enter correct
 * displayname. If everything works fine, you will get the shell prompt.
 * Try X applications; for example, xlogo.
 *
 */
import com.jcraft.jsch.*;

import java.awt.*;

import javax.swing.*;
 
public class X11Forwarding{
  /**
   * @wbp.parser.entryPoint
   */
  public static void main(String[] arg){
 
    String xhost="127.0.0.1";
    int xport=0;
 
    try{
      JSch jsch=new JSch();  
 
      String host=null;
      host=JOptionPane.showInputDialog("Enter username@hostname",System.getProperty("user.name")+"@localhost"); 
     
      String user=host.substring(0, host.indexOf('@'));
      host=host.substring(host.indexOf('@')+1);
 
      Session session=jsch.getSession(user, host, 22);
 
      String display=JOptionPane.showInputDialog("Please enter display name",xhost+":"+xport);
      xhost=display.substring(0, display.indexOf(':'));
      xport=Integer.parseInt(display.substring(display.indexOf(':')+1));
 
      session.setX11Host(xhost);
      session.setX11Port(xport+6000);
      
 
      // username and password will be given via UserInfo interface.
      UserInfo ui=new MyUserInfo();
      session.setUserInfo(ui);
     
      session.connect();
      System.out.println(ui.getPassword());
      Channel channel=session.openChannel("shell");
 
      channel.setXForwarding(true);
 
      channel.setInputStream(System.in);
      channel.setOutputStream(System.out);
 
      channel.connect();
    }
    catch(Exception e){
      System.out.println(e);
    }
  }
 
  public static class MyUserInfo implements UserInfo, UIKeyboardInteractive{
    
	  public String getPassword(){
    	return passwd;
    }
    
    public boolean promptYesNo(String str){
      Object[] options={ "yes", "no" };
      int foo=JOptionPane.showOptionDialog(null, 
             str,
             "Warning", 
             JOptionPane.DEFAULT_OPTION, 
             JOptionPane.WARNING_MESSAGE,
             null, options, options[0]);
       return foo==0;
    }
  
    String passwd;
    JTextField passwordField=(JTextField)new JPasswordField(20);
 
    public String getPassphrase(){ return null; }
    public boolean promptPassphrase(String message){ return true; }
    public boolean promptPassword(String message){
      Object[] ob={passwordField}; 
      int result=JOptionPane.showConfirmDialog(null, ob, message,JOptionPane.OK_CANCEL_OPTION);
      if(result==JOptionPane.OK_OPTION){
    	  passwd=passwordField.getText();
    	  return true;
      }
      else{
    	  return false;
      }
    }
    public void showMessage(String message){
      JOptionPane.showMessageDialog(null, message);
    }
    final GridBagConstraints gbc = 
      new GridBagConstraints(0,0,1,1,1,1,
                             GridBagConstraints.NORTHWEST,
                             GridBagConstraints.NONE,
                             new Insets(0,0,0,0),0,0);
    private Container panel;
    public String[] promptKeyboardInteractive(String destination,String name,String instruction, String[] prompt,boolean[] echo){
    	
    	panel = new JPanel();
    	panel.setLayout(new GridBagLayout());
 
    	gbc.weightx = 1.0;
    	gbc.gridwidth = GridBagConstraints.REMAINDER;
    	gbc.gridx = 0;
    	panel.add(new JLabel(instruction), gbc);
    	gbc.gridy++;
 
    	gbc.gridwidth = GridBagConstraints.RELATIVE;
 
    	JTextField[] texts=new JTextField[prompt.length];
    	for(int i=0; i<prompt.length; i++){
    		gbc.fill = GridBagConstraints.NONE;
    		gbc.gridx = 0;
    		gbc.weightx = 1;
    		panel.add(new JLabel(prompt[i]),gbc);
 
    		gbc.gridx = 1;
    		gbc.fill = GridBagConstraints.HORIZONTAL;
    		gbc.weighty = 1;
    		if(echo[i]){
    			texts[i]=new JTextField(20);
    		}	
    		else{
    			texts[i]=new JPasswordField(20);
    		}
    		panel.add(texts[i], gbc);
    		gbc.gridy++;
    	}
 
    	if(JOptionPane.showConfirmDialog(null, panel,destination+": "+name,JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.OK_OPTION){String[] response=new String[prompt.length];
        	for(int i=0; i<prompt.length; i++){
        		response[i]=texts[i].getText();
        	}
        	return response;
    	}
    	else{
    		return null;  // cancel
    	}
    }
  }

}
/*package com.amazon.ec2.connect;

import java.io.IOException;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.DropMode;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JPasswordField;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.BoxLayout;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JSeparator;

@SuppressWarnings("serial")
public class Connection extends JFrame{
	
	
	private JPasswordField computer_password;
	private JTextField user_name;
	private JPasswordField Password;
	private JPasswordField confirmPassword;
	private JTextField email_account;
	
	private boolean visible;
	public Connection() {
		setTitle("Registeration");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\amohamed\\Desktop\\cloud-models-100255369-orig - Copy.jpg"));
		setForeground(Color.BLUE);
		setFont(new Font("Bookman Old Style", Font.PLAIN, 12));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.GRAY);
		getContentPane().setLayout(null);
		
		JPanel data_panel = new JPanel();
		visible = data_panel.isVisible();
		
		
		data_panel.setBounds(0, 0, 404, 180);
		data_panel.setForeground(Color.BLUE);
		data_panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		data_panel.setBackground(Color.GRAY);
		getContentPane().add(data_panel);
		data_panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("computer password");
		lblNewLabel.setBounds(10, 11, 94, 14);
		lblNewLabel.setLabelFor(this);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setForeground(new Color(230, 230, 250));
		lblNewLabel.setBackground(new Color(230, 230, 250));
		data_panel.add(lblNewLabel);
		
		computer_password = new JPasswordField();
		computer_password.setBounds(193, 8, 189, 20);
		computer_password.setEchoChar('*');
		computer_password.setToolTipText("user computer password");
		data_panel.add(computer_password);
		
		JLabel lblNewLabel_2 = new JLabel("Account Access Key\r\n");
		lblNewLabel_2.setBounds(10, 36, 96, 14);
		lblNewLabel_2.setLabelFor(this);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2.setForeground(new Color(230, 230, 250));
		lblNewLabel_2.setBackground(new Color(230, 230, 250));
		data_panel.add(lblNewLabel_2);
		
		user_name = new JTextField();
		user_name.setBounds(193, 33, 189, 20);
		user_name.setToolTipText("new Account");
		data_panel.add(user_name);
		user_name.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Account Password\r\n");
		lblNewLabel_1.setBounds(10, 61, 88, 14);
		lblNewLabel_1.setLabelFor(this);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setForeground(new Color(230, 230, 250));
		lblNewLabel_1.setBackground(new Color(230, 230, 250));
		data_panel.add(lblNewLabel_1);
		
		Password = new JPasswordField();
		Password.setBounds(193, 58, 189, 20);
		Password.setToolTipText("password\r\n");
		data_panel.add(Password);
		
		JLabel lblAccountPasswordConfirmation = new JLabel("Account Password confirmation\r\n");
		lblAccountPasswordConfirmation.setHorizontalAlignment(SwingConstants.LEFT);
		lblAccountPasswordConfirmation.setForeground(new Color(230, 230, 250));
		lblAccountPasswordConfirmation.setBackground(new Color(230, 230, 250));
		lblAccountPasswordConfirmation.setBounds(10, 86, 155, 14);
		data_panel.add(lblAccountPasswordConfirmation);
		
		confirmPassword = new JPasswordField();
		confirmPassword.setToolTipText("password\r\n");
		confirmPassword.setBounds(193, 83, 189, 20);
		data_panel.add(confirmPassword);
		
		JRadioButton rdbtnStudent = new JRadioButton("Student");
		rdbtnStudent.setForeground(new Color(230, 230, 250));
		rdbtnStudent.setBackground(new Color(128, 128, 128));
		rdbtnStudent.setBounds(20, 110, 109, 23);
		data_panel.add(rdbtnStudent);
		
		JRadioButton rdbtnEmployee = new JRadioButton("Employee");
		rdbtnEmployee.setForeground(new Color(230, 230, 250));
		rdbtnEmployee.setBackground(new Color(128, 128, 128));
		rdbtnEmployee.setBounds(145, 110, 109, 23);
		data_panel.add(rdbtnEmployee);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Company");
		rdbtnNewRadioButton.setForeground(new Color(230, 230, 250));
		rdbtnNewRadioButton.setBackground(new Color(128, 128, 128));
		rdbtnNewRadioButton.setBounds(273, 110, 109, 23);
		data_panel.add(rdbtnNewRadioButton);
		
		JLabel lblEmailAccount = new JLabel("Email Account");
		lblEmailAccount.setHorizontalAlignment(SwingConstants.LEFT);
		lblEmailAccount.setForeground(new Color(230, 230, 250));
		lblEmailAccount.setBackground(new Color(230, 230, 250));
		lblEmailAccount.setBounds(10, 144, 155, 14);
		data_panel.add(lblEmailAccount);
		
		email_account = new JTextField();
		email_account.setToolTipText("email");
		email_account.setColumns(10);
		email_account.setBounds(193, 140, 189, 20);
		data_panel.add(email_account);
		data_panel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblNewLabel, computer_password, lblNewLabel_2, user_name, lblNewLabel_1, Password, lblAccountPasswordConfirmation, confirmPassword, rdbtnStudent, rdbtnEmployee, rdbtnNewRadioButton, lblEmailAccount, email_account}));
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{getContentPane()}));
	
		setLocation(new Point(0,0));
		setEnabled(true);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setToolTipText("");
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		setJMenuBar(menuBar);
		
		JButton btnRejisteration = new JButton("Rejisteration");
		menuBar.add(btnRejisteration);
		
		
		JButton btnLogin = new JButton("Login");
		menuBar.add(btnLogin);
		
		JButton btnSupport = new JButton("Support");
		menuBar.add(btnSupport);
		
		
		
		if(visible){
			btnRejisteration.setEnabled(false);
		}
		else{
			JButton btnBuy = new JButton("Buy");
			menuBar.add(btnBuy);
		}
		
		setVisible(true);
	}

	public static void main(String [] args){
		Connection j = new Connection();
	}
}
*/