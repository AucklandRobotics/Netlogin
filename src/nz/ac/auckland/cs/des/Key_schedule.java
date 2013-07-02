package nz.ac.auckland.cs.des;


// Referenced classes of package nz.ac.auckland.cs.des:
//            C_Block, des_hash_init, uHex

public class Key_schedule
{

    public Key_schedule()
    {
        s_data = new int[32];
        for(int i = 0; i < 32; i++)
            s_data[i] = 0;

    }

    public Key_schedule(String s)
    {
        s_data = new int[32];
        C_Block c_block = new C_Block();
        C_Block c_block1 = new C_Block();
        des_hash_init des_hash_init1 = new des_hash_init();
        key = new C_Block();
        int i = 0;
        for(int k = 0; k < s.length();)
        {
            i %= 8;
            c_block.data[i] |= (byte)s.charAt(k);
            c_block1.data[i] |= (byte)s.charAt(k);
            des_hash_init _tmp = des_hash_init1;
            c_block = c_block.des_ecb_encrypt(des_hash_init.des_hash_key1, true, false, false);
            des_hash_init _tmp1 = des_hash_init1;
            c_block1 = c_block1.des_ecb_encrypt(des_hash_init.des_hash_key2, true, false, false);
            k++;
            i++;
        }

        for(int j = 0; j < 8; j++)
            key.data[j] = (byte)(c_block.data[j] ^ c_block1.data[j]);

        des_set_key(key);
    }

    public Key_schedule(C_Block c_block)
    {
        s_data = new int[32];
        des_set_key(c_block);
    }

    public C_Block des_get_key()
    {
        return key;
    }

    public int get_schedule(int i)
    {
        return s_data[i];
    }

    public int[] get_schedule()
    {
        return s_data;
    }

    public void des_set_key(C_Block c_block)
    {
        int k = 0;
        int j1 = 0;
        key = c_block;
        int ai[] = key.int_array();
        for(int l = 0; l < 16; l++)
        {
            k += LS[l];
            int j;
            int i = j = 0;
            boolean flag = false;
            for(int i1 = 0; i1 < 48; i1++)
            {
                byte byte0 = PC2[i1];
                int l1 = byte0 < 28 ? 0 : 1;
                int i2 = (byte0 + k) % 28;
                i2 = PC1[i2 + l1 * 28];
                if(i2 >= 32)
                {
                    l1 = 1;
                    i2 -= 32;
                } else
                {
                    l1 = 0;
                }
                int k1;
                if(i1 >= 24)
                {
                    flag = true;
                    k1 = i1 - 24;
                } else
                {
                    k1 = i1;
                }
                k1 = (k1 / 6) * 8 + k1 % 6;
                if((ai[l1] & shift_arr[i2]) == 0)
                    continue;
                if(flag)
                    j |= shift_arr[k1];
                else
                    i |= shift_arr[k1];
            }

            s_data[j1++] = i;
            s_data[j1++] = j;
        }

    }

    public String toString()
    {
        String s = "";
        for(int i = 0; i < 32; i++)
            s = (new StringBuilder()).append(s).append(uHex.toHex(s_data[i])).append(" ").toString();

        return s;
    }

    static byte PC1[] = {
        56, 48, 40, 32, 24, 16, 8, 0, 57, 49, 
        41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 
        26, 18, 10, 2, 59, 51, 43, 35, 62, 54, 
        46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 
        29, 21, 13, 5, 60, 52, 44, 36, 28, 20, 
        12, 4, 27, 19, 11, 3
    };
    static byte PC2[] = {
        13, 16, 10, 23, 0, 4, 2, 27, 14, 5, 
        20, 9, 22, 18, 11, 3, 25, 7, 15, 6, 
        26, 19, 12, 1, 40, 51, 30, 36, 46, 54, 
        29, 39, 50, 44, 32, 47, 43, 48, 38, 55, 
        33, 52, 45, 41, 49, 35, 28, 31
    };
    static byte LS[] = {
        1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 
        2, 2, 2, 2, 2, 1
    };
    static int shift_arr[] = {
        1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 
        1024, 2048, 4096, 8192, 16384, 32768, 0x10000, 0x20000, 0x40000, 0x80000, 
        0x100000, 0x200000, 0x400000, 0x800000, 0x1000000, 0x2000000, 0x4000000, 0x8000000, 0x10000000, 0x20000000, 
        0x40000000, 0x80000000
    };
    int s_data[];
    C_Block key;

}
