import { Component, inject, OnDestroy, signal, computed} from '@angular/core';
import { Subscription } from 'rxjs';
import { PdfPreview } from '../services/pdf-preview';
import { PdfThumbnail } from '../models/pdf-preview';
import { ReorderPdfResourceService } from '../../../api';

@Component({
  imports: [],
  selector: 'app-reorder-pdf',
  styleUrl: './reorder-pdf.css',
  templateUrl: './reorder-pdf.html',
})
export class ReorderPdf implements OnDestroy {
  protected readonly selectedFile = signal<File | null>(null);
  protected readonly pageCount = signal<number | null>(null);
  protected readonly previewError = signal<string | null>(null);

  protected readonly thumbnails = signal<PdfThumbnail[]>([]);

  protected readonly previewReady = computed(() => {
    const count = this.pageCount();

    return count !== null && this.thumbnails().length === count && this.previewError() === null;
  });

  private readonly pdfPreview = inject(PdfPreview);
  private previewSubscription?: Subscription;
  private readonly pdfReorder = inject(ReorderPdfResourceService);

  protected readonly reordering = signal(false);
  protected readonly reorderError = signal<string | null>(null);

  protected onFileSelected(event: Event): void {
    this.previewSubscription?.unsubscribe();

    const input = event.target as HTMLInputElement;
    const file = input.files?.item(0) ?? null;

    this.selectedFile.set(file);
    this.pageCount.set(null);
    this.previewError.set(null);
    this.thumbnails.set([]);
    this.reorderError.set(null);

    if (!file) {
      return;
    }

    this.previewSubscription = this.pdfPreview.preview(file).subscribe({
      next: (state) => {
        this.pageCount.set(state.pageCount);
        this.thumbnails.set(state.thumbnails);
      },
      error: () => {
        this.previewError.set('Impossibile leggere il PDF.');
      },
    });
  }

  protected movePage(index: number, direction: -1 | 1): void {
    if (!this.previewReady() || this.reordering()) {
      return;
    }

    this.thumbnails.update((current) => {
      const target = index + direction;

      if (index < 0 || index >= current.length || target < 0 || target >= current.length) {
        return current;
      }

      const reordered = [...current];
      [reordered[index], reordered[target]] = [reordered[target], reordered[index]];

      return reordered;
    });
  }

  protected reorderPdf(): void {
    const file = this.selectedFile();

    if (!file || !this.previewReady() || this.reordering()) {
      return;
    }

    const pageOrder = this.thumbnails().map((page) => page.pageNumber);

    this.reorderError.set(null);
    this.reordering.set(true);

    this.pdfReorder.apiPdfReorderPost(file, pageOrder).subscribe({
      next: (pdf: Blob) => {
        const url = URL.createObjectURL(pdf);
        const link = document.createElement('a');

        link.href = url;
        link.download = 'reordered.pdf';
        link.click();

        setTimeout(() => URL.revokeObjectURL(url), 1000);
        this.reordering.set(false);
      },
      error: () => {
        this.reorderError.set('Impossibile riordinare il PDF.');
        this.reordering.set(false);
      },
    });
  }

  protected movePageTo(index: number, destination: 'start' | 'end'): void {
    if (!this.previewReady() || this.reordering()) {
      return;
    }

    this.thumbnails.update((current) => {
      if (index < 0 || index >= current.length) {
        return current;
      }

      const reordered = [...current];
      const [page] = reordered.splice(index, 1);

      if (destination === 'start') {
        reordered.unshift(page);
      } else {
        reordered.push(page);
      }

      return reordered;
    });
  }

  ngOnDestroy(): void {
    this.previewSubscription?.unsubscribe();
  }
}
