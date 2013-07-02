import java.io.PrintStream;

class MaskingThread extends Thread
{
  private volatile boolean stop;
  private char echochar = '*';

  public MaskingThread(String paramString)
  {
    System.out.print(paramString);
  }

  public void run()
  {
    int i = Thread.currentThread().getPriority();
    Thread.currentThread().setPriority(10);
    try
    {
      this.stop = true;
      while (this.stop)
      {
        System.out.print("\b" + this.echochar);
        try
        {
          Thread.currentThread();
          Thread.sleep(1L);
        }
        catch (InterruptedException localInterruptedException)
        {
          Thread.currentThread().interrupt();
          return;
        }
      }
    }
    finally
    {
      Thread.currentThread().setPriority(i);
    }
  }

  public void stopMasking()
  {
    this.stop = false;
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     MaskingThread
 * JD-Core Version:    0.6.2
 */