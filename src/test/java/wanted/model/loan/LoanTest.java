package wanted.model.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wanted.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static wanted.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static wanted.testutil.Assert.assertThrows;
import static wanted.testutil.TypicalPersons.ALICE;
import static wanted.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import wanted.testutil.PersonBuilder;

public class LoanTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Loan person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSameLoan() {
        // same object -> returns true
        assertTrue(ALICE.isSameLoan(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameLoan(null));
        //removed assert true when loan has the same name

        //Loan editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        // different name, all other attributes same -> returns false
        Loan editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameLoan(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Loan editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameLoan(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameLoan(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Loan aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different loan -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Loan editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different amount -> returns false

        //different date -> returns false

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Loan.class.getCanonicalName() + "{name=" + ALICE.getName() + ", amount=" + ALICE.getAmount()
                + ", date=" + ALICE.getLoanDate() + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
