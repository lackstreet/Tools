package com.lackstreet.pdf.rotate.application.port.out;

import com.lackstreet.pdf.rotate.application.model.RotationAngle;

import java.nio.file.Path;
import java.util.List;

public interface PdfRotatePage {
    byte[] rotate(Path document, List<Integer> pages, RotationAngle angle);
}
