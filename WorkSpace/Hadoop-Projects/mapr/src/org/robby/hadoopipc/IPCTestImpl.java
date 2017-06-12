package org.robby.hadoopipc;

import java.io.IOException;

public class IPCTestImpl implements IPCTest{

	@Override
	public long getProtocolVersion(String arg0, long arg1) throws IOException {
		// TODO Auto-generated method stub
		return IPCServer.IPC_VER;
	}

	@Override
	public int add(int a, int b) {
		// TODO Auto-generated method stub
		return a+b;
	}

}
