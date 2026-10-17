package com.lackstreet.pdf.inspect.application.model;

import java.nio.file.Path;

public record InspectPdfCommand(
        String fileName,
        long sizeBytes,
        Path content
) {
}
