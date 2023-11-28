package fr.uge.thebigadventure.models.environments;

import fr.uge.thebigadventure.models.enums.environment.EnvironmentType;

public class EnvironmentObject {
    protected final char encoding;
    private final EnvironmentType environmentType;

    public EnvironmentObject(EnvironmentType environmentType, char encoding) {
        this.environmentType = environmentType;
        this.encoding = encoding;
    }

    public EnvironmentType getEnvironmentType() {
        return environmentType;
    }

    public char getEncoding() {
        return encoding;
    }
}
