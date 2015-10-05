import java.util.HashMap;

public class Basics
{
    private int i, j, x=6;
    private final String s = new String("hello universe");

    public static final HashMap<String, String> mapXXX = null;
    public final HashMap<String, String> map1 = null;
    public static HashMap<String, String> map2;
    public HashMap<String, String> map3;
    private HashMap<String, String> map4;

    public int[] intArr1 = {0, 1};
    public int intArr3[] = {3, 4, 5};

    public int[][] intDoubleArr1;
    public int intDoubleArr2[][];
    public int[][][][][][][] intDeepArr1;
    public double intDeepArr2[][][][][][];
    private String stringDoubleArr[][] = {{"foo", "bar"}, {new String(), new String()}};

    public static void method0()
    {
        System.out.println("Java is an ugly language."); 
    }

    private static void method1()
    {
        System.out.println("I feel like an idiot when writing Java."); 
    }

    double method2(int i)
    {
        return i+1; 
    }

    int method3(double j) {
        return j + 31 - 17; 
    }

    String method4(int i, double d, String s) {
        return new String(i + " :D " + d); 
    }

    double method7(String j)
    {
        return Double.parseDouble(j);
    }

    double method5(Map<String,String> map) {
        return 0.314;  
    }
}
