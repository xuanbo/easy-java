package tk.fishfish.easyjava.util;

/**
 * 根据文件魔数判断文件实际类型，防止修改后缀误判
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public enum FileType {

    /**
     * jpg、jpeg
     */
    JPEG("JPEG", "FFD8FF"),

    /**
     * png
     */
    PNG("PNG", "89504E47"),

    /**
     * xml
     */
    XML("XML", "3C3F786D6C"),

    /**
     * html
     */
    HTML("HTML", "68746D6C3E"),

    /**
     * pdf
     */
    PDF("PDF", "255044462D312E"),

    /**
     * zip、jar
     */
    ZIP("ZIP", "504B0304"),

    /**
     * rar
     */
    RAR("RAR", "52617221"),

    ;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件魔数
     */
    private String value;

    FileType(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

}
