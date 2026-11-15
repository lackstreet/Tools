package com.lackstreet.pdf.merge.application.service;

import com.lackstreet.pdf.merge.application.model.MergePdfCommand;
import com.lackstreet.pdf.merge.application.port.in.MergePdfUseCase;
import com.lackstreet.pdf.merge.application.port.out.PdfMerger;

import java.util.Objects;

public class PdfMergeService implements MergePdfUseCase {

    private final PdfMerger pdfMerger;

    public PdfMergeService(PdfMerger pdfMerger) {
        this.pdfMerger =  Objects.requireNonNull(pdfMerger,"PdfMerger must not be null");
    }

    @Override
    public byte[] merge(MergePdfCommand command) {
        Objects.requireNonNull(command, "Command must not be null");
        return pdfMerger.merge(command.documents());
    }
}
