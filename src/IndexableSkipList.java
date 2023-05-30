public class IndexableSkipList extends AbstractSkipList {
    final protected double probability;
    public IndexableSkipList(double probability) {
        super();
        this.probability = probability;
    }

    @Override
    public Node find(int val) {
        Node p = head;
        for (int i = head.height(); i >= 0; i--) {
            while(p.getNext(i)!=null && p.getNext(i).key()<=val) {
                p = p.getNext(i);
            }
        }
        return p;
    }

    @Override
    public int generateHeight() {
        int hight=0;
        double random = Math.random();
        while(random>=probability)
        {
            hight++;
            random = Math.random();
        }
        return hight;
    }

    public int rank(int val) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public int select(int index) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }
}
