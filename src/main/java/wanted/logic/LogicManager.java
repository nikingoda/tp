package wanted.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import wanted.commons.core.GuiSettings;
import wanted.commons.core.LogsCenter;
import wanted.logic.commands.Command;
import wanted.logic.commands.CommandResult;
import wanted.logic.commands.exceptions.CommandException;
import wanted.logic.parser.LoanBookParser;
import wanted.logic.parser.exceptions.ParseException;
import wanted.model.Model;
import wanted.model.ReadOnlyLoanBook;
import wanted.model.loan.Loan;
import wanted.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final LoanBookParser loanBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        loanBookParser = new LoanBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = loanBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveLoanBook(model.getLoanBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyLoanBook getLoanBook() {
        return model.getLoanBook();
    }

    @Override
    public ObservableList<Loan> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getLoanBookFilePath() {
        return model.getLoanBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
