export * from './healthCheck.service';
import { HealthCheckService } from './healthCheck.service';
export * from './inspectPdfResource.service';
import { InspectPdfResourceService } from './inspectPdfResource.service';
export const APIS = [HealthCheckService, InspectPdfResourceService];
