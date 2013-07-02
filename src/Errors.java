public class Errors
{
  static final int CMD_RSLT_OK = 1;
  static final int CMD_RSLT_INCOMPLETE = 2;
  static final int CMD_RSLT_WOULD_BLOCK = 10;
  static final int CMD_RSLT_NOT_AUTHORISED = 11;
  static final int CMD_RSLT_UNKNOWN_COMMAND = 12;
  static final int CMD_RSLT_UNKNOWN_CLIENT = 13;
  static final int CMD_RSLT_USER_BANNED = 14;
  static final int CMD_RSLT_USER_WAS_BANNED = 15;
  static final int CMD_RSLT_AUTH_FAILURE = 16;
  static final int CMD_RSLT_COMMUNICATION_FAILUE = 17;
  static final int CMD_RSLT_MALLOC_ERROR = 18;
  static final int CMD_RSLT_IDENTD_FAILUE = 19;
  static final int CMD_RSLT_USER_INVALID = 20;
  static final int CMD_RSLT_RLOCK_FAILURE = 21;
  static final int CMD_RSLT_WLOCK_FAILURE = 22;
  static final int CMD_RSLT_IPAAddEntry_FAILED = 23;
  static final String[] error_messages = { "", "OK", "INCOMPLETE", "", "", "", "", "", "", "", "WOULD_BLOCK", "NOT_AUTHORISED", "UNKNOWN_COMMAND", "UNKNOWN_CLIENT", "USER_BANNED", "USER_WAS_BANNED", "AUTH_FAILURE", "COMMUNICATION_FAILUE", "MALLOC_ERROR", "IDENTD_FAILUE", "USER_INVALID", "RLOCK_FAILURE", "WLOCK_FAILURE", "IPAAddEntry_FAILED" };
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     Errors
 * JD-Core Version:    0.6.2
 */