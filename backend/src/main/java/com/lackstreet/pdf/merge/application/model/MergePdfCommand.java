package com.lackstreet.pdf.merge.application.model;

import java.nio.file.Path;
import java.util.List;

public record MergePdfCommand (List<Path> documents){}
