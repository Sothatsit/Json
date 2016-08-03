package net.sothatsit.json.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputReadStream implements CharReadStream {

    private InputStreamReader reader;
    private char lastChar;
    private char[] buffer;
    private int bufferLength;
    private int bufferIndex;
    private int index;

    private int character;
    private int line;

    public InputReadStream(InputStream is) {
        this.reader = new InputStreamReader(is);
        this.buffer = new char[1024];
        this.bufferLength = 0;

        this.lastChar = 0;
        this.bufferIndex = -1;
        this.index = 0;

        try {
            this.readNextBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readNextBuffer() throws IOException {
        if(this.bufferIndex != -1) {
            this.lastChar = this.buffer[1023];
        }

        this.bufferLength = this.reader.read(this.buffer, 0, 1024);

        this.bufferIndex++;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        if(this.index < this.bufferLength) {
            return true;
        } else if(this.bufferLength == 1024) {
            try {
                this.readNextBuffer();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return this.bufferLength != -1;
        } else {
            return false;
        }
    }

    @Override
    public char next() {
        if(this.hasNext()) {
            if((this.bufferIndex > 0 && this.index == 0 && lastChar == '\n')
                    || (this.index > 0 && this.buffer[this.index - 1] == '\n')) {
                this.line++;
                this.character = 0;
            } else {
                this.character++;
            }

            return this.index == -1 ? lastChar : this.buffer[this.index++];
        } else {
            throw new IndexOutOfBoundsException("Cannot next past end of stream");
        }
    }

    @Override
    public void back() {
        this.index--;

        if(this.index < 0 && this.bufferIndex != 0) {
            throw new UnsupportedOperationException("Cannot back past start of stream");
        } else if(this.index < -1) {
            throw new UnsupportedOperationException("Cannot back more than once in a row");
        }
    }

    @Override
    public String getLocation() {
        StringBuilder builder = new StringBuilder();

        int left = 2;

        for(int index = 0; index < 3; index++) {
            if(this.index < 0) {
                break;
            }

            this.back();

            left++;
        }

        for(int i=0; i < left; i++) {
            if(!this.hasNext()) {
                break;
            }

            char c = this.next();

            if(c == '\n') {
                break;
            }

            builder.append(c);
        }

        return "(Character " + character + " Line " + line + ") \"" + builder + "\"";
    }
}
