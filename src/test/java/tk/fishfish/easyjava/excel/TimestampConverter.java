package tk.fishfish.easyjava.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.DateUtils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Timestamp转换
 *
 * @author 奔波儿灞
 * @see com.alibaba.excel.converters.date.DateStringConverter
 * @since 1.0
 */
public class TimestampConverter implements Converter<Timestamp> {

    @Override
    public Class supportJavaTypeKey() {
        return Timestamp.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Timestamp convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                       GlobalConfiguration configuration) throws Exception {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            Date date = DateUtils.parseDate(cellData.getStringValue(), null);
            return new Timestamp(date.getTime());
        } else {
            Date date = DateUtils.parseDate(cellData.getStringValue(), contentProperty.getDateTimeFormatProperty().getFormat());
            return new Timestamp(date.getTime());
        }
    }

    @Override
    public CellData convertToExcelData(Timestamp value, ExcelContentProperty contentProperty,
                                       GlobalConfiguration configuration) {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return new CellData(DateUtils.format(value, null));
        } else {
            return new CellData(DateUtils.format(value, contentProperty.getDateTimeFormatProperty().getFormat()));
        }
    }

}
