package eu.marrat.adventofcode2024.day07;

import eu.marrat.adventofcode2024.util.Utils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static eu.marrat.adventofcode2024.util.Utils.getLines;

public class Day07 {

    public static void main(String[] args) {
        long sum = getLines("day07/input.txt")
                .map(line -> {
                    String[] split = StringUtils.split(line, ":");

                    long result = Long.parseLong(split[0]);
                    long[] operands = Utils.toLongArray(StringUtils.split(split[1], " "));

                    return new Equation(result, operands);
                })
                .filter(Equation::canBeSolved)
                .mapToLong(Equation::result)
                .sum();

        System.out.println(sum);
    }

    record Equation(long result, long[] operands) {

        public boolean canBeSolved() {
            TreeNode tree = createTree(TreeNode.create(operands[0]), 1);

            return tree.getLeaves()
                    .stream().mapToLong(e -> e.result)
                    .filter(e -> e == result).findAny().isPresent();
        }

        private TreeNode createTree(TreeNode parent, int index) {
            if (index >= operands.length) {
                return parent;
            }

            for (Operation operation : Operation.values()) {
                parent.addChild(operands[index], operation);
            }

            parent.getChildren().forEach(child -> createTree(child, index + 1));

            return parent;
        }
    }

    enum Operation {
        ADD {
            @Override
            public long perform(long operand1, long operand2) {
                return operand1 + operand2;
            }
        },
        MULTIPLY {
            @Override
            public long perform(long operand1, long operand2) {
                return operand1 * operand2;
            }
        },
        CONCAT {
            @Override
            public long perform(long operand1, long operand2) {
                return Long.parseLong(Long.toString(operand1) + Long.toString(operand2));
            }
        };

        public abstract long perform(long operand1, long operand2);
    }

    static class TreeNode {

        public final long operand;

        public final long result;

        private final List<TreeNode> children = new ArrayList<>();

        private TreeNode(TreeNode parent, Operation operationWithParent, long operand) {
            this.operand = operand;

            if (parent == null) {
                result = operand;
            } else {
                result = operationWithParent.perform(parent.result, operand);
            }
        }

        public TreeNode addChild(long operand, Operation operation) {
            TreeNode childNode = new TreeNode(this, operation, operand);

            children.add(childNode);

            return childNode;
        }

        public List<TreeNode> getChildren() {
            return Collections.unmodifiableList(children);
        }

        public List<TreeNode> getLeaves() {
            List<TreeNode> leaves = new ArrayList<>();

            addLeaves(leaves);

            return leaves;
        }

        private void addLeaves(List<TreeNode> leaves) {
            if (children.isEmpty()) {
                leaves.add(this);
            } else {
                children.forEach(child -> child.addLeaves(leaves));
            }
        }

        public static TreeNode create(long operand) {
            return new TreeNode(null, null, operand);
        }

    }

}
