package com.service;

import com.model.DataModel;

import java.io.OutputStream;

public interface service {
    public void save( DataModel dataModel);
    public void getFile(OutputStream ops);
}
