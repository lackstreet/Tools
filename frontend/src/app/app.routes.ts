import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { MergePdf } from './pages/pdf-tools/merge-pdf/merge-pdf';
import { InspectPdf } from './pages/pdf-tools/inspect-pdf/inspect-pdf';
import { ExtractPdf } from './pages/pdf-tools/extract-pdf/extract-pdf';
import { PdfTools } from './pages/pdf-tools/pdf-tools';
import { ReorderPdf } from './pages/pdf-tools/reorder-pdf/reorder-pdf';
import { RotatePdf } from './pages/pdf-tools/rotate-pdf/rotate-pdf';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'pdf', component: PdfTools },
  { path: 'pdf/merge', component: MergePdf },
  { path: 'pdf/inspect', component: InspectPdf },
  { path: 'pdf/extract', component: ExtractPdf },
  { path: 'pdf/reorder', component: ReorderPdf },
  { path: 'pdf/rotate', component: RotatePdf },
];
