import java.util.Random;

public class MultiplicativeShiftingHash implements HashFactory<Long> {
    public MultiplicativeShiftingHash() {}

    @Override
    public HashFunctor<Long> pickHash(int k) {
        return new Functor(k);
    }

    public class Functor implements HashFunctor<Long> {
        final public static long WORD_SIZE = 64;
        final private long a;
        final private long k;

        public Functor(int k){
            Random rnd = new Random();
            a = rnd.nextLong();
            this.k = k;
        }

        @Override
        public int hash(Long key) {
            return (int)(a*key)>>>(WORD_SIZE-k);
        }

        public long a() {
            return a;
        }

        public long k() {
            return k;
        }
    }
}
