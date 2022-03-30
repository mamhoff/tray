package qz.printer_info_9;

import com.sun.jna.*;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.Winspool;

import java.lang.reflect.Field;

import static qz.printer_info_9.Winspool2.*;

public class Main {
    public static void main(String... args) throws Throwable {

        /** Get all printers **
         for(int i : new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9}) {
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
        for(int i : new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9}) {
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
        if (info instanceof PRINTER_INFO_9) {
            // PRINTER_INFO_9 stores all data in DEVMODE Struct
            info = ((PRINTER_INFO_9)info).pDevMode;
        } else if (info instanceof PRINTER_INFO_3) {
            // PRINTER_INFO_3 stores all data in SECURITY_DESCRIPTOR_RELATIVE Struct
            info = ((PRINTER_INFO_3)info).pSecurityDescriptor;
        }

        // Iterate all fields
        for(Field f : info.getClass().getFields()) {
            // Skip internal-use fields
            if (f.getName().startsWith("ALIGN_")) {
                continue;
            }

            System.out.print(spaces(indent + 4) + f.getName() + ": ");
            Object o = f.get(info);
            if(f.getName().equals("pDevMode") && o instanceof WinDef.INT_PTR) {
                // FIXME: How to convert INT_PTR to DEVMODE?
            }
            if (f.getName().equals("Status") || f.getName().equals("dwStatus")) {
                int dwStatus = o instanceof WinDef.DWORD? ((WinDef.DWORD)o).intValue():(Integer)o;
                System.out.println(o);
                if (dwStatus == 0) {
                    System.out.println(spaces(indent + 6) + "PRINTER_STATUS_NORMAL");
                    continue;
                }
                for(Field w : Winspool.class.getDeclaredFields()) {
                    if (w.getName().startsWith("PRINTER_STATUS_")) {
                        int flag = (Integer)(w.get(null));
                        if ((flag & dwStatus) == flag) {
                            System.out.println(spaces(indent + 6) + w.getName());
                        }
                    }
                }
            } else if (o instanceof byte[]) {
                byte[] bytes = (byte[])o;
                Pointer pBytes = new Memory(bytes.length);
                pBytes.write(0, bytes, 0, bytes.length);
                String val = Boolean.getBoolean("w32.ascii")? pBytes.getString(0):pBytes.getWideString(0);
                System.out.println(val);
            } else if (o instanceof Structure) {
                System.out.println(" (" + o.getClass().getSuperclass().getSimpleName() + ")");
                iterateFields((Structure)o, indent + 2);
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
