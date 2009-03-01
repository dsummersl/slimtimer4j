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
