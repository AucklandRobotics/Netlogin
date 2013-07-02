import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class NetLoginGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JLabel upititle = new JLabel("UPI:  ");
	private JLabel plantitle = new JLabel("Internet Plan:  ");
	private JLabel usagetitle = new JLabel("MBs used this month:  ");
	private JLabel statusLabel = new JLabel("Not Connected");
	private JLabel planLabel = new JLabel("");
	private JLabel usageLabel = new JLabel("");
	private JTextField loginTF = new JTextField();
	private JTextField passwordTF = new JPasswordField();
	private NetLoginPreferences p = new NetLoginPreferences();
	private JButton connectButton = new JButton("Connect...");
	private JMenuItem loginMenuItem;
	private JMenuItem changePWMenuItem;
	private JDialog loginDialog;
	private NetLoginConnection netLoginConnection = null;
	private boolean connected = false;
	private final Font globalFont = new Font("Dialog", 0, 12);
	private final Font globalTitleFont = new Font("Dialog", 1, 12);
	private final Color globalTitleColor = new Color(51, 102, 255);
	private TrayIcon trayIcon;
	private String plan_name = "";
	static String versionNumber = "3.0.4";
	static String helpURL = "http://www.ec.auckland.ac.nz/docs/net-student.htm";
	static String passwdChangeURL = "https://admin.ec.auckland.ac.nz/Passwd/";
	static String icon_imagename = "jnetlogin16x16.gif";
	static String rev="1";
	static String aboutInfo = "JNetLogin Client Version " + versionNumber + "\nCopyright(C) 2001-2010 The University of Auckland.\n" + "Release under terms of the GNU GPL. \n\nUnofficial DC Bug fix (rev"+rev+") by AURA (Kerey)";

	public NetLoginGUI()
	{
		super("JNetLoggedIn "+versionNumber+" r"+rev);
		NetLoginGUIBody();
	}

	public NetLoginGUI(String paramString1, String paramString2)
	{
		NetLoginGUIBody();
		if (this.netLoginConnection == null)
			this.netLoginConnection = new NetLoginConnection(this);
		this.netLoginConnection.setUseStaticPingPort(this.p.getUseStaticPingPort());
		this.netLoginConnection.setUsePingTimeout(this.p.getUsePingTimeout());
		try
		{
			rememberedPassword=paramString2;
			this.netLoginConnection.login(paramString1, paramString2);
			this.loginDialog.setVisible(false);
		}
		catch (IOException localIOException)
		{
			//showError(localIOException.getMessage());
		}
		this.statusLabel.setText(paramString1);
		this.loginTF.setText(paramString1);
	}

	public void NetLoginGUIBody()
	{
		if (this.netLoginConnection == null)
			this.netLoginConnection = new NetLoginConnection(this);
		makeLoginDialog();
		GridBagLayout localGridBagLayout = new GridBagLayout();
		GridBagConstraints localGridBagConstraints = new GridBagConstraints();
		JPanel localJPanel = new JPanel();
		localJPanel.setLayout(localGridBagLayout);
		localGridBagConstraints.weightx = 1.0D;
		localGridBagConstraints.weighty = 1.0D;
		localGridBagConstraints.anchor = 10;
		this.upititle.setFont(this.globalTitleFont);
		this.plantitle.setFont(this.globalTitleFont);
		this.usagetitle.setFont(this.globalTitleFont);
		this.upititle.setForeground(this.globalTitleColor);
		this.plantitle.setForeground(this.globalTitleColor);
		this.usagetitle.setForeground(this.globalTitleColor);
		this.statusLabel.setFont(this.globalFont);
		this.planLabel.setFont(this.globalFont);
		this.usageLabel.setFont(this.globalFont);
		this.connectButton.setFont(this.globalTitleFont);
		this.connectButton.setForeground(this.globalTitleColor);
		this.connectButton.setToolTipText("Login to NetAccount");
		this.connectButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramAnonymousActionEvent)
			{
				if (!NetLoginGUI.this.connected)
					NetLoginGUI.this.loginDialog.setVisible(true);
				else
					NetLoginGUI.this.disconnect();
			}
		});
		addExternal(localJPanel, localGridBagConstraints, 0, 0, this.upititle, 3, 13);
		addExternal(localJPanel, localGridBagConstraints, 0, 1, this.plantitle, 3, 13);
		addExternal(localJPanel, localGridBagConstraints, 0, 2, this.usagetitle, 3, 13);
		addExternal(localJPanel, localGridBagConstraints, 1, 0, this.statusLabel, 3, 17);
		addExternal(localJPanel, localGridBagConstraints, 1, 1, this.planLabel, 3, 17);
		addExternal(localJPanel, localGridBagConstraints, 1, 2, this.usageLabel, 3, 17);
		addExternal(localJPanel, localGridBagConstraints, 0, 3, new JSeparator(), 2, 10);
		addExternal(localJPanel, localGridBagConstraints, 1, 3, new JSeparator(), 2, 10);
		addExternal(localJPanel, localGridBagConstraints, 1, 4, this.connectButton, 0, 10);
		makeMenuBar();
		setDefaultCloseOperation(0);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent paramAnonymousWindowEvent)
			{
				NetLoginGUI.this.savePreferences();
				System.exit(0);
			}

			public void windowIconified(WindowEvent paramAnonymousWindowEvent)
			{
				if (SystemTray.isSupported())
				{
					NetLoginGUI.this.setVisible(false);
					NetLoginGUI.this.minimizeToTray();
				}
			}
		});
		setContentPane(localJPanel);
		setBounds(this.p.getMainDialogBounds());
		setBounds(12, 12, 270, 160);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(new ImageIcon(icon_imagename).getImage());
		initTrayIcon();
	}

	private void addExternal(JPanel paramJPanel, GridBagConstraints paramGridBagConstraints, int paramInt1, int paramInt2, JComponent paramJComponent, int paramInt3, int paramInt4)
	{
		paramGridBagConstraints.gridx = paramInt1;
		paramGridBagConstraints.gridy = paramInt2;
		paramGridBagConstraints.fill = paramInt3;
		paramGridBagConstraints.anchor = paramInt4;
		paramJPanel.add(paramJComponent, paramGridBagConstraints);
	}

	private void disconnect()
	{
		if (!this.connected)return;
		connected = false;
		
		this.netLoginConnection.logout();
		//netLoginConnection.resetPings();
		statusLabel.setText("Not Connected");
		planLabel.setText("");
		usageLabel.setText("");
		connectButton.setText("Connect...");
		loginMenuItem.setEnabled(true);
		changePWMenuItem.setEnabled(false);

		int timeout=0;
		while (!rememberedPassword.equals("") && !connected && timeout<5){
			timeout++;
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
			}
			
			
			netLoginConnection.setUseStaticPingPort(NetLoginGUI.this.p.getUseStaticPingPort());
			netLoginConnection.setUsePingTimeout(this.p.getUsePingTimeout());
			try
			{
				if (NetLoginGUI.this.p.getUseAltServer()){
					NetLoginGUI.this.netLoginConnection.login(NetLoginGUI.this.p.getAltServer(), NetLoginGUI.this.loginTF.getText(), rememberedPassword);
					timeout=0;
				}
				else{
					NetLoginGUI.this.netLoginConnection.login(NetLoginGUI.this.loginTF.getText(), rememberedPassword);
					timeout=0;
				}
				NetLoginGUI.this.loginDialog.setVisible(false);
			}
			catch (IOException localIOException)
			{
				//this.connected = false;
			}
			NetLoginGUI.this.passwordTF.setText("");
		}
		if (!rememberedPassword.equals("") && !connected && timeout==5){
			NetLoginGUI.this.showError("Exceded max reconnect attempts. Please try again.");
		}
	}

	private void savePreferences()
	{
		this.p.setMainDialogBounds(getBounds());
		this.p.setLoginDialogBounds(this.loginDialog.getBounds());
		this.p.savePreferences();
	}

	public void update(int paramInt, boolean paramBoolean1, boolean paramBoolean2, String paramString)
	{
		update(paramInt, paramBoolean1, paramBoolean2);
	}

	public void update(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
	{
		if (paramBoolean2)
		{
			this.connected = true;
			this.statusLabel.setText(this.loginTF.getText());
			this.connectButton.setToolTipText("Reconnect to NetAccount");
			this.connectButton.setText("Reconnect");
			this.changePWMenuItem.setEnabled(true);
			this.loginMenuItem.setEnabled(false);
		}
		else
		{
			disconnect();
		}
	}

	public void updateV3(int paramInt1, int paramInt2, boolean paramBoolean, String paramString)
	{
		if (paramBoolean)
		{
			this.connected = true;
			float f = (float)Math.round(paramInt1 / 1024.0D * 100.0D) / 100.0F;
			this.usageLabel.setText("" + f + "MBs");
			paramInt2 &= 251658240;
			switch (paramInt2)
			{
			case 16777216:
				this.plan_name = "Staff";
				break;
			case 33554432:
				this.plan_name = "Sponsored";
				break;
			case 50331648:
				this.plan_name = "Undergraduate";
				break;
			case 67108864:
				this.plan_name = "ExceededAllowance";
				break;
			case 83886080:
				this.plan_name = "NoAccess";
				break;
			case 100663296:
				this.plan_name = "Postgraduate";
				break;
			default:
				this.plan_name = "";
			}
			this.planLabel.setText(this.plan_name);
			this.statusLabel.setText(this.loginTF.getText());
			this.connectButton.setToolTipText("Reconnect to NetAccount");
			this.connectButton.setText("Reconnect");
			this.changePWMenuItem.setEnabled(true);
			this.loginMenuItem.setEnabled(false);
		}
		else
		{
			disconnect();
		}
	}

	public void showAbout()
	{
		JOptionPane.showMessageDialog(this, aboutInfo);
	}

	private String rememberedPassword="";
	
	private void makeLoginDialog()
	{
		this.loginDialog = new JDialog();
		JPanel localJPanel = new JPanel();
		GridBagLayout localGridBagLayout = new GridBagLayout();
		GridBagConstraints localGridBagConstraints = new GridBagConstraints();
		localJPanel.setLayout(localGridBagLayout);
		localGridBagConstraints.weightx = 1.0D;
		localGridBagConstraints.weighty = 1.0D;
		localGridBagConstraints.insets = new Insets(1, 1, 1, 1);
		final JButton localJButton = new JButton("Login");
		localJButton.setFont(this.globalFont);
		localJButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramAnonymousActionEvent)
			{
				NetLoginGUI.this.netLoginConnection.setUseStaticPingPort(NetLoginGUI.this.p.getUseStaticPingPort());
				netLoginConnection.setUsePingTimeout(p.getUsePingTimeout());
				try
				{
					if (NetLoginGUI.this.p.getUseAltServer())
						NetLoginGUI.this.netLoginConnection.login(NetLoginGUI.this.p.getAltServer(), NetLoginGUI.this.loginTF.getText(), rememberedPassword);
					else
						NetLoginGUI.this.netLoginConnection.login(NetLoginGUI.this.loginTF.getText(), rememberedPassword);
					NetLoginGUI.this.loginDialog.setVisible(false);
				}
				catch (IOException localIOException)
				{
					if (localIOException.getMessage().equals("Incorrect password"))
						NetLoginGUI.this.showError(localIOException.getMessage());
					
				}
				rememberedPassword=NetLoginGUI.this.passwordTF.getText();
				((JButton)paramAnonymousActionEvent.getSource()).setEnabled(false);
				NetLoginGUI.this.passwordTF.setText("");
			}
		});
		localJButton.setEnabled(false);
		this.passwordTF.addCaretListener(new CaretListener()
		{
			public void caretUpdate(CaretEvent paramAnonymousCaretEvent)
			{
				if ((!NetLoginGUI.this.passwordTF.getText().equals("")) && (!NetLoginGUI.this.loginTF.getText().equals("")))
					localJButton.setEnabled(true);
				else
					localJButton.setEnabled(false);
			}
		});
		this.loginTF.addCaretListener(new CaretListener()
		{
			public void caretUpdate(CaretEvent paramAnonymousCaretEvent)
			{
				if ((!NetLoginGUI.this.passwordTF.getText().equals("")) && (!NetLoginGUI.this.loginTF.getText().equals("")))
					localJButton.setEnabled(true);
				else
					localJButton.setEnabled(false);
			}
		});
		this.passwordTF.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramAnonymousActionEvent)
			{
				NetLoginGUI.this.netLoginConnection.setUseStaticPingPort(NetLoginGUI.this.p.getUseStaticPingPort());
				netLoginConnection.setUsePingTimeout(p.getUsePingTimeout());
				try
				{
					if (NetLoginGUI.this.p.getUseAltServer())
						NetLoginGUI.this.netLoginConnection.login(NetLoginGUI.this.p.getAltServer(), NetLoginGUI.this.loginTF.getText(), NetLoginGUI.this.passwordTF.getText());
					else
						NetLoginGUI.this.netLoginConnection.login(NetLoginGUI.this.loginTF.getText(), NetLoginGUI.this.passwordTF.getText());
					NetLoginGUI.this.loginDialog.setVisible(false);
				}
				catch (IOException localIOException)
				{
					if (localIOException.getMessage().equals("Incorrect password"))
						NetLoginGUI.this.showError(localIOException.getMessage());
				}
				rememberedPassword=NetLoginGUI.this.passwordTF.getText();
				localJButton.setEnabled(false);
				NetLoginGUI.this.passwordTF.setText("");
			}
		});
		JLabel localJLabel = new JLabel("UPI:");
		localJLabel.setFont(this.globalFont);
		addExternal(localJPanel, localGridBagConstraints, 0, 0, localJLabel, 3, 13);
		localGridBagConstraints.weightx = 5.0D;
		addExternal(localJPanel, localGridBagConstraints, 1, 0, this.loginTF, 1, 17);
		localGridBagConstraints.weightx = 5.0D;
		localJLabel = new JLabel("Password:");
		localJLabel.setFont(new Font("Dialog", 0, 12));
		addExternal(localJPanel, localGridBagConstraints, 0, 1, localJLabel, 3, 13);
		addExternal(localJPanel, localGridBagConstraints, 1, 1, this.passwordTF, 1, 17);
		addExternal(localJPanel, localGridBagConstraints, 1, 2, localJButton, 0, 10);
		localJButton.setSelected(true);
		this.loginDialog.setContentPane(localJPanel);
		this.loginDialog.setTitle("Login");
		this.loginDialog.setVisible(false);
		this.loginDialog.setBounds(this.p.getLoginDialogBounds());
		this.loginDialog.setLocationRelativeTo(null);
		this.loginDialog.setResizable(false);
		this.loginDialog.setIconImage(new ImageIcon(icon_imagename).getImage());
		
	}

	private void showError(String paramString)
	{
		JOptionPane.showMessageDialog(this, "JNetLogin - " + paramString);
		disconnect();
	}

	private void makeMenuBar()
	{
		JMenu localJMenu1 = new JMenu("NetLogin");
		JMenu localJMenu2 = new JMenu("Help");
		JMenuItem localJMenuItem = new JMenuItem("Login");
		localJMenu1.setFont(this.globalFont);
		localJMenu2.setFont(this.globalFont);
		localJMenuItem.setFont(this.globalFont);
		localJMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramAnonymousActionEvent)
			{
				NetLoginGUI.this.loginDialog.setVisible(true);
				NetLoginGUI.this.loginDialog.setBounds(NetLoginGUI.this.p.getLoginDialogBounds());
				NetLoginGUI.this.loginDialog.setLocationRelativeTo(null);
			}
		});
		localJMenu1.add(localJMenuItem);
		this.loginMenuItem = localJMenuItem;
		localJMenuItem = new JMenuItem("Preferences");
		localJMenuItem.setFont(this.globalFont);
		localJMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramAnonymousActionEvent)
			{
				NetLoginGUI.this.p.showPreferencesDialog();
			}
		});
		localJMenu1.add(localJMenuItem);
		localJMenuItem = new JMenuItem("Change Password");
		localJMenuItem.setFont(this.globalFont);
		localJMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramAnonymousActionEvent)
			{
				NetLoginGUI.openURL(NetLoginGUI.passwdChangeURL);
			}
		});
		localJMenuItem.setEnabled(false);
		this.changePWMenuItem = localJMenuItem;
		localJMenu1.add(localJMenuItem);
		localJMenu1.addSeparator();
		localJMenuItem = new JMenuItem("Quit");
		localJMenuItem.setFont(this.globalFont);
		localJMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramAnonymousActionEvent)
			{
				NetLoginGUI.this.savePreferences();
				System.exit(0);
			}
		});
		localJMenu1.add(localJMenuItem);
		localJMenuItem = new JMenuItem("About");
		localJMenuItem.setFont(this.globalFont);
		localJMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramAnonymousActionEvent)
			{
				NetLoginGUI.this.showAbout();
			}
		});
		localJMenu2.add(localJMenuItem);
		localJMenuItem = new JMenuItem("Show Charge Rates...");
		localJMenuItem.setFont(this.globalFont);
		localJMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramAnonymousActionEvent)
			{
				NetLoginGUI.openURL(NetLoginGUI.helpURL);
			}
		});
		localJMenu2.add(localJMenuItem);
		JMenuBar localJMenuBar = new JMenuBar();
		localJMenuBar.add(localJMenu1);
		localJMenuBar.add(localJMenu2);
		setJMenuBar(localJMenuBar);
	}

	public static void openURL(String paramString)
	{
		String str = System.getProperty("os.name");
		try
		{
			Object localObject;
			Method localMethod;
			if (str.startsWith("Mac"))
			{
				localObject = Class.forName("com.apple.eio.FileManager");
				localMethod = ((Class)localObject).getDeclaredMethod("openURL", new Class[] { String.class });
				localMethod.invoke(null, new Object[] { paramString });
			}

			if(str.startsWith("Windows"))
			{
				Runtime.getRuntime().exec((new StringBuilder()).append("rundll32 url.dll,FileProtocolHandler ").append(paramString).toString());
			} else
			{
				String as[] = {
						"firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape"
				};
				String s2 = null;
				for(int i = 0; i < as.length && s2 == null; i++)
					if(Runtime.getRuntime().exec(new String[] {
							"which", as[i]
					}).waitFor() == 0)
						s2 = as[i];

				if(s2 == null)
					throw new Exception("Could not find web browser");
				Runtime.getRuntime().exec(new String[] {
						s2, paramString
				});
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}

	public void minimizeToTray()
	{
		String str = "Status:Discounnted";
		if (this.connected)
			str = "Status:Connected InternetPlan:" + this.plan_name;
		SystemTray localSystemTray = SystemTray.getSystemTray();
		try
		{
			localSystemTray.add(this.trayIcon);
			this.trayIcon.setToolTip(str);
		}
		catch (Exception localException)
		{
			System.out.println("add trayIcon error" + localException);
		}
	}

	private void initTrayIcon()
	{
		Image localImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource(icon_imagename));
		PopupMenu localPopupMenu = new PopupMenu();
		MouseListener local13 = new MouseListener()
		{
			public void mouseClicked(MouseEvent paramAnonymousMouseEvent)
			{
				SystemTray.getSystemTray().remove(NetLoginGUI.this.trayIcon);
				NetLoginGUI.this.setState(0);
				NetLoginGUI.this.setVisible(true);
				NetLoginGUI.this.toFront();
			}

			public void mouseEntered(MouseEvent paramAnonymousMouseEvent)
			{
			}

			public void mouseExited(MouseEvent paramAnonymousMouseEvent)
			{
			}

			public void mousePressed(MouseEvent paramAnonymousMouseEvent)
			{
			}

			public void mouseReleased(MouseEvent paramAnonymousMouseEvent)
			{
			}
		};
		MenuItem localMenuItem1 = new MenuItem("Help");
		ActionListener local14 = new ActionListener()
		{
			public void actionPerformed(ActionEvent paramAnonymousActionEvent)
			{
				NetLoginGUI.openURL(NetLoginGUI.helpURL);
			}
		};
		localMenuItem1.addActionListener(local14);
		MenuItem localMenuItem2 = new MenuItem("Show Charge Rates");
		ActionListener local15 = new ActionListener()
		{
			public void actionPerformed(ActionEvent paramAnonymousActionEvent)
			{
				NetLoginGUI.openURL(NetLoginGUI.helpURL);
			}
		};
		localMenuItem2.addActionListener(local15);
		MenuItem localMenuItem3 = new MenuItem("Change Password");
		ActionListener local16 = new ActionListener()
		{
			public void actionPerformed(ActionEvent paramAnonymousActionEvent)
			{
				NetLoginGUI.openURL(NetLoginGUI.passwdChangeURL);
			}
		};
		localMenuItem3.addActionListener(local16);
		MenuItem localMenuItem4 = new MenuItem("Open JNetLogin");
		ActionListener local17 = new ActionListener()
		{
			public void actionPerformed(ActionEvent paramAnonymousActionEvent)
			{
				SystemTray.getSystemTray().remove(NetLoginGUI.this.trayIcon);
				NetLoginGUI.this.setState(0);
				NetLoginGUI.this.setVisible(true);
				NetLoginGUI.this.toFront();
			}
		};
		localMenuItem4.addActionListener(local17);
		MenuItem localMenuItem5 = new MenuItem("Exit");
		ActionListener local18 = new ActionListener()
		{
			public void actionPerformed(ActionEvent paramAnonymousActionEvent)
			{
				System.exit(0);
			}
		};
		localMenuItem5.addActionListener(local18);
		localPopupMenu.add(localMenuItem1);
		localPopupMenu.addSeparator();
		localPopupMenu.add(localMenuItem2);
		localPopupMenu.add(localMenuItem3);
		localPopupMenu.addSeparator();
		localPopupMenu.add(localMenuItem4);
		localPopupMenu.add(localMenuItem5);
		this.trayIcon = new TrayIcon(localImage, "MyIcon", localPopupMenu);
		this.trayIcon.setImageAutoSize(true);
		this.trayIcon.addMouseListener(local13);
	}
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     NetLoginGUI
 * JD-Core Version:    0.6.2
 */