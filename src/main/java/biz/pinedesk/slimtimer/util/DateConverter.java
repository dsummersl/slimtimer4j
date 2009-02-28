package biz.pinedesk.slimtimer.util;

import com.thoughtworks.xstream.converters.*;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.text.*;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateConverter implements Converter {

    private String format;

    public DateConverter(String format) {
        super();
        this.format = format;
    }

    public boolean canConvert(Class clazz) {
        return Date.class.isAssignableFrom(clazz);
    }

    public void marshal(Object value, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
        Date date = (Date) value;
        writer.setValue(dateToString(date));
    }

    public Object unmarshal(HierarchicalStreamReader reader,
                            UnmarshallingContext context) {
        return toDate(reader.getValue());
    }

    public Date toDate(String value) {
        GregorianCalendar calendar = new GregorianCalendar();
        DateFormat formatter = new SimpleDateFormat(format);
        try {
            calendar.setTime(formatter.parse(value));
        }
        catch (ParseException e) {
            throw new ConversionException(e.getMessage(), e);
        }
        return calendar.getTime();
    }

    public String dateToString(Date date) {
        DateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
}

