import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class NetLoginPreferences
{
	private Properties p = new Properties();
	private final Font globalFont = new Font("Dialog", 0, 12);

	public NetLoginPreferences()
	{
		loadProperties();
	}

	private void loadProperties()
	{
		this.p.put("mainDialogX", "50");
		this.p.put("mainDialogY", "50");
		this.p.put("mainDialogWidth", "150");
		this.p.put("mainDialogHeight", "110");
		this.p.put("loginDialogX", "100");
		this.p.put("loginDialogY", "100");
		this.p.put("loginDialogWidth", "200");
		this.p.put("loginDialogHeight", "130");
		this.p.put("useAltServer", "false");
		this.p.put("altServer", "gate.ec.auckland.ac.nz");
		this.p.put("useStaticPingPort", "false");
		this.p.put("usePingTimeout", "true");
		if (System.getProperty("os.name").equals("Linux"))
		{
			this.p.put("mainDialogHeight", "85");
			this.p.put("loginDialogHeight", "80");
		}
		else if (System.getProperty("os.name").equals("Mac OS X"))
		{
		}
		else//windows
		{
		}
	}

	public void savePreferences()
	{
	}

	public void showPreferencesDialog()
	{
		final JDialog localJDialog = new JDialog();
		JPanel localJPanel = new JPanel();
		GridBagLayout localGridBagLayout = new GridBagLayout();
		GridBagConstraints localGridBagConstraints = new GridBagConstraints();
		localJPanel.setLayout(localGridBagLayout);
		localGridBagConstraints.weightx = 1.0D;
		localGridBagConstraints.weighty = 1.0D;
		localGridBagConstraints.insets = new Insets(1, 1, 1, 1);
		final JCheckBox localJCheckBox1 = new JCheckBox("Alternate server", this.p.getProperty("useAltServer").equals("true"));
		localJCheckBox1.setFont(this.globalFont);
		final JTextField localJTextField = new JTextField(this.p.getProperty("altServer"));
		final JCheckBox localJCheckBox2 = new JCheckBox("Static ping port", this.p.getProperty("useStaticPingPort").equals("true"));
		localJCheckBox2.setFont(this.globalFont);

		final JCheckBox localJCheckBox3 = new JCheckBox("No ping timeout", this.p.getProperty("usePingTimeout").equals("true"));
		localJCheckBox3.setFont(this.globalFont);

		localJCheckBox1.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent paramAnonymousChangeEvent)
			{
				if (((JCheckBox)paramAnonymousChangeEvent.getSource()).isSelected())
					localJTextField.setEnabled(true);
				else
					localJTextField.setEnabled(false);
			}
		});
		addExternal(localJPanel, localGridBagConstraints, 0, 0, localJCheckBox1, 0, 17);
		localGridBagConstraints.weightx = 7.0D;
		addExternal(localJPanel, localGridBagConstraints, 1, 0, localJTextField, 2, 17);
		localGridBagConstraints.weightx = 1.0D;
		addExternal(localJPanel, localGridBagConstraints, 0, 1, localJCheckBox2, 0, 17);
		addExternal(localJPanel, localGridBagConstraints, 1, 1, localJCheckBox3, 0, 17);
		JButton localJButton1 = new JButton("OK");
		localJButton1.setFont(this.globalFont);
		localJButton1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramAnonymousActionEvent)
			{
				p.put("useStaticPingPort", localJCheckBox2.isSelected() ? "true" : "false");
				p.put("useAltServer", localJCheckBox1.isSelected() ? "true" : "false");
				p.put("usePingTimeout", localJCheckBox3.isSelected() ? "true" : "false");
				p.put("altServer", localJTextField.getText());
				localJDialog.dispose();
			}
		});
		addExternal(localJPanel, localGridBagConstraints, 0, 2, localJButton1, 0, 13);
		JButton localJButton2 = new JButton("Cancel");
		localJButton2.setFont(this.globalFont);
		localJButton2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramAnonymousActionEvent)
			{
				localJDialog.dispose();
			}
		});
		addExternal(localJPanel, localGridBagConstraints, 1, 2, localJButton2, 0, 13);
		localJDialog.setContentPane(localJPanel);
		localJDialog.setBounds(100, 100, 300, 130);
		localJDialog.setTitle("JNetLogin - Preferences");
		localJDialog.setVisible(true);
		localJDialog.setLocationRelativeTo(null);
	}

	private void addExternal(JPanel paramJPanel, GridBagConstraints paramGridBagConstraints, int paramInt1, int paramInt2, JComponent paramJComponent, int paramInt3, int paramInt4)
	{
		paramGridBagConstraints.gridx = paramInt1;
		paramGridBagConstraints.gridy = paramInt2;
		paramGridBagConstraints.fill = paramInt3;
		paramGridBagConstraints.anchor = paramInt4;
		paramJPanel.add(paramJComponent, paramGridBagConstraints);
	}

	public boolean getUseAltServer()
	{
		return this.p.getProperty("useAltServer").equals("true");
	}

	public String getAltServer()
	{
		return this.p.getProperty("altServer");
	}

	public boolean getUseStaticPingPort()
	{
		return this.p.getProperty("useStaticPingPort").equals("true");
	}

	public boolean getUsePingTimeout()
	{
		return !this.p.getProperty("usePingTimeout").equals("true");
	}

	public void setMainDialogBounds(Rectangle paramRectangle)
	{
		this.p.put("mainDialogX", "" + paramRectangle.x);
		this.p.put("mainDialogY", "" + paramRectangle.y);
		this.p.put("mainDialogWidth", "" + paramRectangle.width);
		this.p.put("mainDialogHeight", "" + paramRectangle.height);
	}

	public Rectangle getMainDialogBounds()
	{
		return new Rectangle(Integer.parseInt(this.p.getProperty("mainDialogX")), Integer.parseInt(this.p.getProperty("mainDialogY")), Integer.parseInt(this.p.getProperty("mainDialogWidth")), Integer.parseInt(this.p.getProperty("mainDialogHeight")));
	}

	public void setLoginDialogBounds(Rectangle paramRectangle)
	{
		this.p.put("loginDialogX", "" + paramRectangle.x);
		this.p.put("loginDialogY", "" + paramRectangle.y);
		this.p.put("loginDialogWidth", "" + paramRectangle.width);
		this.p.put("loginDialogHeight", "" + paramRectangle.height);
	}

	public Rectangle getLoginDialogBounds()
	{
		return new Rectangle(Integer.parseInt(this.p.getProperty("loginDialogX")), Integer.parseInt(this.p.getProperty("loginDialogY")), Integer.parseInt(this.p.getProperty("loginDialogWidth")), Integer.parseInt(this.p.getProperty("loginDialogHeight")));
	}
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     NetLoginPreferences
 * JD-Core Version:    0.6.2
 */