package tk.fishfish.easyjava.jvm;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * 自定义ClassLoader
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class MyClassLoader extends URLClassLoader {

    public MyClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

}
