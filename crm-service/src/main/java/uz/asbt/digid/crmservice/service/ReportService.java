package uz.asbt.digid.crmservice.service;

import uz.asbt.digid.crmservice.model.ReportRequest;
import uz.asbt.digid.crmservice.model.ReportResponse;

public interface ReportService {
    ReportResponse info(ReportRequest request);

    ReportResponse livenessReport(ReportRequest request);
}
