import java.util.HashMap;

public class Basics
{
    private int i = 0, j, x=5;
    private final String s = new String("hello world");

    public static final HashMap<String, String> map0 = null;
    public final HashMap<String, String> map1 = null;
    public static HashMap<String, String> map2;
    public HashMap<String, String> map3;
    private HashMap<String, String> map4;

    public int[] intArr1 = {0, 1, 2};
    public int intArr2[] = {3, 4, 5};

    public int[][] intDoubleArr1;
    public int intDoubleArr2[][];
    public int[][][][][][] intDeepArr1;
    public int intDeepArr2[][][][][][];
    public String stringDoubleArr[][] = {{"foo", "bar"}, {new String(), new String()}};

    public static void method0()
    {
        System.out.println("Java is a pretty language."); 
    }

    private static void method1()
    {
        System.out.println("I have high self esteem when writing Java."); 
    }

    int method2(int i)
    {
        return i+1; 
    }

    int method3(int j) {
        return j + 31 - 17; 
    }

    String method4(int i, double d) {
        return new String(i + " :D " + d); 
    }

    double method5(String j)
    {
        return Double.parseDouble(j);
    }

    double method6(Map<String,String> map)
    {
        return 0.314;  
    }
}
