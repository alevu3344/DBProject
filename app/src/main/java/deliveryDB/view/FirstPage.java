package deliveryDB.view;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import deliveryDB.controller.FirstController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Optional;

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
        frame.setMinimumSize(new Dimension(600, 600));
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
        this.mainFrame.getContentPane().validate();
        this.mainFrame.getContentPane().repaint();
        this.mainFrame.setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around buttons

        loginButton = createButton("Login");
        registerButton = createButton("Register");

        buttonPanel.add(loginButton, gbc);

        gbc.gridy++;
        buttonPanel.add(registerButton, gbc);

        // Add the buttonPanel to the center of the main frame
        mainFrame.add(buttonPanel, BorderLayout.CENTER);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null); // Center the frame on the screen
    }

    public void redraw() {
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
        button.setPreferredSize(new Dimension(150, 40)); // Set a smaller preferred size for the buttons

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
