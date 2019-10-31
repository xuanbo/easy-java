package tk.fishfish.easyjava.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * 文件类型工具
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public final class FileTypeUtils {

    private static final int LEN = 28;

    private FileTypeUtils() {
        throw new IllegalStateException("Utils");
    }

    public static boolean isType(BufferedInputStream is, FileType fileType) throws IOException {
        Objects.requireNonNull(fileType);

        String header = getFileHeader(is);
        return header.startsWith(fileType.getValue());
    }

    private static String getFileHeader(BufferedInputStream is) throws IOException {
        Objects.requireNonNull(is);

        // 标识
        is.mark(LEN + 1);

        byte[] b = new byte[LEN];
        try {
            int num = is.read(b, 0, LEN);
            if (num < LEN) {
                throw new RuntimeException("can not read file head");
            }
        } finally {
            // 重置，否则无法重复读取流
            is.reset();
        }
        return bytes2hex(b);
    }

    private static String bytes2hex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv.toUpperCase());
        }
        return builder.toString();
    }

}
