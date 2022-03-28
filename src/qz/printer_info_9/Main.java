package qz.printer_info_9;

import com.sun.jna.Native;
import com.sun.jna.Structure;

import java.lang.reflect.Field;

public class Main {
    public static void main(String ... args) throws Throwable {
        String name = "Microsoft XPS Document Writer";
        Winspool2.PRINTER_INFO_9 info9 = WinspoolUtil2.getPrinterInfo9(name);
        System.out.println(name + ": ");
        for(Field f : info9.pDevMode.getClass().getFields()) {
            System.out.print("  " + f.getName() + ": ");
            Object o = f.get(info9.pDevMode);
            if(o instanceof byte[]) {
                // TODO:  Why is the device name corrupting?
                System.out.println(Native.toString((byte[])o));
            } else if(o instanceof Structure) {
                for(Field f2 : o.getClass().getFields()) {
                    System.out.println("    " + f2.getName() + ": " + f2.get(o));
                }
            } else {
                System.out.println(o);
            }
        }
    }
}
