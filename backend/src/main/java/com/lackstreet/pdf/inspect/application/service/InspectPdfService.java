package com.lackstreet.pdf.inspect.application.service;

import com.lackstreet.pdf.inspect.application.model.InspectPdfCommand;
import com.lackstreet.pdf.inspect.application.model.PdfInfo;
import com.lackstreet.pdf.inspect.application.port.in.InspectPdfUseCase;
import com.lackstreet.pdf.inspect.application.port.out.PdfInspector;

public class InspectPdfService implements InspectPdfUseCase {
   private final PdfInspector pdfInspector;

    public InspectPdfService(PdfInspector pdfInspector) {
        this.pdfInspector = pdfInspector;
    }

    @Override
    public PdfInfo inspect(InspectPdfCommand command) {
        if (command == null) {
            throw new NullPointerException("command is null");
        }
        return new PdfInfo(
                command.fileName(),
                command.sizeBytes(),
                pdfInspector.countPages(command.content())
        );
    }
}
