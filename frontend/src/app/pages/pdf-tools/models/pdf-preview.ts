export interface PdfThumbnail {
  pageNumber: number;
  image: string;
}

export interface PdfPreviewState {
  pageCount: number;
  thumbnails: PdfThumbnail[];
}
