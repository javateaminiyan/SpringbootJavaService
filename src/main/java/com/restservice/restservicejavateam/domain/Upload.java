package com.restservice.restservicejavateam.domain;

import javax.persistence.*;

@Entity
@Table(name = "upload")
public class Upload {
    public Upload() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;


    @Column(name = "path")
    String path;

    @Column(name = "filename")
    private String filename;
    @Column(name = "fileType")
    private String fileType;



    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Upload( String filename, String fileType, String path,byte[] pic) {
        this.path = path;
        this.filename = filename;
        this.fileType = fileType;
        this.pic = pic;
    }

    @Lob
    @Column(name = "pic")
    private byte[] pic;

    public byte[] getPic() {
        return this.pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public Upload(String path, byte[] pic) {
        this.path = path;
        this.pic = pic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

//    public Upload(String path) {
//        this.path = path;
//    }
}
