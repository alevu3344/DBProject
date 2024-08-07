package deliverydb.view;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.EmptyBorder;

import deliverydb.controller.ReviewController;
import deliverydb.data.Review;
import deliverydb.data.User;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents the page where users can view and manage reviews.
 */
public class ReviewPage {

    private static final int REVIEW_PANEL_WIDTH = 600;
    private static final int REVIEW_PANEL_HEIGHT = 400;
    private static final int DIALOG_WIDTH = 400;
    private static final int DIALOG_HEIGHT = 300;
    private static final int MAX_RATING = 5;
    private static final int MIN_RATING = 1;
    private static final int RATING_STEP = 1;
    private static final int RATING_DEFAULT = 1;
    private static final int REVIEW_SPINNER_WIDTH = 50;
    private static final int REVIEW_SPINNER_HEIGHT = 30;
    private static final Dimension REVIEW_SPINNER_DIMENSION = new Dimension(REVIEW_SPINNER_WIDTH, REVIEW_SPINNER_HEIGHT);
    private static final Dimension RIGID_AREA_DIMENSION = new Dimension(0, 10);
    private static final int ROWS = 5;
    private static final int COLS = 2;

    private final JFrame mainFrame;
    private final ReviewController controller;
    private List<Review> reviews;
    private final User.Usertype userType;

    /**
     * Constructs a ReviewPage with the specified JFrame and ReviewController.
     *
     * @param mainFrame  the main frame of the application
     * @param controller the controller that handles review actions
     */
    public ReviewPage(final JFrame mainFrame, final ReviewController controller) {
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
            if (userType == User.Usertype.CUSTOMER) {
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
            scrollPane.setPreferredSize(new Dimension(REVIEW_PANEL_WIDTH, REVIEW_PANEL_HEIGHT)); // Adjust dimensions as needed

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

        if (userType == User.Usertype.ADMIN) {
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

        reviewPanel.add(Box.createRigidArea(RIGID_AREA_DIMENSION)); // Space between reviews

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
        dialog.setLayout(new GridLayout(ROWS, COLS));
        dialog.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);

        final JSpinner ratingSpinner = new JSpinner(new SpinnerNumberModel(RATING_DEFAULT, MIN_RATING, MAX_RATING, RATING_STEP));
        ratingSpinner.setPreferredSize(REVIEW_SPINNER_DIMENSION);
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

            if (rating < MIN_RATING || rating > MAX_RATING) {
                JOptionPane.showMessageDialog(dialog, "Rating must be between 1 and 5", "Invalid Rating",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            controller.addReview(rating, reviewArea.getText()); // Add the review
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
