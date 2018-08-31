package com.github.yuichiro_s;

import com.github.yuichiro_s.extractors.SpanishExtractor;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.github.yuichiro_s.Language.SPANISH;

class DumpReader extends DefaultHandler {
    private final String dumpPath;
    private final StringBuilder builder;
    private String currentTitle = null;
    private String sectionTitle = null;
    private Extractor extractor = null;

    DumpReader(String dumpPath, Language lang) {
        this.dumpPath = dumpPath;
        builder = new StringBuilder();
        if (lang == SPANISH) {
            sectionTitle = "==Spanish==";
            extractor = new SpanishExtractor();
        }
    }

    void extract() {
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            Reader reader = new InputStreamReader(new BufferedInputStream(new FileInputStream(dumpPath)), "UTF-8");
            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");
            parser.parse(is, this);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        builder.setLength(0);
    }

    @Override
    public void characters(char ch[], int start, int length) {
        builder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localname, String qName) {
        String s = builder.toString();
        process(qName, s);
    }

    private void process(String tag, String text) {
        if (tag.equals("title")) {
            currentTitle = text;
        } else if (tag.equals("text")) {
            processPage(currentTitle, text);
        }
    }

    private int getLevel(String title) {
        int level = -2;
        for (char c : title.toCharArray()) {
            if (c == '=') {
                level++;
            } else {
                return level;
            }
        }
        return level / 2;
    }

    private void processPage(String title, String text) {
        boolean inLang = false;
        List<Tree> levels = new ArrayList<Tree>();
        for (String line : text.split("\n")) {
            if (line.length() > 0) {
                if (line.startsWith("==")) {
                    // section start
                    int level = getLevel(line);
                    if (level == 0) {
                        inLang = line.equals(sectionTitle);
                    }
                    if (inLang) {
                        String name = line.substring(level + 2, line.length() - level - 2).trim();
                        Tree tree = new Tree(name);
                        if (levels.size() > level) {
                            levels.subList(level, levels.size()).clear();
                        }
                        if (levels.size() > 0) {
                            Tree parent = levels.get(levels.size() - 1);
                            parent.children.add(tree);
                        }
                        levels.add(tree);
                    }
                } else {
                    if (inLang) {
                        Tree level = levels.get(levels.size() - 1);
                        level.content.add(line);
                    }
                }
            }
        }
        if (levels.size() > 0) {
            extractor.extract(title, levels.get(0));
        }
    }
}
