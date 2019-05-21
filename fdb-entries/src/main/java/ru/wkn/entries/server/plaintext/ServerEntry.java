package ru.wkn.entries.server.plaintext;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.wkn.entries.IEntry;
import ru.wkn.entries.ParametersDelimiter;

/**
 * Class {@code ServerEntry} represents network server information.
 *
 * @see IEntry
 * @author Alexey Konev
 */
@NoArgsConstructor
@Getter
@ToString(exclude = "id")
@EqualsAndHashCode(exclude = "id")
public class ServerEntry implements IEntry {

    /**
     * Network server ID.
     */
    private Long id;

    /**
     * Network server URL.
     */
    @Setter
    private String url;

    /**
     * Network server port.
     */
    @Setter
    private int port;

    /**
     * Network server protocol type.
     */
    @Setter
    private ProtocolType protocolType;

    /**
     * Initializes a newly created {@code ServerEntry} object.
     *
     * @param url {@link ServerEntry#url}
     * @param port {@link ServerEntry#port}
     * @param protocolType {@link ServerEntry#protocolType}
     */
    public ServerEntry(String url, int port, ProtocolType protocolType) {
        this.url = url;
        this.port = port;
        this.protocolType = protocolType;
    }

    /**
     * @see IEntry#singleLineRecording()
     */
    @Override
    public String singleLineRecording() {
        return url.concat(ParametersDelimiter.SERVER_PLAIN_TEXT_DELIMITER.getParametersDelimiter())
                .concat(String.valueOf(port)).concat(ParametersDelimiter
                        .SERVER_PLAIN_TEXT_DELIMITER.getParametersDelimiter())
                .concat(protocolType.getProtocolType());
    }
}
