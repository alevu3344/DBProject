package db_lab.view;

import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Optional;

import db_lab.controller.FirstController;
import db_lab.controller.LoginController;

public class FirstPage {

    private JButton loginButton;
    private JButton registerButton;

    private Optional<FirstController> controller;
    private final JFrame mainFrame;

    // Constructor to set up the GUI components
    public FirstPage(Runnable onClose) {
        this.controller = Optional.empty();
        this.mainFrame = this.setupMainFrame(onClose);
        this.setupComponents();
        System.out.println("Constructor of First page");
    }

    private JFrame setupMainFrame(Runnable onClose) {
        JFrame frame = new JFrame("Consegne_Db");
        var padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        ((JComponent) frame.getContentPane()).setBorder(padding);
        frame.setMinimumSize(new Dimension(300, 300));
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose.run();
                System.exit(0);
            }
        });
        return frame;
    }

    public JFrame getMainFrame() {
        return this.mainFrame;
    }


    public void setController(FirstController controller) {
        this.controller = Optional.of(controller);
    }

    private void setupComponents() {
        this.mainFrame.getContentPane().removeAll();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10)); // 2 rows, 1 column, with gaps of 10px
        var padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        buttonPanel.setBorder(padding); // Add padding around the panel

        loginButton = createButton("Login");
        registerButton = createButton("Register");

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        mainFrame.add(buttonPanel, BorderLayout.CENTER);
        mainFrame.pack();
    }

    public void redraw(){
        this.setupComponents();
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font
        button.setBackground(new Color(60, 179, 113)); // Set background color (PaleGreen)
        button.setForeground(Color.WHITE); // Set text color to white
        button.setFocusPainted(false); // Remove focus border
        button.setBorderPainted(false); // Remove border
        button.setOpaque(true); // Make the button opaque for background color to show

        // Add hover effect - change background color when mouse enters
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(34, 139, 34)); // Darker green on hover (ForestGreen)
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(60, 179, 113)); // Restore original color on exit
            }
        });

        // Add action listener
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (text.equals("Login")) {
                    controller.ifPresent(ctrl -> ctrl.handleLoginButtonClick());
                } else if (text.equals("Register")) {
                    controller.ifPresent(ctrl -> ctrl.handleRegisterButtonClick());
                }
            }
        });

        return button;
    }
}
