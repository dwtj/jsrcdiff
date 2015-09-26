import java.util.HashMap;

public class Basics
{
    public static void method0() { }
    private static void method1() { }

    int method1(int i) { }
    int method2(int j) { }
    String method3(int i, double d) { }
    double method4(String j) { }
    double method5(Map<String,String> map) { }

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
}
