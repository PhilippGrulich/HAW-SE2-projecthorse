package com.haw.projecthorse.inputmanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Eine spezielle Liste welche ihre Elemente über eine Priorität sortiert.
 * 
 * @author Philipp Grulich
 * @version 1.0
 *
 * @param <E>
 */
public class PriorityArray<E> implements Iterable<E> {

	List<Content<E>> list = new ArrayList<Content<E>>();
	int prio = 0;

	/**
	 * Die Elemente dieser Liste werden in diesem Object gespeichert.
	 * 
	 * @author Philipp
	 * @version 1.0
	 * @param <T>
	 */
	private class Content<T> implements Comparable<Content<T>> {
		private T content;
		public Integer priority;

		/**
		 * Konstruktor für ein neues Element.
		 * 
		 * @param prio
		 *            Prioriät
		 * @param element
		 *            Object.
		 */
		public Content(final int prio, final T element) {
			priority = prio;
			content = element;
		}

		public T getElement() {
			return content;
		}

		@Override
		public int compareTo(final Content<T> o) {
			return o.priority.compareTo(priority);
		}

	}

	/**
	 * Fügt ein neues Element in die Liste.
	 * 
	 * @param e
	 *            neues Element von Typ E
	 * @param prio
	 *            Priorität
	 * @return boolean
	 */
	public boolean add(final E e, final int prio) {
		return list.add(new Content<E>(prio, e));
	}

	/**
	 * Leert die Liste.
	 */
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

	/**
	 * Liefert die Listen Länge.
	 * 
	 * @return Länge.
	 */
	public int size() {
		return list.size();
	}

}
