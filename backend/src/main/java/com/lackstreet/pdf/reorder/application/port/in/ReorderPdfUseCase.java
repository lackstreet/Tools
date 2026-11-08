package com.lackstreet.pdf.reorder.application.port.in;

import com.lackstreet.pdf.reorder.application.model.ReorderPdfCommand;

public interface ReorderPdfUseCase {

    byte[] reorder(ReorderPdfCommand command);
}
