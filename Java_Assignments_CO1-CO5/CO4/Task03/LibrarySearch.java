import javax.swing.*;import java.awt.*;
public class LibrarySearch extends JFrame {
 public LibrarySearch(){super("Campus Library Search");setLayout(new BorderLayout());JPanel top=new JPanel(new BorderLayout());top.add(new JLabel("Library Search",SwingConstants.CENTER),BorderLayout.NORTH);
  JPanel search=new JPanel(new FlowLayout());JTextField q=new JTextField(20);JComboBox<String> category=new JComboBox<>(new String[]{"All","Books","Journals"});JButton find=new JButton("Search");search.add(q);search.add(category);search.add(find);top.add(search,BorderLayout.SOUTH);add(top,BorderLayout.NORTH);
  JTextArea results=new JTextArea();results.setEditable(false);add(new JScrollPane(results),BorderLayout.CENTER);
  JPanel bottom=new JPanel(new BorderLayout());JPanel buttons=new JPanel(new FlowLayout());for(String s:new String[]{"Prev","Next","Reserve"})buttons.add(new JButton(s));bottom.add(buttons,BorderLayout.CENTER);bottom.add(new JLabel("Ready"),BorderLayout.SOUTH);add(bottom,BorderLayout.SOUTH);
  find.addActionListener(e->results.setText("Search: "+q.getText()+" / "+category.getSelectedItem()));setDefaultCloseOperation(EXIT_ON_CLOSE);setSize(650,450);}
 public static void main(String[] a){SwingUtilities.invokeLater(()->new LibrarySearch().setVisible(true));}
}
