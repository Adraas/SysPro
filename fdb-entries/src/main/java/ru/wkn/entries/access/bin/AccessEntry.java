package ru.wkn.entries.access.bin;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.wkn.entries.IEntry;
import ru.wkn.entries.ParametersDelimiter;

/**
 * The class {@code AccessEntry} represents access information.
 *
 * @see IEntry
 * @author Dinar Mahmutov
 */
@NoArgsConstructor
@Getter
@ToString(exclude = "id")
@EqualsAndHashCode(exclude = "id")
public class AccessEntry implements IEntry {

    /**
     * Access ID.
     */
    private Long id;

    /**
     * Access login.
     */
    private String login;

    /**
     * Access password's hash-code.
     */
    private String passwordHashCode;

    /**
     * Access e-mail.
     */
    private String email;

    /**
     * Initializes a newly created {@code AccessEntry} object.
     *
     * @param login {@link #login}
     * @param passwordHashCode {@link #passwordHashCode}
     * @param email {@link #email}
     */
    public AccessEntry(String login, String passwordHashCode, String email) {
        this.login = login;
        this.passwordHashCode = passwordHashCode;
        this.email = email;
    }

    /**
     * @see IEntry#singleLineRecording()
     */
    @Override
    public String singleLineRecording() {
        return login.concat(ParametersDelimiter.ACCESS_BIN_DELIMITER.getParametersDelimiter())
                .concat(passwordHashCode).concat(ParametersDelimiter.ACCESS_BIN_DELIMITER.getParametersDelimiter())
                .concat(email);
    }
}
