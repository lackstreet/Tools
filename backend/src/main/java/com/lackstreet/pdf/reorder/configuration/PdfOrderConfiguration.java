package com.lackstreet.pdf.reorder.configuration;
import com.lackstreet.pdf.reorder.adapter.out.PdfBoxPageReorderer;
import com.lackstreet.pdf.reorder.application.port.in.ReorderPdfUseCase;
import com.lackstreet.pdf.reorder.application.port.out.PdfPageReorder;
import com.lackstreet.pdf.reorder.application.service.ReorderPdfService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@ApplicationScoped
public class PdfOrderConfiguration {

    @Produces
    @Singleton
    public PdfPageReorder pdfPageReorder() {
        return new PdfBoxPageReorderer();
    }

    @Produces
    @Singleton
    public ReorderPdfUseCase reorderPdfUseCase(PdfPageReorder pdfPageReorder) {
        return new ReorderPdfService(pdfPageReorder);
    }

}
