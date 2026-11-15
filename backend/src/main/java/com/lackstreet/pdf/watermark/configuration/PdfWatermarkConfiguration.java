package com.lackstreet.pdf.watermark.configuration;

import com.lackstreet.pdf.watermark.adapter.out.PdfBoxWatermarkPage;
import com.lackstreet.pdf.watermark.application.port.in.PdfWatermarkUseCase;
import com.lackstreet.pdf.watermark.application.port.out.PdfWatermarkPage;
import com.lackstreet.pdf.watermark.application.service.PdfWatermarkService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@ApplicationScoped
public class PdfWatermarkConfiguration {

    @Produces
    @Singleton
    public PdfWatermarkPage pdfWatermarkPage() {
        return new PdfBoxWatermarkPage();
    }

    @Produces
    @Singleton
    public PdfWatermarkUseCase pdfWatermarkUseCase(
            PdfWatermarkPage pdfWatermarkPage) {
        return new PdfWatermarkService(pdfWatermarkPage);
    }
}
