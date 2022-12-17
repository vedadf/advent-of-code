package org.example.TaskSeven;

import java.util.ArrayList;
import java.util.List;

public class Folder {
    public String getName() {
        return name;
    }

    String name;

    int size = 0;

    Folder parent;
    List<Folder> folders = new ArrayList<>();
    List<MyFile> files = new ArrayList<>();

    public Folder() {}

    public Folder(String name, Folder parent) {
        this.name = name;
        this.parent = parent;
    }

    public void addFolder(Folder folder) {
        folders.add(folder);
    }

    public void addFile(MyFile file) {
        files.add(file);
    }

    public List<Folder> getFolders() {
        return folders;
    }

    public List<MyFile> getFiles() {
        return files;
    }

    public Folder getParent() {
        return parent;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
