package qz.printer_info_9;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

import static qz.printer_info_9.WinGDI2.*;

public interface Winspool2 extends StdCallLibrary {
    Winspool2 INSTANCE = (Winspool2)Native.load("Winspool.drv", Winspool2.class, W32APIOptions.DEFAULT_OPTIONS);
    int CCHFORMNAME = 32;

    /**
     * The PRINTER_INFO_9 structure specifies the per-user default printer settings.
     */
    @FieldOrder({"pDevMode"})
    public static class PRINTER_INFO_9 extends Structure {
        public DEVMODE pDevMode;

        public PRINTER_INFO_9() {
        }

        public PRINTER_INFO_9(int size) {
            super(new Memory((long)size));
        }
    }
}