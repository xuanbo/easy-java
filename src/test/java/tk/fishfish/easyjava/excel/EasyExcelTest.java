package tk.fishfish.easyjava.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * easyexcel测试
 *
 * @author 奔波儿灞
 * @since 1.0
 */
public class EasyExcelTest {

    @Test
    public void customConverter() {
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> row = new HashMap<>();
        row.put("name", "名字");
        row.put("age", 10);
        row.put("time", new Timestamp(System.currentTimeMillis()));
        row.put("date", new Date());
        row.put("sex", 1);
        data.add(row);

        EasyExcel.write("demo.xlsx")
                .head(head(data))
                .registerConverter(new TimestampConverter())
                .sheet("data")
                .doWrite(data(data));
    }

    private List<List<String>> head(List<Map<String, Object>> data) {
        return data.stream()
                .findFirst()
                .map(Map::keySet)
                .map(set -> set.stream().map(Collections::singletonList).collect(Collectors.toList()))
                .orElseGet(Collections::emptyList);
    }

    private List<List<Object>> data(List<Map<String, Object>> data) {
        return data.stream()
                .map(Map::values)
                .map(ArrayList::new)
                .collect(Collectors.toList());
    }

}
