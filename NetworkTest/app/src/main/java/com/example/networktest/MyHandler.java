package com.example.networktest;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler {

    private String nodeName;
    private StringBuffer id;
    private StringBuffer name;
    private StringBuffer version;

    /**
     * 开始解析XML时调用
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        id = new StringBuffer();
        name = new StringBuffer();
        version = new StringBuffer();
    }

    /**
     *开始解析某个节点时调用
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //记住当前节点名字
        nodeName = localName;
    }

    /**
     * 获取某个节点内容时调用（在获取内容时characters()方法会被调用多次一些换行符也会被当成内容解析出来，所以要进行处理）
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if ("id".equals(nodeName)){
            id.append(ch,start,length);
        }else if ("name".equals(nodeName)){
            name.append(ch,start,length);
        }else if ("version".equals(nodeName)){
            version.append(ch,start,length);
        }
    }

    /**
     * 完成解析某个节点时调用
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("app".equals(localName)){
            Log.d("XML_data", "id is "+id.toString().trim());
            Log.d("XML_data", "name is "+name.toString().trim());
            Log.d("XML_data", "version is "+version.toString().trim());
            //清空StringBuffer
            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
        }
    }

    /**
     *解析完成时调用
     * @throws SAXException
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}
