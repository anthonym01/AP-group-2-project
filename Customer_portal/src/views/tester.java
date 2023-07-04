package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class tester extends JFrame implements ActionListener {
    JFrame testview;

    public tester(){
        //create  test view
        testview = new JFrame("testview", null);
        testview.setSize(1300, 700);

        //testview.setVisible(true);
        testview.setLayout(null);
        testview.setLocationRelativeTo(null);
        testview.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testview.setSize(1280, 750);

        JLabel placeholder = new JLabel("test change");
        placeholder.setBounds(90, 15, 100, 30);
        testview.add(placeholder);
    }

    public JFrame getviewFrame(){
        return testview;
    }    

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}
