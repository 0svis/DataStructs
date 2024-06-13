/**
 * @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 09 23
 * Koreguota: Aleksas Riškus, MIK, 2016
 * Tai pirmoji duomenų struktūros klasė, kuri leidžia apjungti
 *   atskirus objektus į vieną visumą - sąrašą.
 * Objektai, kurie bus dedami į sąrašą, turi įdiegti interfeisą Comparable<E>.
 *
 * Užduotis: 1. Peržiūrėkite ir išsiaiškinkite pateiktus metodus. 
 *			 2. Papildykite klasę naujais metodais, kuriuos aprašėte interfeise ListADT.
 * ****************************************************************************
 */
package studijosKTU;

import Lab2Zilinskas.Kompiuteris;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Pagrindinių sąrašo operacijų klasė.
 *
 * @param <E> Sąrašo elementų tipas (klasė)
 */
public class ListKTU<E extends Comparable<E>>
		implements ListADT<E>, Iterable<E>, Cloneable {

	private Node<E> first;   // rodyklė į pirmą mazgą
	private Node<E> last;    // rodyklė į paskutinį mazgą
	private Node<E> current; // rodyklė į einamąjį mazgą, naudojama metode getNext
	private int size;        // sąrašo dydis, tuo pačiu elementų skaičius

	/**
	 * Metodas add įdeda elementą į sąrašo pabaigą
	 *
	 * @param e - tai įdedamas elementas (jis negali būti null)
	 * @return true, jei operacija atlikta sėkmingai
	 */
	@Override
	public boolean add(E e) {
		if (e == null) {
			return false;   // nuliniai objektai nepriimami
		}
		if (first == null) {
			first = new Node<>(e, first);
			last = first;
		} else {
			Node<E> e1 = new Node(e, null);
			last.next = e1;
			last = e1;
		}
		size++;
		return true;
	}

	/**
	 *
	 * @return sąrašo dydis (elementų kiekis)
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Patikrina ar sąrašas yra tuščias
	 *
	 * @return true, jei tuščias
	 */
	@Override
	public boolean isEmpty() {
		return first == null;
	}

	/**
	 * Išvalo sąrašą
	 */
	@Override
	public void clear() {
		size = 0;
		first = null;
		last = null;
		current = null;
	}

	/**
	 * Grąžina elementą pagal jo indeksą (pradinis indeksas 0)
	 *
	 * @param k indeksas
	 * @return k-ojo elemento reikšmę; jei k yra blogas, gąžina null
	 */
	@Override
	public E get(int k) {
		if (k < 0 || k >= size) {
			return null;
		}
		current = first.findNode(k);
		return current.element;
	}

	/**
	 * Pereina prie kitos reikšmės ir ją grąžina
	 *
	 * @return kita reikšmė
	 */
	@Override
	public E getNext() {
		if (current == null) {
			return null;
		}
		current = current.next;
		if (current == null) {
			return null;
		}
		return current.element;
	}

	/**
	 * Sukuria sąrašo kopiją.
	 * @return sąrašo kopiją
	 */
	@Override
	public ListKTU<E> clone() {
		ListKTU<E> cl = null;
		try {
			cl = (ListKTU<E>) super.clone();
		} catch (CloneNotSupportedException e) {
			Ks.ern("Blogai veikia ListKTU klasės protėvio metodas clone()");
			System.exit(1);
		}
		if (first == null) {
			return cl;
		}
		cl.first = new Node<>(this.first.element, null);
		Node<E> e2 = cl.first;
		for (Node<E> e1 = first.next; e1 != null; e1 = e1.next) {
			e2.next = new Node<>(e1.element, null);
			e2 = e2.next;
		}
		cl.last = e2;
		cl.size = this.size;
		return cl;
	}
	
	/**
	 * Formuojamas Object masyvas (E tipo masyvo negalima sukurti), 
	 *   kur surašomi sąrašo elementai
	 *
	 * @return sąrašo elementų masyvas
	 */
	@Override
	public Object[] toArray() {
		Object[] a = new Object[size];
		int i = 0;
		for (Node<E> e1 = first; e1 != null; e1 = e1.next) {
			a[i++] = e1.element;
		}
		return a;
	}

	/**
	 * Masyvo rikiavimas Arrays klasės metodu sort
	 */
	public void sortJava() {
		Object[] a = this.toArray();
		Arrays.sort(a);
		int i = 0;
		for (Node<E> e1 = first; e1 != null; e1 = e1.next) {
			e1.element = (E) a[i++];
		}
	}

	/**
	 * Rikiavimas Arrays klasės metodu sort pagal komparatorių
	 *
	 * @param c komparatorius
	 */
	public void sortJava(Comparator<E> c) {
		Object[] a = this.toArray();
		Arrays.sort(a, (Comparator) c);
		int i = 0;
		for (Node<E> e1 = first; e1 != null; e1 = e1.next) {
			e1.element = (E) a[i++];
		}
	}

	/**
	 * Sąrašo rikiavimas burbuliuko metodu
	 */
	public void sortBuble() {
		if (first == null) {
			return;
		}
		for (;;) {
			boolean jauGerai = true;
			Node<E> e1 = first;
			for (Node<E> e2 = first.next; e2 != null; e2 = e2.next) {
				if (e1.element.compareTo(e2.element) > 0) {
					E t = e1.element;
					e1.element = e2.element;
					e2.element = t;
					jauGerai = false;
				}
				e1 = e2;
			}
			if (jauGerai) {
				return;
			}
		}
	}

	/**
	 * Sąrašo rikiavimas burbuliuko metodu pagal komparatorių
	 *
	 * @param c komparatorius
	 */
	public void sortBuble(Comparator<E> c) {
		if (first == null) {
			return;
		}
		for (;;) {
			boolean jauGerai = true;
			Node<E> e1 = first;
			for (Node<E> e2 = first.next; e2 != null; e2 = e2.next) {
				if (c.compare(e1.element, e2.element) > 0) {
					E t = e1.element;
					e1.element = e2.element;
					e2.element = t;
					jauGerai = false;
				}
				e1 = e2;
			}
			if (jauGerai) {
				return;
			}
		}
	}

        	//--------------------------------------------

	/**
	 * Metodas pašalinantis elementą pagal indeksą
	 * @param k elemento indeksas
	 * @return
	 */
	@Override
	public E remove(int k){
		if(k<0 || k >= size) return null;
		Node<E> actual = null;
		if(k == 0){
			actual = first;
			first = actual.next;
			if(first  == null){
				last = null;
			}
		} else {
			Node<E> previous = first.findNode(k-1);
			actual = previous.next;
			previous.next = actual.next;
			if(actual.next == null){
				last = previous;
			}
		}
		size--;
		return actual.element;
	}

	/**
	 * Metodas nustatantis elemento duomenis bei indeksą
	 * @param index indeksas
	 * @param element elementas
	 * @return
	 */
	@Override
	public E set(int index, E element){
		if(element == null) { return null; }
		if(index < 0 || index >= size) { return null; }
		current = first.findNode(index);
		current.element = element;
		return current.element;
	}

	/**
	 * Metodas pridedantis naują elementą
	 * @param e elementas
	 * @param ind indeksas
	 */
	@Override
	public void add(E e, int ind){
		if(e == null) return;
		if(ind < 0 || ind >= size) return;
		Node<E> elem = new Node<E>(e, first.findNode(ind));
		if(ind == 0){
			first = elem;
		}
		else{
			first.findNode(ind-1).next = elem;
		}
		size++;

	}

	/**
	 * Metodas patikrinantis ar objektas yra masyve
	 * @param o objektas
	 * @return
	 */
	@Override
	public boolean contains(Kompiuteris o){
		for(Node<E> d1 = first; d1 != null; d1 = d1.next){
			if(d1.element == o){
				return true;
			}
		}
		return false;
	}
        
	/**
	 * Sukuria iteratoriaus objektą sąrašo elementų peržiūrai
	 *
	 * @return iteratoriaus objektą
	 */
	@Override
	public Iterator<E> iterator() {
		return new ListIteratorKTU();
	}
        	/**
	 * MinMax rikiavimo metodas be komparatoriaus
	 */
	public void sortMinMax(){
		for(Node<E> d1 = first; d1 != null; d1=d1.next){
			Node<E> maxv = d1;

			for(Node<E> d2 = d1; d2 != null; d2 = d2.next){
				if(d2.element.compareTo(maxv.element) < 0){
					maxv = d2;
				}
			}
			E temp = d1.element;
			d1.element = maxv.element;
			maxv.element = temp;
		}
	}

	/**
	 * MinMax rikiavimo metodas su komparatorium
	 * @param c komparatorius
	 */
	public void sortMinMax(Comparator<E> c){
		for(Node<E> d1 = first; d1 != null; d1=d1.next){
			Node<E> maxv = d1;

			for(Node<E> d2 = d1; d2 != null; d2 = d2.next){
				if(c.compare(d2.element, maxv.element) < 0){
					maxv = d2;
				}
			}
			E temp = d1.element;
			d1.element = maxv.element;
			maxv.element = temp;
		}
	}
	//------------------------------------------

	/**
	 * Iteratoriaus metodų klasė
	 */
	private class ListIteratorKTU implements Iterator<E> {

		private Node<E> iterPosition;

		ListIteratorKTU() {
			iterPosition = first;
		}

		@Override
		public boolean hasNext() {
			return iterPosition != null;
		}

		@Override
		public E next() {
			E d = iterPosition.element;
			iterPosition = iterPosition.next;
			return d;
		}
	}

	/**
	 * Vidinė mazgo klasė
	 *
	 * @param <E> mazgo duomenų tipas
	 */
	private static class Node<E> {

		private E element;    // ji nematoma už klasės ListKTU ribų
		private Node<E> next; // next - kaip įprasta - nuoroda į kitą mazgą

		Node(E e, Node<E> next) { //mazgo konstruktorius
			this.element = e;
			this.next = next;
		}

		/**
		 * Suranda sąraše k-ąjį mazgą
		 *
		 * @param k ieškomo mazgo indeksas (prasideda nuo 0)
		 * @return surastas mazgas
		 */
		public Node<E> findNode(int k) {
			Node<E> e = this;
			for (int i = 0; i < k; i++) {
				e = e.next;
			}
			return e;
		}
	} // klasės Node pabaiga
}
