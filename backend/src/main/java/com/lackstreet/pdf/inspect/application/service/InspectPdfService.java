package com.lackstreet.pdf.inspect.application.service;

import com.lackstreet.pdf.inspect.application.model.InspectPdfCommand;
import com.lackstreet.pdf.inspect.application.model.PdfInfo;
import com.lackstreet.pdf.inspect.application.port.in.InspectPdfUseCase;
import com.lackstreet.pdf.inspect.application.port.out.PdfInspector;

import java.util.Objects;

public class InspectPdfService implements InspectPdfUseCase {
   private final PdfInspector pdfInspector;

    public InspectPdfService(PdfInspector pdfInspector) {
        this.pdfInspector = Objects.requireNonNull(
                pdfInspector,
                "PdfInspector must not be null"
        );
    }

    @Override
    public PdfInfo inspect(InspectPdfCommand command) {
        Objects.requireNonNull(command, "Command must not be null");

        return new PdfInfo(
                command.fileName(),
                command.sizeBytes(),
                pdfInspector.countPages(command.content())
        );
    }
}
