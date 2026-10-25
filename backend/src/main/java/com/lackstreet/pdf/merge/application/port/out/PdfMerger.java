package com.lackstreet.pdf.merge.application.port.out;

import java.nio.file.Path;
import java.util.List;

public interface PdfMerger {
    byte[] merge(List<Path> documents);
}
