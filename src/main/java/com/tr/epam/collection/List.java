package main.java.com.tr.epam.collection;

import java.util.Collection;

public interface List<E> extends Collection<E> {

    E get(int index);

    E set(int index, E element);

    boolean add(E element);

    E remove(int index);

    boolean addAll(int index, Collection<? extends E> c);

    int indexOf(Object o);

    int lastIndexOf(Object o);

    java.util.List<E> subList(int from, int to);

}
