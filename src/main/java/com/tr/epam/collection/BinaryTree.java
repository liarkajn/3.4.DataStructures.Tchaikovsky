package main.java.com.tr.epam.collection;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree<T extends Comparable<T>> implements Tree<T> {

    private Node<T> root;

    @Override
    public boolean add(T element) {
        if (root == null) {
            root = new Node<>(element);
        } else {
            return placeElement(root, element);
        }
        return true;
    }

    private boolean placeElement(Node<T> parent, T element) {
        Node<T> current = parent;
        if (current.value.compareTo(element) > 0) {
            if (current.left != null) {
                current = current.left;
                placeElement(current, element);
            } else {
                Node<T> node = new Node<>(element);
                current.left = node;
                node.parent = current;
            }
        } else if (current.value.compareTo(element) < 0) {
            if (current.right != null) {
                current = current.right;
                placeElement(current, element);
            } else {
                Node<T> node = new Node<>(element);
                current.right = node;
                node.parent = current;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public T remove(T element) {
        if (root == null) {
            return null;
        }
        return remove(root, element);
    }

    private T remove(Node<T> node, T element) {

        if (node.value.equals(element)) {
            remove(node);
            return node.value;
        } else {
            if (node.value.compareTo(element) > 0) {
                if (node.left != null) {
                    return remove(node.left, element);
                }
                return null;
            } else {
                if (node.right != null) {
                    return remove(node.right, element);
                }
                return null;
            }
        }

    }

    private void remove(Node<T> node) {

        if (node.left == null && node.right == null) {

            if (node.parent != null) {
                Node<T> parent = node.parent;
                if (parent.left == node) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                node.parent = null;
                node.value = null;
            }

        } else if (node.right == null) {

            Node<T> parent = node.parent;
            if (parent.left == node) {
                parent.left = node.left;
                node.left.parent = parent;
            } else {
                parent.right = node.left;
                node.left.parent = parent;
            }
            node.parent = null;
            node.left = null;
            node.value = null;

        } else if (node.left == null) {

            Node<T> parent = node.parent;
            if (parent.left == node) {
                parent.left = node.right;
                node.right.parent = parent;
            } else {
                parent.right = node.right;
                node.right.parent = parent;
            }
            node.parent = null;
            node.right = null;
            node.value = null;

        } else {

            if (node.right.left == null) {
                node.value = node.right.value;
                remove(node.right);
            } else {
                Node<T> replace = node.right;
                while (replace.left != null) {
                    replace = replace.left;
                }
                node.value = replace.value;
                remove(replace);
            }

        }

    }

    @Override
    public List<T> preOrder() {
        List<T> result = new ArrayList<>();
        preOrder(root, result);
        return result;
    }

    private List<T> preOrder(Node<T> node, List<T> result) {
        if (node != null) {
            result.add(node.value);
            preOrder(node.left, result);
            preOrder(node.right, result);
        }
        return result;
    }

    @Override
    public List<T> inOrder() {
        List<T> result = new ArrayList<>();
        inOrder(root, result);
        return result;
    }

    private void inOrder(Node<T> node, List<T> result) {
        if (node != null) {
            inOrder(node.left, result);
            result.add(node.value);
            inOrder(node.right, result);
        }
    }

    @Override
    public List<T> postOrder() {
        List<T> result = new ArrayList<>();
        postOrder(root, result);
        return result;
    }

    private List<T> postOrder(Node<T> node, List<T> result) {
        if (node != null) {
            postOrder(node.left, result);
            postOrder(node.right, result);
            result.add(node.value);
        }
        return result;
    }

    @Override
    public int size() {
        return inOrder().size();
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(T element) {
        return findNode(root, element) != null;
    }

    private Node<T> findNode(Node<T> node, T element) {
        if (node.value.equals(element)) {
            remove(node);
            return node;
        } else {
            if (node.value.compareTo(element) > 0) {
                if (node.left != null) {
                    return findNode(node.left, element);
                }
                return null;
            } else {
                if (node.right != null) {
                    return findNode(node.right, element);
                }
                return null;
            }
        }
    }

    private static class Node<E extends Comparable<E>> {

        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;
        private E value;

        Node(E value) {
            this.value = value;
            this.parent = null;
            this.left = null;
            this.right = null;
        }

    }

}
