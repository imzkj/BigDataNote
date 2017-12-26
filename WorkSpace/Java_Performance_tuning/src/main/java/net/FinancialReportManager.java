package net;

public class FinancialReportManager implements IReportManager {
	protected String tid = null;

	public FinancialReportManager(String tid) {
		System.out.println("1");
		this.tid = tid;
	}

	@Override
	public String createReport() {
		return "this is financialReport";
	}

}
