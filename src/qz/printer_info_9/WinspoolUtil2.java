package qz.printer_info_9;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT.HANDLEByReference;
import com.sun.jna.platform.win32.Winspool;
import com.sun.jna.ptr.IntByReference;

import static qz.printer_info_9.Winspool2.*;

/**
 * Winspool Utility API.
 *
 * @author dblock[at]dblock.org, Ivan Ridao Freitas, Padrus, Artem Vozhdayenko
 */
public abstract class WinspoolUtil2 {

    /**
     * The <code>PRINTER_INFO_9</code> Structure specifies the per-user default printer settings.
     *
     * <ul>
     *     <li><code>PRINTER_INFO_9</code>: Per-user defaults are unique to the user/profile.</li>
     *     <li><code>PRINTER_INFO_8</code>: Global defaults are set by the administrator of a printer
     *     and used by anyone.</li>
     * </ul>
     */
    public static PRINTER_INFO_9 getPrinterInfo9(String printerName) {
        IntByReference pcbNeeded = new IntByReference();
        IntByReference pcReturned = new IntByReference();
        HANDLEByReference pHandle = new HANDLEByReference();

        // First pass: Get page size
        if (!Winspool.INSTANCE.OpenPrinter(printerName, pHandle, null)) {
            throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
        }

        Win32Exception we = null;
        PRINTER_INFO_9 pinfo9 = null;

        try {
            // Second pass: Get printer information
            Winspool.INSTANCE.GetPrinter(pHandle.getValue(), 9, null, 0, pcbNeeded);
            if (pcbNeeded.getValue() <= 0) {
                return new PRINTER_INFO_9();
            }

            pinfo9 = new PRINTER_INFO_9(pcbNeeded.getValue());
            if (!Winspool.INSTANCE.GetPrinter(pHandle.getValue(), 9, pinfo9.getPointer(), pcbNeeded.getValue(), pcReturned)) {
                throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
            }

            pinfo9.read();
        } catch (Win32Exception e) {
            we = e;
        } finally {
            if (!Winspool.INSTANCE.ClosePrinter(pHandle.getValue())) {
                Win32Exception ex = new Win32Exception(Kernel32.INSTANCE.GetLastError());
                if (we != null) {
                    ex.addSuppressedReflected(we);
                }
            }
        }

        if (we != null) {
            throw we;
        }

        return pinfo9;
    }
}