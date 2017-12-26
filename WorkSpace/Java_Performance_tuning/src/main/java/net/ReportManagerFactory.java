package net;

import java.util.HashMap;
import java.util.Map;

public class ReportManagerFactory {
	Map<String, IReportManager> finManager = new HashMap<String, IReportManager>();
	Map<String, IReportManager> empManager = new HashMap<String, IReportManager>();

	IReportManager getFinReportManager(String tid) {
		IReportManager r = finManager.get(tid);
		if (r == null) {
			r = new FinancialReportManager(tid);
			finManager.put(tid, r);
		}
		return r;
	}
	
	IReportManager getEmpReportManager(String tid) {
		IReportManager r = empManager.get(tid);
		if (r == null) {
			r = new EmployeeReportManager(tid);
			empManager.put(tid, r);
		}
		return r;
	}	
}
