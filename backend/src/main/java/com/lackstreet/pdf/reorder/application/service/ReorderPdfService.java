package com.lackstreet.pdf.reorder.application.service;

import com.lackstreet.pdf.reorder.application.model.ReorderPdfCommand;
import com.lackstreet.pdf.reorder.application.port.in.ReorderPdfUseCase;
import com.lackstreet.pdf.reorder.application.port.out.PdfPageReorder;

public class ReorderPdfService implements ReorderPdfUseCase {

    private final PdfPageReorder pdfPageReorder;

    public ReorderPdfService(PdfPageReorder pdfPageReorder) {
        this.pdfPageReorder = pdfPageReorder;
    }

    @Override
    public byte[] reorder(ReorderPdfCommand command) {

        if ( command == null ||
                command.document() == null ||
                command.pageOrder() == null ||
                command.pageOrder().isEmpty() ||
                command.pageOrder().stream().distinct().count()
                        != command.pageOrder().size() ||
                command.pageOrder().stream()
                        .anyMatch(page -> page == null || page < 1)
        ) {
            throw new IllegalArgumentException("Invalid command, page order is invalid");
        }
        return pdfPageReorder.reorder(
                command.document(),
                command.pageOrder()
        );

    }

}
