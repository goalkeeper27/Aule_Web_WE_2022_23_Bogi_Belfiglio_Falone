package it.univaq.project.aule_web.controller;

/**
 *
 * @author Giuseppe Della Penna
 *
 * Una banale classe contenitore
 * per coppie di valori.
 * Vengono usati i generics per
 * rendere la classe parametrica
 * rispetto al tipo dei due valori.
 * 
 * @param <S>
 * @param <T>
 *
 */
public class Pair<S,T> {
    private S first;
    private T second;

    public Pair(S first, T second) {
        this.first=first;
        this.second=second;
    }

    /**
     * @return the first
     */
    public S getFirst() {
        return first;
    }

    /**
     * @param first the first to set
     */
    public void setFirst(S first) {
        this.first = first;
    }

    /**
     * @return the second
     */
    public T getSecond() {
        return second;
    }

    /**
     * @param second the second to set
     */
    public void setSecond(T second) {
        this.second = second;
    }

}
