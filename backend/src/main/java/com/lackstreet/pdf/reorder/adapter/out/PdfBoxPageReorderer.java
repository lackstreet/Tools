package com.lackstreet.pdf.reorder.adapter.out;
import com.lackstreet.pdf.reorder.application.port.out.PdfPageReorder;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.List;

public class PdfBoxPageReorderer implements PdfPageReorder {
    @Override
    public byte[] reorder(Path document, List<Integer> pages) {
        try (PDDocument source = Loader.loadPDF(document.toFile());
             PDDocument reordered = new PDDocument();
             ByteArrayOutputStream output = new ByteArrayOutputStream()
        ) {
            if (pages.size() != source.getNumberOfPages()) {
                throw new IllegalArgumentException(
                        "You must include all pages in the reorder"
                );
            }
            for (int pageNumber : pages) {
                if ( pageNumber > source.getNumberOfPages()){
                    throw new IllegalArgumentException("Page number " + pageNumber + " is too large");
                }
                var sourcePage = source.getPage(pageNumber - 1);
                var importedPage = reordered.importPage(sourcePage);
                importedPage.setResources(sourcePage.getResources());
            }
            reordered.save(output);
            return output.toByteArray();

        } catch (IOException e) {
            throw new UncheckedIOException("Impossible to reorder pages", e);
        }
    }
}