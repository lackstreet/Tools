import { Component, signal } from '@angular/core';

@Component({
  imports: [],
  selector: 'app-merge-pdf',
  styleUrl: './merge-pdf.css',
  templateUrl: './merge-pdf.html',
})
export class MergePdf {
  protected readonly files = signal<File[]>([]);

  protected onFilesSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const selected = Array.from(input.files ?? []);

    if (selected.length <= 0) {
      return;
    }

    this.files.update((currentFiles) => [...currentFiles, ...selected]);
    input.value = '';
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
}
