package laborai.studijosktu;

import java.util.Comparator;

/**
 * Rikiuojamos objektų kolekcijos - aibės realizacija AVL-medžiu.
 *
 * @užduotis Peržiūrėkite ir išsiaiškinkite pateiktus metodus.
 *
 * @param <E> Aibės elemento tipas. Turi tenkinti interfeisą Comparable<T>, arba
 * per klasės konstruktorių turi būti paduodamas Comparator<T> klasės objektas.
 *
 * @author darius.matulis@ktu.lt
 */
public class AvlSetKTU<E extends Comparable<E>> extends BstSetKTU<E>
        implements SortedSetADT<E> {

    public AvlSetKTU() {
    }

    public AvlSetKTU(Comparator<? super E> c) {
        super(c);
    }

    /**
     * Aibė papildoma nauju elementu.
     *
     * @param element
     */
    @Override
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in add(E element)");
        }
        root = addRecursive(element, (AVLNode<E>) root);

    }

    /**
     * Privatus rekursinis metodas naudojamas add metode;
     *
     * @param element
     * @param node
     * @return
     */
    private AVLNode<E> addRecursive(E element, AVLNode<E> node) {
        if (node == null) {
            size++;
            return new AVLNode<>(element);
        }
        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.setLeft(addRecursive(element, node.getLeft()));
            if ((height(node.getLeft()) - height(node.getRight())) == 2) {
                int cmp2 = c.compare(element, node.getLeft().element);
                node = (cmp2 < 0) ? rightRotation(node) : doubleRightRotation(node);
            }
        } else if (cmp > 0) {
            node.setRight(addRecursive(element, node.getRight()));
            if ((height(node.getRight()) - height(node.getLeft())) == 2) {
                int cmp2 = c.compare(node.getRight().element, element);
                node = (cmp2 < 0) ? leftRotation(node) : doubleLeftRotation(node);
            }
        }
        node.height = Math.max(height(node.getLeft()), height(node.getRight())) + 1;
        return node;
    }

    /**
     * Pašalinamas elementas iš aibės.
     *
     * @param element
     */
    @Override
    public void remove(E element) {

        throw new UnsupportedOperationException("Studentams reikia realizuoti remove(E element)");
    }

    private AVLNode<E> removeRecursive(E element, AVLNode<E> n) {
        if (n == null) {
            return n;
        }
        // Medyje ieškomas šalinamas elemento mazgas;
        int cmp = c.compare(element, n.element);

        if (cmp < 0) {
            n.left = removeRecursive(element, n.getLeft());
        } else if (cmp > 0) {
            n.right = removeRecursive(element, n.getRight());
        } else if (n.left != null && n.right != null) {
            /* Atvejis kai šalinamas elemento mazgas turi abu vaikus.
             Ieškomas didžiausio rakto elemento mazgas kairiajame pomedyje.
             Galima kita realizacija kai ieškomas mažiausio rakto
             elemento mazgas dešiniajame pomedyje. Tam yra sukurtas
             metodas getMin(E element);
             */
            BstNode<E> nodeMax = getMax(n.left);
            /* Didžiausio rakto elementas (TIK DUOMENYS!) perkeliamas į šalinamo
             elemento mazgą. Pats mazgas nėra pašalinamas - tik atnaujinamas;
             */
            n.element = nodeMax.element;
            // Surandamas ir pašalinamas maksimalaus rakto elemento mazgas;
            n.left = removeMax(n.left);
            size--;
            // Kiti atvejai
        } else {
            n = (n.getLeft() != null) ? n.getLeft() : n.getRight();
            size--;
        }

        return n;
        //throw new UnsupportedOperationException("Studentams reikia realizuoti removeRecursive(E element, AVLNode<E> n)");
    }

//==============================================================================
// Papildomi privatūs metodai, naudojami operacijų su aibe realizacijai
// AVL-medžiu;
//==============================================================================
//==============================================================================
//         n2
//        /                n1
//       n1      ==>      /  \
//      /                n3  n2
//     n3
//==============================================================================
    private AVLNode<E> rightRotation(AVLNode<E> n2) {
        AVLNode<E> n1 = n2.getLeft();
        n2.setLeft(n1.getRight());
        n1.setRight(n2);
        n2.height = Math.max(height(n2.getLeft()), height(n2.getRight())) + 1;
        n1.height = Math.max(height(n1.getLeft()), height(n2)) + 1;
        return n1;
    }

    private AVLNode<E> leftRotation(AVLNode<E> n1) {
        AVLNode<E> n2 = n1.getRight();
        n1.setRight(n2.getLeft());
        n2.setLeft(n1);
        n1.height = Math.max(height(n1.getLeft()), height(n1.getRight())) + 1;
        n2.height = Math.max(height(n2.getRight()), height(n1)) + 1;
        return n2;
    }

//==============================================================================
//        n3               n3
//       /                /                n2
//      n1      ==>      n2      ==>      /  \
//       \              /                n1  n3
//        n2           n1
//==============================================================================     
    private AVLNode<E> doubleRightRotation(AVLNode<E> n3) {
        n3.left = leftRotation(n3.getLeft());
        return rightRotation(n3);
    }

    private AVLNode<E> doubleLeftRotation(AVLNode<E> n1) {
        n1.right = rightRotation(n1.getRight());
        return leftRotation(n1);
    }

    private int height(AVLNode<E> n) {
        return (n == null) ? -1 : n.height;
    }

    public int MedzioDydis() {
        BstNode first = this.getRoot();
        return height(first, 1);
    }
    private int height(BstNode parent, int previous_height) {
        if (parent.left == null && parent.right == null) {
            return previous_height;
        }
        if (parent.left != null && parent.right != null) {
            int left = height(parent.left, previous_height + 1);
            int right = height(parent.right, previous_height + 1);
            if (left > right) {
                return left;
            } else {
                return right;
            }
        }
        if (parent.left == null) {
            return height(parent.right, previous_height + 1);
        }
        if (parent.right == null) {
            return height(parent.left, previous_height + 1);
        }
        return 0;
    }
    /**
     * Vidinė kolekcijos mazgo klasė
     *
     * @param <N> mazgo elemento duomenų tipas
     */
    protected class AVLNode<N> extends BstNode<N> {

        protected int height;

        protected AVLNode(N element) {
            super(element);
            this.height = 0;
        }

        protected void setLeft(AVLNode<N> left) {
            this.left = (BstNode<N>) left;
        }

        protected AVLNode<N> getLeft() {
            return (AVLNode<N>) left;
        }

        protected void setRight(AVLNode<N> right) {
            this.right = (BstNode<N>) right;
        }

        protected AVLNode<N> getRight() {
            return (AVLNode<N>) right;
        }
    }
}
