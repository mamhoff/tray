package qz.printer_info_9;

import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

import static com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.Union;
import com.sun.jna.platform.win32.Winspool;

public interface WinGDI2 {
    @FieldOrder({ "dmDeviceName", "dmSpecVersion", "dmDriverVersion", "dmSize", "dmDriverExtra", "dmFields", "dmUnion1", "dmColor",
                  "dmDuplex", "dmYResolution", "dmTTOption", "dmCollate", "dmFormName", "dmLogPixels", "dmBitsPerPel", "dmPelsWidth",
                  "dmPelsHeight", "dummyunionname2", "dmDisplayFrequency", "dmICMMethod", "dmICMIntent", "dmMediaType", "dmDitherType",
                  "dmReserved1", "dmReserved2", "dmPanningWidth", "dmPanningHeight" })
    public static class DEVMODE extends Structure {
        public static class ByReference extends DEVMODE implements Structure.ByReference {}
        private static final int CHAR_WIDTH = Boolean.getBoolean("w32.ascii") ? 1 : 2;

        public byte[] dmDeviceName = new byte[Winspool.CCHDEVICENAME * CHAR_WIDTH];
        public WORD dmSpecVersion;
        public WORD dmDriverVersion;
        public WORD dmSize;
        public WORD dmDriverExtra;
        public DWORD dmFields;
        public DUMMYUNIONNAME dmUnion1;

        public static class DUMMYUNIONNAME extends Union {
            public DUMMYSTRUCTNAME dummystructname;

            @FieldOrder({ "dmOrientation", "dmPaperSize", "dmPaperLength", "dmPaperWidth", "dmScale", "dmCopies", "dmDefaultSource",
                          "dmPrintQuality" })
            public static class DUMMYSTRUCTNAME extends Structure {
                public short dmOrientation;
                public short dmPaperSize;
                public short dmPaperLength;
                public short dmPaperWidth;
                public short dmScale;
                public short dmCopies;
                public short dmDefaultSource;
                public short dmPrintQuality;

                public DUMMYSTRUCTNAME() {
                    super();
                }
            }

            public POINT dmPosition;
            public DUMMYSTRUCTNAME2 dummystructname2;

            @FieldOrder({ "dmPosition", "dmDisplayOrientation", "dmDisplayFixedOutput" })
            public static class DUMMYSTRUCTNAME2 extends Structure {
                public POINT dmPosition;
                public DWORD dmDisplayOrientation;
                public DWORD dmDisplayFixedOutput;

                public DUMMYSTRUCTNAME2() {
                    super();
                }
            }
        }

        public short dmColor;
        public short dmDuplex;
        public short dmYResolution;
        public short dmTTOption;
        public short dmCollate;
        public byte[] dmFormName = new byte[Winspool2.CCHFORMNAME * CHAR_WIDTH];
        public WORD dmLogPixels;
        public DWORD dmBitsPerPel;
        public DWORD dmPelsWidth;
        public DWORD dmPelsHeight;
        public DUMMYUNIONNAME2 dummyunionname2;

        public static class DUMMYUNIONNAME2 extends Union {
            public DWORD dmDisplayFlags;
            public DWORD dmNup;
        }

        public DWORD dmDisplayFrequency;
        public DWORD dmICMMethod;
        public DWORD dmICMIntent;
        public DWORD dmMediaType;
        public DWORD dmDitherType;
        public DWORD dmReserved1;
        public DWORD dmReserved2;
        public DWORD dmPanningWidth;
        public DWORD dmPanningHeight;
    }
}