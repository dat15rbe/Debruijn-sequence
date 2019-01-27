

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;


public class deBruijn {
    public static void main(String[] args) throws IOException {

        byte mod2RegisterOutput;
        byte[] initialMod2Register = new byte[4];

        byte mod5RegisterOutput;
        byte[] InitialMod5Register = new byte[4];

        byte[] res = new byte[10003];
        for (int i = 0; i < res.length; i++) {
            mod2RegisterOutput = nextMod2Value(InitialMod5Register);
            mod5RegisterOutput = nextMod5Value(initialMod2Register);
            res[i] = (byte) (mod2RegisterOutput + mod5RegisterOutput * 2);
        }

        try {
            PrintWriter printWriter = new PrintWriter(new FileOutputStream("output.txt"));

            for (int i = 0; i < res.length; i++) {
                printWriter.print(res[i]);
            }
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte nextMod5Value(byte[] shiftRegister) {
        byte registerInput;

        if (shiftRegister[3] == 0 && shiftRegister[2] == 0 && shiftRegister[1] == 0 && shiftRegister[0] == 0)//Zero state
            registerInput = 2; //escape zero state back into the next in normal order
        else if (shiftRegister[3] == 4 && shiftRegister[2] == 0 && shiftRegister[1] == 0 && shiftRegister[0] == 0)//enter Zero state
            registerInput = 0;//enter zero state
        else
            registerInput = (byte) ((3 * shiftRegister[3] + 3 * shiftRegister[1] + 4 * shiftRegister[0]) % 5);

        for (int j = 3; j > 0; j--) //shift the registers
            shiftRegister[j] = shiftRegister[j-1];

        shiftRegister[0] = registerInput;
        return shiftRegister[3];
    }

    public static byte nextMod2Value(byte[] shiftRegister) {
        byte registerInput;
        if (shiftRegister[3] == 0 && shiftRegister[2] == 0 && shiftRegister[1] == 0 && shiftRegister[0] == 0)//Zero state
            registerInput = 1;//escape zero state back into the next in normal order
        else if (shiftRegister[2] == 0 && shiftRegister[1] == 0 && shiftRegister[0] == 0) //enter zero state
            registerInput = 0;//enter zero state
        else
            registerInput = (byte) ((shiftRegister[3] + shiftRegister[2]) % 2);

        for (int j = 3; j > 0; j--) //shift the registers
            shiftRegister[j] = shiftRegister[j-1];

        shiftRegister[0] = registerInput;
        return shiftRegister[3];
    }

}
