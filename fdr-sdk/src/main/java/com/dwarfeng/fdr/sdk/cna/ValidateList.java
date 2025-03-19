package com.dwarfeng.fdr.sdk.cna;

import javax.validation.Valid;
import java.util.*;
import java.util.function.UnaryOperator;

/**
 * 带有校验功能的列表。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class ValidateList<E> extends AbstractList<E> implements List<E> {

    @Valid
    private List<E> delegate = new LinkedList<>();

    public ValidateList() {
    }

    public ValidateList(List<E> delegate) {
        this.delegate = delegate;
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return delegate.contains(o);
    }

    // 代理方法，忽略所有警告。
    @SuppressWarnings("NullableProblems")
    @Override
    public Iterator<E> iterator() {
        return delegate.iterator();
    }

    // 代理方法，忽略所有警告。
    @SuppressWarnings("NullableProblems")
    @Override
    public Object[] toArray() {
        return delegate.toArray();
    }

    // 代理方法，忽略所有警告。
    @SuppressWarnings({"NullableProblems"})
    @Override
    public <T> T[] toArray(T[] a) {
        return delegate.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return delegate.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return delegate.remove(o);
    }

    // 代理方法，忽略所有警告。
    @SuppressWarnings({"NullableProblems", "SlowListContainsAll"})
    @Override
    public boolean containsAll(Collection<?> c) {
        return delegate.containsAll(c);
    }

    // 代理方法，忽略所有警告。
    @SuppressWarnings("NullableProblems")
    @Override
    public boolean addAll(Collection<? extends E> c) {
        return delegate.addAll(c);
    }

    // 代理方法，忽略所有警告。
    @SuppressWarnings("NullableProblems")
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return delegate.addAll(index, c);
    }

    // 代理方法，忽略所有警告。
    @SuppressWarnings("NullableProblems")
    @Override
    public boolean removeAll(Collection<?> c) {
        return delegate.removeAll(c);
    }

    // 代理方法，忽略所有警告。
    @SuppressWarnings("NullableProblems")
    @Override
    public boolean retainAll(Collection<?> c) {
        return delegate.retainAll(c);
    }

    // 代理方法，忽略所有警告。
    @SuppressWarnings("NullableProblems")
    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        delegate.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super E> c) {
        delegate.sort(c);
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public E get(int index) {
        return delegate.get(index);
    }

    @Override
    public E set(int index, E element) {
        return delegate.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        delegate.add(index, element);
    }

    @Override
    public E remove(int index) {
        return delegate.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return delegate.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return delegate.lastIndexOf(o);
    }

    // 代理方法，忽略所有警告。
    @SuppressWarnings("NullableProblems")
    @Override
    public ListIterator<E> listIterator() {
        return delegate.listIterator();
    }

    // 代理方法，忽略所有警告。
    @SuppressWarnings("NullableProblems")
    @Override
    public ListIterator<E> listIterator(int index) {
        return delegate.listIterator(index);
    }

    // 代理方法，忽略所有警告。
    @SuppressWarnings("NullableProblems")
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return delegate.subList(fromIndex, toIndex);
    }

    // 代理方法，忽略所有警告。
    @SuppressWarnings("NullableProblems")
    @Override
    public Spliterator<E> spliterator() {
        return delegate.spliterator();
    }

    public List<E> getDelegate() {
        return delegate;
    }

    public void setDelegate(List<E> delegate) {
        this.delegate = delegate;
    }
}
