package xyz.dunjiao.cloud.commons.lang;

import com.google.zxing.common.BitMatrix;

import java.util.Collections;
import java.util.Objects;

/**
 * Utils to print QR code to console
 * @author allen.yuan@live.com
 * {@code} 2022/12/29 09:54
 */
public class QRCodeUtils {
    private static final String WHITE_WHITE = "█";
    private static final String BLACK_BLACK = " ";
    private static final String WHITE_BLACK = "▀";
    private static final String BLACK_WHITE = "▄";
    private static final int QUIET_ZONE = 2;
    private static final String BLACK = "\033[40m  \033[0m";
    private static final String WHITE = "\033[47m  \033[0m";

    public static void print(BitMatrix matrix, boolean small) {
        System.out.println(toString(matrix, small));
    }

    public static String toString(BitMatrix matrix, boolean small) {
        Objects.requireNonNull(matrix, "Missing argument: matrix");
        return small ? toSmallString(matrix) : toBigString(matrix);
    }

    private static String toBigString(BitMatrix matrix) {
        return matrix.toString(BLACK, WHITE);
    }

    private static String toSmallString(BitMatrix matrix) {
        StringBuilder writer = new StringBuilder();

        String header = String.join("", Collections.nCopies(matrix.getWidth() + QUIET_ZONE * 2, WHITE_WHITE));
        writer.append(String.join("", Collections.nCopies(QUIET_ZONE / 2, header + "\n")));

        for (int i = 0; i <= matrix.getWidth(); i += QUIET_ZONE) {
            writer.append(String.join("", Collections.nCopies(QUIET_ZONE, WHITE_WHITE)));
            for (int j = 0; j <= matrix.getWidth(); j++) {
                boolean nextBlack = i + 1 < matrix.getWidth() && matrix.get(j, i + 1);
                boolean currentBlack = matrix.get(j, i);
                if (currentBlack && nextBlack) {
                    writer.append(BLACK_BLACK);
                } else if (currentBlack) {
                    writer.append(BLACK_WHITE);
                } else if (!nextBlack) {
                    writer.append(WHITE_WHITE);
                } else {
                    writer.append(WHITE_BLACK);
                }
            }
            writer.append(String.join("", Collections.nCopies(QUIET_ZONE - 1, WHITE_WHITE)));
            writer.append("\n");
        }

        String trailing = String.join("", Collections.nCopies(matrix.getWidth() + QUIET_ZONE * 2, WHITE_BLACK));
        writer.append(String.join("", Collections.nCopies(QUIET_ZONE / 2 - 1, trailing)));
        writer.append("\n");
        return writer.toString();
    }
}
