package com.lackstreet.pdf.inspect.application.model;

import java.nio.file.Path;
import java.util.Objects;

public record InspectPdfCommand(
        String fileName,
        long sizeBytes,
        Path content
) {
    public InspectPdfCommand {
        Objects.requireNonNull(fileName, "File name must not be null");
        Objects.requireNonNull(content, "Document path must not be null");

        if (fileName.isBlank()) {
            throw new IllegalArgumentException("File name must not be blank");
        }

        if (sizeBytes <= 0) {
            throw new IllegalArgumentException("File size must be positive");
        }
    }
}
