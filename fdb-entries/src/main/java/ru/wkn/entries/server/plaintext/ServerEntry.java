package ru.wkn.entries.server.plaintext;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.wkn.entries.IPlainTextEntry;

@NoArgsConstructor
@Getter
@ToString(exclude = "id")
@EqualsAndHashCode(exclude = "id")
public class ServerEntry implements IPlainTextEntry {

    private Long id;
    @Setter
    private String url;
    @Setter
    private int port;
    @Setter
    private ProtocolType protocolType;

    public ServerEntry(String url, int port, ProtocolType protocolType) {
        this.url = url;
        this.port = port;
        this.protocolType = protocolType;
    }
}
