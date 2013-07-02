import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import nz.ac.auckland.cs.des.Key_schedule;
import nz.ac.auckland.cs.des.desDataInputStream;

public class PingRespHandler extends Thread
{
	private NetLoginGUI netLogingui = null;
	private NetLoginCMD netLogincmd = null;
	private final int ACK = 1;
	private final int NACK = 0;
	private int inToken = 0;
	private DatagramSocket s = null;
	private Key_schedule schedule = null;
	private volatile boolean loop = true;
	private int Next_Sequence_Number_Expected = 0;
	private PingSender pinger;

	public PingRespHandler(NetLoginGUI paramNetLoginGUI, PingSender paramPingSender)
	throws IOException
	{
		this.netLogingui = paramNetLoginGUI;
		this.pinger = paramPingSender;
		this.s = new DatagramSocket();
		this.s.setSoTimeout(500);
	}

	public PingRespHandler(NetLoginCMD paramNetLoginCMD, PingSender paramPingSender)
	throws IOException
	{
		this.netLogincmd = paramNetLoginCMD;
		this.pinger = paramPingSender;
		this.s = new DatagramSocket();
		this.s.setSoTimeout(500);
	}

	public PingRespHandler(NetLoginGUI paramNetLoginGUI, PingSender paramPingSender, DatagramSocket paramDatagramSocket)
	throws IOException
	{
		this.netLogingui = paramNetLoginGUI;
		this.pinger = paramPingSender;
		this.s = paramDatagramSocket;
		this.s.setSoTimeout(500);
	}

	public PingRespHandler(NetLoginCMD paramNetLoginCMD, PingSender paramPingSender, DatagramSocket paramDatagramSocket)
	throws IOException
	{
		this.netLogincmd = paramNetLoginCMD;
		this.pinger = paramPingSender;
		this.s = paramDatagramSocket;
		this.s.setSoTimeout(500);
	}

	public void prepare(int paramInt1, int paramInt2, Key_schedule paramKey_schedule)
	{
		this.inToken = paramInt1;
		this.Next_Sequence_Number_Expected = paramInt2;
		this.schedule = paramKey_schedule;
	}

	public int getLocalPort()
	{
		return this.s.getLocalPort();
	}

	public void end()
	{
		this.loop = false;
		interrupt();
	}


	public synchronized void run()
	{
		byte[] arrayOfByte1 = new byte[8192];
		DatagramPacket localDatagramPacket = new DatagramPacket(arrayOfByte1, arrayOfByte1.length);
		int i2 = 0;
		int i3 = 0;
		setPriority(2);
		while ((this.loop) && (i2 < 10) && (this.pinger.getOutstandingPings() < pinger.keepAliveTimeout))
		{
			localDatagramPacket = new DatagramPacket(arrayOfByte1, arrayOfByte1.length);
			try
			{
				this.s.receive(localDatagramPacket);
				System.out.println("RECIEVED SOMETHING!");
			}
			catch (InterruptedIOException localInterruptedIOException)
			{
				//System.out.println("No packet recieved.");
				continue;
			}
			catch (IOException localIOException)
			{
				System.out.println("Error receiving: " + localIOException);
				i2++;
				continue;
			}
			int k = localDatagramPacket.getLength();
			System.out.println(localDatagramPacket.toString());
			if (k < 24)
			{
				System.out.println("Short packet");
				i2++;
			}
			else
			{
				desDataInputStream localdesDataInputStream = new desDataInputStream(localDatagramPacket.getData(), 0, k, this.schedule);
				try
				{
					int i = localdesDataInputStream.readInt();
					if (this.inToken != i)
					{
						System.out.println("Other end doesn't agree on the current passwd");
						i2++;
					}
					else
					{
						int j = localdesDataInputStream.readInt();
						if (this.Next_Sequence_Number_Expected > j)
						{
							System.out.println("Ping responce sequence numbers out");
						}
						else
						{
							this.Next_Sequence_Number_Expected = (j + 1);
							int m = localdesDataInputStream.readInt();
							int n = localdesDataInputStream.readInt();
							i3 = localdesDataInputStream.readInt();
							int i1 = localdesDataInputStream.readInt();
							byte[] arrayOfByte2 = new byte[i1];
							localdesDataInputStream.read(arrayOfByte2);
							if (this.netLogingui != null)
								this.netLogingui.updateV3(m, i3, true, new String(arrayOfByte2));
							if (this.netLogincmd != null)
								this.netLogincmd.updateV3(m, i3, true, new String(arrayOfByte2));
							i2 = 0;
							this.pinger.zeroOutstandingPings();
							System.out.println("recieved a keepalive.");
							if (n == 0)
								end();
						}
					}
				}
				catch (Exception localException)
				{
					System.out.println("ping recv: Exception:" + localException);
					i2++;
				}
			}
		}
		if (this.pinger.getOutstandingPings() >= pinger.keepAliveTimeout)
			System.err.println("Max outstanding pings reached, disconnecting");
		
		if (this.netLogingui != null)
			this.netLogingui.update(0, false, false);
		if (this.netLogincmd != null)
			this.netLogincmd.update(0, false, false);
	}
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     PingRespHandler
 * JD-Core Version:    0.6.2
 */