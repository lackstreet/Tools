package com.lackstreet.pdf.reorder.application.model;

import java.nio.file.Path;
import java.util.List;

public record ReorderPdfCommand(
        Path document,
        List<Integer> pageOrder
) {
}
