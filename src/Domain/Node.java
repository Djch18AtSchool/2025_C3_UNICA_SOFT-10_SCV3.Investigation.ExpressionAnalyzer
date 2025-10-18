package Domain;

/**
 * Define la estructura del Nodo para la Pila.
 * De acuerdo a la solicitud, almacena un 'char' que representa
 * un operador de la expresión aritmética.
 */
public class Node <T> {
    private T value;
    private Node <T> next;

    /**
     * Constructor del Nodo.
     * @param value El carácter a almacenar.
     */
    public Node(T value) {
        this.value = value;
        this.next = null;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}