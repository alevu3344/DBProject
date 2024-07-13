package deliveryDB.view;

import deliveryDB.controller.ReviewController;
import deliveryDB.data.Review;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

public class ReviewPage {

    private final JFrame mainFrame;
    private final ReviewController controller;
    private List<Review> reviews;

    public ReviewPage(JFrame mainFrame, ReviewController controller) {
        this.mainFrame = mainFrame;
        this.controller = controller;
        this.reviews = this.controller.getReviews(); // Load reviews initially
        this.initializeUI();
    }

    private void initializeUI() {
        freshPane(container -> {
            // Create a main panel to hold the content
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            // Create a panel for the back button
            JPanel buttonPanel = new JPanel();
            JButton backButton = new JButton("Back");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleBack();
                }
            });
            buttonPanel.add(backButton);

            // Create a panel to hold reviews
            JPanel reviewsPanel = new JPanel();
            reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));
            reviewsPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding inside the panel

            // Add each review to the panel
            if (reviews != null) {
                for (Review review : reviews) {
                    reviewsPanel.add(createReviewPanel(review));
                }
            } else {
                reviewsPanel.add(new JLabel("No reviews available"));
            }

            // Wrap the reviews panel in a scroll pane
            JScrollPane scrollPane = new JScrollPane(reviewsPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            // Set a preferred size for the scroll pane
            scrollPane.setPreferredSize(new Dimension(600, 400)); // Adjust dimensions as needed

            // Add the scroll pane and button panel to the main panel
            mainPanel.add(buttonPanel, BorderLayout.NORTH);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            // Add the main panel to the container
            container.add(mainPanel);
        });
    }

    private JPanel createReviewPanel(Review review) {
        JPanel reviewPanel = new JPanel();
        reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));

        // Set border to make the panel stand out
        reviewPanel.setBorder(new CompoundBorder(
            new EmptyBorder(10, 10, 10, 10), // Padding inside the border
            new EtchedBorder() // Outline border
        ));

        // Add review details
        reviewPanel.add(new JLabel("Author: " + review.getAuthor()));
        reviewPanel.add(new JLabel("Rating: " + review.getStars() + " stars"));
        reviewPanel.add(new JLabel("Date: " + review.getDate()));
        reviewPanel.add(new JLabel("Review: " + review.getReview()));
        reviewPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between reviews

        return reviewPanel;
    }

    private void freshPane(Consumer<Container> consumer) {
        Container cp = this.mainFrame.getContentPane();
        cp.removeAll();
        cp.setLayout(new BorderLayout());
        consumer.accept(cp);
        cp.validate();
        cp.repaint();
        this.mainFrame.pack();
    }

    private void handleBack() {
        this.controller.handleBack(); // Assuming handleBack() will manage navigation
    }
}
