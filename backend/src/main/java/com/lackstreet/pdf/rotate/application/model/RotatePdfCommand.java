package com.lackstreet.pdf.rotate.application.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public record RotatePdfCommand(
        Path document,
        List<Integer> pages,
        RotationAngle angle
) {
    public RotatePdfCommand {
        Objects.requireNonNull(document, "Document path cannot be null");
        Objects.requireNonNull(pages, "Pages list must not be null");
        Objects.requireNonNull(angle, "Rotation angle must not be null");

        if (pages.isEmpty()) {
            throw new IllegalArgumentException("Pages list must not be empty");
        }
        if (pages.stream().anyMatch(p -> p == null || p < 1)) {
            throw new IllegalArgumentException("Page numbers must be positive integers");
        }
        pages = pages.stream().distinct().toList();
    }
}
