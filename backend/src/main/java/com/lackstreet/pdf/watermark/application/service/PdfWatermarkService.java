package com.lackstreet.pdf.watermark.application.service;

import com.lackstreet.pdf.watermark.application.model.WatermarkPdfCommand;
import com.lackstreet.pdf.watermark.application.port.in.PdfWatermarkUseCase;
import com.lackstreet.pdf.watermark.application.port.out.PdfWatermarkPage;

import java.util.Objects;

public class PdfWatermarkService implements PdfWatermarkUseCase {
    private final PdfWatermarkPage pdfWatermarkPage;

    public PdfWatermarkService(PdfWatermarkPage pdfWatermarkPage) {
        this.pdfWatermarkPage = Objects.requireNonNull(
                pdfWatermarkPage,
                "PdfWatermarkPage must not be null"
        );
    }

    @Override
    public byte[] watermark(WatermarkPdfCommand command) {
        Objects.requireNonNull(command, "Command must not be null");

        return pdfWatermarkPage.watermark(
                command.document(),
                command.text(),
                command.opacity(),
                command.layout()
        );
    }
}
