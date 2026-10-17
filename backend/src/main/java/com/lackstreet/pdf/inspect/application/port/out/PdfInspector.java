package com.lackstreet.pdf.inspect.application.port.out;

import com.lackstreet.pdf.inspect.application.model.PdfInfo;

import java.nio.file.Path;

public interface PdfInspector {
    int countPages(Path content);
}
