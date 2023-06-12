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
        Node pos = head;
        int rank = 0;
        for (int i= pos.height(); i >=0; i--) {
            while((pos.getNext(i)!=null )&&(pos.getNext(i).key()<=val))
            {
                rank+= pos.getLength(i);
                pos = pos.getNext(i);
            }
            if(pos.key()==val)
            {
                break;
            }


        }
        return rank;
    }

    public int select(int index) {

        Node pos =head;
        int si = index;
        for (int i=pos.height() ; i >=0 && si > 0; i--) {
            while((pos.getNext(i)!=null)&&(si - pos.getLength(i)>0))
            {
                si = si - pos.getLength(i);
                pos = pos.getNext(i);
            }
            if(si - pos.getLength(i)==0)
            {
                si = si - pos.getLength(i);
                pos = pos.getNext(i);
                break;
            }

        }

        return pos.key();
    }
}
