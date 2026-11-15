package com.lackstreet.pdf.reorder.adapter.in.rest;

import com.lackstreet.pdf.reorder.application.model.ReorderPdfCommand;
import com.lackstreet.pdf.reorder.application.port.in.ReorderPdfUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;

@Path("api/pdf")
public class ReorderPdfResource {

    private final ReorderPdfUseCase reorderPdfUseCase;

    @Inject
    public ReorderPdfResource(ReorderPdfUseCase reorderPdfUseCase) {
        this.reorderPdfUseCase = reorderPdfUseCase;
    }

    @POST
    @Path("/reorder")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/pdf")
    public Response reorder(
            @RestForm("file") FileUpload file,
            @RestForm("pageOrder") List<Integer> pageOrder) {

        if (file == null
                || file.size() == 0
                || pageOrder == null
                || pageOrder.isEmpty()) {
            throw new BadRequestException("Files and pages are required");

        }

        ReorderPdfCommand command = new ReorderPdfCommand(file.uploadedFile(), pageOrder);
        byte[] reorderPdf = reorderPdfUseCase.reorder(command);

        return Response.ok(reorderPdf, "application/pdf")
                .header(
                        "Content-Disposition",
                        "attachment; filename=\"reordered.pdf\""
                )
                .build();

    }
}
