package main.java.com.tr.epam.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static main.java.com.tr.epam.collection.exception.ExceptionMessage.INDEX_OUT_OF_BOUND_EXCEPTION;

public class LinkedList<T> implements List<T>, Cloneable, Serializable {

    private int size;
    private Node<T> first;
    private Node<T> last;

    @Override
    public T get(int index) {
        Node<T> node = getNode(index);
        return node.element;
    }

    private Node<T> getNode(int index) {
        if (index >= 0 && index < size) {
            Node<T> result = first;
            for (int i = 0; i < index; i++) {
                result = result.next;
            }
            return result;
        } else {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUND_EXCEPTION);
        }
    }

    private Node<T> getNode(Object element) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (currentNode.element.equals(element)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    @Override
    public boolean add(T element) {
        Node<T> penult = last;
        Node<T> newNode = new Node<T>(element, null, penult);
        last = newNode;
        if (size == 0) {
            first = newNode;
        } else {
            penult.next = newNode;
        }
        size++;
        return true;
    }

    @Override
    public T set(int index, T element) {
        Node<T> node = getNode(index);
        node.element = element;
        return element;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T element : c) {
            add(element);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        Node<T> to = getNode(index);
        Node<T> from = to.prev;
        for (T element : c) {
            Node<T> newElement = new Node<>(element, to, null);
            if (from != null) {
                from.next = newElement;
            } else {
                first = newElement;
            }
            from = newElement;
            size++;
        }
        from.next = to;
        to.prev = from;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node<T> node = getNode(o);
        if (removeNode(node) != null) {
            return true;
        }
        return false;
    }

    @Override
    public T remove(int index) {
        Node<T> node = getNode(index);
        node = removeNode(node);
        if (node != null) {
            return node.element;
        }
        return null;
    }

    private Node<T> removeNode(Node<T> node) {
        if (node != null) {
            if (node.prev != null) {
                if (node.next != null) {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                } else {
                    node.prev.next = null;
                    last = node.prev;
                }
            } else {
                if (node.next != null) {
                    node.next.prev = null;
                    first = node.next;
                }
            }
            size--;
        }
        return node;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            remove(o);
        }
        return true;
    }

    @Override
    public void clear() {
        Node<T> currentNode = first;
        while (currentNode != null) {
            Node<T> deleted = currentNode;
            currentNode = currentNode.next;
            deleted.next = null;
            deleted.prev = null;
            deleted.element = null;
        }
        first = last = null;
        size = 0;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Node<T> currentNode = first;
        while (currentNode != null) {
            Node<T> suspect = currentNode;
            currentNode = currentNode.next;
            T element = suspect.element;
            if (!c.contains(element)) {
                removeNode(suspect);
            }
        }
        return true;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            result[i] = node.element;
            node = node.next;
        }
        return result;
    }

    @Override
    public <E> E[] toArray(E[] a) {
        Node<T> currentNode = first;
        for (int i = 0; i < a.length; i++) {
            ((Object[]) a)[i] = currentNode.element;
            currentNode = currentNode.next;
        }
        return a;
    }

    @Override
    public java.util.List subList(int from, int to) {
        java.util.List list = new ArrayList<>();
        if (from > size() || from < 0 || to > size() || to < 0 || from > to) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUND_EXCEPTION);
        }
        Node<T> startNode = getNode(from);
        for (int i = from; i < to; i++) {
            list.add(startNode.element);
            startNode = startNode.next;
        }
        return list;
    }

    @Override
    public int indexOf(Object o) {
        int currentIndex = 0;
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (currentNode.element.equals(o)) {
                return currentIndex;
            }
            currentNode = currentNode.next;
            currentIndex++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int lastIndex = -1;
        int currentIndex = 0;
        Node<T> currentNode = first;
        while (currentNode != null) {
            if (currentNode.element.equals(o)) {
                lastIndex = currentIndex;
            }
            currentNode = currentNode.next;
            currentIndex++;
        }
        return lastIndex;
    }

    @Override
    public boolean contains(Object o) {
        return getNode(o) != null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < size; i++) {
            Node<T> node = getNode(i);
            result += node.element + "\t";
        }
        return result;
    }

    private class LinkedListIterator implements Iterator<T> {

        private int index;
        private Node<T> current = first;
        private Node<T> lastReturned;


        @Override
        public boolean hasNext() {
            return index < size - 1;
        }

        @Override
        public T next() {
            if (hasNext()) {
                current = current.next;
                lastReturned = current;
                index++;
                return current.element;
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            removeNode(lastReturned);
            index--;
        }
    }

    private static class Node<E> {

        private E element;
        private Node<E> next;
        private Node<E> prev;

        Node(E element, Node<E> next, Node<E> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }

    }

}
