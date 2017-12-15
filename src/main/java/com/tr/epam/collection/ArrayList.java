package main.java.com.tr.epam.collection;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static main.java.com.tr.epam.collection.exception.ExceptionMessage.INDEX_OUT_OF_BOUND_EXCEPTION;

public class ArrayList<T> implements List<T>, Cloneable, Serializable {

    private final static int DEFAULT_LENGTH = 10;
    private Object[] elements;
    private int size;

    public ArrayList() {
        elements = new Object[DEFAULT_LENGTH];
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUND_EXCEPTION);
        }
        return (T) elements[index];
    }

    @Override
    public boolean add(T element) {
        size++;
        if (size > elements.length) {
            expand(DEFAULT_LENGTH);
        }
        elements[size - 1] = element;
        return true;
    }

    @Override
    public T set(int index, T element) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUND_EXCEPTION);
        }
        elements[index] = element;
        return (T) elements[index];
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        addAll(size, c);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        int expandSize = c.size();
        expand(expandSize);
        int i = index;
        for (T element : c) {
            elements[i] = element;
            i++;
        }
        size = size + expandSize;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        System.arraycopy(elements, index + 1, elements, index, size - index + 1);
        size--;
        return true;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUND_EXCEPTION);
        }
        T value = (T) elements[index];
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        size--;
        return value;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object element : c) {
            remove(element);
        }
        return true;
    }

    @Override
    public void clear() {
        for (int index = 0; index < size; index++) {
            elements[index] = null;
        }
        size = 0;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for (int i = 0; i < size; i++) {
            if (!c.contains(elements[i])) {
                remove(i);
            }
        }
        return true;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    public <E> E[] toArray(E[] a) {
        if (a.length < size) {
            for (int i = 0; i < a.length; i++) {
                a[i] = (E) elements[i];
            }
        } else {
            for (int i = 0; i < size; i++) {
                a[i] = (E) elements[i];
            }
        }
        return a;
    }

    @Override
    public java.util.List subList(int from, int to) {
        if (from < 0 || to > size || from > to) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUND_EXCEPTION);
        }
        return Arrays.asList(Arrays.copyOfRange(elements, from, to));
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (o.equals(elements[i])) {
                index = i;
            }
        }
        return index;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(elements[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void expand(int size) {
        elements = Arrays.copyOf(elements, elements.length + size);
    }

    private class ArrayListIterator implements Iterator<T> {

        private int index;
        int lastReturnedIndex;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            if (hasNext()) {
                lastReturnedIndex = index;
                return (T) elements[index++];
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            ArrayList.this.remove(lastReturnedIndex);
            index--;
        }
    }

}
