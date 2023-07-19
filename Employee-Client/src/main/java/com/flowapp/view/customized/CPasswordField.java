package com.flowapp.view.customized;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JPasswordField;


public class CPasswordField extends JPasswordField {
    private String defaultText;


    public CPasswordField() {
        super();

        this.defaultText = "";
        setupFocusListener();
    }

    public CPasswordField(String defaultText) {
        super(defaultText);

        this.defaultText = defaultText;
        setupFocusListener();
    }

    public CPasswordField(String defaultText, int column) {
        super(defaultText, column);

        this.defaultText = defaultText;
        setupFocusListener();
    }

    private void setupFocusListener() {
        setEchoChar((char) 0);
        setForeground(Color.GRAY);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                String text = String.valueOf(getPassword());

                if (text.equalsIgnoreCase(defaultText)) {
                    setText("");
                    setEchoChar('*');
                }

                setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = String.valueOf(getPassword());

                if (text.equalsIgnoreCase("")) {
                    setEchoChar((char) 0);

                    setText(defaultText);
                    setForeground(Color.GRAY);

                    return;
                }

                setForeground(Color.BLACK);
            }
        });
    }
    
    // Reset the field when the clear button is pressed
    public void reset (){
        setEchoChar((char) 0);
        setText(defaultText);
        setForeground(Color.GRAY);
    }

    // Calling this method when populating the fields of a form...
    public void populate(String text){
        setEchoChar('*');
        setText(text);
        setForeground(Color.BLACK);
    }

    // Check if the field value is different from the default text... Will be used when logging in.
    public boolean isTextDifferent() {
        String text = String.valueOf(getPassword());

        return !text.equalsIgnoreCase(defaultText);
    }
}
