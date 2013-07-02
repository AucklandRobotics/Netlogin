import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import nz.ac.auckland.cs.des.Key_schedule;
import nz.ac.auckland.cs.des.desDataOutputStream;

public class PingSender extends Thread
{
	private DatagramSocket s = null;
	private int Auth_Ref = -1;
	private int outtoken = 0;
	private String Host;
	private InetAddress Host_IPAddr;
	private int Port;
	private Key_schedule schedule = null;
	private int Sequence_Number = 0;
	private volatile boolean stop = false;
	private volatile int outstandingPings = 0;
	private NetLoginGUI netLogingui = null;
	private NetLoginCMD netLogincmd = null;
	public int keepAliveTimeout=5;//5 was default.
	
	public void setUsePingTimeout(boolean use){
		if (use)
			keepAliveTimeout=5;
		else
			keepAliveTimeout=9999999;
		//System.out.println(use?"Using ping timeouts.":"Not using ping timeouts.");
	}

	public PingSender(String paramString, int paramInt, NetLoginGUI paramNetLoginGUI)
	throws IOException
	{
		try
		{
			this.s = new DatagramSocket();
		}
		catch (Exception localException)
		{
			throw new IOException("Error creating DatagramSocket: " + localException);
		}
		this.Host = paramString;
		this.Port = paramInt;
		this.Host_IPAddr = InetAddress.getByName(this.Host);
		this.netLogingui = paramNetLoginGUI;
		this.netLogincmd = null;
	}

	public PingSender(String paramString, int paramInt, NetLoginCMD paramNetLoginCMD)
	throws IOException
	{
		try
		{
			this.s = new DatagramSocket();
		}
		catch (Exception localException)
		{
			throw new IOException("Error creating DatagramSocket: " + localException);
		}
		this.Host = paramString;
		this.Port = paramInt;
		this.Host_IPAddr = InetAddress.getByName(this.Host);
		this.netLogincmd = paramNetLoginCMD;
		this.netLogingui = null;
	}

	public void prepare(Key_schedule paramKey_schedule, int paramInt1, int paramInt2, int paramInt3)
	{
		this.schedule = paramKey_schedule;
		this.Auth_Ref = paramInt1;
		this.outtoken = paramInt2;
		this.Sequence_Number = paramInt3;
	}

	public DatagramSocket getSocket()
	{
		return this.s;
	}

	public void stopPinging()
	{
		DatagramPacket localDatagramPacket = null;
		desDataOutputStream localdesDataOutputStream1 = new desDataOutputStream(128);
		desDataOutputStream localdesDataOutputStream2 = new desDataOutputStream(128);
		try
		{
			localdesDataOutputStream2.writeInt(this.outtoken);
			localdesDataOutputStream2.writeInt(this.Sequence_Number + 10000);
			byte[] arrayOfByte1 = localdesDataOutputStream2.des_encrypt(this.schedule);
			localdesDataOutputStream1.writeInt(this.Auth_Ref);
			localdesDataOutputStream1.write(arrayOfByte1, 0, arrayOfByte1.length);
			byte[] arrayOfByte2 = localdesDataOutputStream1.toByteArray();
			localDatagramPacket = new DatagramPacket(arrayOfByte2, arrayOfByte2.length, this.Host_IPAddr, this.Port);
			this.s.send(localDatagramPacket);
		}
		catch (Exception localException)
		{
		}
		this.stop = true;
		interrupt();
	}

	public int getOutstandingPings()
	{
		return this.outstandingPings;
	}

	public void zeroOutstandingPings()
	{
		this.outstandingPings = 0;
	}

	public void sendMessage(String paramString1, String paramString2)
	{
		DatagramPacket localDatagramPacket = null;
		desDataOutputStream localdesDataOutputStream = new desDataOutputStream(8192);
		try
		{
			localdesDataOutputStream.writeInt(-1);
			localdesDataOutputStream.writeBytes("senduser " + paramString1 + " " + paramString2 + " ");
			byte[] arrayOfByte = localdesDataOutputStream.toByteArray();
			localDatagramPacket = new DatagramPacket(arrayOfByte, arrayOfByte.length, this.Host_IPAddr, this.Port);
			this.s.send(localDatagramPacket);
		}
		catch (IOException localIOException)
		{
			System.out.println("PingSender: Error sending message");
		}
	}

	public synchronized void run()
	{
		DatagramPacket localDatagramPacket = null;
		desDataOutputStream localdesDataOutputStream1 = new desDataOutputStream(128);
		desDataOutputStream localdesDataOutputStream2 = new desDataOutputStream(128);
		int i = 0;
		setPriority(2);
		while ((!this.stop) && (this.outstandingPings < keepAliveTimeout) && (i < 10))
		{
			try
			{
				localdesDataOutputStream2.writeInt(this.outtoken);
				localdesDataOutputStream2.writeInt(this.Sequence_Number);
				byte[] arrayOfByte1 = localdesDataOutputStream2.des_encrypt(this.schedule);
				localdesDataOutputStream1.writeInt(this.Auth_Ref);
				localdesDataOutputStream1.write(arrayOfByte1, 0, arrayOfByte1.length);
				byte[] arrayOfByte2 = localdesDataOutputStream1.toByteArray();
				localDatagramPacket = new DatagramPacket(arrayOfByte2, arrayOfByte2.length, this.Host_IPAddr, this.Port);
				this.s.send(localDatagramPacket);
				this.Sequence_Number += 1;
				//System.out.println("sent a keepalive. "+outstandingPings);
				this.outstandingPings += 1;
				i = 0;
			}
			catch (IOException localIOException)
			{
				System.out.println("PingSender: Error sending ping packet");
				i++;
			}
			localdesDataOutputStream2.reset();
			localdesDataOutputStream1.reset();
			byte[] arrayOfByte1 = null;
			byte[] arrayOfByte2 = null;
			localDatagramPacket = null;
			try
			{
				sleep(10000L);
			}
			catch (InterruptedException localInterruptedException)
			{
			}
		}
		try
		{
			this.s.close();
		}
		catch (Exception localException)
		{
			System.err.println("Error closing socket: " + localException);
		}
		System.err.println("Sequence number error. PingSender");
		if (this.netLogingui != null)
			this.netLogingui.update(0, false, false);
		if (this.netLogincmd != null)
			this.netLogincmd.update(0, false, false);
	}
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     PingSender
 * JD-Core Version:    0.6.2
 */