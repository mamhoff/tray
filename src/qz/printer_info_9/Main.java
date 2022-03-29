package qz.printer_info_9;

import com.sun.jna.Native;
import com.sun.jna.Structure;

import java.lang.reflect.Field;

import static qz.printer_info_9.Winspool2.*;

public class Main {
    public static void main(String ... args) throws Throwable {

        /** Get all printers **
        for(int i : new int[] { 1, 2, 3, 4, 5, 9}) {
            System.out.println("PRINTER_INFO_" + i);
            Structure[] all = WinspoolUtil2.getPrinterInfoByStruct(PRINTER_ENUM_LOCAL | PRINTER_ENUM_CONNECTIONS, i);
            System.out.println("-----------");
            for(Structure info : all) {
                System.out.println();
                iterateFields(info);
            }
        }*/

        /** Get specified printer **/
        String name = "Microsoft XPS Document Writer";
        for(int i : new int[] { 1, 2, 3, 4, 5, 9}) {
            System.out.println("[PRINTER_INFO_" + i + "]");
            Structure info = WinspoolUtil2.getPrinterInfoByStruct(name, i);
            System.out.println(spaces(2) + name + ":");
            iterateFields(info, 0);
        }
    }

    /**
     * Crude convenience method for echoing all struct fields to console
     */
    public static void iterateFields(Structure info, int indent) throws Throwable {
        if(info instanceof PRINTER_INFO_9) {
            // PRINTER_INFO_9 stores all data in DEVMODE Struct
            info = ((PRINTER_INFO_9)info).pDevMode;
        } else if(info instanceof PRINTER_INFO_3) {
            // PRINTER_INFO_3 stores all data in SECURITY_DESCRIPTOR_RELATIVE Struct
            info = ((PRINTER_INFO_3)info).pSecurityDescriptor;
        }

        // Iterate all fields
        for(Field f : info.getClass().getFields()) {
            System.out.print(spaces(indent + 4) + f.getName() + ": ");
            Object o = f.get(info);
            if (o instanceof byte[]) {
                // TODO:  Determine why PRINTER_INFO_9's dmDeviceName is corrupted
                System.out.println(Native.toString((byte[])o));
            } else if (o instanceof Structure) {
                System.out.println(" (" + o.getClass().getSuperclass().getSimpleName() + ")");
                iterateFields((Structure)o, 2);
            } else {
                System.out.println(o);
            }
        }
    }

    public static String spaces(int spaces) {
        StringBuilder sb = new StringBuilder(spaces);
        for(int i = 0; i < spaces; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}
