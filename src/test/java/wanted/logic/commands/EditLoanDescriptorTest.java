package wanted.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wanted.logic.commands.CommandTestUtil.NEW_DESC_AMY;
import static wanted.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static wanted.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import wanted.model.loan.exceptions.ExcessRepaymentException;
import wanted.testutil.EditLoanDescriptorBuilder;

public class EditLoanDescriptorTest {

    @Test
    public void equals() throws ExcessRepaymentException {
        // same values -> returns true
        BaseEdit.EditLoanDescriptor descriptorWithSameValues = new BaseEdit.EditLoanDescriptor(NEW_DESC_AMY);
        assertTrue(NEW_DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(NEW_DESC_AMY.equals(NEW_DESC_AMY));

        // null -> returns false
        assertFalse(NEW_DESC_AMY.equals(null));

        // different types -> returns
        // false
        assertFalse(NEW_DESC_AMY.equals(5));

        // different name -> returns false
        BaseEdit.EditLoanDescriptor editedAmy =
                new EditLoanDescriptorBuilder(NEW_DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(NEW_DESC_AMY.equals(editedAmy));

        // different amount -> returns false

        // different tags -> returns false
        editedAmy = new EditLoanDescriptorBuilder(NEW_DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(NEW_DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        BaseEdit.EditLoanDescriptor editLoanDescriptor = new BaseEdit.EditLoanDescriptor();
        String expected = BaseEdit.EditLoanDescriptor.class.getCanonicalName() + "{name="
                + editLoanDescriptor.getName().orElse(null) + ", tags="
                + editLoanDescriptor.getTags().orElse(null) + ", amount="
                + editLoanDescriptor.getAmount().orElse(null) + "}";
        assertEquals(expected, editLoanDescriptor.toString());
    }
}
