/**
 * Copyright 2009: Dane Summers<dsummersl@yahoo.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	 http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

