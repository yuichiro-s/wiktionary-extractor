package com.github.yuichiro_s;

public class Extractor {
    private final String dumpPath;
    private final Language lang;

    public Extractor(String dumpPath, Language lang) {
        this.dumpPath = dumpPath;
        this.lang = lang;
    }

    public void extract() {
        System.out.println(this.dumpPath);
        System.out.println(this.lang);
    }
}
