import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;

abstract public class AbstractSkipList {
    final protected Node head;
    final protected Node tail;
    protected int defaultLength;


    public AbstractSkipList() {
        head = new Node(Integer.MIN_VALUE);
        tail = new Node(Integer.MAX_VALUE);
        increaseHeight();
        this.defaultLength=0;
    }

    public void increaseHeight() {
        head.addLevel(tail, null);
        tail.addLevel(null, head);
        head.setLength(head.height,defaultLength);
    }

    abstract Node find(int key);

    abstract public int generateHeight();

    public Node search(int key) {
        Node curr = find(key);

        return curr.key() == key ? curr : null;
    }

    public Node insert(int key) {
        //
        int sum=0;
        int nextLevel=0;
        //
        int nodeHeight = generateHeight();

        while (nodeHeight > head.height()) {
            increaseHeight();
        }

        Node prevNode = find(key);
        if (prevNode.key() == key) {
            return null;
        }

        Node newNode = new Node(key);

        for (int level = 0; level <= nodeHeight && prevNode != null; ++level) {
            Node nextNode = prevNode.getNext(level);
            newNode.addLevel(nextNode, prevNode);
            prevNode.setNext(level, newNode);
            nextNode.setPrev(level, newNode);
            //
            newNode.setLength(level,prevNode.getLength(level)+1-sum);
            prevNode.setLength(level,sum);
            //
            while (prevNode != null && prevNode.height() == level) {
                //
                sum+=prevNode.getLength(level);
                //
                prevNode = prevNode.getPrev(level);

            }
        }
        //
        while(nextLevel<=head.height()) {
            nextLevel++;
            if (prevNode.height() >= nextLevel) {
                prevNode.setLength(nextLevel,prevNode.getLength(nextLevel)+1);
            } else {
                while (prevNode != null && prevNode.height() < nextLevel) {
                    prevNode = prevNode.getPrev(nextLevel - 1);
                }
                if (prevNode != null)
                    prevNode.setLength(nextLevel, prevNode.getLength(nextLevel) + 1);
            }
        }
        this.defaultLength++;
        //
        return newNode;
    }

    public boolean delete(Node node) {
        //
        if (find(node.key).key != node.key || find(node.key)== head ) {
            return false;
        }
        //
        for (int level = 0; level <= node.height(); ++level) {
            Node prev = node.getPrev(level);
            Node next = node.getNext(level);
            prev.setNext(level, next);
            next.setPrev(level, prev);
            //
            prev.setLength(level,prev.getLength(level)+node.getLength(level));
            while (prev != null && prev.height() == level) {
                prev = prev.getPrev(level);
            }
            //
        }

        return true;
    }

    public int predecessor(Node node) {
        return node.getPrev(0).key();
    }

    public int successor(Node node) {
        return node.getNext(0).key();
    }

    public int minimum() {
        if (head.getNext(0) == tail) {
            throw new NoSuchElementException("Empty Linked-List");
        }

        return head.getNext(0).key();
    }

    public int maximum() {
        if (tail.getPrev(0) == head) {
            throw new NoSuchElementException("Empty Linked-List");
        }

        return tail.getPrev(0).key();
    }

    private void levelToString(StringBuilder s, int level) {
        s.append("H    ");
        Node curr = head.getNext(level);

        while (curr != tail) {
            s.append(curr.key);
            s.append("    ");
            
            curr = curr.getNext(level);
        }

        s.append("T\n");
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int level = head.height(); level >= 0; --level) {
            levelToString(str, level);
        }

        return str.toString();
    }

    public static class  Node {
        final private List<Node> next;
        final private List<Node> prev;
        private int height;
        final private int key;
        final private List<Integer> length;

        public Node(int key) {
            next = new ArrayList<>();
            prev = new ArrayList<>();
            this.height = -1;
            this.key = key;
            this.length = new ArrayList<>();
        }

        public Node getPrev(int level) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            return prev.get(level);
        }

        public Node getNext(int level) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            return next.get(level);
        }

        public void setNext(int level, Node next) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            this.next.set(level, next);
        }

        public void setPrev(int level, Node prev) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            this.prev.set(level, prev);
        }

        public void addLevel(Node next, Node prev) {
            ++height;
            this.next.add(next);
            this.prev.add(prev);
        }

        public int height() { return height; }
        public int key() { return key; }


        public void setLength(int level, int length) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }
            while (level >= this.length.size()) {
                this.length.add(0);
            }
            this.length.set(level, length);
        }
        public Integer getLength(int level) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }
            if (level > this.length.size()) {
                return 0;
            }
            return this.length.get(level);
        }


    }
}
