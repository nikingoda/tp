package wanted.logic.parser;

import static wanted.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static wanted.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wanted.commons.core.LogsCenter;
import wanted.logic.commands.AddCommand;
import wanted.logic.commands.ClearCommand;
import wanted.logic.commands.Command;
import wanted.logic.commands.DeleteCommand;
import wanted.logic.commands.DelhistCommand;
import wanted.logic.commands.EdithistCommand;
import wanted.logic.commands.ExitCommand;
import wanted.logic.commands.FindCommand;
import wanted.logic.commands.HelpCommand;
import wanted.logic.commands.IncreaseCommand;
import wanted.logic.commands.ListCommand;
import wanted.logic.commands.PhoneCommand;
import wanted.logic.commands.RenameCommand;
import wanted.logic.commands.RepayCommand;
import wanted.logic.commands.SortCommand;
import wanted.logic.commands.TagCommand;
import wanted.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class LoanBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(LoanBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EdithistCommand.COMMAND_WORD:
            return new EdithistCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case IncreaseCommand.COMMAND_WORD:
            return new IncreaseCommandParser().parse(arguments);

        case RepayCommand.COMMAND_WORD:
            return new RepayCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case DelhistCommand.COMMAND_WORD:
            return new DelhistCommandParser().parse(arguments);

        case PhoneCommand.COMMAND_WORD:
            return new PhoneCommandParser().parse(arguments);

        case RenameCommand.COMMAND_WORD:
            return new RenameCommandParser().parse(arguments);

        case TagCommand.COMMAND_WORD:
            return new TagCommandParser().parse(arguments);
        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
