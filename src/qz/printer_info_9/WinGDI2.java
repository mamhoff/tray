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
        public short dmSpecVersion;
        public short dmDriverVersion;
        public short dmSize;
        public short dmDriverExtra;
        public int dmFields;
        public DUMMYUNIONNAME dmUnion1;

        /**
         * Converts dmDeviceName from raw byte[] to String
         */
        public String getDmDeviceName() {
            int offset = fieldOffset("dmDeviceName");
            return CHAR_WIDTH == 1 ? getPointer().getString(offset) : getPointer().getWideString(offset);
        }

        /**
         * Converts dmFormName from raw byte[] to String
         */
        public String getDmFormName() {
            int offset = fieldOffset("dmFormName");
            return CHAR_WIDTH == 1 ? getPointer().getString(offset) : getPointer().getWideString(offset);
        }

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
                public int dmDisplayOrientation;
                public int dmDisplayFixedOutput;

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
        public short dmLogPixels;
        public int dmBitsPerPel;
        public int dmPelsWidth;
        public int dmPelsHeight;
        public DUMMYUNIONNAME2 dummyunionname2;

        public static class DUMMYUNIONNAME2 extends Union {
            public int dmDisplayFlags;
            public int dmNup;
        }

        public int dmDisplayFrequency;
        public int dmICMMethod;
        public int dmICMIntent;
        public int dmMediaType;
        public int dmDitherType;
        public int dmReserved1;
        public int dmReserved2;
        public int dmPanningWidth;
        public int dmPanningHeight;
    }
}