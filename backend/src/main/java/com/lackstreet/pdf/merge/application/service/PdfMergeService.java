package com.lackstreet.pdf.merge.application.service;

import com.lackstreet.pdf.merge.application.model.MergePdfCommand;
import com.lackstreet.pdf.merge.application.port.in.MergePdfUseCase;
import com.lackstreet.pdf.merge.application.port.out.PdfMerger;

public class PdfMergeService implements MergePdfUseCase {

    private final PdfMerger pdfMerger;

    public PdfMergeService(PdfMerger pdfMerger) {
        this.pdfMerger = pdfMerger;
    }

    @Override
    public byte[] merge(MergePdfCommand command) {
        if (command == null
                || command.documents() == null
                || command.documents().size() < 2) {
            throw new IllegalArgumentException(
                    "Needs at least two documents to merge."
            );
        }
        return pdfMerger.merge(command.documents());
    }
}
