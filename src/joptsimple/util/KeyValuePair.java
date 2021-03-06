package joptsimple.util;

public final class KeyValuePair
{
  public final String key;
  public final String value;

  private KeyValuePair(String paramString1, String paramString2)
  {
    this.key = paramString1;
    this.value = paramString2;
  }

  public static KeyValuePair valueOf(String paramString)
  {
    int i = paramString.indexOf('=');
    if (i == -1)
      return new KeyValuePair(paramString, "");
    String str1 = paramString.substring(0, i);
    String str2;
    if (i == paramString.length() - 1)
      str2 = "";
    else
      str2 = paramString.substring(i + 1);
    return new KeyValuePair(str1, str2);
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof KeyValuePair))
      return false;
    KeyValuePair localKeyValuePair = (KeyValuePair)paramObject;
    return (this.key.equals(localKeyValuePair.key)) && (this.value.equals(localKeyValuePair.value));
  }

  public int hashCode()
  {
    return this.key.hashCode() ^ this.value.hashCode();
  }

  public String toString()
  {
    return this.key + '=' + this.value;
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.util.KeyValuePair
 * JD-Core Version:    0.6.2
 */