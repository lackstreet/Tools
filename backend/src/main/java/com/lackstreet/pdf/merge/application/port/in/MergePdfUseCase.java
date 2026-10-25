package com.lackstreet.pdf.merge.application.port.in;

import com.lackstreet.pdf.merge.application.model.MergePdfCommand;

public interface MergePdfUseCase {
    byte[] merge(MergePdfCommand command);
}
