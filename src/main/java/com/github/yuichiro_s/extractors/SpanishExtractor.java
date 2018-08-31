package com.github.yuichiro_s.extractors;

import com.github.yuichiro_s.Extractor;
import com.github.yuichiro_s.Tree;

public class SpanishExtractor implements Extractor {
    public void extract(String name, Tree tree) {
        dfs(name, tree);
    }

    private void dfs(String name, Tree tree) {
        if (tree.name.startsWith("Noun")) {
            process(tree);
        } else if (tree.name.startsWith("Verb")) {
            process(tree);
        } else if (tree.name.startsWith("Adjective")) {
            process(tree);
        } else if (tree.name.startsWith("Adverb")) {
            process(tree);
        } else if (tree.name.equals("Preposition")) {
            process(tree);
        } else if (tree.name.equals("Proper noun")) {
            process(tree);
        } else if (tree.name.equals("Pronoun")) {
            process(tree);
        } else if (tree.name.equals("Conjunction")) {
            process(tree);
        } else if (tree.name.equals("Article")) {
            process(tree);
        } else if (tree.name.equals("Phrase")) {
            process(tree);
        } else if (tree.name.equals("Particle")) {
            process(tree);
        } else if (tree.name.equals("Interjection")) {
            process(tree);
        } else if (tree.name.equals("Abbreviation")) {
            process(tree);
        } else if (tree.name.equals("Initialism")) {
            process(tree);
        } else if (tree.name.equals("Contraction")) {
            process(tree);
        }
        for (Tree child : tree.children) {
            dfs(name, child);
        }
    }

    private void process(Tree tree) {

    }
}
