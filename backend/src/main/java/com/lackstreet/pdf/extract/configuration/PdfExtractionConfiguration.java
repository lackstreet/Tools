package com.lackstreet.pdf.extract.configuration;

import com.lackstreet.pdf.extract.adapter.out.PdfBoxPageExtractor;
import com.lackstreet.pdf.extract.application.port.in.ExtractPdfUseCase;
import com.lackstreet.pdf.extract.application.port.out.PdfPageExtractor;
import com.lackstreet.pdf.extract.application.service.ExtractPdfService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@ApplicationScoped
public class PdfExtractionConfiguration {
    @Produces
    @Singleton
    public PdfPageExtractor pdfPageExtractor(){
        return new PdfBoxPageExtractor();
    }


    @Produces
    @Singleton
    public ExtractPdfUseCase extractPdfUseCase(PdfPageExtractor pdfPageExtractor){
        return new ExtractPdfService(pdfPageExtractor);
    }
}
