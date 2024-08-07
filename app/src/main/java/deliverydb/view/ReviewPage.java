package deliverydb.view;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.EmptyBorder;

import deliverydb.controller.ReviewController;
import deliverydb.data.Review;
import deliverydb.data.User;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents the page where users can view and manage reviews.
 */
public class ReviewPage {

    private final JFrame mainFrame;
    private final ReviewController controller;
    private List<Review> reviews;
    private final User.USER_TYPE userType;

    /**
     * Constructs a ReviewPage with the specified JFrame and ReviewController.
     *
     * @param mainFrame  the main frame of the application
     * @param controller the controller that handles review actions
     */
    public ReviewPage(JFrame mainFrame, ReviewController controller) {
        this.mainFrame = mainFrame;
        this.controller = controller;
        this.reviews = this.controller.getReviews(); // Load reviews initially
        this.userType = controller.getUserType();
        initializeUI();
    }

    /**
     * Refreshes the main content pane of the JFrame and applies the given consumer to it.
     *
     * @param consumer a Consumer function that modifies the content pane
     */
    private void freshPane(final Consumer<Container> consumer) {
        final Container cp = this.mainFrame.getContentPane();
        cp.removeAll();
        cp.setLayout(new BorderLayout());
        consumer.accept(cp);
        cp.validate();
        cp.repaint();
        this.mainFrame.pack();
    }

    /**
     * Initializes the user interface for the review page.
     */
    private void initializeUI() {
        freshPane(container -> {
            final JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            final JPanel buttonPanel = new JPanel();

            // Back Button
            final JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> handleBack());
            buttonPanel.add(backButton);

            // Add Review Button (only for CUSTOMER)
            if (userType == User.USER_TYPE.CUSTOMER) {
                final JButton addReviewButton = new JButton("Add Review");
                addReviewButton.addActionListener(e -> showAddReviewDialog());
                buttonPanel.add(addReviewButton);
            }

            // Create a panel to hold reviews
            final JPanel reviewsPanel = new JPanel();
            reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));
            reviewsPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding inside the panel

            // Add each review to the panel
            if (reviews != null) {
                for (final Review review : reviews) {
                    reviewsPanel.add(createReviewPanel(review));
                }
            } else {
                reviewsPanel.add(new JLabel("No reviews available"));
            }

            // Wrap the reviews panel in a scroll pane
            final JScrollPane scrollPane = new JScrollPane(reviewsPanel);
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

    /**
     * Creates a panel to display a review with details and an optional delete button.
     *
     * @param review the review to be displayed
     * @return the JPanel containing the review details
     */
    private JPanel createReviewPanel(final Review review) {
        final JPanel reviewPanel = new JPanel();
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
        reviewPanel.add(new JLabel("Review: " + review.getReviewText()));

        if (userType == User.USER_TYPE.ADMIN) {
            // Add a delete button for admin users
            final JButton deleteButton = new JButton("X");
            deleteButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
            deleteButton.addActionListener(e -> {
                final int response = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to delete this review?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    controller.deleteReview(review);
                    reviews = controller.getReviews(); // Refresh the reviews list
                    initializeUI(); // Refresh the UI to show the updated reviews
                }
            });
            reviewPanel.add(deleteButton);
        }

        reviewPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between reviews

        return reviewPanel;
    }

    /**
     * Handles the action of navigating back to the previous screen.
     */
    private void handleBack() {
        controller.handleBack(); // Assuming handleBack() will manage navigation
    }

    /**
     * Displays a dialog for adding a new review.
     */
    private void showAddReviewDialog() {
        final JDialog dialog = new JDialog(mainFrame, "Add Review", true);
        dialog.setLayout(new GridLayout(5, 2));
        dialog.setSize(400, 300);

        final JSpinner ratingSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1)); // Rating from 1 to 5
        final JTextArea reviewArea = new JTextArea();
        reviewArea.setLineWrap(true);
        reviewArea.setWrapStyleWord(true);

        dialog.add(new JLabel("Rating (1-5):"));
        dialog.add(ratingSpinner);
        dialog.add(new JLabel("Review:"));
        dialog.add(new JScrollPane(reviewArea));

        final JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            final int rating = (Integer) ratingSpinner.getValue();
            final String reviewText = reviewArea.getText();

            if (rating < 1 || rating > 5) {
                JOptionPane.showMessageDialog(dialog, "Rating must be between 1 and 5", "Invalid Rating",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            controller.addReview(rating, reviewText); // Add the review
            reviews = controller.getReviews(); // Refresh the reviews list
            initializeUI(); // Refresh the UI to show the new review
            dialog.dispose(); // Close the dialog
        });

        final JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose()); // Close the dialog

        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
