package net;

public class EmployeeReportManager implements IReportManager {
	protected String tid = null;

	public EmployeeReportManager(String tid) {
		this.tid = tid;
	}

	@Override
	public String createReport() {
		return "this is employeeReport";
	}
    
	
}
