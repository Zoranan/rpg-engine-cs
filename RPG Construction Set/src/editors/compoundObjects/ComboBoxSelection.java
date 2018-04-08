package editors.compoundObjects;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.SpringLayout;
import javax.swing.text.JTextComponent;

/**A Labeled ComboBox
 * @author Will
 *
 */
public class ComboBoxSelection extends LabeledTextBox {
	private JComboBox<String> comboBox;
	private DefaultComboBoxModel<String> model;
	private Action onChangeAction;
	/**
	 * Create a labeled ComboBox
	 * @param labelTxt The text to be displayed to the left of this component on the form.
	 * @param nodeName The name of the xml node that this component is associated with.
	 * @param options The options to be populated into the JComboBox. 
	 */
	public ComboBoxSelection(String labelTxt, String nodeName, String[] options)
	{
		super (labelTxt, nodeName);
		height = 28;
		//replace the text box
		this.remove(txtSelectionField);
		
		comboBox = new JComboBox<String>();
		JTextComponent editor = ((JTextComponent)comboBox.getEditor().getEditorComponent());
		editor.addKeyListener(inputMask);
		springLayout.putConstraint(SpringLayout.NORTH, comboBox, -3, SpringLayout.NORTH, lblLabel);
		springLayout.putConstraint(SpringLayout.WEST, comboBox, 6, SpringLayout.EAST, lblLabel);
		springLayout.putConstraint(SpringLayout.EAST, comboBox, 120, SpringLayout.EAST, lblLabel);
		
		model = new DefaultComboBoxModel<String>();
		setOptions(options);
		comboBox.setModel(model);
		
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (onChangeAction != null && e.getStateChange() == ItemEvent.SELECTED)
					onChangeAction.action();
			}	
		});
		
		add(comboBox);
	}
	
	public ComboBoxSelection(String labelTxt, String nodeName)
	{
		this (labelTxt, nodeName, new String[0]);
	}
	
	public void addActionListener(ActionListener al)
	{
		this.comboBox.addActionListener(al);
	}
	
	public void setSelectionChangeAction(Action a)
	{
		this.onChangeAction = a;
	}
	
	public void setOptions(String[] options)
	{
		model.removeAllElements();
		
		for (int i=0; i<options.length; i++)
			model.addElement(options[i]);
	}
	
	public void setOptions(ArrayList<String> options)
	{
		model.removeAllElements();
		
		for (int i=0; i<options.size(); i++)
			model.addElement(options.get(i));
	}
	
	public void setEditable(boolean b)
	{
		comboBox.setEditable(b);
	}
	
	@Override
	public void setEnabled(boolean b)
	{
		comboBox.setEnabled(b);
	}
	
	@Override
	public void setValue(String s)
	{
		if (comboBox.isEditable())
			comboBox.getEditor().setItem(s);
		else
			setSelection(s);
	}
	
	//Attempts to set the selection to one that exists in the combo box model
	//If an empty string is passed in, we select the first option
	public void setSelection(String s)
	{
		if (s.isEmpty() || s == null)
			clear();
		else
			for (int i = 0; i < model.getSize(); i++)
			{
				if (model.getElementAt(i).equals(s))
				{
					comboBox.setSelectedIndex(i);
					return;
				}
			}
		//If we are here, we need to add the item to our combo box
		//Recursive
		if (s.isEmpty() == false)
		{
			model.addElement(s);
			setSelection(s);
		}
	}
	
	@Override
	public void clear()
	{
		if (model.getSize() > 0)
			comboBox.setSelectedIndex(0);
	}
	
	@Override
	public String getValue()
	{	
		return (String) this.comboBox.getSelectedItem();
	}

}
