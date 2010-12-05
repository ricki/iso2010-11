package com.umbrella.worldconq.comm;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.UUID;

import com.umbrella.worldconq.domain.Session;
import com.umbrella.worldconq.stubserver.IServer;

public class ServerAdapter {

	protected static ServerAdapter mInstance = null;
	
	private String mRemoteName;
	private InetAddress mRemoteHost;
	private int mRemotePort;
	private IServer mProxy;
	
	protected ServerAdapter() {
		mRemoteName = null;
		mRemoteHost = null;
		mRemotePort = 0;
		mProxy = null;
	}
	
	protected ServerAdapter(String remoteName, InetAddress remoteHost, int remotePort) {
		setRemoteInfo(remoteName, remoteHost, remotePort);
	}
	
	public static ServerAdapter getServerAdapter(){
		if (mInstance == null) {
			mInstance = new ServerAdapter();
		}
		
		return mInstance;
	}
	
	public void setRemoteInfo(String remoteName, InetAddress remoteHost, int remotePort) {
		mRemoteName = remoteName;
		mRemoteHost = remoteHost;
		mRemotePort = remotePort;
	}
	
	public void connect() throws MalformedURLException, RemoteException, NotBoundException {
		disconnect();
		String url = String.format("rmi://%s:%d/%s", 
				mRemoteHost.getHostAddress(),
				mRemotePort,
				mRemoteName
		);
		mProxy = (IServer) Naming.lookup(url);
	}
	
	public void disconnect() {
		mProxy = null;
	}
	
	public boolean isConnected() {
		return mProxy != null;
	}
	
	public Session createSession(String Login, String Passwd) throws Exception {
		if (!isConnected()) throw new RemoteException();
		return new Session(mProxy.loginUser(Login, Passwd, null)); // TODO Falta el callback
	}

	public void closeSession(Session session) throws Exception {
		if (!isConnected()) throw new RemoteException();
		mProxy.logoutUser(session.getId());
	}
	
	public void registerUser(String Login, String Passwd, String Email) throws Exception {
		if (!isConnected()) throw new RemoteException();
		mProxy.registerUser(Login, Passwd, Email);
	}

	
}
