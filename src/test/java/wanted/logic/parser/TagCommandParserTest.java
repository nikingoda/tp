package wanted.logic.parser;

import static wanted.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wanted.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static wanted.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static wanted.logic.parser.CliSyntax.PREFIX_TAG;
import static wanted.logic.parser.CommandParserTestUtil.assertParseFailure;
import static wanted.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static wanted.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import wanted.commons.core.datatypes.Index;
import wanted.logic.commands.BaseEdit;
import wanted.logic.commands.TagCommand;
import wanted.testutil.EditLoanDescriptorBuilder;

public class TagCommandParserTest {

    private static final String TAG_EMPTY = PREFIX_TAG + " ";
    private static final String INVALID_TAG_COMMAND_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);
    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_missingParts_failure() {
        //no index specified
        assertParseFailure(parser, "t/ " + VALID_TAG_FRIEND, INVALID_TAG_COMMAND_FORMAT);
        //can double check the above test after v1.5
        //name field not specified so the name is parsed as an index
        String invalidTagField = "1" + " " + VALID_TAG_FRIEND; //missing  preamble
        assertParseFailure(parser, invalidTagField, INVALID_TAG_COMMAND_FORMAT);

        //no index and no name field
        assertParseFailure(parser, " ", INVALID_TAG_COMMAND_FORMAT);

    }

    @Test
    public void parse_invalidPreamble_failure() {
        String tagDesc = " " + "t/ " + VALID_TAG_FRIEND;
        //negative index
        assertParseFailure(parser, "-5" + tagDesc, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // zero index
        assertParseFailure(parser, "0" + tagDesc, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", INVALID_TAG_COMMAND_FORMAT);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + TAG_EMPTY;

        BaseEdit.EditLoanDescriptor descriptor = new EditLoanDescriptorBuilder().withTags().build();
        TagCommand expectedCommand = new TagCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
