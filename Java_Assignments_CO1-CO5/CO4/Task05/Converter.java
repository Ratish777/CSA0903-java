import java.awt.FlowLayout;import java.awt.event.*;import javax.swing.*;import java.text.DecimalFormat;
public class Converter extends JFrame {
    private final JTextField amount=new JTextField(10);private final JComboBox<String> target=new JComboBox<>(new String[]{"USD","EUR"});private final JLabel result=new JLabel(" ");
    private static double rate(String c){return c.equals("USD")?0.012:0.011;}
    public Converter(){super("Currency Converter");setLayout(new FlowLayout());JButton convertButton=new JButton("Convert");add(new JLabel("INR"));add(amount);add(target);add(convertButton);add(result);
        ActionListener action=e->convert();convertButton.addActionListener(action);amount.addActionListener(action);setDefaultCloseOperation(EXIT_ON_CLOSE);pack();}
    private void convert(){try{double value=Double.parseDouble(amount.getText().trim());if(!Double.isFinite(value)||value<0)throw new NumberFormatException();String out=new DecimalFormat("0.00").format(value*rate((String)target.getSelectedItem()));result.setText(out);JOptionPane.showMessageDialog(this,"Converted: "+out);}catch(NumberFormatException ex){result.setText("Invalid amount");JOptionPane.showMessageDialog(this,"Invalid amount","Input error",JOptionPane.ERROR_MESSAGE);}}
    public static void main(String[] args){SwingUtilities.invokeLater(()->new Converter().setVisible(true));}
}
