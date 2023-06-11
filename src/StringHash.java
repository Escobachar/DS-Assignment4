import java.util.Random;

public class StringHash implements HashFactory<String> {

    public StringHash() {
    }

    @Override
    public HashFunctor<String> pickHash(int k) {
        return new Functor(k);
    }

    public class Functor implements HashFunctor<String> {
        final private HashFunctor<Integer> carterWegmanHash;
        final private int c;
        final private int q;

        public Functor(int k){
            Random rnd = new Random();
            int temp = rnd.nextInt((Integer.MAX_VALUE/2 )+ 1,Integer.MAX_VALUE);
            HashingUtils utils = new HashingUtils();
            while(!utils.runMillerRabinTest(temp,10))
                temp = rnd.nextInt((Integer.MAX_VALUE/2 )+ 1,Integer.MAX_VALUE);
            q = temp;
            c = rnd.nextInt(2,q);
            carterWegmanHash = (new ModularHash()).pickHash(k);
        }

        @Override
        public int hash(String key) {
            char[] arr = key.toCharArray();
            int sum = 0;
            for (int i = 0; i < arr.length; i++) {
                sum += HashingUtils.mod((arr[i]*(HashingUtils.mod((int) Math.pow(c,arr.length-i),q))),q);
            }
            return carterWegmanHash.hash(sum);
        }

        public int c() {
            return c;
        }

        public int q() {
            return q;
        }

        public HashFunctor carterWegmanHash() {
            return carterWegmanHash;
        }
    }
}
