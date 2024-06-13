/**
 * @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 09 23
 * Koreguota: Aleksas Riškus, MIK, 2016
 *
 * Tai interfeisas, apibrėžiantis kelias pagrindines vienkrypčio sąrašo operacijas.
 *
 * Užduotis: 1. Peržiūrėkite ir išsiaiškinkite pateiktus metodus.
 *			 2. Papildyti naujais (pasirinktais iš List interfeiso) metodais. 
 *****************************************************************************
 */
package studijosKTU;

import Lab2Zilinskas.Kompiuteris;

public interface ListADT<E> {

	/**
	 * Appends the specified element to the end of this list.
	 *
	 * @param e element to be appended to this list
	 * @return true, if operation is Ok
	 */
	boolean add(E e);

	/**
	 * Returns the number of elements in this list.
	 */
	int size();

	/**
	 * @return true if this list contains no elements.
	 */
	boolean isEmpty();

	/**
	 * Removes all of the elements from this list.
	 */
	void clear();

	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @param k index of the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException {@inheritDoc}
	 */
	E get(int k);

	/**
	 * Atitinka iteratoriaus metodą next (List tokio metodo neturi)
	 * @return kitą reikšmę.
	 */
	E getNext();
	
	/**
	 * Returns an array containing all of the elements in this list in proper sequence (from first to last element).
	 * @return an array containing all of the elements
	 */
	Object[] toArray();

        E set(int index, E element);

	E remove(int k);

	void add(E e, int ind);

	boolean contains(Kompiuteris o);
}
