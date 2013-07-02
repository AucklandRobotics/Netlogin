import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import javax.swing.UIManager;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpecBuilder;

public class NetLogin
{
  public static void main(String[] paramArrayOfString)
    throws Exception
  {
    try
    {
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    }
    catch (Exception localException1)
    {
    }
    if (paramArrayOfString.length == 0)
    {
      new NetLoginGUI();
    }
    else
    {
      OptionParser local1 = new OptionParser()
      {
      };
      try
      {
        OptionSet localOptionSet = local1.parse(paramArrayOfString);
        if (localOptionSet.has("?"))
        {
          local1.printHelpOn(System.out);
          System.exit(0);
        }
        if (localOptionSet.has("u"))
        {
          String str1 = null;
          String str2 = localOptionSet.valueOf("u").toString();
          if ((str2 == null) || (str2.length() == 0))
          {
            System.out.println("Please type your upi with option -u");
            System.exit(0);
          }
          if (localOptionSet.has("p"))
            try
            {
              str1 = localOptionSet.valueOf("p").toString();
            }
            catch (Exception localException3)
            {
              str1 = null;
            }
          if ((str1 == null) || (str1.length() == 0))
            try
            {
              str1 = new String(ConsolePasswordField.getPassword(System.in, "Enter password: "));
            }
            catch (IOException localIOException)
            {
              localIOException.printStackTrace();
            }
          if (str1 == null)
          {
            System.out.println("No password entered");
            System.exit(0);
          }
          if (localOptionSet.has("g"))
            new NetLoginGUI(str2, str1);
          else
            new NetLoginCMD(str2, str1);
        }
      }
      catch (Exception localException2)
      {
        System.out.println(localException2.getMessage());
        local1.printHelpOn(System.out);
        System.exit(0);
      }
    }
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     NetLogin
 * JD-Core Version:    0.6.2
 */