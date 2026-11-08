package com.lackstreet.pdf.extract.adapter.out;

import com.lackstreet.pdf.extract.application.port.out.PdfPageExtractor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.List;

public class PdfBoxPageExtractor implements PdfPageExtractor {
    @Override
    public byte[] extract(Path document, List<Integer> pages) {
       try (PDDocument source = Loader.loadPDF(document.toFile());
            PDDocument extracted = new PDDocument();
            ByteArrayOutputStream output = new ByteArrayOutputStream()
       ) {

           for (int pageNumber : pages) {
               if ( pageNumber > source.getNumberOfPages()){
                   throw new IllegalArgumentException("Page number " + pageNumber + " is too large");
               }
               var sourcePage = source.getPage(pageNumber - 1);
               var importedPage = extracted.importPage(sourcePage);
               importedPage.setResources(sourcePage.getResources());
           }
           extracted.save(output);
           return output.toByteArray();

       } catch (IOException e) {
           throw new UncheckedIOException("Impossible to extract pages", e);
       }
    }
}
