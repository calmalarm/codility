package codility.muaddib_202204;

public class Solution {
    public static final int[][] TICKET = new int[][] { {30,25}, {7,7}, {1,2}, };
    public int last_day;
    public int[] a;
    public int[] x;
    public int[] next;
    public int[] next_7;
    public int[] next_30;
    public int[] sums_x;

    public Solution() {
    }

    public int solution(int[] A) {

        if (A.length==0) return 0;
        if (A[0]>0) {
            int subtrahend = A[0];
            for (int i=0; i<A.length; i++)  A[i] -= subtrahend;
        }
        a = A;
        last_day = A[A.length-1];
        x = new int[last_day+1];
        for (int i=0; i<x.length; i++)  x[i] = 0;
        for (int i=0; i<A.length; i++)  x[A[i]] = 1;

//        System.out.print("[ ");
//        for (int i=0; i<x.length; i++) System.out.print(x[i] + ",");
//        System.out.println(" ]");

        next = new int[last_day+1];
        int tmp = 0;
        for (int i=x.length-1; i>=0; i--) {
            if (x[i] == 0) tmp = 0;
            else tmp++;
            next[i] = tmp;
        }
//        System.out.print("[ ");
//        for (int i=0; i<x.length; i++) System.out.print(next[i] + ",");
//        System.out.println(" ]");

        next_7  = new int[last_day+1];
        next_30 = new int[last_day+1];
        int tmp_sum = 0;
        for (int j=0; j<7  && j < x.length; j++) tmp_sum += x[j];
        next_7[0]  = tmp_sum;
        for (int j=7; j<30 && j < x.length; j++) tmp_sum += x[j];
        next_30[0] = tmp_sum;
        for (int i=1; i<x.length; i++) {
//            tmp_sum = 0;
//            for (int j=0; j<7  && i+j < x.length; j++) tmp_sum += x[i+j];
//            next_7[i]  = tmp_sum;
//            for (int j=7; j<30 && i+j < x.length; j++) tmp_sum += x[i+j];
//            next_30[i] = tmp_sum;
            next_7[i] = next_7[i-1] - x[i-1];
            if (i+6 < x.length) next_7[i] += x[i+6];
            next_30[i] = next_30[i-1] - x[i-1];
            if (i+29 < x.length) next_30[i] += x[i+29];
        }

        sums_x = new int[x.length+31];
        for (int i=0; i<sums_x.length; i++) sums_x[i] = Integer.MAX_VALUE;

        int res = next_ticket(0, 0);

//        System.out.print("[ ");
//        for (int i=0; i<x.length; i++) System.out.print(sums_x[i] + ",");
//        System.out.println(" ]");

        return res;
    }

    public int next_ticket(int day, int sum/*, int ind_a*/) {
        if (day > last_day) return sum;
        if (day > 0) {
            if (sum < sums_x[day-1])
                sums_x[day-1] = sum;
            else
                return Integer.MAX_VALUE;
        }

        int[] result = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};

        for (int i=0; i<3; i++) {
            if (TICKET[i][0] == 30 && next_30[day] <= 12) continue;
            if (TICKET[i][0] <= 7 && next[day] >= 30) continue;
            if (TICKET[i][0] == 7 && next_7[day] <= 3) continue;
            if (TICKET[i][0] == 2 && next[day] > 3) continue;

            int next_day = day + TICKET[i][0];
            result[i] = sum + TICKET[i][1];

            while (next_day <= last_day && x[next_day]==0) next_day += 1;
//            while (next_day <= last_day && next_day > a[ind_a+1] ) {
//                ind_a++;
//            }
//            next_day = a[ind_a];
            result[i] = next_ticket(next_day, result[i] /*, ind_a*/);
        }

        return Math.min( Math.min(result[0],result[1]), result[2] );
    }
}
