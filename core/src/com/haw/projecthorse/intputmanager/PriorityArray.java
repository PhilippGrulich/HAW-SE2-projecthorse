package com.haw.projecthorse.intputmanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PriorityArray<E> implements Iterable<E> {
	
	List<Content<E>> list = new ArrayList<Content<E>>();
	int prio = 0;
	private class Content<E> implements Comparable<Content>{
		private E content; 
		public Integer priority;
		public Content(int prio, E element) {
			priority = prio;
			content = element;
		}

		public E getElement(){
			return content;
		}
		@Override
		public int compareTo(Content o) {
			return o.priority.compareTo(priority);
		}
		
	}
	
	

	
	public boolean add(E e,int prio) {
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
				return list.size()> index;
			}

			@Override
			public E next() {
				E e = list.get(index).getElement();
				index++;
				return e;
			}
		};
		return i;
	}

	
	
	public int size() {
		return list.size();
	}

	
}
