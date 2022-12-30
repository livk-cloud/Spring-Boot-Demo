package com.livk.commons.spring;

import com.livk.commons.util.DateUtils;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * <p>
 * LivkBanner
 * </p>
 *
 * @author livk
 */
@NoArgsConstructor(staticName = "create")
class LivkBanner implements Banner {

    private static final String banner = """
             ██       ██          ██         ██████   ██                       ██
            ░██      ░░          ░██        ██░░░░██ ░██                      ░██
            ░██       ██ ██    ██░██  ██   ██    ░░  ░██  ██████  ██   ██     ░██
            ░██      ░██░██   ░██░██ ██   ░██        ░██ ██░░░░██░██  ░██  ██████
            ░██      ░██░░██ ░██ ░████    ░██        ░██░██   ░██░██  ░██ ██░░░██
            ░██      ░██ ░░████  ░██░██   ░░██    ██ ░██░██   ░██░██  ░██░██  ░██
            ░████████░██  ░░██   ░██░░██   ░░██████  ███░░██████ ░░██████░░██████
            ░░░░░░░░ ░░    ░░    ░░  ░░     ░░░░░░  ░░░  ░░░░░░   ░░░░░░  ░░░░░░
            """;

    @SneakyThrows
    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.println(banner);
        int max = Arrays.stream(banner.split("\n")).mapToInt(String::length).max().orElse(0);
        max = max % 2 == 0 ? max : max + 1;
        Format format = Format.of(max, out);
        format.accept(" Spring Version: " + SpringVersion.getVersion() + " ");
        format.accept(" Spring Boot Version: " + SpringBootVersion.getVersion() + " ");
        format.accept(" Current time: " + DateUtils.format(LocalDateTime.now(), DateUtils.YMD_HMS) + " ");
        format.accept(" Current JDK Version: " + System.getProperty("java.version") + " ");
        format.accept(" Operating System: " + System.getProperty("os.name") + " ");
        out.flush();
    }

    @RequiredArgsConstructor(staticName = "of")
    private static class Format {

        private final static char ch = '*';
        private final int n;
        private final PrintStream out;

        public void accept(String str) {
            int length = str.length();
            if (length < n) {
                int index = (n - length) >> 1;
                str = StringUtils.leftPad(str, length + index, ch);
                str = StringUtils.rightPad(str, n, ch);
            }
            out.println(str);
        }
    }

}
