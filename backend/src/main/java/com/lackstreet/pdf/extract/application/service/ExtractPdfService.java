package com.lackstreet.pdf.extract.application.service;

import com.lackstreet.pdf.extract.application.model.ExtractPdfCommand;
import com.lackstreet.pdf.extract.application.port.in.ExtractPdfUseCase;
import com.lackstreet.pdf.extract.application.port.out.PdfPageExtractor;

import java.util.Objects;

public class ExtractPdfService implements ExtractPdfUseCase {

    private final PdfPageExtractor pdfPageExtractor;

    public ExtractPdfService(PdfPageExtractor pdfPageExtractor) {
        this.pdfPageExtractor = Objects.requireNonNull(
                pdfPageExtractor,
                "PdfPageExtractor must not be null"
        );
    }

    @Override
    public byte[] extract(ExtractPdfCommand command) {
        Objects.requireNonNull(command, "Command must not be null");

        return pdfPageExtractor.extract(
                command.document(),
                command.pages()
        );
    }
}
