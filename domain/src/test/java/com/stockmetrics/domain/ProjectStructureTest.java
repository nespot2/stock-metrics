package com.stockmetrics.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectStructureTest {

    @Test
    @DisplayName("Should not include adapter security module in settings")
    void shouldNotIncludeAdapterSecurityModuleInSettings() throws IOException {
        Path settingsFile = findSettingsFile();
        String settingsContent = Files.readString(settingsFile);

        assertThat(settingsContent).doesNotContain("\"adapter:security\"");
    }

    private static Path findSettingsFile() {
        Path current = Path.of("").toAbsolutePath();
        while (current != null) {
            Path candidate = current.resolve("settings.gradle.kts");
            if (Files.exists(candidate)) {
                return candidate;
            }
            current = current.getParent();
        }
        throw new IllegalStateException("settings.gradle.kts not found");
    }
}
