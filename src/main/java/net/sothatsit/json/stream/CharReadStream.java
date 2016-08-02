package net.sothatsit.json.stream;

public interface CharReadStream {

    public boolean hasNext();

    public char next();

    public void back();

    public String getLocation();

}
