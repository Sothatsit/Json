package net.sothatsit.json.data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonObject extends AbstractJsonElement {

    private Builder builder = new Builder();
    private Map<String, JsonElement> keyValueMap = new LinkedHashMap<>();

    public JsonObject() {
        super("JSON Object");
    }

    public JsonObject(Map<String, JsonElement> keyValueMap) {
        this();

        this.keyValueMap.putAll(keyValueMap);
    }

    @Override
    public Map<String, Object> getValue() {
        Map<String, Object> map = new HashMap<>();

        for(Map.Entry<String, JsonElement> entry : this.keyValueMap.entrySet()) {
            map.put(entry.getKey(), entry.getValue().getValue());
        }

        return map;
    }

    @Override
    public boolean isObject() {
        return true;
    }

    @Override
    public JsonObject asObject() {
        return this;
    }

    public void set(String key, Object value) {
        this.set(key, JsonElement.fromObject(value));
    }

    public void set(String key, JsonElement element) {
        if(key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        if(element == null) {
            throw new IllegalArgumentException("element cannot be null");
        }

        this.keyValueMap.put(key, element);
    }

    public JsonElement remove(String key) {
        if(key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        return this.keyValueMap.remove(key);
    }

    public JsonElement get(String key) {
        if(key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        return this.keyValueMap.get(key);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof JsonObject && this.keyValueMap.equals(((JsonObject) obj).keyValueMap);
    }

    public Builder builder() {
        return this.builder;
    }

    public static Builder newBuilder() {
        return new JsonObject().builder();
    }

    public class Builder {

        private Builder() {

        }

        public Builder set(String key, Object value) {
            JsonObject.this.set(key, value);
            return this;
        }

        public Builder set(String key, JsonElement element) {
            JsonObject.this.set(key, element);
            return this;
        }

        public Builder remove(String... keys) {
            for(String key : keys) {
                JsonObject.this.remove(key);
            }
            return this;
        }

        public JsonObject finish() {
            return JsonObject.this;
        }

    }

}
