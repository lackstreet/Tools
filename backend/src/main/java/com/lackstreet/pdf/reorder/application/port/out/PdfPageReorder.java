package com.lackstreet.pdf.reorder.application.port.out;

import java.nio.file.Path;
import java.util.List;

public interface PdfPageReorder {
    byte[] reorder(Path document, List<Integer> pageOrder);
}
