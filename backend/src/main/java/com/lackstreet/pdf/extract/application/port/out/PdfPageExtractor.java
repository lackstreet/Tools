package com.lackstreet.pdf.extract.application.port.out;

import java.nio.file.Path;
import java.util.List;

public interface PdfPageExtractor {
    byte[] extract(Path document, List<Integer> pages);
}
