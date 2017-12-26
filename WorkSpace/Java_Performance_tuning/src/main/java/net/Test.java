package net;

public class Test {
	public static void main(String[] args) {
		ReportManagerFactory rmf = new ReportManagerFactory();
		IReportManager rm = rmf.getFinReportManager("B");
		IReportManager rm2 = rmf.getFinReportManager("B");
		IReportManager rm3 = rmf.getFinReportManager("B");
		IReportManager rm4 = rmf.getFinReportManager("B");
		IReportManager rm5 = rmf.getFinReportManager("B");
		System.out.println(rm.createReport());
		System.out.println(rm2.createReport());
		System.out.println(rm3.createReport());
		System.out.println(rm4.createReport());
		System.out.println(rm5.createReport());
	}

}
