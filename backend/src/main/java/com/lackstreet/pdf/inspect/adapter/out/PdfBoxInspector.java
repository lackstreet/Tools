package com.lackstreet.pdf.inspect.adapter.out;

import com.lackstreet.pdf.inspect.application.exception.PdfInspectionException;
import com.lackstreet.pdf.inspect.application.port.out.PdfInspector;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.nio.file.Path;

public class PdfBoxInspector implements PdfInspector {
    @Override
    public int countPages(Path content) {
        try(PDDocument document = Loader.loadPDF(content.toFile())) {
            return document.getNumberOfPages();
        } catch (IOException e){
            throw new PdfInspectionException("Error while reading content from pdf inspector.", e);
        }
    }
}
