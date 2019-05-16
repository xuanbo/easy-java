package tk.fishfish.easyjava.annotation;

import java.io.Serializable;

/**
 * 没卵用的javabean
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class Some implements Serializable {

    private Long id;

    private String name;

    public Some() {
    }

    public Some(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Some{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
