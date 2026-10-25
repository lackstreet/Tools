package com.lackstreet.pdf.merge.adapter.out;

import com.lackstreet.pdf.merge.application.port.out.PdfMerger;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.List;

public class PdfBoxMerger implements PdfMerger {
    @Override
    public byte[] merge(List<Path> documents) {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            PDFMergerUtility merger = new PDFMergerUtility();

            for (Path document: documents) {
                merger.addSource(document.toFile());
            }

            merger.setDestinationStream(output);
            merger.mergeDocuments(IOUtils.createMemoryOnlyStreamCache());

            return output.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException("Merge failed, please try again or contact admin", e);
        }
    }
}
