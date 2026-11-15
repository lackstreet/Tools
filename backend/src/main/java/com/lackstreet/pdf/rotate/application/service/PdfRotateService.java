package com.lackstreet.pdf.rotate.application.service;

import com.lackstreet.pdf.rotate.application.model.RotatePdfCommand;
import com.lackstreet.pdf.rotate.application.port.in.PdfRotateUseCase;
import com.lackstreet.pdf.rotate.application.port.out.PdfRotatePage;

import java.util.Objects;

public class PdfRotateService implements PdfRotateUseCase {

    private final PdfRotatePage pdfRotatePage;

    public PdfRotateService(PdfRotatePage pdfRotatePage) {
        this.pdfRotatePage = Objects.requireNonNull(pdfRotatePage, "PdfRotatePage must not be null");
    }

    @Override
    public byte[] rotate(RotatePdfCommand command) {
        Objects.requireNonNull(command, "Command must not be null");
        return pdfRotatePage.rotate(command.document(), command.pages(), command.angle());
    }
}
