package com.lackstreet.pdf.reorder.application.service;

import com.lackstreet.pdf.reorder.application.model.ReorderPdfCommand;
import com.lackstreet.pdf.reorder.application.port.in.ReorderPdfUseCase;
import com.lackstreet.pdf.reorder.application.port.out.PdfPageReorder;

import java.util.Objects;

public class ReorderPdfService implements ReorderPdfUseCase {

    private final PdfPageReorder pdfPageReorder;

    public ReorderPdfService(PdfPageReorder pdfPageReorder) {
        this.pdfPageReorder = Objects.requireNonNull(
                pdfPageReorder,
                "PdfPageReorder must not be null"
        );
    }

    @Override
    public byte[] reorder(ReorderPdfCommand command) {

        Objects.requireNonNull(command, "Command must not be null");

        return pdfPageReorder.reorder(
                command.document(),
                command.pages()
        );

    }

}
