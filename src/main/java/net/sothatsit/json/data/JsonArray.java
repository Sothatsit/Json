package net.sothatsit.json.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonArray extends AbstractJsonElement {

    private Builder builder = new Builder();
    private List<JsonElement> elements = new ArrayList<>();

    public JsonArray() {
        super("JSON Array");
    }

    public JsonArray(List<JsonElement> elements) {
        this();

        this.elements.addAll(elements);
    }

    public JsonArray(JsonElement... elements) {
        this(Arrays.asList(elements));
    }

    @Override
    public List<Object> getValue() {
        List<Object> list = new ArrayList<>();

        for(JsonElement element : this.elements) {
            list.add(element.getValue());
        }

        return list;
    }

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public JsonArray asArray() {
        return this;
    }

    public int size() {
        return this.elements.size();
    }

    public JsonElement get(int index) {
        return this.elements.get(index);
    }

    public boolean add(Object value) {
       return this.add(JsonElement.fromObject(value));
    }

    public boolean add(JsonElement element) {
        return this.elements.add(element);
    }

    public JsonElement remove(int index) {
        return this.elements.remove(index);
    }

    public boolean remove(Object value) {
        return this.remove(JsonElement.fromObject(value));
    }

    public boolean remove(JsonElement element) {
        return this.elements.remove(element);
    }

    public boolean contains(Object value) {
        return this.contains(JsonElement.fromObject(value));
    }

    public boolean contains(JsonElement element) {
        return this.elements.contains(element);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof JsonArray) {
            JsonArray other = (JsonArray) obj;

            if(this.size() != other.size()) {
                return false;
            }

            for(int index = 0; index < this.size(); index++) {
                if(!this.get(index).equals(other.get(index))) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public Builder builder() {
        return this.builder;
    }

    public static Builder newBuilder() {
        return new JsonArray().builder();
    }

    public static JsonArray build(Object... values) {
        return newBuilder().add(values).finish();
    }

    public static JsonArray build(JsonElement... elements) {
        return newBuilder().add(elements).finish();
    }

    public class Builder {

        private Builder() {

        }

        public Builder add(JsonElement... elements) {
            for(JsonElement element : elements) {
                JsonArray.this.add(element);
            }
            return this;
        }

        public Builder add(Object... values) {
            for(Object value : values) {
                JsonArray.this.add(value);
            }
            return this;
        }

        public Builder remove(JsonElement... elements) {
            for(JsonElement element : elements) {
                JsonArray.this.remove(element);
            }
            return this;
        }

        public Builder remove(Object... values) {
            for(Object value : values) {
                JsonArray.this.remove(value);
            }
            return this;
        }

        public Builder remove(int... indexes) {
            for(int index : indexes) {
                JsonArray.this.remove(index);
            }
            return this;
        }

        public JsonArray finish() {
            return JsonArray.this;
        }

    }

}
