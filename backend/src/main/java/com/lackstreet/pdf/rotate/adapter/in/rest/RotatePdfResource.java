package com.lackstreet.pdf.rotate.adapter.in.rest;

import com.lackstreet.pdf.rotate.application.model.RotatePdfCommand;
import com.lackstreet.pdf.rotate.application.model.RotationAngle;
import com.lackstreet.pdf.rotate.application.port.in.PdfRotateUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;

@Path("/api/pdf")
public class RotatePdfResource {

    private final PdfRotateUseCase pdfRotateUseCase;

    @Inject
    public RotatePdfResource(PdfRotateUseCase pdfRotateUseCase) {
        this.pdfRotateUseCase = pdfRotateUseCase;
    }

    @POST
    @Path("/rotate")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/pdf")
    public Response rotate(@RestForm("file") FileUpload file, @RestForm("page") List<Integer> pages, @RestForm("angle") Integer degree) {

        if (file == null || file.size() == 0 || pages == null || pages.isEmpty() || degree == null) {
            throw new BadRequestException("File, pages, and angle are required");
        }

        var command = new RotatePdfCommand(
                file.uploadedFile(),
                pages,
                RotationAngle.fromDegrees(degree)
        );

        byte[] rotatedPdf = pdfRotateUseCase.rotate(command);

        return Response.ok(rotatedPdf)
                .header("Content-Disposition", "attachment; filename=rotated.pdf")
                .build();

    }


}
