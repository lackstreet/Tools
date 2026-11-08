package com.lackstreet.pdf.extract.application.port.in;

import com.lackstreet.pdf.extract.application.model.ExtractPdfCommand;

public interface ExtractPdfUseCase {
    byte[] extract(ExtractPdfCommand command);
}
