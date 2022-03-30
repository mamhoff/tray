package qz.printer_info_9;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

import static qz.printer_info_9.WinGDI2.*;
import static com.sun.jna.platform.win32.WinDef.*;

public interface Winspool2 extends StdCallLibrary {
    Winspool2 INSTANCE = (Winspool2)Native.load("Winspool.drv", Winspool2.class, W32APIOptions.DEFAULT_OPTIONS);
    int CCHFORMNAME = 32;

    /**
     * The PRINTER_INFO_9 structure specifies the per-user default printer settings.
     */
    @FieldOrder({"pDevMode"})
    public static class PRINTER_INFO_9 extends Structure {
        public DEVMODE.ByReference pDevMode;

        public PRINTER_INFO_9() {}

        public PRINTER_INFO_9(int size) {
            super(new Memory((long)size));
        }
    }

    /**
     * The PRINTER_INFO_8 structure specifies the global default printer settings.
     */
    @FieldOrder({"pDevMode"})
    public static class PRINTER_INFO_8 extends Structure {
        public DEVMODE.ByReference pDevMode;

        public PRINTER_INFO_8() {}

        public PRINTER_INFO_8(int size) {
            super(new Memory((long)size));
        }
    }

    /**
     * The PRINTER_INFO_7 structure specifies directory services printer information.
     */
    @FieldOrder({"pszObjectGUID", "dwAction"})
    public static class PRINTER_INFO_7 extends Structure {
        public String pszObjectGUID;
        public int dwAction;

        public PRINTER_INFO_7() {}

        public PRINTER_INFO_7(int size) {
            super(new Memory((long)size));
        }
    }

    /**
     * The PRINTER_INFO_6 structure specifies the status value of a printer.
     */
    @FieldOrder({"dwStatus"})
    public static class PRINTER_INFO_6 extends Structure {
        public int dwStatus;

        public PRINTER_INFO_6() {}

        public PRINTER_INFO_6(int size) {
            super(new Memory((long)size));
        }
    }

    /**
     * The PRINTER_INFO_5 structure specifies detailed printer information.
     */
    @FieldOrder({"pPrinterName", "pPortName", "Attributes", "DeviceNotSelectedTimeout", "TransmissionRetryTimeout" })
    public static class PRINTER_INFO_5 extends Structure {
        public String pPrinterName;
        public String pPortName;
        public int Attributes;
        public int DeviceNotSelectedTimeout;
        public int TransmissionRetryTimeout;

        public PRINTER_INFO_5() {}

        public PRINTER_INFO_5(int size) {
            super(new Memory((long)size));
        }
    }

    /*
     * The PRINTER_INFO_3 structure specifies printer security information.
     */
    @FieldOrder({"pSecurityDescriptor"})
    public static class PRINTER_INFO_3 extends Structure {
        public WinNT.SECURITY_DESCRIPTOR_RELATIVE pSecurityDescriptor;

        public PRINTER_INFO_3() {}

        public PRINTER_INFO_3(int size) {
            super(new Memory((long)size));
        }
    }

}