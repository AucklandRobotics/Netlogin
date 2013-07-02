import java.io.PrintStream;

public class NetLoginCMD
{
  String upi;
  String password;
  private NetLoginConnection netLoginConnection = null;
  private boolean displayStatus = false;

  public NetLoginCMD(String paramString1, String paramString2)
  {
    this.upi = paramString1;
    this.password = paramString2;
    try
    {
      this.netLoginConnection = new NetLoginConnection(this);
      this.netLoginConnection.logincmdline(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      System.out.println(localException);
    }
  }

  public void update(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean2)
    {
      System.out.println("UPI:" + this.upi + " Status:Connected");
    }
    else
    {
      System.out.println("Disconnected");
      this.displayStatus = false;
      System.exit(0);
    }
  }

  public void updateV3(int paramInt1, int paramInt2, boolean paramBoolean, String paramString)
  {
    String str = "";
    if (paramBoolean)
    {
      float f = (float)Math.round(paramInt1 / 1024.0D * 100.0D) / 100.0F;
      paramInt2 &= 251658240;
      switch (paramInt2)
      {
      case 16777216:
        str = "Unlimited";
        break;
      case 33554432:
        str = "Sponsored";
        break;
      case 50331648:
        str = "Premium";
        break;
      case 67108864:
        str = "Standard";
        break;
      case 83886080:
        str = "No Access";
        break;
      default:
        str = "";
      }
      if (this.displayStatus)
      {
        System.out.print("..");
      }
      else
      {
        System.out.println("Status:Connected");
        System.out.println("UPI:" + this.upi);
        System.out.println("Internet Plan:" + str + "\nMBs used this month:" + f + "MBs");
        System.out.print("Pingd is active:.");
        this.displayStatus = true;
      }
    }
    else
    {
      System.out.println("Disconnected");
      this.displayStatus = false;
      System.exit(0);
    }
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     NetLoginCMD
 * JD-Core Version:    0.6.2
 */