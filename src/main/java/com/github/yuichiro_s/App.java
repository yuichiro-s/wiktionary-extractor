package com.github.yuichiro_s;

import org.apache.commons.cli.*;


public class App
{
    public static void main( String[] args )
    {
        CommandLineParser parser = new DefaultParser();
        try {
            Options options = new Options();
            options.addOption("lang", true, "language to extract");
            options.addOption("dump", true, "path to Wiktionary dump");
            CommandLine line = parser.parse(options, args);
            Language lang = Language.valueOf(line.getOptionValue("lang"));
            String dumpPath = line.getOptionValue("dump");
            new DumpReader(dumpPath, lang).extract();
        }
        catch(ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }
    }
}
