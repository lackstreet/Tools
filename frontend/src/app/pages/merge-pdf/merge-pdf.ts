import { Component, inject, signal } from '@angular/core';
import { MergePdfResourceService } from '../../api';

@Component({
  imports: [],
  selector: 'app-merge-pdf',
  styleUrl: './merge-pdf.css',
  templateUrl: './merge-pdf.html',
})
export class MergePdf {
  protected readonly files = signal<File[]>([]);
  protected readonly pdfMerge = inject(MergePdfResourceService);

  protected readonly merging = signal(false);
  protected readonly mergeError = signal<string | null>(null);

  protected onFilesSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const selected = Array.from(input.files ?? []);

    if (selected.length <= 0) {
      return;
    }

    this.files.update((currentFiles) => [...currentFiles, ...selected]);
    input.value = '';
    this.mergeError.set(null);
  }

  protected removeFile(index: number): void {
    this.files.update((currentFiles) => currentFiles.filter((_, i) => i !== index));
  }

  protected moveFile(index: number, direction: -1 | 1): void {
    this.files.update((currentFiles) => {
      const target = index + direction;

      if (target < 0 || target >= currentFiles.length) {
        return currentFiles;
      }

      const reordered = [...currentFiles];

      [reordered[index], reordered[target]] = [reordered[target], reordered[index]];


      return reordered;
    });
  }

  protected mergePdf(): void {
    const files = this.files();
    if (files.length < 2) {
      return;
    }

    this.merging.set(true);
    this.mergeError.set(null);

    this.pdfMerge.apiPdfMergePost(files).subscribe({
      next: (pdf: Blob ) => {
        const url = URL.createObjectURL(pdf);
        const link = document.createElement('a');
        link.href = url;
        link.download = 'merged.pdf';
        link.click();

        setTimeout(() => URL.revokeObjectURL(url), 1000);
        this.merging.set(false);
        },
      error: (error: Error) => {
        console.error('Errore durante il merge dei pdf', error);
        this.mergeError.set("Errore durante il merge dei pdf");
        this.merging.set(false);
      }
    });

  }
}
