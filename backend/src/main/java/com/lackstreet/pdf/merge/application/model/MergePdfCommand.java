package com.lackstreet.pdf.merge.application.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public record MergePdfCommand (List<Path> documents){

    public MergePdfCommand {
        Objects.requireNonNull(documents, "Documents must not be null");

        if (documents.size() < 2) {
            throw new IllegalArgumentException(
                    "At least two documents are required"
            );
        }

        if (documents.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException(
                    "Document paths must not be null"
            );
        }

        documents = List.copyOf(documents);
    }

}
