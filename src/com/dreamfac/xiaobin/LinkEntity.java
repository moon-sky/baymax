package com.dreamfac.xiaobin;

/**
 * 用以封装返回的link类内容
 * @text 提示语内容
 * @url 要打开的链接地址
 * @author Administrator
 *2014-9-16
 */
public class LinkEntity {
String text;
String url;
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}


}
