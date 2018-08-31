package com.github.yuichiro_s;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    public final String name;
    public final List<String> content;
    public final List<Tree> children;

    Tree(String name) {
        this.name = name;
        this.content = new ArrayList<String>();
        this.children = new ArrayList<Tree>();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        dfs(this, 0, builder);
        return builder.toString();
    }

    private void dfs(Tree tree, int level, StringBuilder builder) {
        for (int i = 0; i < level * 2; i++) {
            builder.append(" ");
        }
        for (int i = 0; i < level + 2; i++) {
            builder.append("=");
        }
        builder.append(tree.name);
        for (int i = 0; i < level + 2; i++) {
            builder.append("=");
        }
        builder.append("\n");
        for (String c : tree.content) {
            for (int i = 0; i < level * 2; i++) {
                builder.append(" ");
            }
            builder.append(c);
            builder.append("\n");
        }
        for (Tree t : tree.children) {
            dfs(t, level + 1, builder);
        }
    }
}
