package com.lackstreet.pdf.merge.configuration;

import com.lackstreet.pdf.merge.adapter.out.PdfBoxMerger;
import com.lackstreet.pdf.merge.application.port.in.MergePdfUseCase;
import com.lackstreet.pdf.merge.application.port.out.PdfMerger;
import com.lackstreet.pdf.merge.application.service.PdfMergeService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@ApplicationScoped
public class PdfMergeConfiguration {

    @Produces
    @Singleton
    public PdfMerger pdfMerger() {
        return new PdfBoxMerger();
    }

    @Produces
    @Singleton
    public MergePdfUseCase mergePdfUseCase(PdfMerger pdfMerger) {
        return new PdfMergeService(pdfMerger);
    }
}
