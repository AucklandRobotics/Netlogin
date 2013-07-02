import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SPP_Packet
{
  static final int PASSWD_SERVICE = 302;
  static final String HOST = "cs20.cs.auckland.ac.nz";
  static final int RSLT_OK = 0;
  static final int RSLT_COMMUNICATION_FAILUE = -1;
  static final int RSLT_BAD_VERSION = -2;
  static final int RSLT_PROTOCOL_FAILURE = -3;
  static final int RSLT_BAD_PACKET_TYPE = -4;
  static final int REMOTE_RSLT_BAD_VERSION = -100;
  static final int REMOTE_RSLT_COMMUNICATION_FAILUE = -101;
  static final int REMOTE_RSLT_ACCESS_RESTRICTION = -102;
  static final int REMOTE_RSLT_ACCESS_RESTRICTION_BAN = -103;
  static final int REMOTE_RSLT_PROTOCOL_FAILURE = -104;
  Socket passwdSocket = null;
  DataOutputStream os = null;
  DataInputStream is = null;
  int Last_Read_length = 0;

  public SPP_Packet(String paramString, int paramInt)
    throws IOException, UnknownHostException
  {
    this.passwdSocket = new Socket(paramString, paramInt);
    this.os = new DataOutputStream(new BufferedOutputStream(this.passwdSocket.getOutputStream()));
    this.is = new DataInputStream(new BufferedInputStream(this.passwdSocket.getInputStream()));
  }

  public SPP_Packet()
    throws IOException, UnknownHostException
  {
    this.passwdSocket = new Socket("cs20.cs.auckland.ac.nz", 302);
    this.os = new DataOutputStream(new BufferedOutputStream(this.passwdSocket.getOutputStream()));
    this.is = new DataInputStream(new BufferedInputStream(this.passwdSocket.getInputStream()));
  }

  public void SendPacket(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
    throws IOException
  {
    try
    {
      this.os.writeInt(paramArrayOfByte.length + 8);
    }
    catch (IOException localIOException1)
    {
      throw new IOException("SendPacket: 1" + localIOException1.getMessage());
    }
    try
    {
      this.os.writeInt(paramInt1);
    }
    catch (IOException localIOException2)
    {
      throw new IOException("SendPacket: 2" + localIOException2.getMessage());
    }
    try
    {
      this.os.writeInt(paramInt2);
    }
    catch (IOException localIOException3)
    {
      throw new IOException("SendPacket: 3" + localIOException3.getMessage());
    }
    try
    {
      this.os.write(paramArrayOfByte, 0, paramArrayOfByte.length);
    }
    catch (IOException localIOException4)
    {
      throw new IOException("SendPacket: 4" + localIOException4.getMessage());
    }
    try
    {
      this.os.flush();
    }
    catch (IOException localIOException5)
    {
      throw new IOException("SendPacket: 5" + localIOException5.getMessage());
    }
  }

  public ReadResult ReadPacket(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
    throws IOException
  {
    int i = 0;
    int j = 0;
    int n = 0;
    this.Last_Read_length = (this.is.readInt() - 8);
    if (this.Last_Read_length < 0)
      throw new IOException("ReadPacket 1: data length " + this.Last_Read_length + " < 0");
    if (this.Last_Read_length > paramArrayOfByte.length)
      throw new IOException("ReadPacket 2: data length " + this.Last_Read_length + " > buffer length");
    int k = this.is.readInt();
    if (k != paramInt1)
      n = -4;
    int m = this.is.readInt();
    if ((n == 0) && (m != paramInt2))
      n = m;
    while ((i < this.Last_Read_length) && (j != -1))
    {
      try
      {
        j = this.is.read(paramArrayOfByte, i, this.Last_Read_length - i);
      }
      catch (IOException localIOException)
      {
        throw new IOException("ReadPacket 5: " + localIOException.getMessage());
      }
      i += j;
    }
    return new ReadResult(n, k, m);
  }

  public void close()
    throws IOException
  {
    if (this.passwdSocket != null)
    {
      this.passwdSocket.close();
      this.passwdSocket = null;
    }
    this.os = null;
    this.is = null;
  }

  public void finalize()
  {
    try
    {
      close();
    }
    catch (Exception localException)
    {
      System.err.println("finalize:  " + localException);
    }
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     SPP_Packet
 * JD-Core Version:    0.6.2
 */