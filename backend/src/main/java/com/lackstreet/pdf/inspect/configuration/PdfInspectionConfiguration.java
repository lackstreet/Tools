package com.lackstreet.pdf.inspect.configuration;

import com.lackstreet.pdf.inspect.adapter.out.PdfBoxInspector;
import com.lackstreet.pdf.inspect.application.port.in.InspectPdfUseCase;
import com.lackstreet.pdf.inspect.application.port.out.PdfInspector;
import com.lackstreet.pdf.inspect.application.service.InspectPdfService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@ApplicationScoped
public class PdfInspectionConfiguration {

    @Produces
    @Singleton
    public PdfInspector pdfInspector() {
        return new PdfBoxInspector();
    }

    @Produces
    @Singleton
    public InspectPdfUseCase inspectPdfUseCase(PdfInspector pdfInspector) {
        return new InspectPdfService(pdfInspector);
    }
}