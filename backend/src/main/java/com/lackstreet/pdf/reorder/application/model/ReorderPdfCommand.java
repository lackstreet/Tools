package com.lackstreet.pdf.reorder.application.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public record ReorderPdfCommand(
        Path document,
        List<Integer> pages
) {
    public ReorderPdfCommand {

        Objects.requireNonNull(document, "Document path cannot be null");
        Objects.requireNonNull(pages, "Pages list must not be null");

        if (pages.isEmpty()) {
            throw new IllegalArgumentException("Pages list must not be empty");
        }

        if (pages.stream().anyMatch(p -> p == null || p < 1)) {
            throw new IllegalArgumentException("Pages numbers must be positive integers");
        }

        if (pages.stream().distinct().count() != pages.size()) {
            throw new IllegalArgumentException(
                    "Pages order must not contain duplicates"
            );
        }

        pages = List.copyOf(pages);
    }
}
