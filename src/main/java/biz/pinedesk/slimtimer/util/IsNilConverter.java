package biz.pinedesk.slimtimer.util;

import com.thoughtworks.xstream.converters.*;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class IsNilConverter implements Converter {
    private Converter parentConverter;

    public IsNilConverter(Converter parentConverter) {
        this.parentConverter = parentConverter;
    }

    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
        if (o == null) {
            writer.addAttribute("nil", "true");
        }
        else {
            parentConverter.marshal(o, writer, context);
        }
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        String nil = reader.getAttribute("nil");
        if (nil != null && nil.equalsIgnoreCase("true")) {
            return null;
        }
        return parentConverter.unmarshal(reader, context);
    }

    public boolean canConvert(Class aClass) {
        return parentConverter.canConvert(aClass);
    }
}
