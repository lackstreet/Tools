package com.lackstreet.pdf.reorder.application.port.out;

import java.nio.file.Path;
import java.util.List;

public interface PdfPageReorderer {
    byte[] reorder(Path document, List<Integer> pageOrder);
}
