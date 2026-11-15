package com.lackstreet.pdf.rotate.adapter.out;

import com.lackstreet.pdf.rotate.application.model.RotationAngle;
import com.lackstreet.pdf.rotate.application.port.out.PdfRotatePage;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.List;

public class PdfBoxRotatePage implements PdfRotatePage {
    @Override
    public byte[] rotate(Path document, List<Integer> pages, RotationAngle angle) {
        try (PDDocument pdf = Loader.loadPDF(document.toFile());
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            for (int pageNumber : pages) {
                if (pageNumber > pdf.getNumberOfPages()) {
                    throw new IllegalArgumentException(
                            "Page " + pageNumber + " not present in the document"
                    );
                }
                var page = pdf.getPage(pageNumber - 1);

                int rotation = (page.getRotation() + angle.degrees()) % 360;
                page.setRotation(rotation);
            }
            pdf.save(outputStream);

            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new UncheckedIOException("Impossible to rotate the pages", e);
        }
    }
}
