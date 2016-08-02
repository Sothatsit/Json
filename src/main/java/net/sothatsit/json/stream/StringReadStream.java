package net.sothatsit.json.stream;

public class StringReadStream implements CharReadStream {

    private char[] chars;
    private int index;
    private int length;

    private int character;
    private int line;

    public StringReadStream(String value) {
        this.chars = value.toCharArray();
        this.index = 0;
        this.length = this.chars.length;

        this.character = 0;
        this.line = 1;
    }

    @Override
    public boolean hasNext() {
        return this.index < this.length;
    }

    @Override
    public char next() {
        if(index > 0 && this.chars[index - 1] == '\n') {
            this.line++;
            this.character = 0;
        }

        this.character++;

        return this.chars[this.index++];
    }

    @Override
    public void back() {
        index--;

        if(index < 0) {
            throw new ArrayIndexOutOfBoundsException("Cannot back past start of string");
        }
    }

    public String getLocation() {
        return "(Character " + character + " Line " + line + ")";
    }
}
