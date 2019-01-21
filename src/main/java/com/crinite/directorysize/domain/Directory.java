package com.crinite.directorysize.domain;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Michael Crinite (michaelcrinite)
 * @version 2018-12-02
 */
public class Directory {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    private String name;
    private String path;
    private String absolutePath;
    private Long size;

    private List<Directory> children = new ArrayList<>();
    private Directory parent;
    private File javaDirectory;

    public String getDisplaySize() {
        double division = 1024.0;
        String descriptor = " B";

        if (size != null) {
            double wholeNumber = size.doubleValue();
            if (size >= division) {
                wholeNumber /= division;
                descriptor = "KB";
                if (wholeNumber >= division) {
                    wholeNumber /= division;
                    descriptor = "MB";
                    if (wholeNumber >= division) {
                        wholeNumber /= division;
                        descriptor = "GB";
                        if (wholeNumber >= division) {
                            wholeNumber /= division;
                            descriptor = "TB";
                        }
                    }
                }
            }
            return DECIMAL_FORMAT.format(wholeNumber) + " " + descriptor;
        }
        return "Unknown";
    }

    private int getIntSize(){
        if(size != null) {
            return getSize().intValue();
        }
        return -1;
    }

    public void sortChildren() {
        children.sort(Comparator.comparingInt(Directory::getIntSize).reversed());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public List<Directory> getChildren() {
        return children;
    }

    public void setChildren(List<Directory> children) {
        this.children = children;
    }

    public void addChild(Directory child) {
        this.children.add(child);
    }

    public Directory getParent() {
        return parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public File getJavaDirectory() {
        return javaDirectory;
    }

    public void setJavaDirectory(File javaDirectory) {
        this.javaDirectory = javaDirectory;
    }

    @Override
    public String toString() {
        return "Directory{" +
                "path='" + path + '\'' +
                ", size=" + size +
                '}';
    }
}
