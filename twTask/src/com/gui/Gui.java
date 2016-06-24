package com.gui;

import static com.gui.SwingConsole.run;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import com.task.*;
import com.exception.*;
import com.discount.*;

public class Gui extends JFrame {

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static PriceProcess priceProcess = new PriceProcess();
	private JLabel inputLabel = new JLabel("商品条形码列表：");
	private JLabel outputLabel = new JLabel("商品价格清单：");
	private JLabel discountLabel = new JLabel("参加优惠条形码：");
	private JTextField inputArea = new JTextField(60);
	private JTextArea outputArea = new JTextArea(8, 60);
	private JTextField discountArea = new JTextField(60);
	private String input;
	private String output;
	private String discount;

	// Save which products take part in discounts.
	private Set<String> enjoyProduct = new HashSet<>();
	private List<String> inputBarcode = new ArrayList<>();

	public Gui() {
		//ITEM000001,ITEM000001,ITEM000001,ITEM000001,ITEM000001,ITEM000003-2,ITEM000004,ITEM000004,ITEM000004
		output = "";
		input = "";	
		discount = "";

		inputArea.setText(output);
		discountArea.setText(discount);
		outputArea.setText(output);		
		//output linewrap
		outputArea.setLineWrap(true); 
		inputArea.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				input = ((JTextField) e.getSource()).getText();
				List<String> list = new ArrayList<>();
				String[] content = input.split(",");
				for (int i = 0; i < content.length; i++) {
					list.add(content[i]);
				}
				inputBarcode = list;
				output = priceProcess.calcautePrice(inputBarcode, enjoyProduct);
				outputArea.setText(output);
			}

		});

		discountArea.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				discount = ((JTextField) e.getSource()).getText();
				String[] content = discount.split(",");
				Set<String> set = new HashSet<>();
				for (int i = 0; i < content.length; i++) {
					set.add(content[i]);
				}
				enjoyProduct = set;
				output = priceProcess.calcautePrice(inputBarcode, enjoyProduct);
				outputArea.setText(output);
			}

		});
		FlowLayout layout = new FlowLayout();
		setLayout(layout);
		add(inputLabel);
		add(inputArea);
		add(discountLabel);
		add(discountArea);
		add(outputLabel);
		add(outputArea);
	}

	public static void startSystem() {
		Dimension screensize = tk.getScreenSize();
		int width = 700;
		int height = 400;

		run(new Gui(), width, height);
	}
}
