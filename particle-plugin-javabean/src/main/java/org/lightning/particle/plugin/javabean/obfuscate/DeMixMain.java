package org.lightning.particle.plugin.javabean.obfuscate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * Created by cook on 2019/3/7
 */
public class DeMixMain {

    public static void main(String[] args) throws IOException {
        String x = "Ǵ";
        System.out.println("u" + Integer.toHexString(x.charAt(0)) + ", " + x.charAt(0) + ", " + ((int) x.charAt(0)) + ", hex is U" + toUnicodeHex((int) x.charAt(0)));
        System.out.println("len=" + x.length() + ", " + x.toCharArray().length);
        System.out.println("- [" + (char) Integer.parseInt("1F", 16) + "] ");

        String dir = "/work/bid/202001/scripts";

        String targetDir = "/work/bid/202001/flash-src-generate";

        List<Path> paths = Files.find(Paths.get(dir), 5, (o, o2) -> o2.isRegularFile())
                .collect(Collectors.toList());

        for (Path pa : paths) {
            String fName = pa.getFileName().toString();
            if (!fName.endsWith(".as")) {
                continue;
            }

            try {

                String part = pa.toAbsolutePath().toString().substring(dir.length());
                String newFName = toReadableText(part);

                String newPath = targetDir + newFName;

                System.out.println("file is " + pa + ", newPath is " + newPath);

                List<String> lines = Files.readAllLines(pa);
                List<String> nLines = new ArrayList<>(lines.size());

                for (String line : lines) {
                    nLines.add(toReadableText(line));
                }

                Path nPath = Paths.get(newPath);
                Files.deleteIfExists(nPath);

                if (Files.notExists(nPath.getParent())) {
                    Files.createDirectories(nPath.getParent());
                }

                Files.createFile(nPath);

                Files.write(nPath, nLines);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private static String toReadableText(String line) {
        StringBuilder sb = new StringBuilder(line.length());
        for (char c : line.toCharArray()) {
            int v = (int) c;
            if (c > 0x007E && c < 0x2E50) {
                sb.append(toUnicodeHex(v));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static String toUnicodeHex(int c) {
        return String.format("U%04X", c & 0xFFFF);
    }

}
