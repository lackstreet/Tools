package com.lackstreet.pdf.inspect.application.model;

public record PdfInfo(
        String fileName,
        long sizeBytes,
        int pageCount
) {
}
