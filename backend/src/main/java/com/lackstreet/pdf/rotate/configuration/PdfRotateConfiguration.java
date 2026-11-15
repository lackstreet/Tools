package com.lackstreet.pdf.rotate.configuration;

import com.lackstreet.pdf.rotate.adapter.out.PdfBoxRotatePage;
import com.lackstreet.pdf.rotate.application.port.in.PdfRotateUseCase;
import com.lackstreet.pdf.rotate.application.port.out.PdfRotatePage;
import com.lackstreet.pdf.rotate.application.service.PdfRotateService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@ApplicationScoped
public class PdfRotateConfiguration {

    @Produces
    @Singleton
    public PdfRotatePage pdfRotatePage() {
        return new PdfBoxRotatePage();
    }

    @Produces
    @Singleton
    public PdfRotateUseCase pdfRotateUseCase(PdfRotatePage pdfRotatePage) {
        return new PdfRotateService(pdfRotatePage);
    }

}
