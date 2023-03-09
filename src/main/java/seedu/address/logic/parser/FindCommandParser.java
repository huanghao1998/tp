package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LANGUAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROFILE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Set;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.GitHubProfile;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.person.predicate.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicate.LanguageContainsKeywordsPredicate;
import seedu.address.model.person.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicate.PersonContainsKeywordsPredicate;
import seedu.address.model.person.predicate.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.predicate.ProfileContainsKeywordsPredicate;
import seedu.address.model.person.predicate.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Language;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PROFILE, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_LANGUAGE, PREFIX_TAG);

        // ensure no text before first valid prefix
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        NameContainsKeywordsPredicate namePredicate
                = new NameContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_NAME));
        ProfileContainsKeywordsPredicate profilePredicate
                = new ProfileContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_PROFILE));
        PhoneContainsKeywordsPredicate phonePredicate
                = new PhoneContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_PHONE));
        EmailContainsKeywordsPredicate emailPredicate
                = new EmailContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_EMAIL));
        AddressContainsKeywordsPredicate addressPredicate
                = new AddressContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_ADDRESS));
        LanguageContainsKeywordsPredicate languagePredicate
                = new LanguageContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_LANGUAGE));
        TagContainsKeywordsPredicate tagPredicate
                = new TagContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_TAG));
        PersonContainsKeywordsPredicate personPredicate
                = new PersonContainsKeywordsPredicate(namePredicate,
                profilePredicate,
                phonePredicate,
                emailPredicate,
                addressPredicate,
                languagePredicate,
                tagPredicate);

        return new FindCommand(personPredicate);
    }

}
