package qz.printer_info_9;

import com.sun.jna.LastErrorException;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.W32Errors;
import com.sun.jna.platform.win32.WinNT.HRESULT;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Dummy class to expose addSuppressedReflected to a custom class
 */
public class Win32Exception extends LastErrorException {
    private static final long serialVersionUID = 1L;
    private HRESULT _hr;
    private static Method addSuppressedMethod = null;

    public HRESULT getHR() {
        return this._hr;
    }

    public Win32Exception(int code) {
        this(code, W32Errors.HRESULT_FROM_WIN32(code));
    }

    public Win32Exception(HRESULT hr) {
        this(W32Errors.HRESULT_CODE(hr.intValue()), hr);
    }

    protected Win32Exception(int code, HRESULT hr) {
        this(code, hr, Kernel32Util.formatMessage(hr));
    }

    protected Win32Exception(int code, HRESULT hr, String msg) {
        super(code, msg);
        this._hr = hr;
    }

    public void addSuppressedReflected(Throwable exception) {
        if (addSuppressedMethod != null) {
            try {
                addSuppressedMethod.invoke(this, exception);
            } catch (IllegalAccessException var3) {
                throw new RuntimeException("Failed to call addSuppressedMethod", var3);
            } catch (IllegalArgumentException var4) {
                throw new RuntimeException("Failed to call addSuppressedMethod", var4);
            } catch (InvocationTargetException var5) {
                throw new RuntimeException("Failed to call addSuppressedMethod", var5);
            }
        }
    }

    static {
        try {
            addSuppressedMethod = Throwable.class.getMethod("addSuppressed", Throwable.class);
        } catch (NoSuchMethodException var1) {
        } catch (SecurityException var2) {
            Logger.getLogger(com.sun.jna.platform.win32.Win32Exception.class.getName()).log(Level.SEVERE, "Failed to initialize 'addSuppressed' method", var2);
        }

    }
}
