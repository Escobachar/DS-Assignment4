import java.util.Random;

public class ModularHash implements HashFactory<Integer> {
    public ModularHash() {}

    @Override
    public HashFunctor<Integer> pickHash(int k) {
        return new Functor(k);
    }

    public class Functor implements HashFunctor<Integer> {
        final private int a;
        final private int b;
        final private long p;
        final private int m;
        private HashingUtils utils;

        public Functor(int k) {
            if(k >= 31)
                throw new IllegalArgumentException("k must be lower than 31");
            Random rnd = new Random();
            a = rnd.nextInt(1,Integer.MAX_VALUE);
            b = rnd.nextInt(Integer.MAX_VALUE);
            this.utils = new HashingUtils();
            long temp = utils.genLong((long)(Integer.MAX_VALUE)+1,Long.MAX_VALUE);
            while(!utils.runMillerRabinTest(temp,10)){
                temp = utils.genLong((long)(Integer.MAX_VALUE)+1,Long.MAX_VALUE);
            }
            p = temp;
            m = (int)Math.pow(2,k);
        }

        @Override
        public int hash(Integer key) {
            return (int) HashingUtils.mod(HashingUtils.mod(a * key + b,p),m);
        }

        public int a() { return a; }

        public int b() {
            return b;
        }

        public long p() {
            return p;
        }

        public int m() {
            return m;
        }
    }
}
