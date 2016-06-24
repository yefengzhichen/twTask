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
	private JTextArea  inputArea = new JTextArea(4,20);
	private JTextArea  outputArea = new JTextArea(8,20);
	private JTextArea  discountArea = new JTextArea(4,20);
	private String input;
	private String output;
	private String discount;
	
	//Save  which products take part in discounts. 
	private Set<String> enjoyProduct = new HashSet<>();
	
	public Gui() {
		input="ITEM000001,ITEM000001,ITEM000001,ITEM000001,"
				+ "ITEM000001,ITEM000003-2,ITEM000005,ITEM000005,ITEM000005";
		output="";
		discount="ITEM000001,ITEM000002,ITEM000003,ITEM000004,ITEM000005";
		inputArea.setText(output);
		outputArea.setText(output);
		discountArea.setText(discount);
		inputArea.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub			
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub			
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				int length = e.getDocument().getLength();
				try {
					input = e.getDocument().getText(0, length);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<String> list = new ArrayList<>();
				String[] content = input.split(",");
				for(int i=0;i<content.length;i++){
					list.add(content[i]);
				}
				output = priceProcess.calcautePrice(list, enjoyProduct);
				outputArea.setText(output);
			}		
		});
		discountArea.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub				
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub				
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				int length = e.getDocument().getLength();
				try {
					discount = e.getDocument().getText(0, length);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<String> list = new ArrayList<>();
				String[] content = discount.split(",");
				for(int i=0;i<content.length;i++){
					enjoyProduct.add(content[i]);
				}
				output = priceProcess.calcautePrice(list,enjoyProduct);
				outputArea.setText(output);
			}		
		});
		FlowLayout layout = new FlowLayout();
	    setLayout(layout);
		add(inputLabel);
		add(inputArea);
		add(outputLabel);
		add(outputArea);
		add(discountLabel);
		add(discountArea);		
	}

	
	public static void startSystem() {
		Dimension screensize = tk.getScreenSize();
		int width = screensize.width/2;
		int height = screensize.height/2;
		
		run(new Gui(), width, height);
	}
}


