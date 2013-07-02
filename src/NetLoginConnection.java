import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Random;
import nz.ac.auckland.cs.des.C_Block;
import nz.ac.auckland.cs.des.Key_schedule;
import nz.ac.auckland.cs.des.desDataInputStream;
import nz.ac.auckland.cs.des.desDataOutputStream;

public class NetLoginConnection
{
	final String AUTHD_SERVER = "gate.ec.auckland.ac.nz";
	final int AUTHD_PORT = 312;
	final int PINGD_PORT = 443;
	final int AUTH_REQ_PACKET = 1;
	final int AUTH_REQ_RESPONSE_PACKET = 2;
	final int AUTH_CONFIRM_PACKET = 3;
	final int AUTH_CONFIRM_RESPONSE_PACKET = 4;
	final int NETGUARDIAN_JAVA_CLIENT = 12;
	final int NETGUARDIAN_JAVA_MCLIENT = 34;
	final int CMD_NULL = 0;
	final int CMD_LAST_CMD = 1;
	final int CMD_REGISTER = 2;
	final int CMD_GET_USER_BALANCES_NO_BLOCK = 3;
	final int CMD_GET_USER_BALANCES_DO_BLOCK = 4;
	final int CMD_BAN_USER = 5;
	final int ACK = 1;
	final int NACK = 0;
	final int VERSION = 0;
	final int UNAMESIZ = 9;
	final int PASSWDSIZ = 17;
	final int BUFSIZ = 1024;
	byte[] InBuffer = new byte[1024];
	Random r = new Random();
	int random1 = 0;
	int random2 = 0;
	int Sequence_Number = 0;
	Key_schedule schedule = null;
	int clienttype = 12;
	int cmd_data_length = 2;
	short cmd_data = 0;
	int clientversion = 3;
	int Client_Rel_Version;
	int clientcommand = 3;
	SPP_Packet NetGuardian_stream = null;
	int Auth_Ref;
	int IPBalance;
	int Response_Port;
	PingSender pinger = null;
	PingRespHandler ping_receiver = null;
	boolean useStaticPingPort = false;
	boolean usePingTimeout = false;
	
	String username;
	int OnPeak;
	int localUnitCost;
	int intlOffPeakRate;
	int intlOnPeakRate;
	int start_Peak;
	int endPeak;
	int lastModDate;
	NetLoginGUI parentgui = null;
	NetLoginCMD parentcmd = null;

	public NetLoginConnection(NetLoginCMD paramNetLoginCMD)
	{
		this.parentcmd = paramNetLoginCMD;
	}

	public NetLoginConnection(NetLoginGUI paramNetLoginGUI)
	{
		this.parentgui = paramNetLoginGUI;
	}

	public void logincmdline(String paramString1, String paramString2)
			throws IOException
			{
		logincmdline("gate.ec.auckland.ac.nz", paramString1, paramString2);
			}

	public void logincmdline(String paramString1, String paramString2, String paramString3)
			throws IOException
			{
		this.pinger = new PingSender(paramString1, 443, this.parentcmd);
		if (this.useStaticPingPort)
		{
			this.ping_receiver = new PingRespHandler(this.parentcmd, this.pinger, this.pinger.getSocket());
			this.Response_Port = 0;
		}
		else
		{
			this.ping_receiver = new PingRespHandler(this.parentcmd, this.pinger);
			this.Response_Port = this.ping_receiver.getLocalPort();
		}
		authenticate(paramString1, paramString2, paramString3);
		this.pinger.prepare(this.schedule, this.Auth_Ref, this.random2 + 2, this.Sequence_Number);
		this.ping_receiver.prepare(this.random1 + 3, this.Sequence_Number, this.schedule);
		this.ping_receiver.start();
		this.pinger.start();
			}

	public void login(String paramString1, String paramString2)	throws IOException
	{
		login("gate.ec.auckland.ac.nz", paramString1, paramString2);
	}

	public void login(String paramString1, String paramString2, String paramString3) throws IOException
	{
		username = paramString2;
		pinger = new PingSender(paramString1, 443, this.parentgui);
		pinger.setUsePingTimeout(usePingTimeout);
		if (this.useStaticPingPort)
		{
			this.ping_receiver = new PingRespHandler(this.parentgui, this.pinger, this.pinger.getSocket());
			this.Response_Port = 0;
		}
		else
		{
			this.ping_receiver = new PingRespHandler(this.parentgui, this.pinger);
			this.Response_Port = this.ping_receiver.getLocalPort();
		}
		authenticate(paramString1, paramString2, paramString3);
		this.pinger.prepare(this.schedule, this.Auth_Ref, this.random2 + 2, this.Sequence_Number);
		this.ping_receiver.prepare(this.random1 + 3, this.Sequence_Number, this.schedule);
		this.ping_receiver.start();
		this.pinger.start();
		this.parentgui.update(this.IPBalance, (this.OnPeak & 0x1) == 1, true);
	}

	public void setUseStaticPingPort(boolean paramBoolean)
	{
		this.useStaticPingPort = paramBoolean;
	}
	public void setUsePingTimeout(boolean paramBoolean)
	{
		this.usePingTimeout = paramBoolean;
	}

	public void logout()
	{
		if (this.pinger != null)
			this.pinger.stopPinging();
		if (this.ping_receiver != null)
			this.ping_receiver.end();
	}

	public void resetPings()
	{
		if (this.pinger != null)
			this.pinger.zeroOutstandingPings();
	}

	public void sendMessage(String paramString1, String paramString2)
	{
		if (this.pinger != null)
			this.pinger.sendMessage(paramString1, this.username + ": " + paramString2);
	}

	private void authenticate(String paramString1, String paramString2, String paramString3)
			throws IOException, UnknownHostException
			{
		try
		{
			this.NetGuardian_stream = new SPP_Packet(paramString1, 312);
			this.schedule = new Key_schedule(paramString3);
			sendPacket_1(paramString2);
			RespPacket_1();
			SendSecondPacket();
			ReadSecondResponsePacket();
		}
		catch (UnknownHostException localUnknownHostException)
		{
			throw localUnknownHostException;
		}
		catch (IOException localIOException)
		{
			throw localIOException;
		}
		finally
		{
			if (this!=null && this.NetGuardian_stream!=null){
				this.NetGuardian_stream.close();
				this.NetGuardian_stream = null;
			}
		}
	}

	private void sendPacket_1(String paramString)
			throws IOException
			{
		desDataOutputStream localdesDataOutputStream1 = new desDataOutputStream(128);
		desDataOutputStream localdesDataOutputStream2 = new desDataOutputStream(128);
		this.random1 = this.r.nextInt();
		localdesDataOutputStream2.writeInt(this.random1);
		byte[] arrayOfByte = localdesDataOutputStream2.des_encrypt(this.schedule);
		localdesDataOutputStream1.writeInt(this.clienttype);
		localdesDataOutputStream1.writeInt(this.clientversion);
		localdesDataOutputStream1.writeBytes(paramString, 9);
		localdesDataOutputStream1.write(arrayOfByte, 0, arrayOfByte.length);
		this.NetGuardian_stream.SendPacket(1, 0, localdesDataOutputStream1.toByteArray());
			}

	private void RespPacket_1()
			throws IOException
			{
		DataInputStream localDataInputStream = null;
		if (this==null || this.NetGuardian_stream==null || this.InBuffer==null)return;
		ReadResult localReadResult = this.NetGuardian_stream.ReadPacket(2, 0, this.InBuffer);
		switch (localReadResult.Result)
		{
		case -4:
			throw new IOException("Unexpected PacketType " + localReadResult.Packet_type + " expected " + 2);
		case -100:
			String str = "Client Version incorrect";
			localDataInputStream = new DataInputStream(new ByteArrayInputStream(this.InBuffer, 0, this.NetGuardian_stream.Last_Read_length));
			try
			{
				this.Client_Rel_Version = localDataInputStream.readInt();
				if (this.Client_Rel_Version > 0)
				{
					str = str + "\rThe latest version is " + this.Client_Rel_Version;
					try
					{
						int j = localDataInputStream.readInt();
						if (j != 0)
						{
							byte[] arrayOfByte = new byte[j];
							localDataInputStream.read(arrayOfByte);
							str = str + "\rURL " + arrayOfByte;
						}
					}
					catch (Exception localException1)
					{
					}
				}
			}
			catch (Exception localException2)
			{
			}
			throw new IOException(str);
		case -102:
			throw new IOException("Access Denied from this Network or Host");
		case -103:
			throw new IOException("User Banned for using this host/network");
		case 0:
			break;
			//default:
			//	throw new IOException("Protocol Error " + localReadResult.Result);
		}
		localReadResult = null;
		//if (this.NetGuardian_stream.Last_Read_length < 20)
		//	throw new IOException("RespPacket_1: AUTH_REQ_RESPONSE_PACKET too short, " + this.NetGuardian_stream.Last_Read_length + " bytes " + C_Block.size() * 4);
		localDataInputStream = new DataInputStream(new ByteArrayInputStream(this.InBuffer, 0, 4));
		this.Client_Rel_Version = localDataInputStream.readInt();
		if (this==null || this.NetGuardian_stream==null)return;
		desDataInputStream localdesDataInputStream = new desDataInputStream(this.InBuffer, 4, this.NetGuardian_stream.Last_Read_length, this.schedule);
		int i = localdesDataInputStream.readInt();
		if (this.random1 + 1 != i)
			throw new IOException("Incorrect password");
		this.random2 = localdesDataInputStream.readInt();
		this.schedule = new Key_schedule(localdesDataInputStream.readC_Block());
	}

	private void SendSecondPacket()	throws IOException
	{
		if (this==null || this.NetGuardian_stream==null)return;
		desDataOutputStream localdesDataOutputStream = new desDataOutputStream(128);
		this.cmd_data = ((short)this.Response_Port);
		localdesDataOutputStream.writeInt(this.random2 + 1);
		localdesDataOutputStream.writeInt(this.clientcommand);
		localdesDataOutputStream.writeInt(this.cmd_data_length);
		localdesDataOutputStream.writeShort(this.cmd_data);
		byte[] arrayOfByte = localdesDataOutputStream.des_encrypt(this.schedule);
		this.NetGuardian_stream.SendPacket(3, 0, arrayOfByte);
	}

	private void ReadSecondResponsePacket()	throws IOException {
		if (this==null || this.NetGuardian_stream==null)return;
		DataInputStream localDataInputStream = null;
		ReadResult localReadResult = null;
		localReadResult = this.NetGuardian_stream.ReadPacket(4, 0, this.InBuffer);
		switch (localReadResult.Result)
		{
		case -4:
			throw new IOException("Unexpected PacketType " + localReadResult.Packet_type + " expected " + 2);
		case 0:
			break;
		default:
			throw new IOException("Protocol Error " + localReadResult.Result);
		}
		if (this==null || this.NetGuardian_stream==null)return;
		if (this.NetGuardian_stream.Last_Read_length < 40)
			throw new IOException("ReadSecondResponsePacket: AUTH_CONFIRM_RESPONSE_PACKET too short");
		localDataInputStream = new DataInputStream(new ByteArrayInputStream(this.InBuffer, 0, 28));
		this.OnPeak = localDataInputStream.readInt();
		this.localUnitCost = localDataInputStream.readInt();
		this.intlOffPeakRate = localDataInputStream.readInt();
		this.intlOnPeakRate = localDataInputStream.readInt();
		this.start_Peak = localDataInputStream.readInt();
		this.endPeak = localDataInputStream.readInt();
		this.lastModDate = localDataInputStream.readInt();
		desDataInputStream localdesDataInputStream = new desDataInputStream(this.InBuffer, 28, this.NetGuardian_stream.Last_Read_length, this.schedule);
		int i = localdesDataInputStream.readInt();
		if (this.random1 + 2 != i)
			throw new IOException("Incorrect password");
		int j = localdesDataInputStream.readInt();
		int k = localdesDataInputStream.readInt();
		if ((j != 1) && (j != 10))
			throw new IOException("got Nack on authentication " + Errors.error_messages[j]);
		if (j == 10)
			throw new IOException("User record is locked. Unable to read IP balance");
		if (k < 8)
			throw new IOException("Cmd result buffer too small");
		this.Auth_Ref = localdesDataInputStream.readInt();
		this.IPBalance = localdesDataInputStream.readInt();
	}
}