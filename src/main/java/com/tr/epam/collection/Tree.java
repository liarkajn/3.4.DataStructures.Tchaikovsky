package main.java.com.tr.epam.collection;

import java.util.List;

public interface Tree<E extends Comparable<E>> {

    boolean add(E element);

    E remove(E element);

    List<E> preOrder();

    List<E> inOrder();

    List<E> postOrder();

    boolean contains(E element);

    int size();

    boolean isEmpty();

}
