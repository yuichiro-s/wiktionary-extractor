package com.github.yuichiro_s;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

class Extractor extends DefaultHandler {
    private final Language lang;
    private final StringBuilder builder;
    private String currentTitle;

    Extractor(Language lang) {
        this.lang = lang;
        this.builder = new StringBuilder();
        this.currentTitle = null;
    }

    void extract() {
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            Reader reader = new InputStreamReader(new BufferedInputStream(System.in), "UTF-8");
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

    private void processPage(String title, String text) {
        System.out.print("=================");
        System.out.print(title);
        System.out.print("=================");
        System.out.println();
        System.out.println(text);
    }
}
