package com.lackstreet.pdf.merge.adapter.in.rest;

import com.lackstreet.pdf.merge.application.port.in.MergePdfUseCase;

import com.lackstreet.pdf.merge.application.model.MergePdfCommand;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;

@Path("/api/pdf")
public class MergePdfResource {

    private final MergePdfUseCase mergePdfUseCase;

    @Inject
    public MergePdfResource(MergePdfUseCase mergePdfUseCase) {
        this.mergePdfUseCase = mergePdfUseCase;
    }

    @POST
    @Path("/merge")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/pdf")
    public Response merge(@RestForm("files")List<FileUpload> files){
        if(files == null || files.size() < 2){
            throw new BadRequestException("Not enough files for merging.");
        }
        var documents = files.stream()
                .map(FileUpload::uploadedFile)
                .toList();

        byte [] mergePdf = mergePdfUseCase.merge(new MergePdfCommand(documents));
        return Response.ok(mergePdf,"application/pdf")
                .header(
                        "Content-Disposition",
                        "attachment; filename=\"merged.pdf\""
                ).build();
    }

}
