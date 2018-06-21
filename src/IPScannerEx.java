import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

public class IPScannerEx extends JFrame {

	int threads = 0;

	Pattern pattern;
	Matcher matcher;

	JPanel statusPanel;
	JLabel statusLabel;

	public IPScannerEx() {
		super("Network Scanner");

		// menu begin
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// add menu item

		JMenu scanMenu = new JMenu("Scan");
		JMenu gotoMenu = new JMenu("Go to");
		JMenu commandsMenu = new JMenu("Commands");
		JMenu favoritesMenu = new JMenu("Favorites");
		JMenu toolsMenu = new JMenu("Tools");
		JMenu helpMenu = new JMenu("Help");

		menuBar.add(scanMenu);
		menuBar.add(gotoMenu);
		menuBar.add(commandsMenu);
		menuBar.add(favoritesMenu);
		menuBar.add(toolsMenu);
		menuBar.add(helpMenu);

		// set scan menu

		JMenuItem loadFromFileAction = new JMenuItem("Load from file...");
		JMenuItem exportAllAction = new JMenuItem("Export all...");
		JMenuItem exportSelectionAction = new JMenuItem("Export selection...");
		JMenuItem quitAction = new JMenuItem("Quit");

		scanMenu.add(loadFromFileAction);
		scanMenu.add(exportAllAction);
		scanMenu.add(exportSelectionAction);
		scanMenu.addSeparator();
		scanMenu.add(quitAction);

		// set goto menu

		JMenuItem nextAliveHostAction = new JMenuItem("Next alive host");
		JMenuItem nextOpenPortAction = new JMenuItem("Next open port");
		JMenuItem nextDeadHostAction = new JMenuItem("Next dead host");
		JMenuItem preAliveHostAction = new JMenuItem("Previous alive host");
		JMenuItem preOpenPortAction = new JMenuItem("Previous open port");
		JMenuItem preDeadHostAction = new JMenuItem("Previous dead host");
		JMenuItem findAction = new JMenuItem("Find...");

		gotoMenu.add(nextAliveHostAction);
		gotoMenu.add(nextOpenPortAction);
		gotoMenu.add(nextDeadHostAction);
		gotoMenu.addSeparator();
		gotoMenu.add(preAliveHostAction);
		gotoMenu.add(preOpenPortAction);
		gotoMenu.add(preDeadHostAction);
		gotoMenu.addSeparator();
		gotoMenu.add(findAction);

		// set commands menu-commands

		JMenuItem showDetailsAction = new JMenuItem("Show details");
		JMenuItem rescanIPAction = new JMenuItem("Rescan IP(s)");
		JMenuItem deleteIPAction = new JMenuItem("Delete IP(s)");
		JMenuItem copyIPAction = new JMenuItem("Copy IP");
		JMenuItem copyDetailsAction = new JMenuItem("Copy Details");
		JMenu openAction = new JMenu("Open");

		commandsMenu.add(showDetailsAction);
		commandsMenu.addSeparator();
		commandsMenu.add(rescanIPAction);
		commandsMenu.add(deleteIPAction);
		commandsMenu.add(copyIPAction);
		commandsMenu.add(copyDetailsAction);
		commandsMenu.addSeparator();
		commandsMenu.add(openAction);

		// set commands menu-open

		JMenuItem editOpenersAction = new JMenuItem("Edit openers...");
		JMenuItem windowsSharesAction = new JMenuItem("Windows Shares");
		JMenuItem webBrowserAction = new JMenuItem("Web Browser");
		JMenuItem FTPAction = new JMenuItem("FTP");
		JMenuItem telnetAction = new JMenuItem("Telnet");
		JMenuItem pingAction = new JMenuItem("Ping");
		JMenuItem traceRouteAction = new JMenuItem("Trace route");
		JMenuItem geoLocateAction = new JMenuItem("Geo locate");
		JMenuItem emailSampleAction = new JMenuItem("E-mail sample");

		openAction.add(editOpenersAction);
		openAction.addSeparator();
		openAction.add(windowsSharesAction);
		openAction.add(webBrowserAction);
		openAction.add(FTPAction);
		openAction.add(telnetAction);
		openAction.add(pingAction);
		openAction.add(traceRouteAction);
		openAction.add(geoLocateAction);
		openAction.add(emailSampleAction);

		// set favorites menu

		JMenuItem addCurrentAction = new JMenuItem("Add current...");
		JMenuItem manageFavoritesAction = new JMenuItem("Manage favorites...");

		favoritesMenu.add(addCurrentAction);
		favoritesMenu.add(manageFavoritesAction);

		// set tools menu-tools

		JMenuItem preferencesAction = new JMenuItem("Preferences...");
		JMenuItem fetchersAction = new JMenuItem("Fetchers...");
		JMenu selectionAction = new JMenu("Selection");
		JMenuItem scanStatisticsAction = new JMenuItem("Scan statistics");

		toolsMenu.add(preferencesAction);
		toolsMenu.add(fetchersAction);
		toolsMenu.addSeparator();
		toolsMenu.add(selectionAction);
		toolsMenu.add(scanStatisticsAction);

		// set tools menu-selection

		JMenuItem aliveHostsAction = new JMenuItem("Alive hosts");
		JMenuItem deadHostsAction = new JMenuItem("Dead hosts");
		JMenuItem withOpenPortsAction = new JMenuItem("With open ports");
		JMenuItem withoutOpenPortsAction = new JMenuItem("Without open ports");
		JMenuItem invertSelectionAction = new JMenuItem("Invert selection");

		selectionAction.add(aliveHostsAction);
		selectionAction.add(deadHostsAction);
		selectionAction.add(withOpenPortsAction);
		selectionAction.add(withoutOpenPortsAction);
		selectionAction.addSeparator();
		selectionAction.add(invertSelectionAction);

		// set help menu

		JMenuItem gettingStartedAction = new JMenuItem("Getting Started");
		JMenuItem officialWebsiteAction = new JMenuItem("Official Website");
		JMenuItem FAQAction = new JMenuItem("FAQ");
		JMenuItem reportAnIssueAction = new JMenuItem("Report an issue");
		JMenuItem pluginsAction = new JMenuItem("Plugins");

		// set scan-quit action

		quitAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// menu end

		// ----------------------

		// status bar begin

		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		add(statusPanel, BorderLayout.SOUTH);

		JLabel readyLabel = new JLabel("Ready");
		JLabel displayLabel = new JLabel("Display All");
		JLabel threadLabel = new JLabel("Thread : " + threads);

		statusPanel.add(readyLabel);
		statusPanel.add(displayLabel);
		statusPanel.add(threadLabel);

		readyLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
		displayLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
		threadLabel.setBorder(new BevelBorder(BevelBorder.RAISED));

		readyLabel.setPreferredSize(new Dimension(300, 20));
		displayLabel.setPreferredSize(new Dimension(150, 20));
		threadLabel.setPreferredSize(new Dimension(150, 20));

		// status bar end

		// ----------------------

		// table begin

		String titles[] = new String[] { "IP", "Ping", "Hostname", "TTL", "Ports[0+]" };
		Object[][] stats = initializeTableData();

		JTable ipTable = new JTable(stats, titles);

		JScrollPane sp = new JScrollPane(ipTable);
		add(sp, BorderLayout.CENTER);

		// table end

		// ----------------------

		// toolbar begin

		String ipRangeStartText = "192.168.0.0";
		String ipRangeEndText = "192.168.3.255";

		JToolBar toolbar1 = new JToolBar();
		toolbar1.setLayout(new FlowLayout(FlowLayout.LEFT));
		JToolBar toolbar2 = new JToolBar();
		toolbar2.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel IPRangeStart = new JLabel("IP Range : ");
		JTextField tfRangeStart = new JTextField(ipRangeStartText, 10);
		JLabel IPRangeEnd = new JLabel("to");
		JTextField tfRangeEnd = new JTextField(ipRangeEndText, 10);

		tfRangeStart.setPreferredSize(new Dimension(30, 30));
		tfRangeEnd.setPreferredSize(new Dimension(90, 30));

		JLabel lbHostName = new JLabel("Hostname : ");

		String myHostname = null;
		try {
			myHostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException errhostname1) {

		}

		JTextField tfHostName = new JTextField(myHostname, 10);

		JButton buttonIP = new JButton("IP" + "ก่");

		// add buttonIP action listener
		buttonIP.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					tfRangeStart.setText(InetAddress.getLocalHost().getHostAddress());
					tfRangeEnd.setText(InetAddress.getLocalHost().getHostAddress());
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		});

		JComboBox netMaskCombo = new JComboBox();

		netMaskCombo.addItem("/24");
		netMaskCombo.addItem("/26");
		netMaskCombo.addItem("/16");

		JButton btStart = new JButton("Start");

		tfHostName.setPreferredSize(new Dimension(90, 30));
		buttonIP.setPreferredSize(new Dimension(40, 30));
		netMaskCombo.setPreferredSize(new Dimension(90, 30));
		btStart.setPreferredSize(new Dimension(90, 30));

		toolbar1.add(IPRangeStart);
		toolbar1.add(tfRangeStart);
		toolbar1.add(IPRangeEnd);
		toolbar1.add(tfRangeEnd);

		toolbar2.add(lbHostName);
		toolbar2.add(tfHostName);
		toolbar2.add(buttonIP);
		toolbar2.add(netMaskCombo);
		toolbar2.add(btStart);

		JPanel toolBarPane = new JPanel(new BorderLayout());
		toolBarPane.add(toolbar1, BorderLayout.NORTH);
		toolBarPane.add(toolbar2, BorderLayout.SOUTH);

		add(toolBarPane, BorderLayout.NORTH);

		// toolbar end

		// set default option
		setSize(700, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		// set start action

		btStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Pinging[] pg = new Pinging[254];
				for (int i = 0; i < 253; i++) {
					pg[i] = new Pinging("192.168.1." + (i + 1));
					pg[i].start();
				}
				for (int i = 0; i < 255; i++) {
					Object[] msg = pg[i].getMsg();
					if (msg[1] == null) {
						msg[1] = "n/a";
						msg[2] = "n/s";
						msg[3] = "n/s";
					} else if (msg[3] == null) {
						msg[3] = "n/a";
					}
					if (msg[1] != null) {

					}
					stats[i][0] = msg[0];
					stats[i][1] = msg[1];
					stats[i][2] = msg[2];
					stats[i][3] = msg[3];
				}
				ipTable.repaint();
			}
		});

		// start action end

	}

	public Object[][] initializeTableData() {
		Object[][] results = new Object[254][5];
		return results;
	}

	public static void main(String[] args) {
		new IPScannerEx();
	}

}
