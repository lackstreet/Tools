package com.lackstreet.pdf.extract.application.model;

import java.nio.file.Path;
import java.util.List;

public record ExtractPdfCommand(
        Path document,
        List<Integer> pages
) {
}
