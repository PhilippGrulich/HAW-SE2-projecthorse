package com.haw.projecthorse.inputmanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * Eine Spezielle lieste welche ihre Elemente �ber eine Priorit�t sortiert
 * @author Philipp
 *
 * @param <E>
 */
public class PriorityArray<E> implements Iterable<E> {

	List<Content<E>> list = new ArrayList<Content<E>>();
	int prio = 0;

	private class Content<T> implements Comparable<Content<T>> {
		private T content;
		public Integer priority;

		public Content(int prio, T element) {
			priority = prio;
			content = element;
		}

		public T getElement() {
			return content;
		}

		@Override
		public int compareTo(Content<T> o) {
			return o.priority.compareTo(priority);
		}

	}

	public boolean add(E e, int prio) {
		return list.add(new Content<E>(prio, e));
	}

	public void clear() {
		list.clear();

	}

	@Override
	public Iterator<E> iterator() {
		Collections.sort(list);
		Iterator<E> i = new Iterator<E>() {
			int index = 0;

			@Override
			public boolean hasNext() {
				return list.size() > index;
			}

			@Override
			public E next() {
				E e = list.get(index).getElement();
				index++;
				return e;
			}

			@Override
			public void remove() {
				list.remove(index);

			}
		};
		return i;
	}

	public int size() {
		return list.size();
	}

}
