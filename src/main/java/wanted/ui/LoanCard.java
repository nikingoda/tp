package wanted.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import wanted.model.loan.Loan;
import wanted.model.loan.Phone;

/**
 * An UI component that displays information of a {@code Loan}.
 */
public class LoanCard extends UiPart<Region> {

    private static final String FXML = "LoanListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on LoanBook level 4</a>
     */

    public final Loan loan;
    private boolean isReturned;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;
    @FXML
    private Label amount;
    @FXML
    private Label phone;
    @FXML
    private Label date;
    @FXML
    private Label status;
    @FXML
    private VBox transactionsBox;
    /**
     * Creates a {@code PersonCode} with the given {@code Loan} and index to display.
     */
    public LoanCard(Loan loan, int displayedIndex) {
        super(FXML);

        this.loan = loan;
        isReturned = this.loan.getLoanAmount().isRepaid();
        id.setText(displayedIndex + ". ");
        status.setText(getStatus());
        name.setText(loan.getName().fullName);
        amount.setText("Loan Amount: " + loan.getLoanAmount().getRemainingAmount()
                .getStringRepresentationWithFixedDecimalPoint());

        if (loan.getPhone() != null && !loan.getPhone().equals(Phone.EMPTY_PHONE)) {
            this.phone.setText("Phone number: " + loan.getPhone().getValue());
        } else {
            this.phone.setText("No phone number available");
        }
        // date.setText("Loan Date: " + loan.getLoanDate().toString());
        // Sort tags alphabetically and display them
        loan.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        transactionsBox.getStyleClass().add("transaction-box");
        loan.getLoanAmount().getTransactionHistoryCopy().stream().forEach(transaction -> {
            Label txnLabel = new Label("• " + transaction.getExplanation());
            txnLabel.getStyleClass().add("transaction-label");
            transactionsBox.getChildren().add(txnLabel);
        });
        updateBackground();
    }

    private void updateBackground() {
        if (isReturned) {
            cardPane.setStyle("-fx-background-image: url('/images/papyrus.png');"
                    + "-fx-background-size: cover;");
        } else {
            cardPane.setStyle("-fx-background-image: url('/images/blood_splatter_background.png');"
                    + "-fx-background-size: cover;");
        }
    }

    private String getStatus() {
        status.getStyleClass().removeAll("status-wanted", "status-not-wanted");

        if (isReturned) {
            status.getStyleClass().add("status-not-wanted");
            return "Not Wanted";
        } else {
            status.getStyleClass().add("status-wanted");
            return "Wanted";
        }
    }

}
