package com.lackstreet.pdf.extract.application.service;

import com.lackstreet.pdf.extract.application.model.ExtractPdfCommand;
import com.lackstreet.pdf.extract.application.port.in.ExtractPdfUseCase;
import com.lackstreet.pdf.extract.application.port.out.PdfPageExtractor;

public class ExtractPdfService implements ExtractPdfUseCase {

    private final PdfPageExtractor pdfPageExtractor;

    public ExtractPdfService(PdfPageExtractor pdfPageExtractor) {
        this.pdfPageExtractor = pdfPageExtractor;
    }

    @Override
    public byte[] extract(ExtractPdfCommand command) {
        if (command == null
                || command.document() == null
                || command.pages() == null
                || command.pages().isEmpty())
        {
            throw new IllegalArgumentException("command or command.document() or command.pages() is null");
        }
        if (command.pages().stream()
                .anyMatch(page -> page == null || page < 1)) {
            throw new IllegalArgumentException("pages cannot be null or empty");
        }

        var pages = command.pages().stream()
                .distinct()
                .sorted()
                .toList();
        return pdfPageExtractor.extract(command.document(), pages);
    }
}
