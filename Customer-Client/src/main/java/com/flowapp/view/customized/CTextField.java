package com.flowapp.view.customized;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;



public class CTextField extends JTextField {
    private String defaultText;


    public CTextField() {
        super();

        this.defaultText = "";

        setupFocusListener();
    }

    public CTextField(String defaultText) {
        super(defaultText);

        this.defaultText = defaultText;

        setupFocusListener();
    }

    public CTextField(String defaultText, int column) {
        super(defaultText, column);

        this.defaultText = defaultText;

        setupFocusListener();
    }

    private void setupFocusListener() {
        setForeground(Color.GRAY);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equalsIgnoreCase(defaultText)) {
                    setText("");
                }

                setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().equalsIgnoreCase("")) {
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
        setText(defaultText);
        setForeground(Color.GRAY);
    }

    // Calling this method when populating the fields of a form...
    public void populate(String text){
        setText(text);
        setForeground(Color.BLACK);
    }

    // Check if the field value is different from the default text... Will be used when logging in.
    public boolean isTextDifferent() {
        String text = getText();

        return !text.equalsIgnoreCase(defaultText);
    }
}
