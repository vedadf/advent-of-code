package org.example.TaskSeven;

public class MyFile {
    String name;

    int size = 0;
    Folder parent;

    public MyFile() {}

    public MyFile(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
