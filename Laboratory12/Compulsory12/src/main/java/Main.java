import classinfo.MyClassLoader;

public class Main {
    public void testClassLoading() {
        MyClassLoader loader = new MyClassLoader();
        loader.loadClass("testclasses.StudentDemo");
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.testClassLoading();
    }
}
