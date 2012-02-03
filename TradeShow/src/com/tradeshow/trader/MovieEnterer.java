package com.tradeshow.trader;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import com.tradeshow.interfaces.*;

/**
 * A GUI component that can be used to enter available movies
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class MovieEnterer extends JFrame implements KeyListener, TradeSubject {
	private HashSet<TradeObserver> observers;
	private JTextField textField;
	private String game;

	/**
	 * Default Constructor
	 * 
	 */
	public MovieEnterer() {
		super();
		ImageIcon icon;
		JButton endButton;
		JComponent contentPane;
		JLabel logo;

		observers = new HashSet<TradeObserver>();

		this.game = game;
		contentPane = (JComponent) getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.setBackground(Color.WHITE);

		icon = new ImageIcon("src/iconTradeShow.gif");
		setIconImage(icon.getImage());

		icon = new ImageIcon("src/logoTradeShow.gif");
		logo = new JLabel(icon);
		logo.setBackground(Color.WHITE);
		contentPane.add(logo, BorderLayout.CENTER);

		textField = new JTextField();
		textField.addKeyListener(this);
		contentPane.add(textField, BorderLayout.SOUTH);

		/*
		 * MAY BE NEDED FOR A SMOOTHER END endButton = new
		 * JButton("End TradeShow"); endButton.addActionListener(new
		 * ButtonListener()); contentPane.add(endButton, BorderLayout.SOUTH);
		 */

		setTitle("Enter movie title:");

		setSize(400, 150);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Add an observer
	 * 
	 * @param observer
	 *            The observer
	 */
	public void addObserver(TradeObserver observer) {
		observers.add(observer);
	}

	/**
	 * Handle keyPressed events (required by KeyListener)
	 * 
	 * @param evt
	 *            The KeyEvent
	 */
	public void keyPressed(KeyEvent evt) {
		int code;

		code = evt.getKeyCode();

		if (code == KeyEvent.VK_ENTER) {
			notifyObservers(textField.getText());
			textField.setText("");
		}
	}

	/**
	 * Handle keyReleased events (required by KeyListener)
	 * 
	 * @param evt
	 *            The KeyEvent
	 */
	public void keyReleased(KeyEvent evt) {
	}

	/**
	 * Handle keyTyped events (required by KeyListener)
	 * 
	 * @param evt
	 *            The KeyEvent
	 */
	public void keyTyped(KeyEvent evt) {
	}

	/**
	 * Notify all observers of an alert
	 * 
	 * @param text
	 *            A description of the alert
	 */
	public void notifyObservers(String text) {
		TradeObserver observer;
		Iterator<TradeObserver> i;

		i = observers.iterator();
		while (i.hasNext()) {
			observer = i.next();
			observer.handleAvailableMovie(text);
		}
	}

	/**
	 * Remove an observer
	 * 
	 * @param observer
	 *            The observer
	 */
	public void removeObserver(TradeObserver observer) {
		observers.remove(observer);
	}

}// MovieEnterer Class

